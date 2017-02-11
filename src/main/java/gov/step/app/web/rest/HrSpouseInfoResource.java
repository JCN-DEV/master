package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrSpouseInfo;
import gov.step.app.domain.HrSpouseInfoLog;
import gov.step.app.repository.HrSpouseInfoLogRepository;
import gov.step.app.repository.HrSpouseInfoRepository;
import gov.step.app.repository.search.HrSpouseInfoSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing HrSpouseInfo.
 */
@RestController
@RequestMapping("/api")
public class HrSpouseInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrSpouseInfoResource.class);

    @Inject
    private HrSpouseInfoRepository hrSpouseInfoRepository;

    @Inject
    private HrSpouseInfoSearchRepository hrSpouseInfoSearchRepository;

    @Inject
    private HrSpouseInfoLogRepository hrSpouseInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrSpouseInfos -> Create a new hrSpouseInfo.
     */
    @RequestMapping(value = "/hrSpouseInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrSpouseInfo> createHrSpouseInfo(@Valid @RequestBody HrSpouseInfo hrSpouseInfo) throws URISyntaxException {
        log.debug("REST request to save HrSpouseInfo : {}", hrSpouseInfo);
        if (hrSpouseInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrSpouseInfo", "idexists", "A new hrSpouseInfo cannot already have an ID")).body(null);
        }
        if(hrSpouseInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrSpouseInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrSpouseInfo result = hrSpouseInfoRepository.save(hrSpouseInfo);
        hrSpouseInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrSpouseInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrSpouseInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrSpouseInfos -> Updates an existing hrSpouseInfo.
     */
    @RequestMapping(value = "/hrSpouseInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrSpouseInfo> updateHrSpouseInfo(@Valid @RequestBody HrSpouseInfo hrSpouseInfo) throws URISyntaxException {
        log.debug("REST request to update HrSpouseInfo : {}", hrSpouseInfo);
        if (hrSpouseInfo.getId() == null) {
            return createHrSpouseInfo(hrSpouseInfo);
        }

        // Add LOG info for Approval Purpose.
        HrSpouseInfoLog logInfo = new HrSpouseInfoLog();
        HrSpouseInfo dbModelInfo = hrSpouseInfoRepository.findOne(hrSpouseInfo.getId());
        logInfo = conversionService.getSpouseLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrSpouseInfoLogRepository.save(logInfo);
        hrSpouseInfo.setLogId(logInfo.getId());
        hrSpouseInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrSpouseInfo result = hrSpouseInfoRepository.save(hrSpouseInfo);
        hrSpouseInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrSpouseInfo", hrSpouseInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrSpouseInfos -> get all the hrSpouseInfos.
     */
    @RequestMapping(value = "/hrSpouseInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrSpouseInfo>> getAllHrSpouseInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrSpouseInfos");
        Page<HrSpouseInfo> page = hrSpouseInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrSpouseInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrSpouseInfos/:id -> get the "id" hrSpouseInfo.
     */
    @RequestMapping(value = "/hrSpouseInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrSpouseInfo> getHrSpouseInfo(@PathVariable Long id) {
        log.debug("REST request to get HrSpouseInfo : {}", id);
        HrSpouseInfo hrSpouseInfo = hrSpouseInfoRepository.findOne(id);
        return Optional.ofNullable(hrSpouseInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrSpouseInfos/:id -> delete the "id" hrSpouseInfo.
     */
    @RequestMapping(value = "/hrSpouseInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrSpouseInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrSpouseInfo : {}", id);
        hrSpouseInfoRepository.delete(id);
        hrSpouseInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrSpouseInfo", id.toString())).build();
    }

    @RequestMapping(value = "/hrSpouseInfos/checknid/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeCode(@RequestParam String value)
    {
        Optional<HrSpouseInfo> modelInfo = hrSpouseInfoRepository.findOneByNationalId(value.toLowerCase());
        log.debug("hrSpouseInfos by nationalId: "+value+", Stat: "+Optional.empty().equals(modelInfo));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(modelInfo))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * SEARCH  /_search/hrSpouseInfos/:query -> search for the hrSpouseInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrSpouseInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrSpouseInfo> searchHrSpouseInfos(@PathVariable String query) {
        log.debug("REST request to search HrSpouseInfos for query {}", query);
        return StreamSupport
            .stream(hrSpouseInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmployeeInfos/my -> get my employee.
     */
    @RequestMapping(value = "/hrSpouseInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrSpouseInfo> getMySpouse()
    {
        log.debug("REST request to search hrSpouse for current user");

        List<HrSpouseInfo> modelInfoList = hrSpouseInfoRepository.findAllByEmployeeIsCurrentUser();
        return modelInfoList;
    }


    /**
     * GET  /hrSpouseInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrSpouseInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrSpouseInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrSpouseInfo modelInfo = hrSpouseInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrSpouseInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrSpouseInfoLog modelLog = hrSpouseInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrSpouseInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrSpouseInfoLog modelLog = hrSpouseInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrSpouseInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getSpouseModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrSpouseInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrSpouseInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrSpouseInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrSpouseInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrSpouseInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrSpouseInfos List : logStatus: {} ",logStatus);
        List<HrSpouseInfo> modelList = hrSpouseInfoRepository.findAllByLogStatus(logStatus);

        HrSpouseInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrSpouseInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrSpouseInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrSpouseInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrSpouseInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrSpouseInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrSpouseInfo modelInfo = hrSpouseInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Transfer");
        if(modelInfo.getLogId() != 0)
        {
            HrSpouseInfoLog modelLog = hrSpouseInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
