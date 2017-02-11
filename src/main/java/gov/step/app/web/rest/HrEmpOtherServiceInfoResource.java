package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpOtherServiceInfo;
import gov.step.app.domain.HrEmpOtherServiceInfoLog;
import gov.step.app.repository.HrEmpOtherServiceInfoLogRepository;
import gov.step.app.repository.HrEmpOtherServiceInfoRepository;
import gov.step.app.repository.search.HrEmpOtherServiceInfoSearchRepository;
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
 * REST controller for managing HrEmpOtherServiceInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpOtherServiceInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpOtherServiceInfoResource.class);

    @Inject
    private HrEmpOtherServiceInfoRepository hrEmpOtherServiceInfoRepository;

    @Inject
    private HrEmpOtherServiceInfoSearchRepository hrEmpOtherServiceInfoSearchRepository;

    @Inject
    private HrEmpOtherServiceInfoLogRepository hrEmpOtherServiceInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpOtherServiceInfos -> Create a new hrEmpOtherServiceInfo.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpOtherServiceInfo> createHrEmpOtherServiceInfo(@Valid @RequestBody HrEmpOtherServiceInfo hrEmpOtherServiceInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpOtherServiceInfo : {}", hrEmpOtherServiceInfo);
        if (hrEmpOtherServiceInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpOtherServiceInfo", "idexists", "A new hrEmpOtherServiceInfo cannot already have an ID")).body(null);
        }
        if(hrEmpOtherServiceInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpOtherServiceInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpOtherServiceInfo result = hrEmpOtherServiceInfoRepository.save(hrEmpOtherServiceInfo);
        hrEmpOtherServiceInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpOtherServiceInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpOtherServiceInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpOtherServiceInfos -> Updates an existing hrEmpOtherServiceInfo.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpOtherServiceInfo> updateHrEmpOtherServiceInfo(@Valid @RequestBody HrEmpOtherServiceInfo hrEmpOtherServiceInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpOtherServiceInfo : {}", hrEmpOtherServiceInfo);
        if (hrEmpOtherServiceInfo.getId() == null) {
            return createHrEmpOtherServiceInfo(hrEmpOtherServiceInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpOtherServiceInfoLog logInfo = new HrEmpOtherServiceInfoLog();
        HrEmpOtherServiceInfo dbModelInfo = hrEmpOtherServiceInfoRepository.findOne(hrEmpOtherServiceInfo.getId());
        logInfo = conversionService.getOtherServiceLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpOtherServiceInfoLogRepository.save(logInfo);
        hrEmpOtherServiceInfo.setLogId(logInfo.getId());
        hrEmpOtherServiceInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrEmpOtherServiceInfo result = hrEmpOtherServiceInfoRepository.save(hrEmpOtherServiceInfo);
        hrEmpOtherServiceInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpOtherServiceInfo", hrEmpOtherServiceInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpOtherServiceInfos -> get all the hrEmpOtherServiceInfos.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpOtherServiceInfo>> getAllHrEmpOtherServiceInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpOtherServiceInfos");
        Page<HrEmpOtherServiceInfo> page = hrEmpOtherServiceInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpOtherServiceInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpOtherServiceInfos/:id -> get the "id" hrEmpOtherServiceInfo.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpOtherServiceInfo> getHrEmpOtherServiceInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpOtherServiceInfo : {}", id);
        HrEmpOtherServiceInfo hrEmpOtherServiceInfo = hrEmpOtherServiceInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpOtherServiceInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpOtherServiceInfos/:id -> delete the "id" hrEmpOtherServiceInfo.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpOtherServiceInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpOtherServiceInfo : {}", id);
        hrEmpOtherServiceInfoRepository.delete(id);
        hrEmpOtherServiceInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpOtherServiceInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpOtherServiceInfos/:query -> search for the hrEmpOtherServiceInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpOtherServiceInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpOtherServiceInfo> searchHrEmpOtherServiceInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpOtherServiceInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpOtherServiceInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpOtherServiceInfos -> get all the hrEmpOtherServiceInfos.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpOtherServiceInfo> getAllHrOtherServiceInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpOtherServiceInfos by LoggedIn Employee");
        List<HrEmpOtherServiceInfo> modelInfoList = hrEmpOtherServiceInfoRepository.findAllByEmployeeIsCurrentUser();
        return modelInfoList;
    }

    /**
     * GET  /hrEmpOtherServiceInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpOtherServiceInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpOtherServiceInfo modelInfo = hrEmpOtherServiceInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpOtherServiceInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpOtherServiceInfoLog modelLog = hrEmpOtherServiceInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpOtherServiceInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpOtherServiceInfoLog modelLog = hrEmpOtherServiceInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpOtherServiceInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getOtherServiceModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpOtherServiceInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpOtherServiceInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpOtherServiceInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpOtherServiceInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpOtherServiceInfos List : logStatus: {} ",logStatus);
        List<HrEmpOtherServiceInfo> modelList = hrEmpOtherServiceInfoRepository.findAllByLogStatus(logStatus);

        HrEmpOtherServiceInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpOtherServiceInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpOtherServiceInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpOtherServiceInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpOtherServiceInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpOtherServiceInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpOtherServiceInfo modelInfo = hrEmpOtherServiceInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("OtherService");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpOtherServiceInfoLog modelLog = hrEmpOtherServiceInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
