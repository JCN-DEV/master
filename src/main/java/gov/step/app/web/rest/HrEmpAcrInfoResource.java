package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAcrInfo;
import gov.step.app.domain.HrEmpAcrInfoLog;
import gov.step.app.repository.HrEmpAcrInfoLogRepository;
import gov.step.app.repository.HrEmpAcrInfoRepository;
import gov.step.app.repository.search.HrEmpAcrInfoSearchRepository;
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
 * REST controller for managing HrEmpAcrInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAcrInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAcrInfoResource.class);

    @Inject
    private HrEmpAcrInfoRepository hrEmpAcrInfoRepository;

    @Inject
    private HrEmpAcrInfoSearchRepository hrEmpAcrInfoSearchRepository;

    @Inject
    private HrEmpAcrInfoLogRepository hrEmpAcrInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpAcrInfos -> Create a new hrEmpAcrInfo.
     */
    @RequestMapping(value = "/hrEmpAcrInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAcrInfo> createHrEmpAcrInfo(@Valid @RequestBody HrEmpAcrInfo hrEmpAcrInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpAcrInfo : {}", hrEmpAcrInfo);
        if (hrEmpAcrInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAcrInfo", "idexists", "A new hrEmpAcrInfo cannot already have an ID")).body(null);
        }

        if(hrEmpAcrInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpAcrInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpAcrInfo result = hrEmpAcrInfoRepository.save(hrEmpAcrInfo);
        hrEmpAcrInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAcrInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAcrInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAcrInfos -> Updates an existing hrEmpAcrInfo.
     */
    @RequestMapping(value = "/hrEmpAcrInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAcrInfo> updateHrEmpAcrInfo(@Valid @RequestBody HrEmpAcrInfo hrEmpAcrInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpAcrInfo : {}", hrEmpAcrInfo);
        if (hrEmpAcrInfo.getId() == null) {
            return createHrEmpAcrInfo(hrEmpAcrInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpAcrInfoLog logInfo = new HrEmpAcrInfoLog();
        HrEmpAcrInfo dbModelInfo = hrEmpAcrInfoRepository.findOne(hrEmpAcrInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpAcrInfoLogRepository.save(logInfo);

        hrEmpAcrInfo.setLogId(logInfo.getId());
        hrEmpAcrInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrEmpAcrInfo result = hrEmpAcrInfoRepository.save(hrEmpAcrInfo);
        hrEmpAcrInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAcrInfo", hrEmpAcrInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAcrInfos -> get all the hrEmpAcrInfos.
     */
    @RequestMapping(value = "/hrEmpAcrInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAcrInfo>> getAllHrEmpAcrInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAcrInfos");
        Page<HrEmpAcrInfo> page = hrEmpAcrInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAcrInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAcrInfos/:id -> get the "id" hrEmpAcrInfo.
     */
    @RequestMapping(value = "/hrEmpAcrInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAcrInfo> getHrEmpAcrInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpAcrInfo : {}", id);
        HrEmpAcrInfo hrEmpAcrInfo = hrEmpAcrInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpAcrInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAcrInfos/:id -> delete the "id" hrEmpAcrInfo.
     */
    @RequestMapping(value = "/hrEmpAcrInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAcrInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpAcrInfo : {}", id);
        hrEmpAcrInfoRepository.delete(id);
        hrEmpAcrInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAcrInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAcrInfos/:query -> search for the hrEmpAcrInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAcrInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAcrInfo> searchHrEmpAcrInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpAcrInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpAcrInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpAcrInfos -> get all the hrEmpAcrInfos.
     */
    @RequestMapping(value = "/hrEmpAcrInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAcrInfo> getAllHrAcrInfoByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpAcrInfos by LoggedIn Employee");
        List<HrEmpAcrInfo> acrList = hrEmpAcrInfoRepository.findAllByEmployeeIsCurrentUser();
        return acrList;
    }

    /**
     * GET  /hrEmpAcrInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpAcrInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpAcrInfosApprover POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpAcrInfo modelInfo = hrEmpAcrInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpAcrInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAcrInfoLog modelLog = hrEmpAcrInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAcrInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAcrInfoLog modelLog = hrEmpAcrInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAcrInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpAcrInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpAcrInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("HrEmpAcrInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpAcrInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpAcrInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpAcrInfosApprover List : logStatus: {} ",logStatus);
        List<HrEmpAcrInfo> modelList = hrEmpAcrInfoRepository.findAllByLogStatus(logStatus);

        HrEmpAcrInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpAcrInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpAcrInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }
}
