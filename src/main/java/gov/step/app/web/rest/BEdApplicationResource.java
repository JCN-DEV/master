package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeAnotherApplicationStatus;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.BEdApplicationSearchRepository;
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
 * REST controller for managing BEdApplication.
 */
@RestController
@RequestMapping("/api")
public class BEdApplicationResource {

    private final Logger log = LoggerFactory.getLogger(BEdApplicationResource.class);

    @Inject
    private BEdApplicationRepository bEdApplicationRepository;

    @Inject
    private BEdApplicationSearchRepository bEdApplicationSearchRepository;

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
    private BEdApplicationStatusLogRepository bEdApplicationStatusLogRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;
    /**
     * POST  /bEdApplications -> Create a new bEdApplication.
     */
    @RequestMapping(value = "/bEdApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> createBEdApplication(@RequestBody BEdApplication bEdApplication) throws URISyntaxException {
        log.debug("REST request to save BEdApplication : {}", bEdApplication);
        if (bEdApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bEdApplication cannot already have an ID").body(null);
        }

        bEdApplication.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
//        bEdApplication.setCreateDate(LocalDate.now());
        bEdApplication.setDateCrated(LocalDate.now());
        InstEmployee instEmployee = null;
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmployeeresult.setbEDAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
        instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);

        bEdApplication.setInstEmployee(instEmployeeresult);
        bEdApplication.setStatus(MpoApplicationStatusType.COMPLITE.getCode());
        BEdApplication result = bEdApplicationRepository.save(bEdApplication);
        bEdApplicationSearchRepository.save(result);


