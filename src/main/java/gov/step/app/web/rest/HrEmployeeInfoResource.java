package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.designationType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.HrEmployeeInfoSearchRepository;
import gov.step.app.service.HrmConversionService;
import gov.step.app.service.MailService;
import gov.step.app.service.constnt.HRMManagementConstant;
import gov.step.app.service.util.MiscFileInfo;
import gov.step.app.service.util.MiscFileUtilities;
import gov.step.app.service.util.MiscUtilities;
import gov.step.app.web.rest.dto.HrmApprovalDto;
import gov.step.app.web.rest.dto.HrmDashboardDto;
import gov.step.app.web.rest.dto.MiscParamDto;
import gov.step.app.web.rest.jdbc.dao.HRMJdbcDao;
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
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing HrEmployeeInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmployeeInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmployeeInfoResource.class);

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private HrEmployeeInfoSearchRepository hrEmployeeInfoSearchRepository;

    @Inject
    private HrEmployeeInfoLogRepository hrEmployeeInfoLogRepository;

    MiscFileUtilities fileUtils = new MiscFileUtilities();

    @Inject
    private HrmConversionService conversionService;

    @Inject
    private HRMJdbcDao hrmJdbcDao;

    @Inject
    private MailService mailService;

    @Inject
    private HrDesignationSetupRepository hrDesignationSetupRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    /**
     * POST  /hrEmployeeInfos -> Create a new hrEmployeeInfo.
     */
    @RequestMapping(value = "/hrEmployeeInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> createHrEmployeeInfo(@Valid @RequestBody HrEmployeeInfo hrEmployeeInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmployeeInfo : {}", hrEmployeeInfo);
        if (hrEmployeeInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmployeeInfo", "idexists", "A new hrEmployeeInfo cannot already have an ID")).body(null);
        }

        hrEmployeeInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_FREE);
        String tempPass = hrEmployeeInfo.getLogComments();
        hrEmployeeInfo.setLogComments("");
        HrEmployeeInfo result = hrEmployeeInfoRepository.save(hrEmployeeInfo);

        //Sent Confirmation Email to Employee Email.
        hrEmployeeInfo.setLogComments(tempPass);
        mailService.sendEmployeeBasicInfoEmail(hrEmployeeInfo);

        hrEmployeeInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmployeeInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmployeeInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmployeeInfos -> Updates an existing hrEmployeeInfo.
     */
    @RequestMapping(value = "/hrEmployeeInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> updateHrEmployeeInfo(@Valid @RequestBody HrEmployeeInfo hrEmployeeInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmployeeInfo : {}", hrEmployeeInfo);
        if (hrEmployeeInfo.getId() == null) {
            return createHrEmployeeInfo(hrEmployeeInfo);
        }

        HrEmployeeInfo tempEmpInfo = hrEmployeeInfoRepository.findOne(hrEmployeeInfo.getId());

        //Saving Employee Image Document to Dir.
        MiscFileInfo empPhoto = new MiscFileInfo();
        try{
            empPhoto.fileData(hrEmployeeInfo.getEmpPhoto())
                .fileName(hrEmployeeInfo.getImageName())
                .contentType(hrEmployeeInfo.getEmpPhotoContentType())
                .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
            if (tempEmpInfo.getImageName() == null ||
                tempEmpInfo.getImageName().length() < HRMManagementConstant.MINIMUM_IMG_NAME_LENGTH) {
                empPhoto = fileUtils.saveFileAsByte(empPhoto);
            } else {
                empPhoto.fileName(tempEmpInfo.getImageName());
                empPhoto = fileUtils.updateFileAsByte(empPhoto);
            }
        }catch(Exception ex){
            log.error("Exception: UpdateHrInfo empPhoto "+ex.getMessage());
        }

        hrEmployeeInfo.setEmpPhoto(new byte[1]);
        hrEmployeeInfo.setImageName(empPhoto.fileName());

        //Saving Quota Centificate Image Document to Dir.
        MiscFileInfo quotaCert = new MiscFileInfo();
        try{
            quotaCert.fileData(hrEmployeeInfo.getQuotaCert())
                .fileName(hrEmployeeInfo.getQuotaCertName())
                .contentType(hrEmployeeInfo.getQuotaCertContentType())
                .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);

            if (tempEmpInfo.getQuotaCertName() == null ||
                tempEmpInfo.getQuotaCertName().length() < HRMManagementConstant.MINIMUM_IMG_NAME_LENGTH) {
                quotaCert = fileUtils.saveFileAsByte(quotaCert);
            } else {
                quotaCert.fileName(tempEmpInfo.getQuotaCertName());
                quotaCert = fileUtils.updateFileAsByte(quotaCert);
            }
        }catch(Exception ex){
            log.error("Exception: UpdateHrInfo quotaCertImg "+ex.getMessage());
        }

        hrEmployeeInfo.setQuotaCert(new byte[1]);
        hrEmployeeInfo.setQuotaCertName(quotaCert.fileName());

        try {
            // Add LOG info for Approval Purpose.
            HrEmployeeInfoLog logInfo = new HrEmployeeInfoLog();
            HrEmployeeInfo dbModelInfo = hrEmployeeInfoRepository.findOne(hrEmployeeInfo.getId());
            logInfo = conversionService.getLogFromSource(dbModelInfo);
            logInfo.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            logInfo = hrEmployeeInfoLogRepository.save(logInfo);
            hrEmployeeInfo.setLogId(logInfo.getId());
            hrEmployeeInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);
        } catch (Exception ex) {
            log.error("EmployeeLog saving error:" + ex);
            hrEmployeeInfo.setLogId(-1l);
        }
        hrEmployeeInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_LOCKED);

        HrEmployeeInfo result = hrEmployeeInfoRepository.save(hrEmployeeInfo);
        hrEmployeeInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmployeeInfo", hrEmployeeInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmployeeInfos -> get all the hrEmployeeInfos.
     */
    @RequestMapping(value = "/hrEmployeeInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmployeeInfo>> getAllHrEmployeeInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmployeeInfos");
        Page<HrEmployeeInfo> page = hrEmployeeInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmployeeInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmployeeInfos -> get all the hrEmployeeInfos.
     */
    @RequestMapping(value = "/hrEmployeeInfosDetail",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmployeeInfo>> getAllHrEmployeeInfosDetail(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmployeeInfos");
        Page<HrEmployeeInfo> page = hrEmployeeInfoRepository.findAll(pageable);
        for (HrEmployeeInfo hrEmployeeInfo : page.getContent()) {
            MiscFileInfo empPhoto = new MiscFileInfo();
            empPhoto.fileName(hrEmployeeInfo.getImageName())
                .contentType(hrEmployeeInfo.getEmpPhotoContentType())
                .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
            empPhoto = fileUtils.readFileAsByte(empPhoto);
            hrEmployeeInfo.setEmpPhoto(empPhoto.fileData());
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmployeeInfosDetail");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmployeeInfos/:id -> get the "id" hrEmployeeInfo.
     */
    @RequestMapping(value = "/hrEmployeeInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> getHrEmployeeInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmployeeInfo : {}", id);
        HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(id);

        MiscFileInfo empPhoto = new MiscFileInfo();
        empPhoto.fileName(hrEmployeeInfo.getImageName())
            .contentType(hrEmployeeInfo.getEmpPhotoContentType())
            .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
        empPhoto = fileUtils.readFileAsByte(empPhoto);
        hrEmployeeInfo.setEmpPhoto(empPhoto.fileData());


        MiscFileInfo quotaCert = new MiscFileInfo();
        quotaCert.fileName(hrEmployeeInfo.getQuotaCertName())
            .contentType(hrEmployeeInfo.getQuotaCertContentType())
            .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
        quotaCert = fileUtils.readFileAsByte(quotaCert);
        hrEmployeeInfo.setQuotaCert(quotaCert.fileData());

        return Optional.ofNullable(hrEmployeeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmployeeInfos/:id -> delete the "id" hrEmployeeInfo.
     */
    @RequestMapping(value = "/hrEmployeeInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmployeeInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmployeeInfo : {}", id);
        hrEmployeeInfoRepository.delete(id);
        hrEmployeeInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmployeeInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmployeeInfos/:query -> search for the hrEmployeeInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmployeeInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> searchHrEmployeeInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmployeeInfos for query {}", query);
        return StreamSupport
            .stream(hrEmployeeInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmployeeInfos/my -> get my employee.
     */
    @RequestMapping(value = "/hrEmployeeInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> getMyEmployee() {
        log.debug("REST request to search HrEmployeeInfos for current user");

        HrEmployeeInfo modelInfo = hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser();

        MiscFileInfo empPhoto = new MiscFileInfo();
        empPhoto.fileName(modelInfo.getImageName())
            .contentType(modelInfo.getEmpPhotoContentType())
            .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
        empPhoto = fileUtils.readFileAsByte(empPhoto);
        modelInfo.setEmpPhoto(empPhoto.fileData());

        MiscFileInfo quotaCert = new MiscFileInfo();
        quotaCert.fileName(modelInfo.getQuotaCertName())
            .contentType(modelInfo.getQuotaCertContentType())
            .filePath(HRMManagementConstant.EMPLOYEE_PHOTO_FILE_DIR);
        quotaCert = fileUtils.readFileAsByte(quotaCert);
        modelInfo.setQuotaCert(quotaCert.fileData());

        return Optional.ofNullable(modelInfo)
            .map(employee -> new ResponseEntity<>(
                employee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /hrEmployeeInfos/newempid -> get my newempid.
     */
    @RequestMapping(value = "/hrEmployeeInfos/newempid",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<String> getUniqueEmpId() {
        log.debug("REST new Employee ID hrEmployeeInfos for new user");

        MiscUtilities miscUtil = new MiscUtilities();
        String newEmpId = miscUtil.getUniqueEmployeeId();

        return new ResponseEntity<String>(newEmpId, HttpStatus.OK);
    }

    @RequestMapping(value = "/hrEmployeeInfos/checknid/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeCode(@RequestParam String value) {
        Optional<HrEmployeeInfo> modelInfo = hrEmployeeInfoRepository.findOneByNationalId(value.toLowerCase());
        log.debug("hrEmployeeInfos by code: " + value + ", Stat: " + Optional.empty().equals(modelInfo));
        Map map = new HashMap();
        map.put("value", value);
        if (Optional.empty().equals(modelInfo)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }

    /**
     * GET  /hrEmployeeInfosApprover/ -> process the approval request.
     */
    @RequestMapping(value = "/hrEmployeeInfosApprover",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> updateModelApproval(@Valid @RequestBody HrmApprovalDto approvalDto) {
        log.debug("REST request to Approve hrEmployeeInfo POST: Type: {} ID: {}, comment : {}", approvalDto.getActionType(), approvalDto.getEntityId(), approvalDto.getLogComments());

        HrEmployeeInfo modelInfo = hrEmployeeInfoRepository.findOne(approvalDto.getEntityId());

        if (approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.APPROVAL_LOG_STATUS_ACCEPT)) {
            log.debug("REST request to APROVING ID: {}", approvalDto.getEntityId());
            modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_APPROVED);
            modelInfo.setActiveStatus(true);
            modelInfo = hrEmployeeInfoRepository.save(modelInfo);
            if (modelInfo.getLogId() != 0) {
                HrEmployeeInfoLog modelLog = hrEmployeeInfoLogRepository.findOne(modelInfo.getLogId());
                if (modelLog != null) {
                    modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_APPROVED);
                    modelLog.setActionBy(modelInfo.getCreateBy());
                    modelLog.setActionComments(approvalDto.getLogComments());
                    modelLog = hrEmployeeInfoLogRepository.save(modelLog);
                }
            }
        } else {
            log.debug("REST request to REJECTING ID: {}", approvalDto.getEntityId());
            if (modelInfo.getLogId() != 0) {
                HrEmployeeInfoLog modelLog = hrEmployeeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                modelLog.setLogStatus(HRMManagementConstant.APPROVAL_LOG_STATUS_ROLLBACKED);
                modelLog.setActionBy(modelInfo.getCreateBy());
                modelLog.setActionComments(approvalDto.getLogComments());
                modelLog = hrEmployeeInfoLogRepository.save(modelLog);

                modelInfo = conversionService.getModelFromLog(modelLog, modelInfo);
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo.setActiveStatus(false);
                modelInfo = hrEmployeeInfoRepository.save(modelInfo);
            } else {
                modelInfo.setLogStatus(HRMManagementConstant.APPROVAL_STATUS_REJECTED);
                modelInfo.setActiveStatus(false);
                modelInfo.setLogComments(approvalDto.getLogComments());
                modelInfo = hrEmployeeInfoRepository.save(modelInfo);
            }
        }
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("hrEmployeeInfo", approvalDto.getEntityId().toString())).build();
    }

    /**
     * GET  /hrEmployeeInfosApprover/:logStatus -> get address list by log status.
     */
    @RequestMapping(value = "/hrEmployeeInfosApprover/{logStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmApprovalDto> getModelListByLogStatus(@PathVariable Long logStatus) {
        log.debug("REST request to Approve hrEmployeeInfo List : logStatus: 0 or 5 ");
        List<HrEmployeeInfo> modelList = hrEmployeeInfoRepository.findAllEmployeeByStatuses();

        HrEmployeeInfoLog modelLogInfo = null;
        List<HrmApprovalDto> modelDtoList = new ArrayList<HrmApprovalDto>();
        HrmApprovalDto dtoInfo = null;
        for (HrEmployeeInfo modelInfo : modelList) {
            dtoInfo = new HrmApprovalDto();
            dtoInfo.setEntityObject(modelInfo);
            if (modelInfo.getLogId() != 0) {
                modelLogInfo = hrEmployeeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
                dtoInfo.setEntityLogObject(modelLogInfo);
            }
            modelDtoList.add(dtoInfo);
        }
        return modelDtoList;
    }

    /**
     * GET  /hrEmployeeInfosApprover/log/:entityId -> load model and LogModel by entity id
     */
    @RequestMapping(value = "/hrEmployeeInfosApprover/log/{entityId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrmApprovalDto getModelAndLogObjectByModelId(@PathVariable String entityId) {
        log.debug("REST request to Log hrEmployeeInfo Model and Log of id: {} ", entityId);

        Long id = Long.parseLong(entityId);
        HrmApprovalDto approvalDto = new HrmApprovalDto();
        HrEmployeeInfo modelInfo = hrEmployeeInfoRepository.findOne(id);
        approvalDto.setEntityObject(modelInfo);
        approvalDto.setEntityId(id);
        approvalDto.setEntityName("Employee");
        if (modelInfo.getLogId() != 0) {
            HrEmployeeInfoLog modelLog = hrEmployeeInfoLogRepository.findOneByIdAndLogStatus(modelInfo.getLogId(), HRMManagementConstant.APPROVAL_LOG_STATUS_ACTIVE);
            approvalDto.setEntityLogObject(modelLog);
        }
        return approvalDto;
    }

    @RequestMapping(value = "/hrEmployeeInfos/findEmploy/getEmp/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> getHrEmployeeInfoByIds(@PathVariable Long id) {
        log.debug("REST request to get HrEmploymentInfo : {}", id);
        HrEmployeeInfo hrEmployeeInfo = new HrEmployeeInfo();
        if (hrEmployeeInfoRepository.findOne(id) != null) {
            hrEmployeeInfo = hrEmployeeInfoRepository.findOne(id);
        }/*else{
            hrEmployeeInfo.setFullName("Not Found");
        }*/
        //HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmployeeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/hrEmployeeInfos/findEmployees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmployeeInfo> getHrEmployeeInfoById(@PathVariable String id) {
        log.debug("REST request to get HrEmploymentInfo : {}", id);
        HrEmployeeInfo hrEmployeeInfo = new HrEmployeeInfo();
        if (hrEmployeeInfoRepository.findByEmployeeId(id) != null) {
            hrEmployeeInfo = hrEmployeeInfoRepository.findByEmployeeId(id);
        }/*else{
            hrEmployeeInfo.setFullName("Not Found");
        }*/
        //HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmployeeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /hrEmployeeInfosDashboard -> get approved and rejected list through view.
     */
    @RequestMapping(value = "/hrEmployeeInfosDashboard",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrmDashboardDto> getApprovedRejectedDashboardData(Pageable pageable) {
        log.debug("REST request to Get Approved and Rejected List Data by View  ");
        List<HrmDashboardDto> approvalRejectedList = hrmJdbcDao.findApproveRejectedList();
        return approvalRejectedList;
    }

    /**
     * Check Total hrEmployeeInfos desiglimitcheck by designation id
     *
     * @param desigId
     * @return
     */
    @RequestMapping(value = "/hrEmployeeInfosDesigCheck/{orgtype}/{desigId}/{refid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEmployeeDepartmentLimit(@PathVariable String orgtype, @PathVariable Long desigId, @PathVariable Long refid) {
        log.debug("hrEmployeeInfosDesigCheck by designation :" + desigId+", orgtype: "+orgtype+", refid:"+refid);

        int totalEmployeeByDesig = 0;

        if(orgtype.equalsIgnoreCase("Institute"))
        {
            totalEmployeeByDesig = hrmJdbcDao.getEmployeeWiseDesignationCountByInstitute(desigId, refid);
        }
        else
        {
            totalEmployeeByDesig = hrmJdbcDao.getEmployeeWiseDesignationCountByOrganization(desigId, refid);
        }

        Map map = new HashMap();
        map.put("orgtype", orgtype);
        map.put("desigId", desigId);
        map.put("refid", refid);
        HrDesignationSetup designationInfo = hrDesignationSetupRepository.findOne(desigId);
        map.put("totalEmployee", totalEmployeeByDesig);
        if (designationInfo.getElocattedPosition() > totalEmployeeByDesig) {
            map.put("isValid", true);
        } else map.put("isValid", false);
        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    /**
     * GET  /hrEmployeeInfosByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/hrEmployeeInfosByFilter/{fieldName}/{fieldValue}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> getModelListByFilter(@PathVariable String fieldName, @PathVariable String fieldValue) {
        log.debug("REST request to hrEmployeeInfosByFilter List : Filed: {} , Value: {} ", fieldName, fieldValue);

        List<HrEmployeeInfo> modelList = hrmJdbcDao.findAllEmployeeByFilter(fieldName, fieldValue);
        log.debug("List Len:  ", modelList.size());
        return modelList;
    }

    /**
     * GET  /hrEmployeeInfosByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/hrEmployeeInfosListByWorkArea/{areaid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> hrEmployeeInfosListByWorkArea(@PathVariable long areaid) {
        log.debug("REST request to hrEmployeeInfosListByWorkArea List : WorkArea: {} ", areaid);

        List<HrEmployeeInfo> modelList = hrEmployeeInfoRepository.findEmployeeByWorkarea(areaid);
        log.debug("List Len:  ", modelList.size());
        return modelList;
    }

    /**
     * POST  /hrEmployeeInfosOffOn -> Create a new hrEmployeeInfosOffOn.
     */
    @RequestMapping(value = "/hrEmployeeInfosOffOn",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public void activateDeactiveEmployeeUser(@Valid @RequestBody HrmApprovalDto approvalDto) throws URISyntaxException
    {
        log.debug("REST request to save hrEmployeeInfosOffOn : {}", approvalDto.getActionType());
        if (approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.EMPLOYEE_DEACTIVATE))
        {
            HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(approvalDto.getEntityId());
            //HrEmployeeInfo hrEmployeeInfo = (HrEmployeeInfo) approvalDto.getEntityObject();
            log.debug("Deactivating employee : {}", hrEmployeeInfo.getFullName());
            //Employee Deactivation
            if (hrEmployeeInfo.getId() != null) {
                hrEmployeeInfo.setLogStatus(HRMManagementConstant.HR_EMPLOYEE_DEACTIVATED);
                hrEmployeeInfo.setActiveStatus(false);
                hrEmployeeInfo.setLogComments(approvalDto.getLogComments());
                HrEmployeeInfo result = hrEmployeeInfoRepository.save(hrEmployeeInfo);
            }
            log.debug("Deactivating User : {}", hrEmployeeInfo.getUser().getLogin());
            //User Deactivation
            if (hrEmployeeInfo.getUser().getId() != null) {
                User user = hrEmployeeInfo.getUser();
                user.setActivated(false);
                userRepository.save(user);
            }
        }
        else if (approvalDto.getActionType().equalsIgnoreCase(HRMManagementConstant.EMPLOYEE_ACTIVATE))
        {
            HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(approvalDto.getEntityId());
            //HrEmployeeInfo hrEmployeeInfo = (HrEmployeeInfo) approvalDto.getEntityObject();
            log.debug("Activating employee : {}", hrEmployeeInfo.getFullName());
            //Employee Deactivation
            if (hrEmployeeInfo.getId() != null) {
                hrEmployeeInfo.setLogStatus(HRMManagementConstant.HR_EMPLOYEE_ACTIVATED);
                hrEmployeeInfo.setActiveStatus(true);
                hrEmployeeInfo.setLogComments("");
                HrEmployeeInfo result = hrEmployeeInfoRepository.save(hrEmployeeInfo);
            }

            log.debug("Activating User : {}", hrEmployeeInfo.getUser().getLogin());
            //User Deactivation
            if (hrEmployeeInfo.getUser().getId() != null) {
                User user = hrEmployeeInfo.getUser();
                user.setActivated(true);
                userRepository.save(user);
            }
        }
    }

    /**
     * GET  /hrEmployeeInfosByDateRange -> get all the hrEmployeeInfosByDateRange by BirthDate and Active Status
     */
    @RequestMapping(value = "/hrEmployeeInfosByDateRange",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmployeeInfo>> getEmployeeByDateRange(
        @Valid @RequestBody MiscParamDto miscParamDto , Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrEmployeeInfosByDateRange by dateMin:{} and dateMax:{}: ",miscParamDto.getMinDate().toString(), miscParamDto.getMaxDate().toString());

        Page<HrEmployeeInfo> page = hrEmployeeInfoRepository.findEmployeeListBirthDateRange(miscParamDto.getMinDate(), miscParamDto.getMaxDate(), pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmployeeInfosByDateRange");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmployeeInfosListByDesigLevel/:desigLevel-> get employee by desigLevel.
     */
    @RequestMapping(value = "/hrEmployeeInfosListByDesigLevel/{desigLevel}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> hrEmployeeInfosListByDesigLevel(@PathVariable int desigLevel) throws Exception {
        log.debug("REST request to hrEmployeeInfosListByDesigLevl List : desigLevel: {} ", desigLevel);

        List<HrEmployeeInfo> modelList = hrEmployeeInfoRepository.findEmployeeListByDesignationLevel(desigLevel);
        log.debug("List Len:  ", modelList.size());
        return modelList;
    }

    /**
     * GET  /hrEmployeeInfosListByDesigLevel/:desigLevel-> get employee by desigLevel.
     */
    @RequestMapping(value = "/hrEmployeeInfosListByDesigType/{desigType}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmployeeInfo> hrEmployeeInfosListByDesigType(@PathVariable String desigType) throws Exception
    {
        log.debug("REST request to hrEmployeeInfosListByDesigType List type: {}", desigType);
        designationType employeeType = designationType.valueOf(desigType);
        if(employeeType==null)
            employeeType = designationType.HRM;

        List<HrEmployeeInfo> modelList = hrEmployeeInfoRepository.findAllEmployeeListByType(employeeType);
        log.debug("List Len:  ", modelList.size());
        return modelList;
    }

    /**
     * POST  /hrEmployeeInfoTransfer -> Transfer a new hrEmployeeInfo.
     */
    @RequestMapping(value = "/hrEmployeeInfoTransfer",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> transferEmployee(@Valid @RequestBody HrEmployeeInfo hrEmployeeInfo) throws URISyntaxException {
        log.debug("REST request to save transferEmployee : {}", hrEmployeeInfo);

        Map map = new HashMap();

        try {
            // Save employee information into LOG .
            HrEmployeeInfoLog logInfo = new HrEmployeeInfoLog();
            HrEmployeeInfo dbModelInfo = hrEmployeeInfoRepository.findOne(hrEmployeeInfo.getId());
            logInfo = conversionService.getLogFromSource(dbModelInfo);
            logInfo.setLogStatus(HRMManagementConstant.DTE_TRANSFER_IN_LOG_STATUS);
            logInfo.setActionComments(designationType.DTE_Employee.toString());

            logInfo = hrEmployeeInfoLogRepository.save(logInfo);

            // update fields value for transfer within employee entity
            hrEmployeeInfoRepository.updateEmployeeForDteTransfer(hrEmployeeInfo.getId(), designationType.DTE_Employee, hrEmployeeInfo.getOrganizationType(),
                hrEmployeeInfo.getWorkArea().getId(), hrEmployeeInfo.getWorkAreaDtl().getId(), hrEmployeeInfo.getDesignationInfo().getId(), hrEmployeeInfo.getLogId());

            // Update authority
            // Remove ROLE_HRM_USER role for the employee user.
            // Add ROLE_DTE_EMPLOYEE role to employee user. If ROLE_DTE_EMPLOYEE not available, create new role for ROLE_DTE_EMPLOYEE.

            Authority hrmEmployeeAuthority = authorityRepository.findOne(HRMManagementConstant.ROLE_HRM_USER_NAME);
            Authority dteEmployeeAuthority = authorityRepository.findOne(HRMManagementConstant.ROLE_DTE_EMPLOYEE_NAME);
            if(dteEmployeeAuthority==null)
            {
                log.debug("REST Adding Authority, as not exist : {} ", HRMManagementConstant.ROLE_DTE_EMPLOYEE_NAME);
                dteEmployeeAuthority=new Authority();
                dteEmployeeAuthority.setName(HRMManagementConstant.ROLE_DTE_EMPLOYEE_NAME);
                authorityRepository.save(dteEmployeeAuthority);
            }

            User employeeUser = userRepository.findOne(hrEmployeeInfo.getUser().getId());
            Set<Authority> employeeAuthorities = employeeUser.getAuthorities();

            if(! employeeAuthorities.contains(dteEmployeeAuthority))
            {
                log.debug("REST mapping user authority : {} with user {}", HRMManagementConstant.ROLE_DTE_EMPLOYEE_NAME, employeeUser.getLogin());
                employeeAuthorities.add(dteEmployeeAuthority);
                employeeAuthorities.remove(hrmEmployeeAuthority);
                employeeUser.setAuthorities(employeeAuthorities);
                userRepository.save(employeeUser);
            }

            // Update teacher information like active status change or role change.
            // Call the teacher repository method here.

            map.put("desigType", designationType.DTE_Employee);
            map.put("newRole", HRMManagementConstant.ROLE_DTE_EMPLOYEE_NAME);
            map.put("success", "true");
            map.put("message", "");

        }
        catch (Exception ex)
        {
            log.error("EmployeeLog saving error:" + ex);
            map.put("success", "false");
            map.put("message", ex.getLocalizedMessage());
        }

        return new ResponseEntity<Map>(map, HttpStatus.OK);
    }
}
