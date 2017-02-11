package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpChildrenInfo;
import gov.step.app.domain.HrEmpChildrenInfoLog;
import gov.step.app.repository.HrEmpChildrenInfoLogRepository;
import gov.step.app.repository.HrEmpChildrenInfoRepository;
import gov.step.app.repository.search.HrEmpChildrenInfoSearchRepository;
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
 * REST controller for managing HrEmpChildrenInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpChildrenInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpChildrenInfoResource.class);

    @Inject
    private HrEmpChildrenInfoRepository hrEmpChildrenInfoRepository;

    @Inject
    private HrEmpChildrenInfoSearchRepository hrEmpChildrenInfoSearchRepository;

    @Inject
    private HrEmpChildrenInfoLogRepository hrEmpChildrenInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpChildrenInfos -> Create a new hrEmpChildrenInfo.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfo> createHrEmpChildrenInfo(@Valid @RequestBody HrEmpChildrenInfo hrEmpChildrenInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpChildrenInfo : {}", hrEmpChildrenInfo);
        if (hrEmpChildrenInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpChildrenInfo", "idexists", "A new hrEmpChildrenInfo cannot already have an ID")).body(null);
        }
        if(hrEmpChildrenInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpChildrenInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpChildrenInfo result = hrEmpChildrenInfoRepository.save(hrEmpChildrenInfo);
        hrEmpChildrenInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpChildrenInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpChildrenInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpChildrenInfos -> Updates an existing hrEmpChildrenInfo.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfo> updateHrEmpChildrenInfo(@Valid @RequestBody HrEmpChildrenInfo hrEmpChildrenInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpChildrenInfo : {}", hrEmpChildrenInfo);
        if (hrEmpChildrenInfo.getId() == null) {
            return createHrEmpChildrenInfo(hrEmpChildrenInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpChildrenInfoLog logInfo = new HrEmpChildrenInfoLog();
        HrEmpChildrenInfo dbModelInfo = hrEmpChildrenInfoRepository.findOne(hrEmpChildrenInfo.getId());
        logInfo = conversionService.getChildrenLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpChildrenInfoLogRepository.save(logInfo);

        hrEmpChildrenInfo.setLogId(logInfo.getId());
        hrEmpChildrenInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpChildrenInfo result = hrEmpChildrenInfoRepository.save(hrEmpChildrenInfo);
        hrEmpChildrenInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpChildrenInfo", hrEmpChildrenInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpChildrenInfos -> get all the hrEmpChildrenInfos.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpChildrenInfo>> getAllHrEmpChildrenInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpChildrenInfos");
        Page<HrEmpChildrenInfo> page = hrEmpChildrenInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpChildrenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpChildrenInfos/:id -> get the "id" hrEmpChildrenInfo.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfo> getHrEmpChildrenInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpChildrenInfo : {}", id);
        HrEmpChildrenInfo hrEmpChildrenInfo = hrEmpChildrenInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpChildrenInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpChildrenInfos/:id -> delete the "id" hrEmpChildrenInfo.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpChildrenInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpChildrenInfo : {}", id);
        hrEmpChildrenInfoRepository.delete(id);
        hrEmpChildrenInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpChildrenInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpChildrenInfos/:query -> search for the hrEmpChildrenInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpChildrenInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpChildrenInfo> searchHrEmpChildrenInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpChildrenInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpChildrenInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpChildrenInfos -> get all the hrEmpChildrenInfos.
     */
    @RequestMapping(value = "/hrEmpChildrenInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpChildrenInfo>> getAllHrEmpChildrenInfosByLoggedInUser(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpChildrenInfos by logged In user ");
        Page<HrEmpChildrenInfo> page = hrEmpChildrenInfoRepository.findAllByEmployeeIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpChildrenInfos/my");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpChildrenInfosApprover/ -> process the approval request
     */
    @RequestMapping(value = "/hrEmpChildrenInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve HrEmpChildrenInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpChildrenInfo modelInfo = hrEmpChildrenInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpChildrenInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpChildrenInfoLog modelLog = hrEmpChildrenInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpChildrenInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpChildrenInfoLog modelLog = hrEmpChildrenInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpChildrenInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getChildrenModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpChildrenInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpChildrenInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpChildrenInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpChildrenInfosApprover/:logStatus -> get model list by log status.
     */
    @RequestMapping(value = "/hrEmpChildrenInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve HrEmpChildrenInfo List : logStatus: {} ",logStatus);
        List<HrEmpChildrenInfo> modelList = hrEmpChildrenInfoRepository.findAllByLogStatus(logStatus);

        HrEmpChildrenInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpChildrenInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpChildrenInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpChildrenInfosApprover/log/:entityId -> get model and logModel by entity id.
     */
    @RequestMapping(value = "/hrEmpChildrenInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log HrEmpChildrenInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpChildrenInfo modelInfo = hrEmpChildrenInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Children");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpChildrenInfoLog modelLog = hrEmpChildrenInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }

}
