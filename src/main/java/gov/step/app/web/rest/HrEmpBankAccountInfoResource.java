package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpBankAccountInfo;
import gov.step.app.domain.HrEmpBankAccountInfoLog;
import gov.step.app.repository.HrEmpBankAccountInfoLogRepository;
import gov.step.app.repository.HrEmpBankAccountInfoRepository;
import gov.step.app.repository.search.HrEmpBankAccountInfoSearchRepository;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing HrEmpBankAccountInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpBankAccountInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpBankAccountInfoResource.class);

    @Inject
    private HrEmpBankAccountInfoRepository hrEmpBankAccountInfoRepository;

    @Inject
    private HrEmpBankAccountInfoSearchRepository hrEmpBankAccountInfoSearchRepository;

    @Inject
    private HrEmpBankAccountInfoLogRepository hrEmpBankAccountInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEmpBankAccountInfos -> Create a new hrEmpBankAccountInfo.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpBankAccountInfo> createHrEmpBankAccountInfo(@Valid @RequestBody HrEmpBankAccountInfo hrEmpBankAccountInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpBankAccountInfo : {}", hrEmpBankAccountInfo);
        if (hrEmpBankAccountInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpBankAccountInfo", "idexists", "A new hrEmpBankAccountInfo cannot already have an ID")).body(null);
        }

        if(hrEmpBankAccountInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpBankAccountInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEmpBankAccountInfo result = hrEmpBankAccountInfoRepository.save(hrEmpBankAccountInfo);
        hrEmpBankAccountInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpBankAccountInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpBankAccountInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpBankAccountInfos -> Updates an existing hrEmpBankAccountInfo.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpBankAccountInfo> updateHrEmpBankAccountInfo(@Valid @RequestBody HrEmpBankAccountInfo hrEmpBankAccountInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpBankAccountInfo : {}", hrEmpBankAccountInfo);
        if (hrEmpBankAccountInfo.getId() == null) {
            return createHrEmpBankAccountInfo(hrEmpBankAccountInfo);
        }

        // Add LOG info for Approval Purpose.
        HrEmpBankAccountInfoLog logInfo = new HrEmpBankAccountInfoLog();
        HrEmpBankAccountInfo dbModelInfo = hrEmpBankAccountInfoRepository.findOne(hrEmpBankAccountInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpBankAccountInfoLogRepository.save(logInfo);

        hrEmpBankAccountInfo.setLogId(logInfo.getId());
        hrEmpBankAccountInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpBankAccountInfo result = hrEmpBankAccountInfoRepository.save(hrEmpBankAccountInfo);
        hrEmpBankAccountInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpBankAccountInfo", hrEmpBankAccountInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpBankAccountInfos -> get all the hrEmpBankAccountInfos.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpBankAccountInfo>> getAllHrEmpBankAccountInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpBankAccountInfos");
        Page<HrEmpBankAccountInfo> page = hrEmpBankAccountInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpBankAccountInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpBankAccountInfos/:id -> get the "id" hrEmpBankAccountInfo.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpBankAccountInfo> getHrEmpBankAccountInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpBankAccountInfo : {}", id);
        HrEmpBankAccountInfo hrEmpBankAccountInfo = hrEmpBankAccountInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpBankAccountInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpBankAccountInfos/:id -> delete the "id" hrEmpBankAccountInfo.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpBankAccountInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpBankAccountInfo : {}", id);
        hrEmpBankAccountInfoRepository.delete(id);
        hrEmpBankAccountInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpBankAccountInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpBankAccountInfos/:query -> search for the hrEmpBankAccountInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpBankAccountInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpBankAccountInfo> searchHrEmpBankAccountInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpBankAccountInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpBankAccountInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpBankAccountInfos -> get all the hrEmpBankAccountInfos.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpBankAccountInfo> getAllHrBankAccountByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of HrEmpBankAccountInfos by LoggedIn Employee");
        List<HrEmpBankAccountInfo> bankAccountList = hrEmpBankAccountInfoRepository.findAllByEmployeeIsCurrentUser();
        return bankAccountList;
    }

    /**
     * GET  /hrEmpBankAccountInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpBankAccountInfosApprover POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpBankAccountInfo modelInfo = hrEmpBankAccountInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpBankAccountInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpBankAccountInfoLog modelLog = hrEmpBankAccountInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpBankAccountInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpBankAccountInfoLog modelLog = hrEmpBankAccountInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpBankAccountInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpBankAccountInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpBankAccountInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("HrEmpBankAccountInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpBankAccountInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpBankAccountInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpBankAccountInfosApprover List : logStatus: {} ",logStatus);
        List<HrEmpBankAccountInfo> modelList = hrEmpBankAccountInfoRepository.findAllByLogStatus(logStatus);

        HrEmpBankAccountInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpBankAccountInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpBankAccountInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpBankSalaryAccountInfos -> get all the hrEmpBankSalaryAccountInfos.
     */
    @RequestMapping(value = "/hrEmpBankSalaryAccountInfos/{emplId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getAllHrBankSalaryAccountByCurrentEmployee(@PathVariable long emplId)
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpBankSalaryAccountInfos by LoggedIn Employee and salary account {}",emplId);
        List<HrEmpBankAccountInfo> bankAccountList = hrEmpBankAccountInfoRepository.findAllBySalaryAccountByEmployee(emplId);

        Map map = new HashMap();
        if(bankAccountList!=null && bankAccountList.size() > 0)
        {
            map.put("value", bankAccountList.size());
            map.put("isValid", false);
        }
        else
        {
            map.put("value", 0);
            map.put("isValid", true);
        }
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
