package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmploymentInfo;
import gov.step.app.domain.HrEmploymentInfoLog;
import gov.step.app.repository.HrEmploymentInfoLogRepository;
import gov.step.app.repository.HrEmploymentInfoRepository;
import gov.step.app.repository.search.HrEmploymentInfoSearchRepository;
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
 * REST controller for managing HrEmploymentInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmploymentInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmploymentInfoResource.class);

    @Inject
    private HrEmploymentInfoRepository hrEmploymentInfoRepository;

    @Inject
    private HrEmploymentInfoSearchRepository hrEmploymentInfoSearchRepository;

    @Inject
    private HrEmploymentInfoLogRepository hrEmploymentInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmploymentInfos -> Create a new hrEmploymentInfo.
     */
    @RequestMapping(value = "/hrEmploymentInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfo> createHrEmploymentInfo(@Valid @RequestBody HrEmploymentInfo hrEmploymentInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmploymentInfo : {}", hrEmploymentInfo);
        if (hrEmploymentInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmploymentInfo", "idexists", "A new hrEmploymentInfo cannot already have an ID")).body(null);
        }
        if(hrEmploymentInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmploymentInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmploymentInfo result = hrEmploymentInfoRepository.save(hrEmploymentInfo);
        hrEmploymentInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmploymentInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmploymentInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmploymentInfos -> Updates an existing hrEmploymentInfo.
     */
    @RequestMapping(value = "/hrEmploymentInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfo> updateHrEmploymentInfo(@Valid @RequestBody HrEmploymentInfo hrEmploymentInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmploymentInfo : {}", hrEmploymentInfo);
        if (hrEmploymentInfo.getId() == null) {
            return createHrEmploymentInfo(hrEmploymentInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmploymentInfoLog logInfo = new HrEmploymentInfoLog();
        HrEmploymentInfo dbModelInfo = hrEmploymentInfoRepository.findOne(hrEmploymentInfo.getId());
        logInfo = conversionService.getEmploymentLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmploymentInfoLogRepository.save(logInfo);

        hrEmploymentInfo.setLogId(logInfo.getId());
        hrEmploymentInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        HrEmploymentInfo result = hrEmploymentInfoRepository.save(hrEmploymentInfo);
        hrEmploymentInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmploymentInfo", hrEmploymentInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmploymentInfos -> get all the hrEmploymentInfos.
     */
    @RequestMapping(value = "/hrEmploymentInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmploymentInfo>> getAllHrEmploymentInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmploymentInfos");
        Page<HrEmploymentInfo> page = hrEmploymentInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmploymentInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmploymentInfos/:id -> get the "id" hrEmploymentInfo.
     */
    @RequestMapping(value = "/hrEmploymentInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfo> getHrEmploymentInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmploymentInfo : {}", id);
        HrEmploymentInfo hrEmploymentInfo = hrEmploymentInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmploymentInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmploymentInfos/:id -> delete the "id" hrEmploymentInfo.
     */
    @RequestMapping(value = "/hrEmploymentInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmploymentInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmploymentInfo : {}", id);
        hrEmploymentInfoRepository.delete(id);
        hrEmploymentInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmploymentInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmploymentInfos/:query -> search for the hrEmploymentInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmploymentInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmploymentInfo> searchHrEmploymentInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmploymentInfos for query {}", query);
        return StreamSupport
            .stream(hrEmploymentInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /hrEmploymentInfos/my -> get the current logged in hrEmploymentInfo.
     */
    @RequestMapping(value = "/hrEmploymentInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmploymentInfo> getHrEmploymentInfoByCurrentEmployee() {
        log.debug("REST request to get HrEmploymentInfo by current logged in ");
        List<HrEmploymentInfo> modelInfoList = hrEmploymentInfoRepository.findAllByEmployeeIsCurrentUser();
        return modelInfoList;
    }


    /**
     * GET  /hrEmploymentInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmploymentInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve HrEmploymentInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmploymentInfo modelInfo = hrEmploymentInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmploymentInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmploymentInfoLog modelLog = hrEmploymentInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmploymentInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmploymentInfoLog modelLog = hrEmploymentInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmploymentInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getEmploymentModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmploymentInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmploymentInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmploymentInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmploymentInfosApprover/:logStatus -> get model list by log status.
     */
    @RequestMapping(value = "/hrEmploymentInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve HrEmploymentInfo List : logStatus: {} ",logStatus);
        List<HrEmploymentInfo> modelList = hrEmploymentInfoRepository.findAllByLogStatus(logStatus);

        HrEmploymentInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmploymentInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmploymentInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmploymentInfosApprover/log/:entityId -> get model and logModel by entity id.
     */
    @RequestMapping(value = "/hrEmploymentInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log HrEmploymentInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmploymentInfo modelInfo = hrEmploymentInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Employment");
        if(modelInfo.getLogId() != 0)
        {
            HrEmploymentInfoLog modelLog = hrEmploymentInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
