package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpTransferApplInfo;
import gov.step.app.domain.HrEmpTransferApplInfoLog;
import gov.step.app.repository.HrEmpTransferApplInfoLogRepository;
import gov.step.app.repository.HrEmpTransferApplInfoRepository;
import gov.step.app.repository.search.HrEmpTransferApplInfoSearchRepository;
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
 * REST controller for managing HrEmpTransferApplInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpTransferApplInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpTransferApplInfoResource.class);

    @Inject
    private HrEmpTransferApplInfoRepository hrEmpTransferApplInfoRepository;

    @Inject
    private HrEmpTransferApplInfoSearchRepository hrEmpTransferApplInfoSearchRepository;

    @Inject
    private HrEmpTransferApplInfoLogRepository hrEmpTransferApplInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpTransferApplInfos -> Create a new hrEmpTransferApplInfo.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferApplInfo> createHrEmpTransferApplInfo(@Valid @RequestBody HrEmpTransferApplInfo hrEmpTransferApplInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpTransferApplInfo : {}", hrEmpTransferApplInfo);
        if (hrEmpTransferApplInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpTransferApplInfo", "idexists", "A new hrEmpTransferApplInfo cannot already have an ID")).body(null);
        }

        if(hrEmpTransferApplInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpTransferApplInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpTransferApplInfo result = hrEmpTransferApplInfoRepository.save(hrEmpTransferApplInfo);
        hrEmpTransferApplInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpTransferApplInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpTransferApplInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpTransferApplInfos -> Updates an existing hrEmpTransferApplInfo.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferApplInfo> updateHrEmpTransferApplInfo(@Valid @RequestBody HrEmpTransferApplInfo hrEmpTransferApplInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpTransferApplInfo : {}", hrEmpTransferApplInfo);
        if (hrEmpTransferApplInfo.getId() == null) {
            return createHrEmpTransferApplInfo(hrEmpTransferApplInfo);
        }
        if(hrEmpTransferApplInfo.getLogStatus()==8)
        {
            hrEmpTransferApplInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_REJECTED);
        }
        else if(hrEmpTransferApplInfo.getLogStatus()==7)
        {
            hrEmpTransferApplInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        }
        else
        {
            // Add LOG info for Approval Purpose.
            HrEmpTransferApplInfoLog logInfo = new HrEmpTransferApplInfoLog();
            HrEmpTransferApplInfo dbModelInfo = hrEmpTransferApplInfoRepository.findOne(hrEmpTransferApplInfo.getId());
            logInfo = conversionService.getLogFromSource(dbModelInfo);
            logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            logInfo = hrEmpTransferApplInfoLogRepository.save(logInfo);
            hrEmpTransferApplInfo.setLogId(logInfo.getId());
            hrEmpTransferApplInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpTransferApplInfo result = hrEmpTransferApplInfoRepository.save(hrEmpTransferApplInfo);
        hrEmpTransferApplInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpTransferApplInfo", hrEmpTransferApplInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpTransferApplInfos -> get all the hrEmpTransferApplInfos.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpTransferApplInfo>> getAllHrEmpTransferApplInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpTransferApplInfos");
        Page<HrEmpTransferApplInfo> page = hrEmpTransferApplInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpTransferApplInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpTransferApplInfos/:id -> get the "id" hrEmpTransferApplInfo.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferApplInfo> getHrEmpTransferApplInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpTransferApplInfo : {}", id);
        HrEmpTransferApplInfo hrEmpTransferApplInfo = hrEmpTransferApplInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpTransferApplInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpTransferApplInfos/:id -> delete the "id" hrEmpTransferApplInfo.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpTransferApplInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpTransferApplInfo : {}", id);
        hrEmpTransferApplInfoRepository.delete(id);
        hrEmpTransferApplInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpTransferApplInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpTransferApplInfos/:query -> search for the hrEmpTransferApplInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpTransferApplInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTransferApplInfo> searchHrEmpTransferApplInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpTransferApplInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpTransferApplInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpTransferApplInfos -> get all the hrEmpTransferApplInfo.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTransferApplInfo> getAllHrModelInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpTransferApplInfos by LoggedIn Employee");
        List<HrEmpTransferApplInfo> modelInfoList = hrEmpTransferApplInfoRepository.findAllByEmployeeIsCurrentUser();

        return modelInfoList;
    }

    /**
     * GET  /HrEmpTransferApplInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve HrEmpTransferApplInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpTransferApplInfo modelInfo = hrEmpTransferApplInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpTransferApplInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTransferApplInfoLog modelLog = hrEmpTransferApplInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTransferApplInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTransferApplInfoLog modelLog = hrEmpTransferApplInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTransferApplInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpTransferApplInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpTransferApplInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("HrEmpTransferApplInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /HrEmpTransferApplInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpTransferApplInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve HrEmpTransferApplInfo List : logStatus: {} ",logStatus);
        List<HrEmpTransferApplInfo> modelList = hrEmpTransferApplInfoRepository.findAllByLogStatus(logStatus);

        HrEmpTransferApplInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpTransferApplInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpTransferApplInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /HrEmpTransferApplInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpTransferApplInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log HrEmpTransferApplInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpTransferApplInfo modelInfo = hrEmpTransferApplInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("TransferApplication");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpTransferApplInfoLog modelLog = hrEmpTransferApplInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
