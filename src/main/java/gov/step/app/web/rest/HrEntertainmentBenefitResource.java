package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEntertainmentBenefit;
import gov.step.app.domain.HrEntertainmentBenefitLog;
import gov.step.app.repository.HrEntertainmentBenefitLogRepository;
import gov.step.app.repository.HrEntertainmentBenefitRepository;
import gov.step.app.repository.search.HrEntertainmentBenefitSearchRepository;
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
 * REST controller for managing HrEntertainmentBenefit.
 */
@RestController
@RequestMapping("/api")
public class HrEntertainmentBenefitResource {

    private final Logger log = LoggerFactory.getLogger(HrEntertainmentBenefitResource.class);

    @Inject
    private HrEntertainmentBenefitRepository hrEntertainmentBenefitRepository;

    @Inject
    private HrEntertainmentBenefitSearchRepository hrEntertainmentBenefitSearchRepository;

    @Inject
    private HrEntertainmentBenefitLogRepository hrEntertainmentBenefitLogRepository;

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEntertainmentBenefits -> Create a new hrEntertainmentBenefit.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEntertainmentBenefit> createHrEntertainmentBenefit(@Valid @RequestBody HrEntertainmentBenefit hrEntertainmentBenefit) throws URISyntaxException {
        log.debug("REST request to save HrEntertainmentBenefit : {}", hrEntertainmentBenefit);
        if (hrEntertainmentBenefit.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEntertainmentBenefit", "idexists", "A new hrEntertainmentBenefit cannot already have an ID")).body(null);
        }
        if(hrEntertainmentBenefit.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEntertainmentBenefit.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        HrEntertainmentBenefit result = hrEntertainmentBenefitRepository.save(hrEntertainmentBenefit);
        hrEntertainmentBenefitSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEntertainmentBenefits/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEntertainmentBenefit", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEntertainmentBenefits -> Updates an existing hrEntertainmentBenefit.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEntertainmentBenefit> updateHrEntertainmentBenefit(@Valid @RequestBody HrEntertainmentBenefit hrEntertainmentBenefit) throws URISyntaxException {
        log.debug("REST request to update HrEntertainmentBenefit : {}", hrEntertainmentBenefit);
        if (hrEntertainmentBenefit.getId() == null) {
            return createHrEntertainmentBenefit(hrEntertainmentBenefit);
        }

        // Add LOG info for Approval Purpose.
        HrEntertainmentBenefitLog logInfo = new HrEntertainmentBenefitLog();
        HrEntertainmentBenefit dbModelInfo = hrEntertainmentBenefitRepository.findOne(hrEntertainmentBenefit.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEntertainmentBenefitLogRepository.save(logInfo);
        hrEntertainmentBenefit.setLogId(logInfo.getId());
        hrEntertainmentBenefit.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEntertainmentBenefit result = hrEntertainmentBenefitRepository.save(hrEntertainmentBenefit);
        hrEntertainmentBenefitSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEntertainmentBenefit", hrEntertainmentBenefit.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEntertainmentBenefits -> get all the hrEntertainmentBenefits.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEntertainmentBenefit>> getAllHrEntertainmentBenefits(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEntertainmentBenefits");
        Page<HrEntertainmentBenefit> page = hrEntertainmentBenefitRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEntertainmentBenefits");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEntertainmentBenefits/:id -> get the "id" hrEntertainmentBenefit.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEntertainmentBenefit> getHrEntertainmentBenefit(@PathVariable Long id) {
        log.debug("REST request to get HrEntertainmentBenefit : {}", id);
        HrEntertainmentBenefit hrEntertainmentBenefit = hrEntertainmentBenefitRepository.findOne(id);
        return Optional.ofNullable(hrEntertainmentBenefit)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEntertainmentBenefits/:id -> delete the "id" hrEntertainmentBenefit.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEntertainmentBenefit(@PathVariable Long id) {
        log.debug("REST request to delete HrEntertainmentBenefit : {}", id);
        hrEntertainmentBenefitRepository.delete(id);
        hrEntertainmentBenefitSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEntertainmentBenefit", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEntertainmentBenefits/:query -> search for the hrEntertainmentBenefit corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEntertainmentBenefits/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEntertainmentBenefit> searchHrEntertainmentBenefits(@PathVariable String query) {
        log.debug("REST request to search HrEntertainmentBenefits for query {}", query);
        return StreamSupport
            .stream(hrEntertainmentBenefitSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /hrEntertainmentBenefits -> get all the hrEntertainmentBenefits.
     */
    @RequestMapping(value = "/hrEntertainmentBenefits/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEntertainmentBenefit> getAllHrModelInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpPublicationInfos by LoggedIn Employee");
        List<HrEntertainmentBenefit> modelInfoList = hrEntertainmentBenefitRepository.findAllByEmployeeIsCurrentUser();

        return modelInfoList;
    }

    /**
     * GET  /hrEntertainmentBenefitsApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEntertainmentBenefitsApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEntertainmentBenefit POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEntertainmentBenefit modelInfo = hrEntertainmentBenefitRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEntertainmentBenefitRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEntertainmentBenefitLog modelLog = hrEntertainmentBenefitLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEntertainmentBenefitLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEntertainmentBenefitLog modelLog = hrEntertainmentBenefitLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEntertainmentBenefitLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEntertainmentBenefitRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEntertainmentBenefitRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEntertainmentBenefit", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEntertainmentBenefitsApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEntertainmentBenefitsApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEntertainmentBenefit List : logStatus: {} ",logStatus);
        List<HrEntertainmentBenefit> modelList = hrEntertainmentBenefitRepository.findAllByLogStatus(logStatus);

        HrEntertainmentBenefitLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEntertainmentBenefit modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEntertainmentBenefitLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEntertainmentBenefitsApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEntertainmentBenefitsApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEntertainmentBenefit Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEntertainmentBenefit modelInfo = hrEntertainmentBenefitRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("EntertainmentBenefit");
        if(modelInfo.getLogId() != 0)
        {
            HrEntertainmentBenefitLog modelLog = hrEntertainmentBenefitLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
