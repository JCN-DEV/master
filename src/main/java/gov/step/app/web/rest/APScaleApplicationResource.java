package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeAnotherApplicationStatus;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.APScaleApplicationSearchRepository;
import gov.step.app.repository.search.APScaleApplicationStatusLogSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing APScaleApplication.
 */
@RestController
@RequestMapping("/api")
public class APScaleApplicationResource {

    private final Logger log = LoggerFactory.getLogger(APScaleApplicationResource.class);

    @Inject
    private APScaleApplicationRepository aPScaleApplicationRepository;

    @Inject
    private APScaleApplicationSearchRepository aPScaleApplicationSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private APScaleApplicationStatusLogRepository apScaleApplicationStatusLogRepository;

    @Inject
    private APScaleApplicationStatusLogSearchRepository apScaleApplicationStatusLogSearchRepository;

    @Inject
    private MpoApplicationResource mpoApplicationResource;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /aPScaleApplications -> Create a new aPScaleApplication.
     */
    @RequestMapping(value = "/aPScaleApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> createAPScaleApplication(@Valid @RequestBody APScaleApplication aPScaleApplication) throws URISyntaxException {
        log.debug("REST request to save APScaleApplication : {}", aPScaleApplication);
        if (aPScaleApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aPScaleApplication cannot already have an ID").body(null);
        }
        /*APScaleApplication result = aPScaleApplicationRepository.save(aPScaleApplication);
        aPScaleApplicationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aPScaleApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aPScaleApplication", result.getId().toString()))
            .body(result);
    }*/
        aPScaleApplication.setCreateDate(LocalDate.now());
        aPScaleApplication.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
    InstEmployee instEmployee = null;
    String userName = SecurityUtils.getCurrentUserLogin();
    InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
    instEmployeeresult.setaPAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
    instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);

    //File file = aPScaleApplicationDto.getDisActionFile();

    //aPScaleApplication tsa = convertaPScaleToDomain(aPScaleApplicationDto);
    String filepath="/backup/mpo/";

    if(aPScaleApplication.getDisActionFile() != null){
        String filename=SecurityUtils.getCurrentUserLogin();
        try {
            // AttachmentUtil.saveAttachment(filepath,filename,);
            aPScaleApplication.setDisActionFileName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, aPScaleApplication.getDisActionFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        // tsa.setDisActionFileName(filename);
    }

//        attachment.setFileContentName(AttachmentUtil.saveAttachment(filepath, filename, attachment.getFile()));

        aPScaleApplication.setDisActionFile(null);
        aPScaleApplication.setInstEmployee(instEmployeeresult);
        aPScaleApplication.setStatus(MpoApplicationStatusType.COMPLITE.getCode());
        aPScaleApplication.setDateCrated(LocalDate.now());
        aPScaleApplication.setDateModified(LocalDate.now());
        APScaleApplication result = aPScaleApplicationRepository.save(aPScaleApplication);
        aPScaleApplicationSearchRepository.save(result);

    APScaleApplicationStatusLog apScaleApplicationStatusLog = new APScaleApplicationStatusLog();
        apScaleApplicationStatusLog.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        apScaleApplicationStatusLog.setCreateDate(LocalDate.now());
        apScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        apScaleApplicationStatusLog.setApScaleApplication(result);
        apScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        apScaleApplicationStatusLog.setStatus(result.getStatus());
        apScaleApplicationStatusLogRepository.save(apScaleApplicationStatusLog);

        apScaleApplicationStatusLogSearchRepository.save(apScaleApplicationStatusLog);

        instEmployeeresult.setaPAppStatus(EmployeeAnotherApplicationStatus.PENDING.getCode());
        instEmployeeRepository.save(instEmployeeresult);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployee.getName()+" has applied for Timescale.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(instEmployee.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);
    return ResponseEntity.created(new URI("/api/aPScaleApplications/" + result.getId()))
        .headers(HeaderUtil.createEntityCreationAlert("apScaleApplication", result.getId().toString()))
        .body(result);
}

