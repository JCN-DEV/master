package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpProfMemberInfo;
import gov.step.app.domain.HrEmpProfMemberInfoLog;
import gov.step.app.repository.HrEmpProfMemberInfoLogRepository;
import gov.step.app.repository.HrEmpProfMemberInfoRepository;
import gov.step.app.repository.search.HrEmpProfMemberInfoSearchRepository;
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
 * REST controller for managing HrEmpProfMemberInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpProfMemberInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpProfMemberInfoResource.class);

    @Inject
    private HrEmpProfMemberInfoRepository hrEmpProfMemberInfoRepository;

    @Inject
    private HrEmpProfMemberInfoSearchRepository hrEmpProfMemberInfoSearchRepository;

    @Inject
    private HrEmpProfMemberInfoLogRepository hrEmpProfMemberInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpProfMemberInfos -> Create a new hrEmpProfMemberInfo.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfo> createHrEmpProfMemberInfo(@Valid @RequestBody HrEmpProfMemberInfo hrEmpProfMemberInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpProfMemberInfo : {}", hrEmpProfMemberInfo);
        if (hrEmpProfMemberInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpProfMemberInfo", "idexists", "A new hrEmpProfMemberInfo cannot already have an ID")).body(null);
        }
        if(hrEmpProfMemberInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpProfMemberInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpProfMemberInfo result = hrEmpProfMemberInfoRepository.save(hrEmpProfMemberInfo);
        hrEmpProfMemberInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpProfMemberInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpProfMemberInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpProfMemberInfos -> Updates an existing hrEmpProfMemberInfo.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfo> updateHrEmpProfMemberInfo(@Valid @RequestBody HrEmpProfMemberInfo hrEmpProfMemberInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpProfMemberInfo : {}", hrEmpProfMemberInfo);
        if (hrEmpProfMemberInfo.getId() == null) {
            return createHrEmpProfMemberInfo(hrEmpProfMemberInfo);
        }


        // Add LOG info for Approval Purpose.
        HrEmpProfMemberInfoLog logInfo = new HrEmpProfMemberInfoLog();
        HrEmpProfMemberInfo dbModelInfo = hrEmpProfMemberInfoRepository.findOne(hrEmpProfMemberInfo.getId());
        logInfo = conversionService.getProfMembrLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpProfMemberInfoLogRepository.save(logInfo);

        hrEmpProfMemberInfo.setLogId(logInfo.getId());
        hrEmpProfMemberInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpProfMemberInfo result = hrEmpProfMemberInfoRepository.save(hrEmpProfMemberInfo);
        hrEmpProfMemberInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpProfMemberInfo", hrEmpProfMemberInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpProfMemberInfos -> get all the hrEmpProfMemberInfos.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpProfMemberInfo>> getAllHrEmpProfMemberInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpProfMemberInfos");
        Page<HrEmpProfMemberInfo> page = hrEmpProfMemberInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpProfMemberInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpProfMemberInfos/:id -> get the "id" hrEmpProfMemberInfo.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfo> getHrEmpProfMemberInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpProfMemberInfo : {}", id);
        HrEmpProfMemberInfo hrEmpProfMemberInfo = hrEmpProfMemberInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpProfMemberInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpProfMemberInfos/:id -> delete the "id" hrEmpProfMemberInfo.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpProfMemberInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpProfMemberInfo : {}", id);
        hrEmpProfMemberInfoRepository.delete(id);
        hrEmpProfMemberInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpProfMemberInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpProfMemberInfos/:query -> search for the hrEmpProfMemberInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpProfMemberInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpProfMemberInfo> searchHrEmpProfMemberInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpProfMemberInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpProfMemberInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpProfMemberInfos -> get all the hrEmpProfMemberInfos.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpProfMemberInfo> getAllHrModelByCurrentEmployee()
        throws URISyntaxException {
        log.debug("REST request to get a page of hrEmpProfMemberInfos by LoggedIn Employee");
        List<HrEmpProfMemberInfo> modelInfoList = hrEmpProfMemberInfoRepository.findAllByEmployeeIsCurrentUser();

        return modelInfoList;
    }

    /**
     * GET  /hrEmpProfMemberInfosApprover/ -> process the approval request
     */
    @RequestMapping(value = "/hrEmpProfMemberInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve ProfMembership POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpProfMemberInfo modelInfo = hrEmpProfMemberInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpProfMemberInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpProfMemberInfoLog modelLog = hrEmpProfMemberInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpProfMemberInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpProfMemberInfoLog modelLog = hrEmpProfMemberInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpProfMemberInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getProfMembrModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpProfMemberInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpProfMemberInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpProfMemberInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpProfMemberInfosApprover/:logStatus -> get model list by log status.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve ProfMembership List : logStatus: {} ",logStatus);
        List<HrEmpProfMemberInfo> modelList = hrEmpProfMemberInfoRepository.findAllByLogStatus(logStatus);

        HrEmpProfMemberInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpProfMemberInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpProfMemberInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpProfMemberInfosApprover/log/:entityId -> get model and logModel by entity id.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log ProfMembership Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpProfMemberInfo modelInfo = hrEmpProfMemberInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Prof.Member");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpProfMemberInfoLog modelLog = hrEmpProfMemberInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
