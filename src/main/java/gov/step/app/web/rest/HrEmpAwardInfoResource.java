package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAwardInfo;
import gov.step.app.domain.HrEmpAwardInfoLog;
import gov.step.app.repository.HrEmpAwardInfoLogRepository;
import gov.step.app.repository.HrEmpAwardInfoRepository;
import gov.step.app.repository.search.HrEmpAwardInfoSearchRepository;
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
 * REST controller for managing HrEmpAwardInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAwardInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAwardInfoResource.class);

    @Inject
    private HrEmpAwardInfoRepository hrEmpAwardInfoRepository;

    @Inject
    private HrEmpAwardInfoSearchRepository hrEmpAwardInfoSearchRepository;

    @Inject
    private HrEmpAwardInfoLogRepository hrEmpAwardInfoLogRepository;

    @Inject
    private HrmConversionService conversionService;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    /**
     * POST  /hrEmpAwardInfos -> Create a new hrEmpAwardInfo.
     */
    @RequestMapping(value = "/hrEmpAwardInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAwardInfo> createHrEmpAwardInfo(@Valid @RequestBody HrEmpAwardInfo hrEmpAwardInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpAwardInfo : {}", hrEmpAwardInfo);
        if (hrEmpAwardInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAwardInfo", "idexists", "A new hrEmpAwardInfo cannot already have an ID")).body(null);
        }
        if(hrEmpAwardInfo.getLogStatus() < HRMManagementConstant.APPROVAL_STATUS_ADMIN_ENTRY)
        {
            hrEmpAwardInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpAwardInfo.getGoOrderDoc())
            .fileName(hrEmpAwardInfo.getGoOrderDocName())
            .contentType(hrEmpAwardInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.AWARD_GOORDER_FILE_DIR);

        goFile = fileUtils.saveFileAsByte(goFile);
        hrEmpAwardInfo.setGoOrderDoc(new byte[2]);
        hrEmpAwardInfo.setGoOrderDocName(goFile.fileName());

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEmpAwardInfo.getCertDoc())
            .fileName(hrEmpAwardInfo.getCertDocName())
            .contentType(hrEmpAwardInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.AWARD_CERT_FILE_DIR);

        certFile = fileUtils.saveFileAsByte(certFile);
        hrEmpAwardInfo.setCertDoc(new byte[2]);
        hrEmpAwardInfo.setCertDocName(certFile.fileName());

        HrEmpAwardInfo result = hrEmpAwardInfoRepository.save(hrEmpAwardInfo);
        hrEmpAwardInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAwardInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAwardInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAwardInfos -> Updates an existing hrEmpAwardInfo.
     */
    @RequestMapping(value = "/hrEmpAwardInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAwardInfo> updateHrEmpAwardInfo(@Valid @RequestBody HrEmpAwardInfo hrEmpAwardInfo) throws URISyntaxException
    {
        log.debug("REST request to update HrEmpAwardInfo : {}", hrEmpAwardInfo);
        if (hrEmpAwardInfo.getId() == null)
        {
            return createHrEmpAwardInfo(hrEmpAwardInfo);
        }

        //Saving Go Order Document to Dir.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileData(hrEmpAwardInfo.getGoOrderDoc())
            .fileName(hrEmpAwardInfo.getGoOrderDocName())
            .contentType(hrEmpAwardInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.AWARD_GOORDER_FILE_DIR);

        goFile = fileUtils.updateFileAsByte(goFile);
        hrEmpAwardInfo.setGoOrderDoc(new byte[1]);
        hrEmpAwardInfo.setGoOrderDocName(goFile.fileName());

        //Saving Certificate Document to Dir.
        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileData(hrEmpAwardInfo.getCertDoc())
            .fileName(hrEmpAwardInfo.getCertDocName())
            .contentType(hrEmpAwardInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.AWARD_CERT_FILE_DIR);

        certFile = fileUtils.updateFileAsByte(certFile);
        hrEmpAwardInfo.setCertDoc(new byte[1]);
        hrEmpAwardInfo.setCertDocName(certFile.fileName());



        // Add LOG info for Approval Purpose.
        HrEmpAwardInfoLog logInfo = new HrEmpAwardInfoLog();
        HrEmpAwardInfo dbModelInfo = hrEmpAwardInfoRepository.findOne(hrEmpAwardInfo.getId());
        logInfo = conversionService.getAwardLogFromSource(dbModelInfo);
        logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);

        logInfo = hrEmpAwardInfoLogRepository.save(logInfo);

        hrEmpAwardInfo.setLogId(logInfo.getId());
        hrEmpAwardInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);



        HrEmpAwardInfo result = hrEmpAwardInfoRepository.save(hrEmpAwardInfo);
        hrEmpAwardInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAwardInfo", hrEmpAwardInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAwardInfos -> get all the hrEmpAwardInfos.
     */
    @RequestMapping(value = "/hrEmpAwardInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAwardInfo>> getAllHrEmpAwardInfos(Pageable pageable)
        throws URISyntaxException
    {
        log.debug("REST request to get a page of HrEmpAwardInfos");
        Page<HrEmpAwardInfo> page = hrEmpAwardInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAwardInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAwardInfos/:id -> get the "id" hrEmpAwardInfo.
     */
    @RequestMapping(value = "/hrEmpAwardInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAwardInfo> getHrEmpAwardInfo(@PathVariable Long id)
    {
        log.debug("REST request to get HrEmpAwardInfo : {}", id);
        HrEmpAwardInfo hrEmpAwardInfo = hrEmpAwardInfoRepository.findOne(id);

        // Read file from Disk and assign to object.
        MiscFileInfo goFile = new MiscFileInfo();
        goFile.fileName(hrEmpAwardInfo.getGoOrderDocName())
            .contentType(hrEmpAwardInfo.getGoOrderDocContentType())
            .filePath(HRMManagementConstant.AWARD_GOORDER_FILE_DIR);
        goFile = fileUtils.readFileAsByte(goFile);
        hrEmpAwardInfo.setGoOrderDoc(goFile.fileData());

        MiscFileInfo certFile = new MiscFileInfo();
        certFile.fileName(hrEmpAwardInfo.getCertDocName())
            .contentType(hrEmpAwardInfo.getCertDocContentType())
            .filePath(HRMManagementConstant.AWARD_CERT_FILE_DIR);
        certFile = fileUtils.readFileAsByte(certFile);
        hrEmpAwardInfo.setCertDoc(certFile.fileData());

        return Optional.ofNullable(hrEmpAwardInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAwardInfos/:id -> delete the "id" hrEmpAwardInfo.
     */
    @RequestMapping(value = "/hrEmpAwardInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAwardInfo(@PathVariable Long id)
    {
        log.debug("REST request to delete HrEmpAwardInfo : {}", id);
        hrEmpAwardInfoRepository.delete(id);
        hrEmpAwardInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAwardInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAwardInfos/:query -> search for the hrEmpAwardInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAwardInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAwardInfo> searchHrEmpAwardInfos(@PathVariable String query)
    {
        log.debug("REST request to search HrEmpAwardInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpAwardInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpAwardInfos -> get all the hrEmpAwardInfos.
     */
    @RequestMapping(value = "/hrEmpAwardInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAwardInfo> getAllHrTrainingInfosByCurrentEmployee()
        throws URISyntaxException
    {
        log.debug("REST request to get a page of hrEmpAwardInfos by LoggedIn Employee");
        List<HrEmpAwardInfo> awardList = hrEmpAwardInfoRepository.findAllByEmployeeIsCurrentUser();
        for(HrEmpAwardInfo awardInfo : awardList)
        {
            // Read file from Disk and assign to object.
            MiscFileInfo goFile = new MiscFileInfo();
            goFile.fileName(awardInfo.getGoOrderDocName())
                .contentType(awardInfo.getGoOrderDocContentType())
                .filePath(HRMManagementConstant.AWARD_GOORDER_FILE_DIR);
            goFile = fileUtils.readFileAsByte(goFile);
            awardInfo.setGoOrderDoc(goFile.fileData());

            MiscFileInfo certFile = new MiscFileInfo();
            certFile.fileName(awardInfo.getCertDocName())
                .contentType(awardInfo.getCertDocContentType())
                .filePath(HRMManagementConstant.AWARD_CERT_FILE_DIR);
            certFile = fileUtils.readFileAsByte(certFile);
            awardInfo.setCertDoc(certFile.fileData());
        }
        return awardList;
    }


    /**
     * GET  /hrEmpAwardInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmpAwardInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmpAwardInfos POST: Type: {} ID: {}, comment : {}",approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmpAwardInfo modelInfo = hrEmpAwardInfoRepository.findOne(approvalDto.getEntityId());

        if(approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT))
        {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmpAwardInfoRepository.save(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAwardInfoLog modelLog = hrEmpAwardInfoLogRepository.findOne(modelInfo.getLogId());
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAwardInfoLogRepository.save(modelLog);
            }
        }
        else
        {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if(modelInfo.getLogId() != 0)
            {
                HrEmpAwardInfoLog modelLog = hrEmpAwardInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmpAwardInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getAwardModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmpAwardInfoRepository.save(modelInfo);
            }
            else
            {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmpAwardInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmpAwardInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmpAwardInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmpAwardInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmpAwardInfos List : logStatus: {} ",logStatus);
        List<HrEmpAwardInfo> modelList = hrEmpAwardInfoRepository.findAllByLogStatus(logStatus);

        HrEmpAwardInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for(HrEmpAwardInfo modelInfo : modelList)
        {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if(modelInfo.getLogId() != 0)
            {
                modelLogInfo = hrEmpAwardInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /HrEmpAwardInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmpAwardInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmpAwardInfos Model and Log of id: {} ",entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmpAwardInfo modelInfo = hrEmpAwardInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Award");
        if(modelInfo.getLogId() != 0)
        {
            HrEmpAwardInfoLog modelLog = hrEmpAwardInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }

}