        BEdApplicationStatusLog bEdApplicationStatusLog = new BEdApplicationStatusLog();
        bEdApplicationStatusLog.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        bEdApplicationStatusLog.setCreateDate(LocalDate.now());
        bEdApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        bEdApplicationStatusLog.setBEdApplicationId(result);
        bEdApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        bEdApplicationStatusLog.setStatus(result.getStatus());
        bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);
        instEmployeeresult.setbEDAppStatus(EmployeeAnotherApplicationStatus.PENDING.getCode());
        instEmployeeRepository.save(instEmployeeresult);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployeeresult.getName()+" has applied for Timescale.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(instEmployeeresult.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);

        return ResponseEntity.created(new URI("/api/bEdApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bEdApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bEdApplications -> Updates an existing bEdApplication.
     */
    @RequestMapping(value = "/bEdApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> updateBEdApplication(@RequestBody BEdApplication bEdApplication) throws URISyntaxException {
        log.debug("REST request to update BEdApplication : {}", bEdApplication);
        if (bEdApplication.getId() == null) {
            return createBEdApplication(bEdApplication);
        }
        InstEmployee instEmployee=null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG) && bEdApplication.getDgFinalApproval()) {
            bEdApplication.setStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            bEdApplication.setDateModified(LocalDate.now());

        }else{
            bEdApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(bEdApplication.getStatus()).getCode());
        }

        BEdApplication result = bEdApplicationRepository.save(bEdApplication);

        BEdApplicationStatusLog bEdApplicationStatusLog = new BEdApplicationStatusLog();
        bEdApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        bEdApplicationStatusLog.setBEdApplicationId(bEdApplication);
        bEdApplicationStatusLog.setStatus(result.getStatus());
        bEdApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(bEdApplicationStatusLog.getStatus()).getRemarks());
        bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);

        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setbEDAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }
        bEdApplicationSearchRepository.save(bEdApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bEdApplication", bEdApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bEdApplications -> get all the bEdApplications.
     */
    @RequestMapping(value = "/bEdApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BEdApplication>> getAllBEdApplications(Pageable pageable)
        throws URISyntaxException {
        Page<BEdApplication> page = bEdApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bEdApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bEdApplications/:id -> get the "id" bEdApplication.
     */
    @RequestMapping(value = "/bEdApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> getBEdApplication(@PathVariable Long id) {
        log.debug("REST request to get BEdApplication : {}", id);
        return Optional.ofNullable(bEdApplicationRepository.findOne(id))
            .map(bEdApplication -> new ResponseEntity<>(
                bEdApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/bEdApplications/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> declineBedApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        BEdApplication bEdApplication=null;
        InstEmployee instEmployee=null;
        BEdApplication result = null;
        BEdApplicationStatusLog bEdApplicationStatusLog = new BEdApplicationStatusLog();
        if(id>0){
            bEdApplication=bEdApplicationRepository.findOne(id);
            bEdApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(bEdApplication.getStatus()).getDeclineRemarks());
            //timeScaleApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
            if(bEdApplication.getInstEmployee()!=null){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to mpoapplication condition");
                instEmployee=bEdApplication.getInstEmployee();
                instEmployee.setTimescaleAppStatus(EmployeeMpoApplicationStatus.DECLINED.getCode());
                instEmployeeRepository.save(instEmployee);
            }
            bEdApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = bEdApplicationRepository.save(bEdApplication);
            bEdApplicationSearchRepository.save(result);
        }


        log.debug("REST request to update bEdApplication : {}--------", bEdApplication);
        log.debug("REST request to update cause : {}--------", cause);
        if (bEdApplication == null) {
            return ResponseEntity.badRequest().header("Failure", "A new bEdApplication cannot already have an ID").body(null);
        }

        bEdApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        bEdApplicationStatusLog.setCause(cause);
        bEdApplicationStatusLog.setBEdApplicationId(result);
        bEdApplicationStatusLog.setStatus(result.getStatus());
        //mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);

        //timeScaleApplicationst.save(mpoApplication);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your Assistance Professor Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("apapplication");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bEdApplication", bEdApplication.getId().toString()))
            .body(null);
    }


    /**
     * DELETE  /bEdApplications/:id -> delete the "id" bEdApplication.
     */
    @RequestMapping(value = "/bEdApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBEdApplication(@PathVariable Long id) {
        log.debug("REST request to delete BEdApplication : {}", id);
        bEdApplicationRepository.delete(id);
        bEdApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bEdApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bEdApplications/:query -> search for the bEdApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bEdApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BEdApplication> searchBEdApplications(@PathVariable String query) {
        return StreamSupport
            .stream(bEdApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/bedApplication/bedList/{status}",
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
        District deoDistrict =null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
            if (status) {
                if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                    page = rptJdbcDao.findBEdListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                }
            }else{
                page = rptJdbcDao.findBEdListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
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
                            page = rptJdbcDao.findBEdApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        //page = rptJdbcDao.findBEdApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findBEdApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findBEdListByApproveStatus(mpoStatus.getCode());
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
                            page = rptJdbcDao.findBEdPendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findBEdPendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findBEdPendingListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findBEdPendingListByStatus(mpoStatus.getCode());
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findBEdListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findBEdPendingListForAdmin( MpoApplicationStatusType.APPROVEDBYDG.getCode());
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




    @RequestMapping(value = "/bedApplication/bedList/{status}/{levelId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getBEdListByLevel(Pageable pageable, @PathVariable Boolean status, @PathVariable Long levelId)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get Timescale list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        //List<TimeScaleApplication> timeScaleList = new ArrayList<TimeScaleApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        District deoDestrict=null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
            if (status) {
                if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                    page = rptJdbcDao.findBEdListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                }
            }else{
                page = rptJdbcDao.findBEdListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
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
                            page = rptJdbcDao.findBEdApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        //page = rptJdbcDao.findBEdApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDestrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDestrict!=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findBEdApprovedListByDistrictId(deoDestrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findBEdListByApproveStatus(mpoStatus.getCode());
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
                            page = rptJdbcDao.findBEdPendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findBEdPendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDestrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDestrict!=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findBEdPendingListByDistrictIdAndTeacherLevel(deoDestrict.getId(), mpoStatus.getCode(), levelId);
                        }
                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findBEdPendingListByStatusAndLevel(mpoStatus.getCode(), levelId);
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findBEdListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findBEdPendingListForAdmin( MpoApplicationStatusType.APPROVEDBYDG.getCode());
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


    @RequestMapping(value = "/bEdApplications/bEdList/{status}",
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

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }



    @RequestMapping(value = "/bEdApplications/summaryList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getBEdSummarylList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get b.Ed summary list");

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
        List<Map<String,Object>> page=null;
       /* if (SecurityUtils.isCurrentUserInRole("ROLE_AD") || SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR") || SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findBEdSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }*/

        if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
            page = rptJdbcDao.findBEdSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
            page = rptJdbcDao.findBEdSummaryShitByStatus(mpoStatus.getCode());
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findBEdSummaryShitByStatus(mpoStatus.getCode());
        }
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }


    /**
     * PUT  /timeScaleApplications -> Updates an existing timeScaleApplication.
     */
    @RequestMapping(value = "/bEdApplications/forward/{forwardTo}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> forwardBEdApplication(@Valid @RequestBody BEdApplication bEdApplication, @PathVariable Integer forwardTo, @RequestParam(required = false, name = "cause") String cause, @RequestParam(required = false, name = "memoNo") String memoNo) throws URISyntaxException {
        log.debug("REST request to update BEdApplication : {}", bEdApplication);
        if (bEdApplication.getId() == null) {
            return createBEdApplication(bEdApplication);
        }
        InstEmployee instEmployee=null;
        bEdApplication.setStatus(MpoApplicationStatusType.prevMpoApplicationStatusType(forwardTo).getCode());
        bEdApplication.setDateModified(LocalDate.now());
        if(memoNo != null){
            bEdApplication.setMemoNo(memoNo);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANEGINGCOMMITTEE)){
            bEdApplication.setCommitteeApproveDate(LocalDate.now());
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DEO)){
            bEdApplication.setDeoApproveDate(LocalDate.now());
        }
        BEdApplication result = bEdApplicationRepository.save(bEdApplication);

        BEdApplicationStatusLog bEdApplicationStatusLog = new BEdApplicationStatusLog();
        bEdApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        bEdApplicationStatusLog.setBEdApplicationId(bEdApplication);
        bEdApplicationStatusLog.setStatus(result.getStatus());
        bEdApplicationStatusLog.setRemarks(
            MpoApplicationStatusType.getMpoApplicationStatusTypeByRole(mpoApplicationResource.getCurrentUserRoleForMpo()).getViewMessage() +
                " Forwarded to " +
                MpoApplicationStatusType.fromInt(forwardTo).getViewMessage());
        //timeScaleApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());

        bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setTimescaleAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

        bEdApplicationSearchRepository.save(bEdApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bEdApplication", bEdApplication.getId().toString()))
            .body(result);
    }




    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/bEdApplications/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplication> getMpoApplicationByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(bEdApplicationRepository.findByInstEmployeeCode(code))
            .map(bEdApplication -> new ResponseEntity<>(
                bEdApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }
}
