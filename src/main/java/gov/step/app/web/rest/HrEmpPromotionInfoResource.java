package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpPromotionInfo;
import gov.step.app.domain.HrEmpPromotionInfoLog;
import gov.step.app.repository.HrEmpPromotionInfoLogRepository;
import gov.step.app.repository.HrEmpPromotionInfoRepository;
import gov.step.app.repository.search.HrEmpPromotionInfoSearchRepository;
import gov.step.app.service.HrmConversionService;
import gov.step.app.service.constnt.HRMManagementConstant;
import gov.step.app.service.util.MiscFileInfo;
import gov.step.app.service.util.MiscFileUtilities;
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
 * REST controller for managing HrEmpPromotionInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpPromotionInfoResource
{
    private final Logger log = LoggerFactory.getLogger(HrEmpPromotionInfoResource.class);

    @Inject
    private HrEmpPromotionInfoRepository hrEmpPromotionInfoRepository;

    @Inject
    private HrEmpPromotionInfoSearchRepository hrEmpPromotionInfoSearchRepository;

    @Inject
    private HrEmpPromotionInfoLogRepository hrEmpPromotionInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpPromotionInfos -> Create a new hrEmpPromotionInfo.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPromotionInfo> createHrEmpPromotionInfo(@Valid @RequestBody HrEmpPromotionInfo hrEmpPromotionInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpPromotionInfo : {}", hrEmpPromotionInfo);
        if (hrEmpPromotionInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpPromotionInfo", "idexists", "A new hrEmpPromotionInfo cannot already have an ID")).body(null);
        }
        if(hrEmpPromotionInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpPromotionInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpPromotionInfo.getGoDoc())
            .fileName(hrEmpPromotionInfo.getGoDocName())
            .contentType(hrEmpPromotionInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.PROMOTION_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpPromotionInfo.setGoDoc(new byte[1]);
        hrEmpPromotionInfo.setGoDocName(goFile.fileName());

        HrEmpPromotionInfo result = hrEmpPromotionInfoRepository.save(hrEmpPromotionInfo);
        hrEmpPromotionInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpPromotionInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpPromotionInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpPromotionInfos -> Updates an existing hrEmpPromotionInfo.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPromotionInfo> updateHrEmpPromotionInfo(@Valid @RequestBody HrEmpPromotionInfo hrEmpPromotionInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpPromotionInfo : {}", hrEmpPromotionInfo);
        if (hrEmpPromotionInfo.getId() == null) {
            return createHrEmpPromotionInfo(hrEmpPromotionInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpPromotionInfo.getGoDoc())
            .fileName(hrEmpPromotionInfo.getGoDocName())
            .contentType(hrEmpPromotionInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.PROMOTION_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpPromotionInfo.setGoDoc(new byte[1]);
        hrEmpPromotionInfo.setGoDocName(goFile.fileName());

        // Add LOG info for Approval Purpose.
        HrEmpPromotionInfoLog logInfo = new HrEmpPromotionInfoLog();
        HrEmpPromotionInfo dbModelInfo = hrEmpPromotionInfoRepository.findOne(hrEmpPromotionInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpPromotionInfoLogRepository.save(logInfo);
        hrEmpPromotionInfo.setLogId(logInfo.getId());
        hrEmpPromotionInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmpPromotionInfo result = hrEmpPromotionInfoRepository.save(hrEmpPromotionInfo);
        hrEmpPromotionInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpPromotionInfo", hrEmpPromotionInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpPromotionInfos -> get all the hrEmpPromotionInfos.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpPromotionInfo>> getAllHrEmpPromotionInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpPromotionInfos");
        Page<HrEmpPromotionInfo> page = hrEmpPromotionInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpPromotionInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpPromotionInfos/:id -> get the "id" hrEmpPromotionInfo.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPromotionInfo> getHrEmpPromotionInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpPromotionInfo : {}", id);
        HrEmpPromotionInfo hrEmpPromotionInfo = hrEmpPromotionInfoRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpPromotionInfo.getGoDocName())
            .contentType(hrEmpPromotionInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.PROMOTION_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpPromotionInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(hrEmpPromotionInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpPromotionInfos/:id -> delete the "id" hrEmpPromotionInfo.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpPromotionInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpPromotionInfo : {}", id);
        hrEmpPromotionInfoRepository.delete(id);
        hrEmpPromotionInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpPromotionInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpPromotionInfos/:query -> search for the hrEmpPromotionInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpPromotionInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPromotionInfo> searchHrEmpPromotionInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpPromotionInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpPromotionInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpTransferInfos -> get all the hrEmpTransferInfos/my.
     */
    @RequestMapping(value = "/hrEmpPromotionInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPromotionInfo> getAllHrTransferInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of HrPromotion by LoggedIn Employee");
        List<HrEmpPromotionInfo> promotionList = hrEmpPromotionInfoRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpPromotionInfo hrEmpPromotionInfo : promotionList)
        {
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(hrEmpPromotionInfo.getGoDocName())
                .contentType(hrEmpPromotionInfo.getGoDocContentType())
                .filePath(HRMManagementConstant.PROMOTION_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            hrEmpPromotionInfo.setGoDoc(goFile.fileData());
        }
        return promotionList;
    }

    /**
     * GET  /hrEmpPromotionInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpPromotionInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpPromotionInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpPromotionInfo modelInfo = hrEmpPromotionInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpPromotionInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPromotionInfoLog modelLog = hrEmpPromotionInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPromotionInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPromotionInfoLog modelLog = hrEmpPromotionInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPromotionInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpPromotionInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpPromotionInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpPromotionInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpPromotionInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpPromotionInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus)
    {
        log.debug("REST request to Approve hrEmpPromotionInfo List : logStatus: {} ",logStatus);
        List<HrEmpPromotionInfo> modelList = hrEmpPromotionInfoRepository.findAllByLogStatus(logStatus);

        HrEmpPromotionInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpPromotionInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpPromotionInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpPromotionInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpPromotionInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpPromotionInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpPromotionInfo modelInfo = hrEmpPromotionInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Education");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpPromotionInfoLog modelLog = hrEmpPromotionInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
