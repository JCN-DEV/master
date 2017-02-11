package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpPreGovtJobInfo;
import gov.step.app.domain.HrEmpPreGovtJobInfoLog;
import gov.step.app.repository.HrEmpPreGovtJobInfoLogRepository;
import gov.step.app.repository.HrEmpPreGovtJobInfoRepository;
import gov.step.app.repository.search.HrEmpPreGovtJobInfoSearchRepository;
import gov.step.app.service.HrmConversionService;
import gov.step.app.service.constnt.HRMManagementConstant;
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
 * REST controller for managing HrEmpPreGovtJobInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpPreGovtJobInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpPreGovtJobInfoResource.class);

    @Inject
    private HrEmpPreGovtJobInfoRepository hrEmpPreGovtJobInfoRepository;

    @Inject
    private HrEmpPreGovtJobInfoSearchRepository hrEmpPreGovtJobInfoSearchRepository;

    @Inject
    private HrEmpPreGovtJobInfoLogRepository hrEmpPreGovtJobInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpPreGovtJobInfos -> Create a new hrEmpPreGovtJobInfo.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPreGovtJobInfo> createHrEmpPreGovtJobInfo(@Valid @RequestBody HrEmpPreGovtJobInfo hrEmpPreGovtJobInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpPreGovtJobInfo : {}", hrEmpPreGovtJobInfo);
        if (hrEmpPreGovtJobInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpPreGovtJobInfo", "idexists", "A new hrEmpPreGovtJobInfo cannot already have an ID")).body(null);
        }
        if(hrEmpPreGovtJobInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpPreGovtJobInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpPreGovtJobInfo result = hrEmpPreGovtJobInfoRepository.save(hrEmpPreGovtJobInfo);
        hrEmpPreGovtJobInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpPreGovtJobInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpPreGovtJobInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpPreGovtJobInfos -> Updates an existing hrEmpPreGovtJobInfo.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPreGovtJobInfo> updateHrEmpPreGovtJobInfo(@Valid @RequestBody HrEmpPreGovtJobInfo hrEmpPreGovtJobInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpPreGovtJobInfo : {}", hrEmpPreGovtJobInfo);
        if (hrEmpPreGovtJobInfo.getId() == null) {
            return createHrEmpPreGovtJobInfo(hrEmpPreGovtJobInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpPreGovtJobInfoLog logInfo = new HrEmpPreGovtJobInfoLog();
        HrEmpPreGovtJobInfo dbModelInfo = hrEmpPreGovtJobInfoRepository.findOne(hrEmpPreGovtJobInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpPreGovtJobInfoLogRepository.save(logInfo);
        hrEmpPreGovtJobInfo.setLogId(logInfo.getId());
        hrEmpPreGovtJobInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpPreGovtJobInfo result = hrEmpPreGovtJobInfoRepository.save(hrEmpPreGovtJobInfo);
        hrEmpPreGovtJobInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpPreGovtJobInfo", hrEmpPreGovtJobInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpPreGovtJobInfos -> get all the hrEmpPreGovtJobInfos.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpPreGovtJobInfo>> getAllHrEmpPreGovtJobInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpPreGovtJobInfos");
        Page<HrEmpPreGovtJobInfo> page = hrEmpPreGovtJobInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpPreGovtJobInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpPreGovtJobInfos/:id -> get the "id" hrEmpPreGovtJobInfo.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPreGovtJobInfo> getHrEmpPreGovtJobInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpPreGovtJobInfo : {}", id);
        HrEmpPreGovtJobInfo hrEmpPreGovtJobInfo = hrEmpPreGovtJobInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpPreGovtJobInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpPreGovtJobInfos/:id -> delete the "id" hrEmpPreGovtJobInfo.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpPreGovtJobInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpPreGovtJobInfo : {}", id);
        hrEmpPreGovtJobInfoRepository.delete(id);
        hrEmpPreGovtJobInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpPreGovtJobInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpPreGovtJobInfos/:query -> search for the hrEmpPreGovtJobInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpPreGovtJobInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPreGovtJobInfo> searchHrEmpPreGovtJobInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpPreGovtJobInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpPreGovtJobInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /hrEmpPreGovtJobInfos -> get all the hrEmpPreGovtJobInfos.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPreGovtJobInfo> getAllHrTrainingInfosByCurrentEmployee()
        throws URISyntaxException {
        log.debug("REST request to get a page of hrEmpPreGovtJobInfos by LoggedIn Employee");
        List<HrEmpPreGovtJobInfo> modelInfoList = hrEmpPreGovtJobInfoRepository.findAllByEmployeeIsCurrentUser();

        return modelInfoList;
    }

    /**
     * GET  /hrEmpPreGovtJobInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpPreGovtJobInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpPreGovtJobInfo modelInfo = hrEmpPreGovtJobInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpPreGovtJobInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPreGovtJobInfoLog modelLog = hrEmpPreGovtJobInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPreGovtJobInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPreGovtJobInfoLog modelLog = hrEmpPreGovtJobInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPreGovtJobInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpPreGovtJobInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpPreGovtJobInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpPreGovtJobInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpPreGovtJobInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpPreGovtJobInfo List : logStatus: {} ",logStatus);
        List<HrEmpPreGovtJobInfo> modelList = hrEmpPreGovtJobInfoRepository.findAllByLogStatus(logStatus);

        HrEmpPreGovtJobInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpPreGovtJobInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpPreGovtJobInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpPreGovtJobInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpPreGovtJobInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpPreGovtJobInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpPreGovtJobInfo modelInfo = hrEmpPreGovtJobInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("PreGovtJob");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpPreGovtJobInfoLog modelLog = hrEmpPreGovtJobInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
