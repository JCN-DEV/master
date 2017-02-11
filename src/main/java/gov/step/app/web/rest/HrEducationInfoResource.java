package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEducationInfo;
import gov.step.app.domain.HrEducationInfoLog;
import gov.step.app.repository.HrEducationInfoLogRepository;
import gov.step.app.repository.HrEducationInfoRepository;
import gov.step.app.repository.search.HrEducationInfoSearchRepository;
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
 * REST controller for managing HrEducationInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEducationInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEducationInfoResource.class);

    @Inject
    private HrEducationInfoRepository hrEducationInfoRepository;

    @Inject
    private HrEducationInfoSearchRepository hrEducationInfoSearchRepository;

    @Inject
    private HrEducationInfoLogRepository hrEducationInfoLogRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    @Inject
    private HrmConversionService conversionService;

    /**
     * POST  /hrEducationInfos -> Create a new hrEducationInfo.
     */
    @RequestMapping(value = "/hrEducationInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEducationInfo> createHrEducationInfo(@Valid @RequestBody HrEducationInfo hrEducationInfo) throws URISyntaxException {
        log.debug("REST request to save HrEducationInfo : {}", hrEducationInfo);
        if (hrEducationInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEducationInfo", "idexists", "A new hrEducationInfo cannot already have an ID")).body(null);
        }
        if(hrEducationInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEducationInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEducationInfo.getCertificateDoc())
            .fileName(hrEducationInfo.getCertificateDocName())
            .contentType(hrEducationInfo.getCertificateDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_CERT_FILE_DIR);

        certFile = fileUtils.saveFileAsByte(certFile);
        hrEducationInfo.setCertificateDocName(certFile.fileName());
        hrEducationInfo.setCertificateDoc(new byte[2]);

        //Saving Transcript Document to Dir.
        MiscFileInfo transFile = new MiscFileInfo();
        transFile.fileData(hrEducationInfo.getTranscriptDoc())
            .fileName(hrEducationInfo.getTranscriptDocName())
            .contentType(hrEducationInfo.getTranscriptDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_TRANS_FILE_DIR);

        transFile = fileUtils.saveFileAsByte(transFile);
        hrEducationInfo.setTranscriptDocName(transFile.fileName());
        hrEducationInfo.setTranscriptDoc(new byte[2]);

        // Saving Data to Database.
        HrEducationInfo result = hrEducationInfoRepository.save(hrEducationInfo);
        hrEducationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEducationInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEducationInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEducationInfos -> Updates an existing hrEducationInfo.
     */
    @RequestMapping(value = "/hrEducationInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEducationInfo> updateHrEducationInfo(@Valid @RequestBody HrEducationInfo hrEducationInfo) throws URISyntaxException {
        log.debug("REST request to update HrEducationInfo : {}", hrEducationInfo);
        if (hrEducationInfo.getId() == null) {
            return createHrEducationInfo(hrEducationInfo);
        }

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEducationInfo.getCertificateDoc())
            .fileName(hrEducationInfo.getCertificateDocName())
            .contentType(hrEducationInfo.getCertificateDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_CERT_FILE_DIR);

        certFile = fileUtils.updateFileAsByte(certFile);
        hrEducationInfo.setCertificateDoc(new byte[1]);

        //Saving Transcript Document to Dir.
        MiscFileInfo transFile = new MiscFileInfo();
        transFile.fileData(hrEducationInfo.getTranscriptDoc())
            .fileName(hrEducationInfo.getTranscriptDocName())
            .contentType(hrEducationInfo.getTranscriptDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_TRANS_FILE_DIR);

        transFile = fileUtils.updateFileAsByte(transFile);
        hrEducationInfo.setTranscriptDoc(new byte[1]);

        // Add LOG info for Approval Purpose.
        HrEducationInfoLog logInfo = new HrEducationInfoLog();
        HrEducationInfo dbModelInfo = hrEducationInfoRepository.findOne(hrEducationInfo.getId());
        logInfo = conversionService.getLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
        logInfo = hrEducationInfoLogRepository.save(logInfo);
        hrEducationInfo.setLogId(logInfo.getId());
        hrEducationInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEducationInfo result = hrEducationInfoRepository.save(hrEducationInfo);
        hrEducationInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEducationInfo", hrEducationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEducationInfos -> get all the hrEducationInfos.
     */
    @RequestMapping(value = "/hrEducationInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEducationInfo>> getAllHrEducationInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEducationInfos");
        Page<HrEducationInfo> page = hrEducationInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEducationInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEducationInfos/:id -> get the "id" hrEducationInfo.
     */
    @RequestMapping(value = "/hrEducationInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEducationInfo> getHrEducationInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEducationInfo : {}", id);
        HrEducationInfo hrEducationInfo = hrEducationInfoRepository.findOne(id);

        // Read file from Disk and assign to object.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileName(hrEducationInfo.getCertificateDocName())
            .contentType(hrEducationInfo.getCertificateDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_CERT_FILE_DIR);
        certFile = fileUtils.readFileAsByte(certFile);
        hrEducationInfo.setCertificateDoc(certFile.fileData());

        MiscFileInfo transFile = new MiscFileInfo();
        transFile.fileName(hrEducationInfo.getTranscriptDocName())
            .contentType(hrEducationInfo.getTranscriptDocContentType())
            .filePath(HRMManagementConstant.EDUCATION_TRANS_FILE_DIR);
        transFile = fileUtils.readFileAsByte(transFile);
        hrEducationInfo.setTranscriptDoc(transFile.fileData());


        return Optional.ofNullable(hrEducationInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEducationInfos/:id -> delete the "id" hrEducationInfo.
     */
    @RequestMapping(value = "/hrEducationInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEducationInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEducationInfo : {}", id);
        hrEducationInfoRepository.delete(id);
        hrEducationInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEducationInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEducationInfos/:query -> search for the hrEducationInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEducationInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEducationInfo> searchHrEducationInfos(@PathVariable String query) {
        log.debug("REST request to search HrEducationInfos for query {}", query);
        return StreamSupport
            .stream(hrEducationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEducationInfos -> get all the hrEducationInfos.
     */
    @RequestMapping(value = "/hrEducationInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEducationInfo> getAllHrEducationInfosByCurrentEmployee()
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEducationInfos by LoggedIn Employee");
        List<HrEducationInfo> educationList = hrEducationInfoRepository.findAllByEmployeeIsCurrentUser();

        for(HrEducationInfo educationInfo : educationList)
        {
            // Read file from Disk and assign to object.
            MiscFileInfo certFile = new MiscFileInfo();
            certFile.fileName(educationInfo.getCertificateDocName())
                .contentType(educationInfo.getCertificateDocContentType())
                .filePath(HRMManagementConstant.EDUCATION_CERT_FILE_DIR);
            certFile = fileUtils.readFileAsByte(certFile);
            educationInfo.setCertificateDoc(certFile.fileData());

            MiscFileInfo transFile = new MiscFileInfo();
            transFile.fileName(educationInfo.getTranscriptDocName())
                .contentType(educationInfo.getTranscriptDocContentType())
                .filePath(HRMManagementConstant.EDUCATION_TRANS_FILE_DIR);
            transFile = fileUtils.readFileAsByte(transFile);
            educationInfo.setTranscriptDoc(transFile.fileData());
        }

        return educationList;
    }

    /**
     * GET  /hrEducationInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEducationInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEducationInfo POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEducationInfo modelInfo = hrEducationInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEducationInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEducationInfoLog modelLog = hrEducationInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEducationInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEducationInfoLog modelLog = hrEducationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEducationInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEducationInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEducationInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEducationInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEducationInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEducationInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEducationInfo List : logStatus: {} ",logStatus);
        List<HrEducationInfo> modelList = hrEducationInfoRepository.findAllByLogStatus(logStatus);

        HrEducationInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEducationInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEducationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEducationInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEducationInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEducationInfo Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEducationInfo modelInfo = hrEducationInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Education");
        if(modelInfo.getLogId() != 0)
        {
            HrEducationInfoLog modelLog = hrEducationInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }
}