    /**
     * PUT  /aPScaleApplications -> Updates an existing aPScaleApplication.
     */
    @RequestMapping(value = "/aPScaleApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> updateAPScaleApplication(@Valid @RequestBody APScaleApplication aPScaleApplication) throws URISyntaxException {
        log.debug("REST request to update APScaleApplication : {}", aPScaleApplication);
        if (aPScaleApplication.getId() == null) {
            return createAPScaleApplication(aPScaleApplication);
        }
        /*APScaleApplication result = aPScaleApplicationRepository.save(aPScaleApplication);
        aPScaleApplicationSearchRepository.save(aPScaleApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aPScaleApplication", aPScaleApplication.getId().toString()))
            .body(result);
    }*/
    InstEmployee instEmployee=null;
        aPScaleApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(aPScaleApplication.getStatus()).getCode());
    APScaleApplication result = aPScaleApplicationRepository.save(aPScaleApplication);
    aPScaleApplicationSearchRepository.save(aPScaleApplication);

    APScaleApplicationStatusLog apScaleApplicationStatusLog = new APScaleApplicationStatusLog();
        apScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        apScaleApplicationStatusLog.setApScaleApplication(aPScaleApplication);
        apScaleApplicationStatusLog.setStatus(result.getStatus());
        apScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(apScaleApplicationStatusLog.getStatus()).getRemarks());
        apScaleApplicationStatusLogRepository.save(apScaleApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setaPAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

    aPScaleApplicationSearchRepository.save(aPScaleApplication);
    return ResponseEntity.ok()
        .headers(HeaderUtil.createEntityUpdateAlert("aPScaleApplication", aPScaleApplication.getId().toString()))
        .body(result);
}

    /**
     * GET  /aPScaleApplications -> get all the aPScaleApplications.
     */
    @RequestMapping(value = "/aPScaleApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<APScaleApplication>> getAllAPScaleApplications(Pageable pageable)
        throws URISyntaxException {
        Page<APScaleApplication> page = aPScaleApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aPScaleApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aPScaleApplications/:id -> get the "id" aPScaleApplication.
     */
    @RequestMapping(value = "/aPScaleApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> getAPScaleApplication(@PathVariable Long id) {
        log.debug("REST request to get APScaleApplication : {}", id);
        return Optional.ofNullable(aPScaleApplicationRepository.findOne(id))
            .map(aPScaleApplication -> new ResponseEntity<>(
                aPScaleApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/aPScaleApplications/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> declineApApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        APScaleApplication apScaleApplication=null;
        InstEmployee instEmployee=null;
        APScaleApplication result = null;
        APScaleApplicationStatusLog apScaleApplicationStatusLog = new APScaleApplicationStatusLog();
        if(id>0){
            apScaleApplication=aPScaleApplicationRepository.findOne(id);
            apScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(apScaleApplication.getStatus()).getDeclineRemarks());
            //timeScaleApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
            if(apScaleApplication.getInstEmployee()!=null){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to mpoapplication condition");
                instEmployee=apScaleApplication.getInstEmployee();
                instEmployee.setTimescaleAppStatus(EmployeeMpoApplicationStatus.DECLINED.getCode());
                instEmployeeRepository.save(instEmployee);
            }
            apScaleApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = aPScaleApplicationRepository.save(apScaleApplication);
            aPScaleApplicationSearchRepository.save(result);
        }


        log.debug("REST request to update apScaleApplication : {}--------", apScaleApplication);
        log.debug("REST request to update cause : {}--------", cause);
        if (apScaleApplication == null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }

        apScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        apScaleApplicationStatusLog.setCause(cause);
        apScaleApplicationStatusLog.setApScaleApplication(result);
        apScaleApplicationStatusLog.setStatus(result.getStatus());
        //mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        apScaleApplicationStatusLogRepository.save(apScaleApplicationStatusLog);

        //timeScaleApplicationst.save(mpoApplication);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your Assistance Professor Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("apapplication");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apScaleApplication", apScaleApplication.getId().toString()))
            .body(null);
    }

    /**
     * DELETE  /aPScaleApplications/:id -> delete the "id" aPScaleApplication.
     */
    @RequestMapping(value = "/aPScaleApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAPScaleApplication(@PathVariable Long id) {
        log.debug("REST request to delete APScaleApplication : {}", id);
        aPScaleApplicationRepository.delete(id);
        aPScaleApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aPScaleApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aPScaleApplications/:query -> search for the aPScaleApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aPScaleApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<APScaleApplication> searchAPScaleApplications(@PathVariable String query) {
        return StreamSupport
            .stream(aPScaleApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/aPscaleApplications/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> getAPScaleAapScaleApplicationpplicationByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(aPScaleApplicationRepository.findByInstEmployeeCode(code))
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }


    /**
     * PUT  /timeScaleApplications -> Updates an existing timeScaleApplication.
     */
    @RequestMapping(value = "/aPscaleApplications/forward/{forwardTo}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplication> forwardAPApplication(@Valid @RequestBody APScaleApplication apScaleApplication, @PathVariable Integer forwardTo, @RequestParam(required = false, name = "cause") String cause, @RequestParam(required = false, name = "memoNo") String memoNo) throws URISyntaxException {
        log.debug("REST request to update apScaleApplication : {}", apScaleApplication);
        if (apScaleApplication.getId() == null) {
            return createAPScaleApplication(apScaleApplication);
        }
        InstEmployee instEmployee=null;
        apScaleApplication.setStatus(MpoApplicationStatusType.prevMpoApplicationStatusType(forwardTo).getCode());
        apScaleApplication.setDateModified(LocalDate.now());
        if(memoNo != null){
            apScaleApplication.setMemoNo(memoNo);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANEGINGCOMMITTEE)){
            apScaleApplication.setCommitteeApproveDate(LocalDate.now());
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DEO)){
            apScaleApplication.setDeoApproveDate(LocalDate.now());
        }
        APScaleApplication result = aPScaleApplicationRepository.save(apScaleApplication);
        aPScaleApplicationSearchRepository.save(result);

        APScaleApplicationStatusLog apScaleApplicationStatusLog = new APScaleApplicationStatusLog();
        apScaleApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        apScaleApplicationStatusLog.setApScaleApplication(apScaleApplication);
        apScaleApplicationStatusLog.setStatus(result.getStatus());
        apScaleApplicationStatusLog.setRemarks(
            MpoApplicationStatusType.getMpoApplicationStatusTypeByRole(mpoApplicationResource.getCurrentUserRoleForMpo()).getViewMessage() +
                " Forwarded to " +
                MpoApplicationStatusType.fromInt(forwardTo).getViewMessage());
        //timeScaleApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());

        apScaleApplicationStatusLogRepository.save(apScaleApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setaPAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

        apScaleApplicationStatusLogSearchRepository.save(apScaleApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("apApplication", apScaleApplication.getId().toString()))
            .body(result);
    }





    @RequestMapping(value = "/aPScaleApplications/summaryList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getAPSummarylList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get MPO summary list");

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
        List<Map<String,Object>> page=null;
        if (SecurityUtils.isCurrentUserInRole("ROLE_AD") || SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR") || SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findAPSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }



    @RequestMapping(value = "/aPScaleApplications/aPScaleList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getaPScaleList(Pageable pageable, @PathVariable Boolean status)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get aPScale list");
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
                    page = rptJdbcDao.findAPScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                }
            }else{
                page = rptJdbcDao.findAPScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            }
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
                            page = rptJdbcDao.findAPScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findAPScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findAPScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findAPScaleApproveListByStatus(mpoStatus.getCode());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findAPScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findAPScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findAPScalePendingListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findAPScalePendingListByStatus(mpoStatus.getCode());
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findAPScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findAPScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
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



    @RequestMapping(value = "/aPScaleApplications/aPList/{status}/{levelId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getaPScaleListByLevel(Pageable pageable, @PathVariable Boolean status, @PathVariable Long levelId)
        throws URISyntaxException {
        List<Map<String,Object>> page=null;

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
                    page = rptJdbcDao.findAPScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                }
            }else{
                page = rptJdbcDao.findAPScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            }
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
                            page = rptJdbcDao.findAPScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findAPScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findAPScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findAPScaleApproveListByStatus(mpoStatus.getCode());
                    }

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            page = rptJdbcDao.findAPScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findAPScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            page = rptJdbcDao.findAPPendingListByDistrictIdAndTeacherLevel(deoDistrict.getId(), mpoStatus.getCode(), levelId);
                        }
                    }else{
                        page = rptJdbcDao.findAPPendingListByStatusAndLevel(mpoStatus.getCode(), levelId);
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                page = rptJdbcDao.findAPScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                page = rptJdbcDao.findAPScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
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






}
