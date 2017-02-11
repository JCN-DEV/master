package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpActingDutyInfo;
import gov.step.app.domain.HrEmpActingDutyInfoLog;
import gov.step.app.repository.HrEmpActingDutyInfoLogRepository;
import gov.step.app.repository.HrEmpActingDutyInfoRepository;
import gov.step.app.repository.search.HrEmpActingDutyInfoSearchRepository;
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
 * REST controller for managing HrEmpActingDutyInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpActingDutyInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpActingDutyInfoResource.class);

    @Inject
    private HrEmpActingDutyInfoRepository hrEmpActingDutyInfoRepository;

    @Inject
    private HrEmpActingDutyInfoLogRepository hrEmpActingDutyInfoLogRepository;

    @Inject
    private HrEmpActingDutyInfoSearchRepository hrEmpActingDutyInfoSearchRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpActingDutyInfos -> Create a new hrEmpActingDutyInfo.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpActingDutyInfo> createHrEmpActingDutyInfo(@Valid @RequestBody HrEmpActingDutyInfo hrEmpActingDutyInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpActingDutyInfo : {}", hrEmpActingDutyInfo);
        if (hrEmpActingDutyInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpActingDutyInfo", "idexists", "A new hrEmpActingDutyInfo cannot already have an ID")).body(null);
        }
        if(hrEmpActingDutyInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpActingDutyInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpActingDutyInfo.getGoDoc())
            .fileName(hrEmpActingDutyInfo.getGoDocName())
            .contentType(hrEmpActingDutyInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ACTING_DUTY_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpActingDutyInfo.setGoDoc(new byte[1]);
        hrEmpActingDutyInfo.setGoDocName(goFile.fileName());

        HrEmpActingDutyInfo result = hrEmpActingDutyInfoRepository.save(hrEmpActingDutyInfo);
        hrEmpActingDutyInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpActingDutyInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpActingDutyInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpActingDutyInfos -> Updates an existing hrEmpActingDutyInfo.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpActingDutyInfo> updateHrEmpActingDutyInfo(@Valid @RequestBody HrEmpActingDutyInfo hrEmpActingDutyInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpActingDutyInfo : {}", hrEmpActingDutyInfo);
        if (hrEmpActingDutyInfo.getId() == null) {
            return createHrEmpActingDutyInfo(hrEmpActingDutyInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpActingDutyInfo.getGoDoc())
            .fileName(hrEmpActingDutyInfo.getGoDocName())
            .contentType(hrEmpActingDutyInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ACTING_DUTY_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpActingDutyInfo.setGoDoc(new byte[1]);
        hrEmpActingDutyInfo.setGoDocName(goFile.fileName());

        // Add LOG info for Approval Purpose.
        HrEmpActingDutyInfoLog logInfo = new HrEmpActingDutyInfoLog();
        HrEmpActingDutyInfo dbModelInfo = hrEmpActingDutyInfoRepository.findOne(hrEmpActingDutyInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpActingDutyInfoLogRepository.save(logInfo);
        hrEmpActingDutyInfo.setLogId(logInfo.getId());
        hrEmpActingDutyInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpActingDutyInfo result = hrEmpActingDutyInfoRepository.save(hrEmpActingDutyInfo);
        hrEmpActingDutyInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpActingDutyInfo", hrEmpActingDutyInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpActingDutyInfos -> get all the hrEmpActingDutyInfos.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpActingDutyInfo>> getAllHrEmpActingDutyInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpActingDutyInfos");
        Page<HrEmpActingDutyInfo> page = hrEmpActingDutyInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpActingDutyInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpActingDutyInfos/:id -> get the "id" hrEmpActingDutyInfo.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpActingDutyInfo> getHrEmpActingDutyInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpActingDutyInfo : {}", id);
        HrEmpActingDutyInfo modelInfo = hrEmpActingDutyInfoRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(modelInfo.getGoDocName())
            .contentType(modelInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ACTING_DUTY_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        modelInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(modelInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpActingDutyInfos/:id -> delete the "id" hrEmpActingDutyInfo.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpActingDutyInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpActingDutyInfo : {}", id);
        hrEmpActingDutyInfoRepository.delete(id);
        hrEmpActingDutyInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpActingDutyInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpActingDutyInfos/:query -> search for the hrEmpActingDutyInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpActingDutyInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpActingDutyInfo> searchHrEmpActingDutyInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpActingDutyInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpActingDutyInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /hrEmpActingDutyInfos/my -> get the current logged in hrEmpActingDutyInfos.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpActingDutyInfo> getHrModelInfoByCurrentEmployee() {
        log.debug("REST request to get hrEmpActingDutyInfos by current logged in ");
        HrEmpActingDutyInfo modelInfo = hrEmpActingDutyInfoRepository.findOneByEmployeeIsCurrentUser();

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(modelInfo.getGoDocName())
            .contentType(modelInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.ACTING_DUTY_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        modelInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(modelInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /hrEmpActingDutyInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpActingDutyInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpActingDutyInfo modelInfo = hrEmpActingDutyInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpActingDutyInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpActingDutyInfoLog modelLog = hrEmpActingDutyInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpActingDutyInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpActingDutyInfoLog modelLog = hrEmpActingDutyInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpActingDutyInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpActingDutyInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpActingDutyInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpActingDutyInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpActingDutyInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpActingDutyInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpActingDutyInfo List : logStatus: {} ",logStatus);
        List<HrEmpActingDutyInfo> modelList = hrEmpActingDutyInfoRepository.findAllByLogStatus(logStatus);

        HrEmpActingDutyInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpActingDutyInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpActingDutyInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpActingDutyInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpActingDutyInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpActingDutyInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpActingDutyInfo modelInfo = hrEmpActingDutyInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Education");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpActingDutyInfoLog modelLog = hrEmpActingDutyInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
