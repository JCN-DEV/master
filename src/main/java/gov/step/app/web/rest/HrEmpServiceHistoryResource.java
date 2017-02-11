package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpServiceHistory;
import gov.step.app.domain.HrEmpServiceHistoryLog;
import gov.step.app.repository.HrEmpServiceHistoryLogRepository;
import gov.step.app.repository.HrEmpServiceHistoryRepository;
import gov.step.app.repository.search.HrEmpServiceHistorySearchRepository;
import gov.step.app.service.HrmConversionService;
import gov.step.app.service.constnt.HRMManagementConstant;
import gov.step.app.service.util.MiscFileInfo;
import gov.step.app.service.util.MiscFileUtilities;
import gov.step.app.web.rest.dto.HrmApprovalDto;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing HrEmpServiceHistory.
 */
@RestController
@RequestMapping("/api")
public class HrEmpServiceHistoryResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpServiceHistoryResource.class);

    @Inject
    private HrEmpServiceHistoryRepository hrEmpServiceHistoryRepository;

    @Inject
    private HrEmpServiceHistorySearchRepository hrEmpServiceHistorySearchRepository;

    @Inject
    private HrEmpServiceHistoryLogRepository hrEmpServiceHistoryLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpServiceHistorys -> Create a new hrEmpServiceHistory.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpServiceHistory> createHrEmpServiceHistory(@Valid @RequestBody HrEmpServiceHistory hrEmpServiceHistory) throws URISyntaxException {
        log.debug("REST request to save HrEmpServiceHistory : {}", hrEmpServiceHistory);
        if (hrEmpServiceHistory.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpServiceHistory", "idexists", "A new hrEmpServiceHistory cannot already have an ID")).body(null);
        }
        if(hrEmpServiceHistory.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpServiceHistory.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpServiceHistory.getGoDoc())
            .fileName(hrEmpServiceHistory.getGoDocName())
            .contentType(hrEmpServiceHistory.getGoDocContentType())
            .filePath(HRMManagementConstant.SERV_HISTORY_TOUR_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpServiceHistory.setGoDoc(new byte[1]);
        hrEmpServiceHistory.setGoDocName(goFile.fileName());

        HrEmpServiceHistory result = hrEmpServiceHistoryRepository.save(hrEmpServiceHistory);
        hrEmpServiceHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpServiceHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpServiceHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpServiceHistorys -> Updates an existing hrEmpServiceHistory.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpServiceHistory> updateHrEmpServiceHistory(@Valid @RequestBody HrEmpServiceHistory hrEmpServiceHistory) throws URISyntaxException {
        log.debug("REST request to update HrEmpServiceHistory : {}", hrEmpServiceHistory);
        if (hrEmpServiceHistory.getId() == null) {
            return createHrEmpServiceHistory(hrEmpServiceHistory);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpServiceHistory.getGoDoc())
            .fileName(hrEmpServiceHistory.getGoDocName())
            .contentType(hrEmpServiceHistory.getGoDocContentType())
            .filePath(HRMManagementConstant.SERV_HISTORY_TOUR_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpServiceHistory.setGoDoc(new byte[1]);
        hrEmpServiceHistory.setGoDocName(goFile.fileName());



        // Add LOG info for Approval Purpose.
        HrEmpServiceHistoryLog logInfo = new HrEmpServiceHistoryLog();
        HrEmpServiceHistory dbModelInfo = hrEmpServiceHistoryRepository.findOne(hrEmpServiceHistory.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpServiceHistoryLogRepository.save(logInfo);

        hrEmpServiceHistory.setLogId(logInfo.getId());
        hrEmpServiceHistory.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpServiceHistory result = hrEmpServiceHistoryRepository.save(hrEmpServiceHistory);
        hrEmpServiceHistorySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpServiceHistory", hrEmpServiceHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpServiceHistorys -> get all the hrEmpServiceHistorys.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpServiceHistory>> getAllHrEmpServiceHistorys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpServiceHistorys");
        Page<HrEmpServiceHistory> page = hrEmpServiceHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpServiceHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpServiceHistorys/:id -> get the "id" hrEmpServiceHistory.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpServiceHistory> getHrEmpServiceHistory(@PathVariable Long id) {
        log.debug("REST request to get HrEmpServiceHistory : {}", id);
        HrEmpServiceHistory hrEmpServiceHistory = hrEmpServiceHistoryRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpServiceHistory.getGoDocName())
            .contentType(hrEmpServiceHistory.getGoDocContentType())
            .filePath(HRMManagementConstant.SERV_HISTORY_TOUR_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpServiceHistory.setGoDoc(goFile.fileData());

        return Optional.ofNullable(hrEmpServiceHistory)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpServiceHistorys/:id -> delete the "id" hrEmpServiceHistory.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpServiceHistory(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpServiceHistory : {}", id);
        hrEmpServiceHistoryRepository.delete(id);
        hrEmpServiceHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpServiceHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpServiceHistorys/:query -> search for the hrEmpServiceHistory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpServiceHistorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpServiceHistory> searchHrEmpServiceHistorys(@PathVariable String query) {
        log.debug("REST request to search HrEmpServiceHistorys for query {}", query);
        return StreamSupport
            .stream(hrEmpServiceHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpServiceHistorys -> get all the hrEmpServiceHistorys.
     */
    @RequestMapping(value = "/hrEmpServiceHistorys/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpServiceHistory> getAllHrServiceHistoryInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpServiceHistorys by LoggedIn Employee");
        List<HrEmpServiceHistory> modelInfoList = hrEmpServiceHistoryRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpServiceHistory modelInfo : modelInfoList)
        {
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(modelInfo.getGoDocName())
                .contentType(modelInfo.getGoDocContentType())
                .filePath(HRMManagementConstant.SERV_HISTORY_TOUR_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            modelInfo.setGoDoc(goFile.fileData());
        }
        return modelInfoList;
    }

    /**
     * GET  /hrEmpServiceHistorysApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpServiceHistorysApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpServiceHistory POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpServiceHistory modelInfo = hrEmpServiceHistoryRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpServiceHistoryRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpServiceHistoryLog modelLog = hrEmpServiceHistoryLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpServiceHistoryLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpServiceHistoryLog modelLog = hrEmpServiceHistoryLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpServiceHistoryLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpServiceHistoryRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpServiceHistoryRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpServiceHistory", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpServiceHistorysApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpServiceHistorysApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpServiceHistory List : logStatus: {} ",logStatus);
        List<HrEmpServiceHistory> modelList = hrEmpServiceHistoryRepository.findAllByLogStatus(logStatus);

        HrEmpServiceHistoryLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpServiceHistory modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpServiceHistoryLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpServiceHistorysApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpServiceHistorysApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpServiceHistory Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpServiceHistory modelInfo = hrEmpServiceHistoryRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Education");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpServiceHistoryLog modelLog = hrEmpServiceHistoryLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }

    /**
     * GET  /hrEmpServiceHistorysByEmp/:empId -> get address list by empId.
     */
    @RequestMapping(value = "/hrEmpServiceHistorysByEmp/{empId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpServiceHistory> getServiceHistoryListByEmp(@PathVariable Long empId) {
        log.debug("REST service history by : empId: {} ",empId);
        List<HrEmpServiceHistory> empServiceHistoryList = hrEmpServiceHistoryRepository.findAllEmployee(empId);
        return empServiceHistoryList;
    }
}
