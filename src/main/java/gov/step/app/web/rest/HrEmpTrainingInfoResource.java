package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpTrainingInfo;
import gov.step.app.domain.HrEmpTrainingInfoLog;
import gov.step.app.repository.HrEmpTrainingInfoLogRepository;
import gov.step.app.repository.HrEmpTrainingInfoRepository;
import gov.step.app.repository.search.HrEmpTrainingInfoSearchRepository;
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
 * REST controller for managing HrEmpTrainingInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpTrainingInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpTrainingInfoResource.class);

    @Inject
    private HrEmpTrainingInfoRepository hrEmpTrainingInfoRepository;

    @Inject
    private HrEmpTrainingInfoSearchRepository hrEmpTrainingInfoSearchRepository;

    @Inject
    private HrEmpTrainingInfoLogRepository hrEmpTrainingInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpTrainingInfos -> Create a new hrEmpTrainingInfo.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTrainingInfo> createHrEmpTrainingInfo(@Valid @RequestBody HrEmpTrainingInfo hrEmpTrainingInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpTrainingInfo : {}", hrEmpTrainingInfo);
        if (hrEmpTrainingInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpTrainingInfo", "idexists", "A new hrEmpTrainingInfo cannot already have an ID")).body(null);
        }
        if(hrEmpTrainingInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpTrainingInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpTrainingInfo.getGoOrderDoc())
            .fileName(hrEmpTrainingInfo.getGoOrderDocName())
            .contentType(hrEmpTrainingInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.TRAINING_GOORDER_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpTrainingInfo.setGoOrderDoc(new byte[2]);
        hrEmpTrainingInfo.setGoOrderDocName(goFile.fileName());

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEmpTrainingInfo.getCertDoc())
            .fileName(hrEmpTrainingInfo.getCertDocName())
            .contentType(hrEmpTrainingInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.TRAINING_CERT_FILE_DIR);

        certFile = fileUtils.saveFileAsByte(certFile);
        hrEmpTrainingInfo.setCertDoc(new byte[2]);
        hrEmpTrainingInfo.setCertDocName(certFile.fileName());

        HrEmpTrainingInfo result = hrEmpTrainingInfoRepository.save(hrEmpTrainingInfo);
        hrEmpTrainingInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpTrainingInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpTrainingInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpTrainingInfos -> Updates an existing hrEmpTrainingInfo.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTrainingInfo> updateHrEmpTrainingInfo(@Valid @RequestBody HrEmpTrainingInfo hrEmpTrainingInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpTrainingInfo : {}", hrEmpTrainingInfo);
        if (hrEmpTrainingInfo.getId() == null) {
            return createHrEmpTrainingInfo(hrEmpTrainingInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpTrainingInfo.getGoOrderDoc())
            .fileName(hrEmpTrainingInfo.getGoOrderDocName())
            .contentType(hrEmpTrainingInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.TRAINING_GOORDER_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpTrainingInfo.setGoOrderDoc(new byte[1]);

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEmpTrainingInfo.getCertDoc())
            .fileName(hrEmpTrainingInfo.getCertDocName())
            .contentType(hrEmpTrainingInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.TRAINING_CERT_FILE_DIR);

        certFile = fileUtils.updateFileAsByte(certFile);
        hrEmpTrainingInfo.setCertDoc(new byte[1]);

        // Add LOG info for Approval Purpose.
        HrEmpTrainingInfoLog logInfo = new HrEmpTrainingInfoLog();
        HrEmpTrainingInfo dbModelInfo = hrEmpTrainingInfoRepository.findOne(hrEmpTrainingInfo.getId());
        logInfo = conversionService.getTrainingLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEmpTrainingInfoLogRepository.save(logInfo);
        hrEmpTrainingInfo.setLogId(logInfo.getId());
        hrEmpTrainingInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);


        HrEmpTrainingInfo result = hrEmpTrainingInfoRepository.save(hrEmpTrainingInfo);
        hrEmpTrainingInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpTrainingInfo", hrEmpTrainingInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpTrainingInfos -> get all the hrEmpTrainingInfos.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpTrainingInfo>> getAllHrEmpTrainingInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpTrainingInfos");
        Page<HrEmpTrainingInfo> page = hrEmpTrainingInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpTrainingInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpTrainingInfos/:id -> get the "id" hrEmpTrainingInfo.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpTrainingInfo> getHrEmpTrainingInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpTrainingInfo : {}", id);
        HrEmpTrainingInfo hrEmpTrainingInfo = hrEmpTrainingInfoRepository.findOne(id);

        // Read file from Disk and assign to object.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpTrainingInfo.getGoOrderDocName())
            .contentType(hrEmpTrainingInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.TRAINING_GOORDER_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpTrainingInfo.setGoOrderDoc(goFile.fileData());

        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileName(hrEmpTrainingInfo.getCertDocName())
            .contentType(hrEmpTrainingInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.TRAINING_CERT_FILE_DIR);
        certFile = fileUtils.readFileAsByte(certFile);
        hrEmpTrainingInfo.setCertDoc(certFile.fileData());

        return Optional.ofNullable(hrEmpTrainingInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpTrainingInfos/:id -> delete the "id" hrEmpTrainingInfo.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpTrainingInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpTrainingInfo : {}", id);
        hrEmpTrainingInfoRepository.delete(id);
        hrEmpTrainingInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpTrainingInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpTrainingInfos/:query -> search for the hrEmpTrainingInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpTrainingInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTrainingInfo> searchHrEmpTrainingInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpTrainingInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpTrainingInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpTrainingInfos -> get all the hrEmpTrainingInfos.
     */
    @RequestMapping(value = "/hrEmpTrainingInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpTrainingInfo> getAllHrTrainingInfosByCurrentEmployee()
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEducationInfos by LoggedIn Employee");
        List<HrEmpTrainingInfo> modelInfoList = hrEmpTrainingInfoRepository.findAllByEmployeeIsCurrentUser();

        for(HrEmpTrainingInfo modelInfo : modelInfoList)
        {
            // Read file from Disk and assign to object.
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(modelInfo.getGoOrderDocName())
                .contentType(modelInfo.getGoOrderDocContentType())
                .filePath(HRMManagementConstant.TRAINING_GOORDER_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            modelInfo.setGoOrderDoc(goFile.fileData());

            MiscFileInfo certFile = new MiscFileInfo();
            certFile.fileName(modelInfo.getCertDocName())
                .contentType(modelInfo.getCertDocContentType())
                .filePath(HRMManagementConstant.TRAINING_CERT_FILE_DIR);
            certFile = fileUtils.readFileAsByte(certFile);
            modelInfo.setCertDoc(certFile.fileData());
        }

        return modelInfoList;
    }

    /**
     * GET  /hrEmpTrainingInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpTrainingInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpTrainingInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpTrainingInfo modelInfo = hrEmpTrainingInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpTrainingInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTrainingInfoLog modelLog = hrEmpTrainingInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTrainingInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpTrainingInfoLog modelLog = hrEmpTrainingInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpTrainingInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getTrainingModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpTrainingInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpTrainingInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpTrainingInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpTrainingInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpTrainingInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpTrainingInfos List : logStatus: {} ",logStatus);
        List<HrEmpTrainingInfo> modelList = hrEmpTrainingInfoRepository.findAllByLogStatus(logStatus);

        HrEmpTrainingInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpTrainingInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpTrainingInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmpTrainingInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpTrainingInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpTrainingInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpTrainingInfo modelInfo = hrEmpTrainingInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Address");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpTrainingInfoLog modelLog = hrEmpTrainingInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
