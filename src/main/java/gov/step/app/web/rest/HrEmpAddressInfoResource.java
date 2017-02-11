package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAddressInfo;
import gov.step.app.domain.HrEmpAddressInfoLog;
import gov.step.app.repository.HrEmpAddressInfoLogRepository;
import gov.step.app.repository.HrEmpAddressInfoRepository;
import gov.step.app.repository.search.HrEmpAddressInfoSearchRepository;
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
 * REST controller for managing HrEmpAddressInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAddressInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAddressInfoResource.class);

    @Inject
    private HrEmpAddressInfoRepository hrEmpAddressInfoRepository;

    @Inject
    private HrEmpAddressInfoSearchRepository hrEmpAddressInfoSearchRepository;

    @Inject
    private HrEmpAddressInfoLogRepository hrEmpAddressInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpAddressInfos -> Create a new hrEmpAddressInfo.
     */
    @RequestMapping(value = "/hrEmpAddressInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfo> createHrEmpAddressInfo(@Valid @RequestBody HrEmpAddressInfo hrEmpAddressInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpAddressInfo : {}", hrEmpAddressInfo);
        if (hrEmpAddressInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAddressInfo", "idexists", "A new hrEmpAddressInfo cannot already have an ID")).body(null);
        }
        if(hrEmpAddressInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpAddressInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpAddressInfo result = hrEmpAddressInfoRepository.save(hrEmpAddressInfo);
        hrEmpAddressInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAddressInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAddressInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAddressInfos -> Updates an existing hrEmpAddressInfo.
     */
    @RequestMapping(value = "/hrEmpAddressInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfo> updateHrEmpAddressInfo(@Valid @RequestBody HrEmpAddressInfo hrEmpAddressInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpAddressInfo : {}", hrEmpAddressInfo);
        if (hrEmpAddressInfo.getId() == null) {
            return createHrEmpAddressInfo(hrEmpAddressInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpAddressInfoLog logInfo = new HrEmpAddressInfoLog();
        HrEmpAddressInfo dbModelInfo = hrEmpAddressInfoRepository.findOne(hrEmpAddressInfo.getId());
        logInfo = conversionService.getAddressLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpAddressInfoLogRepository.save(logInfo);

        hrEmpAddressInfo.setLogId(logInfo.getId());
        hrEmpAddressInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpAddressInfo result = hrEmpAddressInfoRepository.save(hrEmpAddressInfo);
        hrEmpAddressInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAddressInfo", hrEmpAddressInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAddressInfos -> get all the hrEmpAddressInfos.
     */
    @RequestMapping(value = "/hrEmpAddressInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAddressInfo>> getAllHrEmpAddressInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAddressInfos");
        Page<HrEmpAddressInfo> page = hrEmpAddressInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAddressInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAddressInfos/:id -> get the "id" hrEmpAddressInfo.
     */
    @RequestMapping(value = "/hrEmpAddressInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfo> getHrEmpAddressInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpAddressInfo : {}", id);
        HrEmpAddressInfo hrEmpAddressInfo = hrEmpAddressInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpAddressInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAddressInfos/:id -> delete the "id" hrEmpAddressInfo.
     */
    @RequestMapping(value = "/hrEmpAddressInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAddressInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpAddressInfo : {}", id);
        hrEmpAddressInfoRepository.delete(id);
        hrEmpAddressInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAddressInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAddressInfos/:query -> search for the hrEmpAddressInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAddressInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAddressInfo> searchHrEmpAddressInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpAddressInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpAddressInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpAddressInfos/my -> get all the hrEmpAddressInfos by login User
     */
    @RequestMapping(value = "/hrEmpAddressInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAddressInfo>> getAllHrEmpAddressInfosByLoggedInUser(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAddressInfos by current logged in user");
        Page<HrEmpAddressInfo> page = hrEmpAddressInfoRepository.findAllByEmployeeIsCurrentUser(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAddressInfos/my");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAddressInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpAddressInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve HrEmpAddressInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpAddressInfo modelInfo = hrEmpAddressInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpAddressInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAddressInfoLog modelLog = hrEmpAddressInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAddressInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAddressInfoLog modelLog = hrEmpAddressInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAddressInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getAddressModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpAddressInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpAddressInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpAddressInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpAddressInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpAddressInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve HrEmpAddressInfo List : logStatus: {} ",logStatus);
        List<HrEmpAddressInfo> modelList = hrEmpAddressInfoRepository.findAllByLogStatus(logStatus);

        HrEmpAddressInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpAddressInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpAddressInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpAddressInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpAddressInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log HrEmpAddressInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpAddressInfo modelInfo = hrEmpAddressInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Address");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpAddressInfoLog modelLog = hrEmpAddressInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }

}
