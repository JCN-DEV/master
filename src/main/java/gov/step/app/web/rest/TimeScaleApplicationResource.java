package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeAnotherApplicationStatus;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.TimeScaleApplicationSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.dto.TimeScaleApplicationDto;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.AttachmentUtil;
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
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TimeScaleApplication.
 */
@RestController
@RequestMapping("/api")
public class TimeScaleApplicationResource {

    private final Logger log = LoggerFactory.getLogger(TimeScaleApplicationResource.class);

    @Inject
    private TimeScaleApplicationRepository timeScaleApplicationRepository;

    @Inject
    private TimeScaleApplicationSearchRepository timeScaleApplicationSearchRepository;

    @Inject
    private TimeScaleApplicationStatusLogRepository timeScaleApplicationStatusLogRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private MpoApplicationResource mpoApplicationResource;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;

    /**
     * POST  /timeScaleApplications -> Create a new timeScaleApplication.
     */
    @RequestMapping(value = "/timeScaleApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> createTimeScaleApplication(@Valid @RequestBody TimeScaleApplication timeScaleApplication) throws URISyntaxException {
        log.debug("REST request to save TimeScaleApplication : {}", timeScaleApplication);
        if (timeScaleApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new timeScaleApplication cannot already have an ID").body(null);
        }

        log.debug(timeScaleApplication.toString()+"*******************");
        InstEmployee instEmployee = null;
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmployeeresult.setTimescaleAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
        instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);

        //File file = timeScaleApplicationDto.getDisActionFile();

        //TimeScaleApplication tsa = convertTimeScaleToDomain(timeScaleApplicationDto);
        String filepath="/backup/mpo/";

        if(timeScaleApplication.getDisActionFile() != null){
            String filename=SecurityUtils.getCurrentUserLogin();
            try {
               // AttachmentUtil.saveAttachment(filepath,filename,);
                timeScaleApplication.setDisActionFileName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, timeScaleApplication.getDisActionFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
           // tsa.setDisActionFileName(filename);
        }

//        attachment.setFileContentName(AttachmentUtil.saveAttachment(filepath, filename, attachment.getFile()));

        timeScaleApplication.setDisActionFile(null);
        timeScaleApplication.setInstEmployee(instEmployeeresult);
        timeScaleApplication.setDate(new Date());
        timeScaleApplication.setDateCrated(LocalDate.now());
        timeScaleApplication.setDateModified(LocalDate.now());
        timeScaleApplication.setStatus(MpoApplicationStatusType.COMPLITE.getCode());
        TimeScaleApplication result = timeScaleApplicationRepository.save(timeScaleApplication);
        timeScaleApplicationSearchRepository.save(result);

        TimeScaleApplicationStatusLog timeScaleApplicationStatusLog = new TimeScaleApplicationStatusLog();
        timeScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeScaleApplicationStatusLog.setTimeScaleApplicationId(result);
        timeScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        timeScaleApplicationStatusLog.setStatus(result.getStatus());
        timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);

        timeScaleApplicationSearchRepository.save(result);

