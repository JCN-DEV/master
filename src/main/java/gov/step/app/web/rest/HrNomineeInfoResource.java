package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrNomineeInfo;
import gov.step.app.domain.HrNomineeInfoLog;
import gov.step.app.repository.HrNomineeInfoLogRepository;
import gov.step.app.repository.HrNomineeInfoRepository;
import gov.step.app.repository.search.HrNomineeInfoSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing HrNomineeInfo.
 */
@RestController
@RequestMapping("/api")
public class HrNomineeInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrNomineeInfoResource.class);

    @Inject
    private HrNomineeInfoRepository hrNomineeInfoRepository;

    @Inject
    private HrNomineeInfoSearchRepository hrNomineeInfoSearchRepository;

    @Inject
    private HrNomineeInfoLogRepository hrNomineeInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrNomineeInfos -> Create a new hrNomineeInfo.
     */
    @RequestMapping(value = "/hrNomineeInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrNomineeInfo> createHrNomineeInfo(@Valid @RequestBody HrNomineeInfo hrNomineeInfo) throws URISyntaxException {
        log.debug("REST request to save HrNomineeInfo : {}", hrNomineeInfo);
        if (hrNomineeInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrNomineeInfo", "idexists", "A new hrNomineeInfo cannot already have an ID")).body(null);
        }
        if(hrNomineeInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrNomineeInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrNomineeInfo result = hrNomineeInfoRepository.save(hrNomineeInfo);
        hrNomineeInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrNomineeInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrNomineeInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrNomineeInfos -> Updates an existing hrNomineeInfo.
     */
    @RequestMapping(value = "/hrNomineeInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrNomineeInfo> updateHrNomineeInfo(@Valid @RequestBody HrNomineeInfo hrNomineeInfo) throws URISyntaxException {
        log.debug("REST request to update HrNomineeInfo : {}", hrNomineeInfo);
        if (hrNomineeInfo.getId() == null) {
            return createHrNomineeInfo(hrNomineeInfo);
        }

        // Add LOG info for Approval Purpose.
        HrNomineeInfoLog logInfo = new HrNomineeInfoLog();
        HrNomineeInfo dbModelInfo = hrNomineeInfoRepository.findOne(hrNomineeInfo.getId());
        logInfo = conversionService.getNomineeLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrNomineeInfoLogRepository.save(logInfo);
        hrNomineeInfo.setLogId(logInfo.getId());
        hrNomineeInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrNomineeInfo result = hrNomineeInfoRepository.save(hrNomineeInfo);
        hrNomineeInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrNomineeInfo", hrNomineeInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrNomineeInfos -> get all the hrNomineeInfos.
     */
    @RequestMapping(value = "/hrNomineeInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrNomineeInfo>> getAllHrNomineeInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrNomineeInfos");
        Page<HrNomineeInfo> page = hrNomineeInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrNomineeInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrNomineeInfos/:id -> get the "id" hrNomineeInfo.
     */
    @RequestMapping(value = "/hrNomineeInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrNomineeInfo> getHrNomineeInfo(@PathVariable Long id) {
        log.debug("REST request to get HrNomineeInfo : {}", id);
        HrNomineeInfo hrNomineeInfo = hrNomineeInfoRepository.findOne(id);
        return Optional.ofNullable(hrNomineeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrNomineeInfos/:id -> delete the "id" hrNomineeInfo.
     */
    @RequestMapping(value = "/hrNomineeInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrNomineeInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrNomineeInfo : {}", id);
        hrNomineeInfoRepository.delete(id);
        hrNomineeInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrNomineeInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrNomineeInfos/:query -> search for the hrNomineeInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrNomineeInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrNomineeInfo> searchHrNomineeInfos(@PathVariable String query) {
        log.debug("REST request to search HrNomineeInfos for query {}", query);
        return StreamSupport
            .stream(hrNomineeInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrNomineeInfos/my -> get my nominee.
     */
    @RequestMapping(value = "/hrNomineeInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrNomineeInfo> getMyNominee()
    {
        log.debug("REST request to search hrNomineeInfos for current user");
        List<HrNomineeInfo> modelInfoList = hrNomineeInfoRepository.findAllByEmployeeIsCurrentUser();
        return modelInfoList;
    }


    /**
     * GET  /hrNomineeInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrNomineeInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrNomineeInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrNomineeInfo modelInfo = hrNomineeInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrNomineeInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrNomineeInfoLog modelLog = hrNomineeInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrNomineeInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrNomineeInfoLog modelLog = hrNomineeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrNomineeInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getNomineeModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrNomineeInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrNomineeInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrNomineeInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrNomineeInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrNomineeInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrNomineeInfos List : logStatus: {} ",logStatus);
        List<HrNomineeInfo> modelList = hrNomineeInfoRepository.findAllByLogStatus(logStatus);

        HrNomineeInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrNomineeInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrNomineeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrNomineeInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrNomineeInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrNomineeInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrNomineeInfo modelInfo = hrNomineeInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Transfer");
        if(modelInfo.getLogId() != 0)
        {
            HrNomineeInfoLog modelLog = hrNomineeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
