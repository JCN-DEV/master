package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpPublicationInfo;
import gov.step.app.domain.HrEmpPublicationInfoLog;
import gov.step.app.repository.HrEmpPublicationInfoLogRepository;
import gov.step.app.repository.HrEmpPublicationInfoRepository;
import gov.step.app.repository.search.HrEmpPublicationInfoSearchRepository;
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
 * REST controller for managing HrEmpPublicationInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpPublicationInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpPublicationInfoResource.class);

    @Inject
    private HrEmpPublicationInfoRepository hrEmpPublicationInfoRepository;

    @Inject
    private HrEmpPublicationInfoSearchRepository hrEmpPublicationInfoSearchRepository;

    @Inject
    private HrEmpPublicationInfoLogRepository hrEmpPublicationInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpPublicationInfos -> Create a new hrEmpPublicationInfo.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPublicationInfo> createHrEmpPublicationInfo(@Valid @RequestBody HrEmpPublicationInfo hrEmpPublicationInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpPublicationInfo : {}", hrEmpPublicationInfo);
        if (hrEmpPublicationInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpPublicationInfo", "idexists", "A new hrEmpPublicationInfo cannot already have an ID")).body(null);
        }
        if(hrEmpPublicationInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpPublicationInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Publication Document to Dir.
        MiscFileInfo publFile = new MiscFileInfo();
        publFile.fileData(hrEmpPublicationInfo.getPublicationDoc())
            .fileName(hrEmpPublicationInfo.getPublicationDocName())
            .contentType(hrEmpPublicationInfo.getPublicationDocContentType())
            .filePath(HRMManagementConstant.PUBLICATION_FILE_DIR);

        publFile = fileUtils.saveFileAsByte(publFile);
        hrEmpPublicationInfo.setPublicationDoc(new byte[2]);
        hrEmpPublicationInfo.setPublicationDocName(publFile.fileName());

        HrEmpPublicationInfo result = hrEmpPublicationInfoRepository.save(hrEmpPublicationInfo);
        hrEmpPublicationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpPublicationInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpPublicationInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpPublicationInfos -> Updates an existing hrEmpPublicationInfo.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPublicationInfo> updateHrEmpPublicationInfo(@Valid @RequestBody HrEmpPublicationInfo hrEmpPublicationInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpPublicationInfo : {}", hrEmpPublicationInfo);
        if (hrEmpPublicationInfo.getId() == null) {
            return createHrEmpPublicationInfo(hrEmpPublicationInfo);
        }

        //Saving Publication Document to Dir.
        MiscFileInfo publFile = new MiscFileInfo();
        publFile.fileData(hrEmpPublicationInfo.getPublicationDoc())
            .fileName(hrEmpPublicationInfo.getPublicationDocName())
            .contentType(hrEmpPublicationInfo.getPublicationDocContentType())
            .filePath(HRMManagementConstant.PUBLICATION_FILE_DIR);

        publFile = fileUtils.updateFileAsByte(publFile);
        hrEmpPublicationInfo.setPublicationDoc(new byte[1]);
        hrEmpPublicationInfo.setPublicationDocName(publFile.fileName());


        // Add LOG info for Approval Purpose.
        HrEmpPublicationInfoLog logInfo = new HrEmpPublicationInfoLog();
        HrEmpPublicationInfo dbModelInfo = hrEmpPublicationInfoRepository.findOne(hrEmpPublicationInfo.getId());
        logInfo = conversionService.getPublicationLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpPublicationInfoLogRepository.save(logInfo);
        hrEmpPublicationInfo.setLogId(logInfo.getId());
        hrEmpPublicationInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrEmpPublicationInfo result = hrEmpPublicationInfoRepository.save(hrEmpPublicationInfo);
        hrEmpPublicationInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpPublicationInfo", hrEmpPublicationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpPublicationInfos -> get all the hrEmpPublicationInfos.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpPublicationInfo>> getAllHrEmpPublicationInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpPublicationInfos");
        Page<HrEmpPublicationInfo> page = hrEmpPublicationInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpPublicationInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpPublicationInfos/:id -> get the "id" hrEmpPublicationInfo.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpPublicationInfo> getHrEmpPublicationInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpPublicationInfo : {}", id);
        HrEmpPublicationInfo hrEmpPublicationInfo = hrEmpPublicationInfoRepository.findOne(id);

        // Read file from Disk and assign to object.
        MiscFileInfo publFile = new MiscFileInfo();
        publFile.fileName(hrEmpPublicationInfo.getPublicationDocName())
            .contentType(hrEmpPublicationInfo.getPublicationDocContentType())
            .filePath(HRMManagementConstant.PUBLICATION_FILE_DIR);
        publFile = fileUtils.readFileAsByte(publFile);
        hrEmpPublicationInfo.setPublicationDoc(publFile.fileData());

        return Optional.ofNullable(hrEmpPublicationInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpPublicationInfos/:id -> delete the "id" hrEmpPublicationInfo.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpPublicationInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpPublicationInfo : {}", id);
        hrEmpPublicationInfoRepository.delete(id);
        hrEmpPublicationInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpPublicationInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpPublicationInfos/:query -> search for the hrEmpPublicationInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpPublicationInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPublicationInfo> searchHrEmpPublicationInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpPublicationInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpPublicationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpPublicationInfos -> get all the hrEmpPublicationInfos.
     */
    @RequestMapping(value = "/hrEmpPublicationInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpPublicationInfo> getAllHrPublicationInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpPublicationInfos by LoggedIn Employee");
        List<HrEmpPublicationInfo> modelInfoList = hrEmpPublicationInfoRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpPublicationInfo modelInfo : modelInfoList)
        {
            MiscFileInfo publFile = new MiscFileInfo();
            publFile.fileName(modelInfo.getPublicationDocName())
                .contentType(modelInfo.getPublicationDocContentType())
                .filePath(HRMManagementConstant.PUBLICATION_FILE_DIR);
            publFile = fileUtils.readFileAsByte(publFile);
            modelInfo.setPublicationDoc(publFile.fileData());

        }
        return modelInfoList;
    }

    /**
     * GET  /hrEmpPublicationInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpPublicationInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpPublicationInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpPublicationInfo modelInfo = hrEmpPublicationInfoRepository.findOne(approvalDto.getEntityId());
        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpPublicationInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPublicationInfoLog modelLog = hrEmpPublicationInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPublicationInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpPublicationInfoLog modelLog = hrEmpPublicationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpPublicationInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getPublicationModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpPublicationInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpPublicationInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpPublicationInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpPublicationInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpPublicationInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpPublicationInfos List : logStatus: {} ",logStatus);
        List<HrEmpPublicationInfo> modelList = hrEmpPublicationInfoRepository.findAllByLogStatus(logStatus);

        HrEmpPublicationInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpPublicationInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpPublicationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpPublicationInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpPublicationInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpPublicationInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpPublicationInfo modelInfo = hrEmpPublicationInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Publication");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpPublicationInfoLog modelLog = hrEmpPublicationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