        instEmployeeresult.setTimescaleAppStatus(EmployeeAnotherApplicationStatus.PENDING.getCode());
        instEmployeeRepository.save(instEmployeeresult);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployeeresult.getName()+" has applied for Timescale.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(instEmployeeresult.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.created(new URI("/api/timeScaleApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScaleApplication", result.getId().toString()))
            .body(result);
    }


    /**
     * PUT  /timeScaleApplications -> Updates an existing timeScaleApplication.
     */
    @RequestMapping(value = "/timeScaleApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> updateTimeScaleApplication(@Valid @RequestBody TimeScaleApplication timeScaleApplication) throws URISyntaxException {
        log.debug("REST request to update TimeScaleApplication : {}", timeScaleApplication);
        if (timeScaleApplication.getId() == null) {
            return createTimeScaleApplication(timeScaleApplication);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG) && timeScaleApplication.getDgFinalApproval()) {
            timeScaleApplication.setStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            timeScaleApplication.setDateModified(LocalDate.now());

        }else{
            timeScaleApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(timeScaleApplication.getStatus()).getCode());

        }
        //InstEmployee instEmployee=null;
        //timeScaleApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(timeScaleApplication.getStatus()).getCode());
        TimeScaleApplication result = timeScaleApplicationRepository.save(timeScaleApplication);

        TimeScaleApplicationStatusLog timeScaleApplicationStatusLog = new TimeScaleApplicationStatusLog();
        timeScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeScaleApplicationStatusLog.setTimeScaleApplicationId(timeScaleApplication);
        timeScaleApplicationStatusLog.setStatus(result.getStatus());
        timeScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(timeScaleApplicationStatusLog.getStatus()).getRemarks());
        timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setTimescaleAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

        timeScaleApplicationSearchRepository.save(timeScaleApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplication", timeScaleApplication.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeScaleApplications -> Updates an existing timeScaleApplication.
     */
    @RequestMapping(value = "/timeScaleApplications/forward/{forwardTo}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> forwardTimeScaleApplication(@Valid @RequestBody TimeScaleApplication timeScaleApplication, @PathVariable Integer forwardTo, @RequestParam(required = false, name = "cause") String cause, @RequestParam(required = false, name = "memoNo") String memoNo) throws URISyntaxException {
        log.debug("REST request to update TimeScaleApplication : {}", timeScaleApplication);
        if (timeScaleApplication.getId() == null) {
            return createTimeScaleApplication(timeScaleApplication);
        }
        InstEmployee instEmployee=null;
        timeScaleApplication.setStatus(MpoApplicationStatusType.prevMpoApplicationStatusType(forwardTo).getCode());
        timeScaleApplication.setDateModified(LocalDate.now());
        if(memoNo != null){
            timeScaleApplication.setMemoNo(memoNo);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANEGINGCOMMITTEE)){
            timeScaleApplication.setCommitteeApproveDate(LocalDate.now());
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DEO)){
            timeScaleApplication.setDeoApproveDate(LocalDate.now());
        }
        TimeScaleApplication result = timeScaleApplicationRepository.save(timeScaleApplication);

        TimeScaleApplicationStatusLog timeScaleApplicationStatusLog = new TimeScaleApplicationStatusLog();
        timeScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeScaleApplicationStatusLog.setTimeScaleApplicationId(timeScaleApplication);
        timeScaleApplicationStatusLog.setStatus(result.getStatus());
        timeScaleApplicationStatusLog.setRemarks(
            MpoApplicationStatusType.getMpoApplicationStatusTypeByRole(mpoApplicationResource.getCurrentUserRoleForMpo()).getViewMessage() +
                " Forwarded to " +
                MpoApplicationStatusType.fromInt(forwardTo).getViewMessage());
        //timeScaleApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());

        timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setTimescaleAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

        timeScaleApplicationSearchRepository.save(timeScaleApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplication", timeScaleApplication.getId().toString()))
            .body(result);
    }



    /**
     * GET  /timeScaleApplications -> get all the timeScaleApplications.
     */
    @RequestMapping(value = "/timeScaleApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeScaleApplication>> getAllTimeScaleApplications(Pageable pageable)
        throws URISyntaxException {
        Page<TimeScaleApplication> page = timeScaleApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timeScaleApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timeScaleApplications/:id -> get the "id" timeScaleApplication.
     */
    @RequestMapping(value = "/timeScaleApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> getTimeScaleApplication(@PathVariable Long id) throws Exception {
        log.debug("REST request to get TimeScaleApplication : {}", id);
        TimeScaleApplication application = timeScaleApplicationRepository.findOne(id);
        String filepath="/backup/teacher_info/";

        if(application.getDisActionFileName() != null) {
            if(AttachmentUtil.retriveAttachment(filepath, application.getDisActionFileName()) != null){
                application.setDisActionFile(AttachmentUtil.retriveAttachment(filepath,  application.getDisActionFileName()));
               // instEmployeeresult.setImageName(instEmployeeresult.getImageName().substring(0, (instEmployeeresult.getImageName().length() - 17)));

            }
        }

        return Optional.ofNullable(application)
            .map(timeScaleApplication -> new ResponseEntity<>(
                timeScaleApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/timeScaleApplications/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> declineTimescaleApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        TimeScaleApplication timeScaleApplication=null;
        InstEmployee instEmployee=null;
        TimeScaleApplication result = null;
        TimeScaleApplicationStatusLog timeScaleApplicationStatusLog = new TimeScaleApplicationStatusLog();
        if(id>0){
            timeScaleApplication=timeScaleApplicationRepository.findOne(id);
            timeScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(timeScaleApplication.getStatus()).getDeclineRemarks());
            //timeScaleApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
            if(timeScaleApplication.getInstEmployee()!=null){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to mpoapplication condition");
                instEmployee=timeScaleApplication.getInstEmployee();
                instEmployee.setTimescaleAppStatus(EmployeeMpoApplicationStatus.DECLINED.getCode());
                instEmployeeRepository.save(instEmployee);
            }
            timeScaleApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = timeScaleApplicationRepository.save(timeScaleApplication);
            timeScaleApplicationSearchRepository.save(result);
        }


        log.debug("REST request to update MpoApplication : {}--------", timeScaleApplication);
        log.debug("REST request to update cause : {}--------", cause);
        if (timeScaleApplication == null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }

        timeScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        timeScaleApplicationStatusLog.setCause(cause);
        timeScaleApplicationStatusLog.setTimeScaleApplicationId(result);
        timeScaleApplicationStatusLog.setStatus(result.getStatus());
        //mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);

        //timeScaleApplicationst.save(mpoApplication);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your Timescale Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("timescale.application");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplication", timeScaleApplication.getId().toString()))
            .body(null);
    }

    /**
     * DELETE  /timeScaleApplications/:id -> delete the "id" timeScaleApplication.
     */
    @RequestMapping(value = "/timeScaleApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeScaleApplication(@PathVariable Long id) {
        log.debug("REST request to delete TimeScaleApplication : {}", id);
        timeScaleApplicationRepository.delete(id);
        timeScaleApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeScaleApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/timeScaleApplications/:query -> search for the timeScaleApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/timeScaleApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeScaleApplication> searchTimeScaleApplications(@PathVariable String query) {
        return StreamSupport
            .stream(timeScaleApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/timescaleApplications/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplication> getTimeScaleApplicationByEmployeeCode(@PathVariable String code)
        throws Exception {

        TimeScaleApplication application = timeScaleApplicationRepository.findByInstEmployeeCode(code);
        String filepath="/backup/teacher_info/";

        if(application != null && application.getDisActionFileName() != null) {
            if(AttachmentUtil.retriveAttachment(filepath, application.getDisActionFileName()) != null){
                application.setDisActionFile(AttachmentUtil.retriveAttachment(filepath,  application.getDisActionFileName()));
            }
        }
        return Optional.ofNullable(application)
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }

    @RequestMapping(value = "/timescaleApplications/timescaleList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getTimescaleList(Pageable pageable, @PathVariable Boolean status)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get Timescale list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        //List<TimeScaleApplication> timeScaleList = new ArrayList<TimeScaleApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        District deoDistrict=null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
            if (status) {
            if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                page = rptJdbcDao.findTimeScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
            }
            }else{
                page = rptJdbcDao.findTimeScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            }

        } else {
            if (status) {//approve list
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            /*blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        log.debug("-------------mpo status code----------------"+mpoStatus.getCode());
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByInstituteId(pageable, mpoStatus.getCode(),instGenInfo.getInstitute().getId());
                            //page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("approved status :"+mpoStatus.getCode());
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScaleApproveListByStatus(mpoStatus.getCode());
                        log.debug(page.toString());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else { //pending list
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) { //Role not Found
           /* blunk list return*/
                    page = null;
                } else { //Role Found
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("comes to pending list maneging committee");
                        page = rptJdbcDao.findTimeScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        // Time Scale Extension : Conditional Time Scale To AP
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("pending status :"+mpoStatus.getCode());
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScalePendingListByStatus(mpoStatus.getCode());

                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }
        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        if(page !=null){
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }

    //AP pending List

    @RequestMapping(value = "/timescaleApplications/timescaleApList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getTimescaleAPList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get Timescale list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        //List<TimeScaleApplication> timeScaleList = new ArrayList<TimeScaleApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        District deoDistrict=null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
            if (status) {
                if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                    page = rptJdbcDao.findTimeScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                }
            }else{
                page = rptJdbcDao.findTimeScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            }

        } else {
            if (status) {//approve list
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            /*blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        log.debug("-------------mpo status code----------------"+mpoStatus.getCode());
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByInstituteId(pageable, mpoStatus.getCode(),instGenInfo.getInstitute().getId());
                            //page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("approved status :"+mpoStatus.getCode());
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScaleApproveListByStatus(mpoStatus.getCode());
                        log.debug(page.toString());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else { //pending list
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) { //Role not Found
           /* blunk list return*/
                    page = null;
                } else { //Role Found
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("comes to pending list maneging committee");
                        page = rptJdbcDao.findTimeScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        // Time Scale Extension : Conditional Time Scale To AP
                        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                        log.debug("pending status :"+mpoStatus.getCode());
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        //page = rptJdbcDao.findTimeScalePendingListByStatus(mpoStatus.getCode());
                        if (SecurityUtils.isCurrentUserInRole("ROLE_FRONTDESK") || SecurityUtils.isCurrentUserInRole("ROLE_AD") || SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR") || SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
                            page = rptJdbcDao.findTimeScaleApPendingListByStatus(mpoStatus.getCode());
                            log.debug(page.toString());
                        }

                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimeScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }
        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        if(page !=null){
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }

/*
    @RequestMapping(value = "/timescaleApplications/timeScaleList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplication>> getMpoPayScaleList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        Page<MpoApplication> page = null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.APPROVEDBYDG;

        if (status) {

            //page = mpoApplicationRepository.findPayScaleAssignedList(pageable, mpoStatus.getCode());

        } else {
            //page = mpoApplicationRepository.findPayScaleNotAssignedList(pageable, mpoStatus.getCode());

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/timeScaleList");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }*/



    @RequestMapping(value = "/timescaleApplications/timescaleList/{status}/{levelId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getTimescaleListByStatusAndLevel(Pageable pageable, @PathVariable Boolean status, @PathVariable Long levelId)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        District deoDistrict=null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
        } else {
            if (status) {



                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            /*blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        log.debug("-------------mpo status code----------------"+mpoStatus.getCode());
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByInstituteId(pageable, mpoStatus.getCode(),instGenInfo.getInstitute().getId());
                            //page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findTimeScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimeScaleApproveListByStatus(mpoStatus.getCode());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimeScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findTimeScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findTimescalePendingListByDistrictIdAndTeacherLevel( deoDistrict.getId(), mpoStatus.getCode(),levelId);
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findTimescalePendingListByStatusAndLevel(mpoStatus.getCode(), levelId);
                    }

                }
            }


        }
       /* if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findTimescaleListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findMpoPendingListForAdmin( MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }*/

        //return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
        if(page !=null){
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }


    @RequestMapping(value = "/timescaleApplications/SummaryList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getTimescaleSummarylList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get MPO summary list");

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
        List<Map<String,Object>> page=null;
        /*if (SecurityUtils.isCurrentUserInRole("ROLE_AD") || SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR") || SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findTimescaleSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }*/
        if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
            page = rptJdbcDao.findTimescaleSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
            page = rptJdbcDao.findTimescaleSummaryShitByStatus(mpoStatus.getCode());
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findTimescaleSummaryShitByStatus(mpoStatus.getCode());
        }
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }


    public TimeScaleApplication convertTimeScaleToDomain(TimeScaleApplicationDto timeScaleApplicationDto){
        log.debug(timeScaleApplicationDto.toString()+">>>>>>>>>>>>>>>>>>>>>>>>");
        TimeScaleApplication timeScaleApplication = new TimeScaleApplication();
        if(timeScaleApplicationDto.getId() != null && timeScaleApplicationDto.getId() > 0){
            timeScaleApplication.setId(timeScaleApplicationDto.getId());
        }

        timeScaleApplication.setAgendaNumber(timeScaleApplicationDto.getAgendaNumber());
        timeScaleApplication.setStatus(timeScaleApplicationDto.getStatus());
        timeScaleApplication.setInstEmployee(timeScaleApplicationDto.getInstEmployee());
        timeScaleApplication.setDate(timeScaleApplicationDto.getDate());
        timeScaleApplication.setDisActionCaseNo(timeScaleApplicationDto.getDisActionCaseNo());
        timeScaleApplication.setDisciplinaryAction(timeScaleApplicationDto.getDisciplinaryAction());
        timeScaleApplication.setIndexNo(timeScaleApplicationDto.getIndexNo());
        timeScaleApplication.setResulationDate(timeScaleApplicationDto.getResulationDate());
        timeScaleApplication.setWorkingBreak(timeScaleApplicationDto.getWorkingBreak());
        timeScaleApplication.setWorkingBreakStart(timeScaleApplicationDto.getWorkingBreakStart());
        timeScaleApplication.setWorkingBreakEnd(timeScaleApplicationDto.getWorkingBreakEnd());
        timeScaleApplication.setDisActionFileName(timeScaleApplicationDto.getDisActionFileName());
        log.debug(timeScaleApplication.toString()+">>>>>>>>>>>>>>>>>>>>>>>>");

        return  timeScaleApplication;
    }
}
