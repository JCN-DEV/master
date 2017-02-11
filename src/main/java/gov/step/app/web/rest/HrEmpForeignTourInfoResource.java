package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpForeignTourInfo;
import gov.step.app.domain.HrEmpForeignTourInfoLog;
import gov.step.app.repository.HrEmpForeignTourInfoLogRepository;
import gov.step.app.repository.HrEmpForeignTourInfoRepository;
import gov.step.app.repository.search.HrEmpForeignTourInfoSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HrEmpForeignTourInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpForeignTourInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpForeignTourInfoResource.class);

    @Inject
    private HrEmpForeignTourInfoRepository hrEmpForeignTourInfoRepository;

    @Inject
    private HrEmpForeignTourInfoSearchRepository hrEmpForeignTourInfoSearchRepository;

    @Inject
    private HrEmpForeignTourInfoLogRepository hrEmpForeignTourInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpForeignTourInfos -> Create a new hrEmpForeignTourInfo.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpForeignTourInfo> createHrEmpForeignTourInfo(@Valid @RequestBody HrEmpForeignTourInfo hrEmpForeignTourInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpForeignTourInfo : {}", hrEmpForeignTourInfo);
        if (hrEmpForeignTourInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpForeignTourInfo", "idexists", "A new hrEmpForeignTourInfo cannot already have an ID")).body(null);
        }
        if(hrEmpForeignTourInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpForeignTourInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpForeignTourInfo.getGoDoc())
            .fileName(hrEmpForeignTourInfo.getGoDocName())
            .contentType(hrEmpForeignTourInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.FOREIGN_TOUR_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpForeignTourInfo.setGoDoc(new byte[1]);
        hrEmpForeignTourInfo.setGoDocName(goFile.fileName());

        HrEmpForeignTourInfo result = hrEmpForeignTourInfoRepository.save(hrEmpForeignTourInfo);
        hrEmpForeignTourInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpForeignTourInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpForeignTourInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpForeignTourInfos -> Updates an existing hrEmpForeignTourInfo.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpForeignTourInfo> updateHrEmpForeignTourInfo(@Valid @RequestBody HrEmpForeignTourInfo hrEmpForeignTourInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpForeignTourInfo : {}", hrEmpForeignTourInfo);
        if (hrEmpForeignTourInfo.getId() == null) {
            return createHrEmpForeignTourInfo(hrEmpForeignTourInfo);
        }
        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpForeignTourInfo.getGoDoc())
            .fileName(hrEmpForeignTourInfo.getGoDocName())
            .contentType(hrEmpForeignTourInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.FOREIGN_TOUR_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpForeignTourInfo.setGoDoc(new byte[1]);
        hrEmpForeignTourInfo.setGoDocName(goFile.fileName());

        // Add LOG info for Approval Purpose.
        HrEmpForeignTourInfoLog logInfo = new HrEmpForeignTourInfoLog();
        HrEmpForeignTourInfo dbModelInfo = hrEmpForeignTourInfoRepository.findOne(hrEmpForeignTourInfo.getId());
        logInfo = conversionService.getForeignTourLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpForeignTourInfoLogRepository.save(logInfo);
        hrEmpForeignTourInfo.setLogId(logInfo.getId());
        hrEmpForeignTourInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpForeignTourInfo result = hrEmpForeignTourInfoRepository.save(hrEmpForeignTourInfo);
        hrEmpForeignTourInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpForeignTourInfo", hrEmpForeignTourInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpForeignTourInfos -> get all the hrEmpForeignTourInfos.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpForeignTourInfo>> getAllHrEmpForeignTourInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpForeignTourInfos");
        Page<HrEmpForeignTourInfo> page = hrEmpForeignTourInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpForeignTourInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpForeignTourInfos/:id -> get the "id" hrEmpForeignTourInfo.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpForeignTourInfo> getHrEmpForeignTourInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpForeignTourInfo : {}", id);
        HrEmpForeignTourInfo hrEmpForeignTourInfo = hrEmpForeignTourInfoRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpForeignTourInfo.getGoDocName())
            .contentType(hrEmpForeignTourInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.FOREIGN_TOUR_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpForeignTourInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(hrEmpForeignTourInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpForeignTourInfos/:id -> delete the "id" hrEmpForeignTourInfo.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpForeignTourInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpForeignTourInfo : {}", id);
        hrEmpForeignTourInfoRepository.delete(id);
        hrEmpForeignTourInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpForeignTourInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpForeignTourInfos/:query -> search for the hrEmpForeignTourInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpForeignTourInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpForeignTourInfo> searchHrEmpForeignTourInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpForeignTourInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpForeignTourInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpPublicationInfos -> get all the hrEmpPublicationInfos.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpForeignTourInfo> getAllHrPublicationInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpForeignTourInfos by LoggedIn Employee");
        List<HrEmpForeignTourInfo> modelInfoList = hrEmpForeignTourInfoRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpForeignTourInfo modelInfo : modelInfoList)
        {
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(modelInfo.getGoDocName())
                .contentType(modelInfo.getGoDocContentType())
                .filePath(HRMManagementConstant.FOREIGN_TOUR_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            modelInfo.setGoDoc(goFile.fileData());

        }
        return modelInfoList;
    }

    /**
     * GET  /hrEmpForeignTourInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpForeignTourInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpForeignTourInfo modelInfo = hrEmpForeignTourInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpForeignTourInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpForeignTourInfoLog modelLog = hrEmpForeignTourInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpForeignTourInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpForeignTourInfoLog modelLog = hrEmpForeignTourInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpForeignTourInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getForeignTourModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpForeignTourInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpForeignTourInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpForeignTourInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpForeignTourInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpForeignTourInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpForeignTourInfos List : logStatus: {} ",logStatus);
        List<HrEmpForeignTourInfo> modelList = hrEmpForeignTourInfoRepository.findAllByLogStatus(logStatus);

        HrEmpForeignTourInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpForeignTourInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpForeignTourInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpForeignTourInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpForeignTourInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpForeignTourInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpForeignTourInfo modelInfo = hrEmpForeignTourInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("ForeignTour");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpForeignTourInfoLog modelLog = hrEmpForeignTourInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
