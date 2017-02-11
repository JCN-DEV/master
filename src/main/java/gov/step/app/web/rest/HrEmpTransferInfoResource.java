package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpTransferInfo;
import gov.step.app.domain.HrEmpTransferInfoLog;
import gov.step.app.repository.HrEmpTransferInfoLogRepository;
import gov.step.app.repository.HrEmpTransferInfoRepository;
import gov.step.app.repository.search.HrEmpTransferInfoSearchRepository;
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
 * REST controller for managing HrEmpTransferInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpTransferInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpTransferInfoResource.class);

    @Inject
    private HrEmpTransferInfoRepository hrEmpTransferInfoRepository;

    @Inject
    private HrEmpTransferInfoSearchRepository hrEmpTransferInfoSearchRepository;

    @Inject
    private HrEmpTransferInfoLogRepository hrEmpTransferInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpTransferInfos -> Create a new hrEmpTransferInfo.
     */
    @RequestMapping(value = "/hrEmpTransferInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferInfo> createHrEmpTransferInfo(@Valid @RequestBody HrEmpTransferInfo hrEmpTransferInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpTransferInfo : {}", hrEmpTransferInfo);
        if (hrEmpTransferInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpTransferInfo", "idexists", "A new hrEmpTransferInfo cannot already have an ID")).body(null);
        }
        if(hrEmpTransferInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpTransferInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpTransferInfo.getGoDoc())
            .fileName(hrEmpTransferInfo.getGoDocName())
            .contentType(hrEmpTransferInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.TRANSFER_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpTransferInfo.setGoDoc(new byte[1]);
        hrEmpTransferInfo.setGoDocName(goFile.fileName());

        HrEmpTransferInfo result = hrEmpTransferInfoRepository.save(hrEmpTransferInfo);
        hrEmpTransferInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpTransferInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpTransferInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpTransferInfos -> Updates an existing hrEmpTransferInfo.
     */
    @RequestMapping(value = "/hrEmpTransferInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferInfo> updateHrEmpTransferInfo(@Valid @RequestBody HrEmpTransferInfo hrEmpTransferInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpTransferInfo : {}", hrEmpTransferInfo);
        if (hrEmpTransferInfo.getId() == null) {
            return createHrEmpTransferInfo(hrEmpTransferInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpTransferInfo.getGoDoc())
            .fileName(hrEmpTransferInfo.getGoDocName())
            .contentType(hrEmpTransferInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.TRANSFER_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpTransferInfo.setGoDoc(new byte[1]);
        hrEmpTransferInfo.setGoDocName(goFile.fileName());

        // Add LOG info for Approval Purpose.
        HrEmpTransferInfoLog logInfo = new HrEmpTransferInfoLog();
        HrEmpTransferInfo dbModelInfo = hrEmpTransferInfoRepository.findOne(hrEmpTransferInfo.getId());
        logInfo = conversionService.getTransferLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpTransferInfoLogRepository.save(logInfo);
        hrEmpTransferInfo.setLogId(logInfo.getId());
        hrEmpTransferInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrEmpTransferInfo result = hrEmpTransferInfoRepository.save(hrEmpTransferInfo);
        hrEmpTransferInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpTransferInfo", hrEmpTransferInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpTransferInfos -> get all the hrEmpTransferInfos.
     */
    @RequestMapping(value = "/hrEmpTransferInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpTransferInfo>> getAllHrEmpTransferInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpTransferInfos");
        Page<HrEmpTransferInfo> page = hrEmpTransferInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpTransferInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpTransferInfos/:id -> get the "id" hrEmpTransferInfo.
     */
    @RequestMapping(value = "/hrEmpTransferInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTransferInfo> getHrEmpTransferInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpTransferInfo : {}", id);
        HrEmpTransferInfo hrEmpTransferInfo = hrEmpTransferInfoRepository.findOne(id);

        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpTransferInfo.getGoDocName())
            .contentType(hrEmpTransferInfo.getGoDocContentType())
            .filePath(HRMManagementConstant.TRANSFER_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpTransferInfo.setGoDoc(goFile.fileData());

        return Optional.ofNullable(hrEmpTransferInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpTransferInfos/:id -> delete the "id" hrEmpTransferInfo.
     */
    @RequestMapping(value = "/hrEmpTransferInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpTransferInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpTransferInfo : {}", id);
        hrEmpTransferInfoRepository.delete(id);
        hrEmpTransferInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpTransferInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpTransferInfos/:query -> search for the hrEmpTransferInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpTransferInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTransferInfo> searchHrEmpTransferInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpTransferInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpTransferInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpTransferInfos -> get all the hrEmpTransferInfos/my.
     */
    @RequestMapping(value = "/hrEmpTransferInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTransferInfo> getAllHrTransferInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrTransfer by LoggedIn Employee");
        List<HrEmpTransferInfo> transferList = hrEmpTransferInfoRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpTransferInfo transferInfo : transferList)
        {
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(transferInfo.getGoDocName())
                .contentType(transferInfo.getGoDocContentType())
                .filePath(HRMManagementConstant.TRANSFER_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            transferInfo.setGoDoc(goFile.fileData());

        }
        return transferList;
    }

    /**
     * GET  /hrEmpTransferInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpTransferInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpTransferInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpTransferInfo modelInfo = hrEmpTransferInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpTransferInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTransferInfoLog modelLog = hrEmpTransferInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTransferInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTransferInfoLog modelLog = hrEmpTransferInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTransferInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getTransferModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpTransferInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpTransferInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpTransferInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpTransferInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpTransferInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpTransferInfos List : logStatus: {} ",logStatus);
        List<HrEmpTransferInfo> modelList = hrEmpTransferInfoRepository.findAllByLogStatus(logStatus);

        HrEmpTransferInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpTransferInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpTransferInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpTransferInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpTransferInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpTransferInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpTransferInfo modelInfo = hrEmpTransferInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Transfer");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpTransferInfoLog modelLog = hrEmpTransferInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
