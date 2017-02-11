package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.MpoApplicationSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.apache.commons.lang3.StringUtils;
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
 * REST controller for managing MpoApplication.
 */
@RestController
@RequestMapping("/api")
public class MpoApplicationResource {
    private final Logger log = LoggerFactory.getLogger(MpoApplicationResource.class);

    @Inject
    private MpoApplicationRepository mpoApplicationRepository;

    @Inject
    private MpoApplicationSearchRepository mpoApplicationSearchRepository;

    @Inject
    private MpoApplicationStatusLogRepository mpoApplicationStatusLogRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private MpoCommitteeDescisionRepository mpoCommitteeDescisionRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;
    /**
     * POST  /mpoApplications -> Create a new mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> createMpoApplication(@Valid @RequestBody MpoApplication mpoApplication) throws URISyntaxException {
        log.debug("REST request to save MpoApplication : {}", mpoApplication);
        if (mpoApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }
        InstEmployee instEmployee=null;
        String userName = SecurityUtils.getCurrentUserLogin();
        log.debug("********************"+userName);
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmployeeresult.setMpoAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
        instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);
        log.debug("&&&&&&&&&&&&&&&&&&&"+instEmployeeresult.toString());
        mpoApplication.setInstEmployee(instEmployeeresult);
        mpoApplication.setDateCrated(LocalDate.now());
        mpoApplication.setDateModified(LocalDate.now());
        //mpoApplication.setInstEmployee(instEmployeeresult);
        mpoApplication.setStatus(MpoApplicationStatusType.COMPLITE.getCode());
        MpoApplication result = mpoApplicationRepository.save(mpoApplication);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result.toString());
        MpoApplicationStatusLog mpoApplicationStatusLog = new MpoApplicationStatusLog();
        mpoApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mpoApplicationStatusLog.setMpoApplicationId(result);
        mpoApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        mpoApplicationStatusLog.setStatus(result.getStatus());
        mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);
        if(mpoApplication.getInstEmployee()!=null && mpoApplication.getInstEmployee().getId()>0){
            instEmployee=instEmployeeRepository.findOne(mpoApplication.getInstEmployee().getId());
            instEmployee.setMpoAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
            instEmployeeRepository.save(instEmployee);
        }
        mpoApplicationSearchRepository.save(result);


        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployee.getName()+" has applied for mpo.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(instEmployee.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.created(new URI("/api/mpoApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> updateMpoApplication(@Valid @RequestBody MpoApplication mpoApplication) throws URISyntaxException {
        log.debug("REST request to update MpoApplication : {}", mpoApplication);
        if (mpoApplication.getId() == null) {
            return createMpoApplication(mpoApplication);
        }
         if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG) && mpoApplication.getDgFinalApproval()) {
             InstEmployee instEmployee = null;
             mpoApplication.setStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());
             mpoApplication.setDateModified(LocalDate.now());
             if (mpoApplication.getInstEmployee() != null && mpoApplication.getInstEmployee().getId() > 0) {
                 instEmployee = instEmployeeRepository.findOne(mpoApplication.getInstEmployee().getId());
                 Integer initVal = 100000;
                 String indexNo = "";
                 String year = String.valueOf(LocalDate.now().getYear());
                 String month = String.valueOf(LocalDate.now().getMonthValue());
                 String day = String.valueOf(LocalDate.now().getDayOfMonth());
                 if (month.length() < 2) {
                     month = "0" + month;
                 }
                 Random rand = new Random();

                 int n = rand.nextInt(900000) + 100000;
                 indexNo = year.substring(year.length() - 2) + month + day + n;
                 /*while(instEmployeeRepository.findOneByEmployeeIndexNo(indexNo) != null){
                     initVal++;
                 }*/

                 instEmployee.setIndexNo(indexNo);
                 instEmployee.setMpoActiveDate(LocalDate.now());
                 instEmployee.setMpoAppStatus(EmployeeMpoApplicationStatus.APPROVED.getCode());
                 instEmployeeRepository.save(instEmployee);
             }
         }else{
             mpoApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(mpoApplication.getStatus()).getCode());

         }
        mpoApplication.setDateModified(LocalDate.now());
             MpoApplication result = mpoApplicationRepository.save(mpoApplication);

             MpoApplicationStatusLog mpoApplicationStatusLog = new MpoApplicationStatusLog();
             mpoApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
             mpoApplicationStatusLog.setMpoApplicationId(result);
             mpoApplicationStatusLog.setStatus(result.getStatus());
             mpoApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(mpoApplicationStatusLog.getStatus()).getRemarks());
             mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);




             mpoApplicationSearchRepository.save(mpoApplication);
             return ResponseEntity.ok()
                 .headers(HeaderUtil.createEntityUpdateAlert("mpoApplication", mpoApplication.getId().toString()))
                 .body(result);



       /* return ResponseEntity.badRequest().headers(HeaderUtil.createEntityDeclineAlert("mpoApplication", "")).body(mpoApplication);*/
    }

    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications/forward/{forwardTo}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> forwardMpoApplication(@Valid @RequestBody MpoApplication mpoApplication, @PathVariable Integer forwardTo, @RequestParam(required = false, name = "cause") String cause, @RequestParam(required = false, name = "memoNo") String memoNo) throws URISyntaxException {
        log.debug("REST request to update MpoApplication : {}", mpoApplication);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> printing remark comment :"+cause);
        if (mpoApplication.getId() == null) {
            return createMpoApplication(mpoApplication);
        }
        mpoApplication.setStatus(MpoApplicationStatusType.prevMpoApplicationStatusType(forwardTo).getCode());
        mpoApplication.setDateModified(LocalDate.now());
        if(memoNo != null){
            mpoApplication.setMemoNo(memoNo);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANEGINGCOMMITTEE)){
            mpoApplication.setCommitteeApproveDate(LocalDate.now());
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DEO)){
            mpoApplication.setDeoApproveDate(LocalDate.now());
        }
        MpoApplication result = mpoApplicationRepository.save(mpoApplication);

        MpoApplicationStatusLog mpoApplicationStatusLog = new MpoApplicationStatusLog();
        mpoApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mpoApplicationStatusLog.setMpoApplicationId(result);
        mpoApplicationStatusLog.setStatus(result.getStatus());
        mpoApplicationStatusLog.setCause(cause);
        mpoApplicationStatusLog.setRemarks(
            MpoApplicationStatusType.getMpoApplicationStatusTypeByRole(getCurrentUserRoleForMpo()).getViewMessage() +
                " Forwarded to " +
                MpoApplicationStatusType.fromInt(forwardTo).getViewMessage());
        mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);

        mpoApplicationSearchRepository.save(mpoApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoApplication", mpoApplication.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> declineMpoApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        MpoApplication mpoApplication=null;
        InstEmployee instEmployee=null;
        MpoApplication result = null;
        MpoApplicationStatusLog mpoApplicationStatusLog = new MpoApplicationStatusLog();
        if(id>0){
            mpoApplication=mpoApplicationRepository.findOne(id);
            mpoApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(mpoApplication.getStatus()).getDeclineRemarks());
            mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        if(mpoApplication.getInstEmployee()!=null){
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to mpoapplication condition");
                instEmployee=mpoApplication.getInstEmployee();
                instEmployee.setMpoAppStatus(EmployeeMpoApplicationStatus.DECLINED.getCode());
                instEmployeeRepository.save(instEmployee);
            }
            mpoApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = mpoApplicationRepository.save(mpoApplication);
            mpoApplicationSearchRepository.save(result);
        }


        log.debug("REST request to update MpoApplication : {}--------", mpoApplication);
        log.debug("REST request to update cause : {}--------", cause);
        if (mpoApplication == null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }

        //mpoApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(mpoApplication.getStatus()).getCode());

        mpoApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        mpoApplicationStatusLog.setCause(cause);
        mpoApplicationStatusLog.setMpoApplicationId(result);
        mpoApplicationStatusLog.setStatus(result.getStatus());
        mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);

        mpoApplicationSearchRepository.save(mpoApplication);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your MPO Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.application");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoApplication", mpoApplication.getId().toString()))
            .body(null);
    }

    /**
     * GET  /mpoApplications -> get all the mpoApplications.
     */
    @RequestMapping(value = "/mpoApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplication>> getAllMpoApplications(Pageable pageable)
        throws URISyntaxException {
        Page<MpoApplication> page = mpoApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoApplications/institute/:id -> get the  mpoApplication by Institute
     */
    @RequestMapping(value = "/mpoApplications/institute/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplication> getMpoApplicationByInstitute(@PathVariable Long id)
        throws URISyntaxException {
        List<MpoApplication> mpoApplications = new ArrayList<MpoApplication>();
        //mpoApplicationRepository.findByInstituteId(id);
        return mpoApplications;

    }

    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/mpoApplications/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> getMpoApplicationByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(mpoApplicationRepository.findByInstEmployeeCode(code))
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }

    /**
     * GET  /mpoApplications/:id -> get the "id" mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplication> getMpoApplication(@PathVariable Long id) {
        log.debug("REST request to get MpoApplication : {}", id);
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
        log.debug(mpoApplicationRepository.findOne(id).toString());
        return Optional.ofNullable(mpoApplicationRepository.findOne(id))
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoApplications/:id -> delete the "id" mpoApplication.
     */
    @RequestMapping(value = "/mpoApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoApplication(@PathVariable Long id) {
        log.debug("REST request to delete MpoApplication : {}", id);
        mpoApplicationRepository.delete(id);
        mpoApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoApplications/:query -> search for the mpoApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplication> searchMpoApplications(@PathVariable String query) {
        return StreamSupport
            .stream(mpoApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/application/approveList/{type}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplicationStatusType> getApplicationApproveList(@PathVariable String type) {
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>  "+type);
        if (type != null && (type.equals("BM") || type.equals("VOC"))) {
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> comes to if  ");
            return MpoApplicationStatusType.getMpoApproveList(type,getCurrentUserRoleForMpo());
        }else{
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> comes to else  ");
            return new ArrayList<MpoApplicationStatusType>();
        }
        }

    @RequestMapping(value = "/mpoApplications/payScaleList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplication>> getMpoPayScaleList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        Page<MpoApplication> page;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.APPROVEDBYDG;

        if (status) {

            page = mpoApplicationRepository.findPayScaleAssignedList(pageable, mpoStatus.getCode());

        } else {
            page = mpoApplicationRepository.findPayScaleNotAssignedList(pageable, mpoStatus.getCode());

        }

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }

    @RequestMapping(value = "/mpoApplications/mpoList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getMpoList(Pageable pageable, @PathVariable Boolean status)
    //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        //User deo=null;
        District deoDestrict = null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
        } else {
            //this for mpo committee at dte. this is not using now
            if (SecurityUtils.isCurrentUserInRole("ROLE_MPOCOMMITTEE")) {
                List<Long> mpoCommitteeDescisionsIds = mpoCommitteeDescisionRepository.findMpoIdsByCurrentUser();
                String ids = StringUtils.join(mpoCommitteeDescisionsIds, ',');
                log.debug("*****************************************************************" +ids);
                page = rptJdbcDao.findMpoPendingListForMpoCommittee(MpoApplicationStatusType.APPROVEDBYDG.getCode(),ids);
            } else {
            if (status) {
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                log.debug("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
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
                            page = rptJdbcDao.findApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                            page = rptJdbcDao.findApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        //deo=(userRepository.findOneByLogin(userName)).get();
                        deoDestrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        //if(deo!=null && deo.getDistrict() !=null && deo.getDistrict().getId()>0){
                        if(deoDestrict!=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            //page = rptJdbcDao.findApprovedListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findApprovedListByDistrictId(deoDestrict.getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOCOMMITTEE")) {
                            //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                            page = rptJdbcDao.findMpoListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
                            //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                            page = rptJdbcDao.findMpoListByApproveStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());

                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findMpoListByApproveStatus(mpoStatus.getCode());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                           // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
                        page = rptJdbcDao.findMpoPendingListByStatus(MpoApplicationStatusType.SUMMARIZEDBYAD.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        //deo=(userRepository.findOneByLogin(userName)).get();
                        deoDestrict = deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDestrict!=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findPendingListByDistrictId( deoDestrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                    //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findMpoPendingListByStatus(mpoStatus.getCode());
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findMpoListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
               // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findMpoPendingListForAdmin( MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

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

    @RequestMapping(value = "/mpoApplications/mpoList/{status}/{levelId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getMpoListByStatusAndLevel(Pageable pageable, @PathVariable Boolean status, @PathVariable Long levelId)
    //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        // Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        //User deo=null;
        District deoDistrict = null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
        } else {
            if (status) {
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                log.debug("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
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
                            page = rptJdbcDao.findApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                            page = rptJdbcDao.findApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
//                        deo=(userRepository.findOneByLogin(userName)).get();
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOCOMMITTEE")) {
                            //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                            page = rptJdbcDao.findMpoListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findMpoListByApproveStatus(mpoStatus.getCode());
                    }

                    /*page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());*/

                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                           // page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findPendingListByInstituteId(instGenInfo.getInstitute().getId(),mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findPendingListByDistrictIdAndTeacherLevel(deoDistrict.getId(), mpoStatus.getCode(),levelId);
                        }
                    }else{
                    //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findMpoPendingListByStatusAndLevel(mpoStatus.getCode(),levelId);
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                //page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findMpoListByApproveStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
               // page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
                page = rptJdbcDao.findMpoPendingListForAdmin( MpoApplicationStatusType.APPROVEDBYDG.getCode());
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

    @RequestMapping(value = "/mpoApplications/mpoFinalList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getMpoFinalList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get final MPO list");
        List<Map<String,Object>> page=null;
        page = rptJdbcDao.findMpoFinalPendingList(MpoApplicationStatusType.APPROVEDBYDG.getCode(), MpoApplicationStatusType.APPROVEDBYDG.getCode());
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }

    @RequestMapping(value = "/mpoApplications/mpoSummaryList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getMpoSummarylList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get MPO summary list");

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
        List<Map<String,Object>> page=null;
            if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
                page = rptJdbcDao.findMpoSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
            }else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
                page = rptJdbcDao.findMpoSummaryShitByStatus(mpoStatus.getCode());
            }else if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
                page = rptJdbcDao.findMpoSummaryShitByStatus(mpoStatus.getCode());
            }
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }

    /*@RequestMapping(value = "/mpoApplications/mpoList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        Page<MpoApplication> page;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;

        if (currentUserStatus == null) {
            *//*blunk list return*//*
            page = null;
        } else {
            if (status) {
                mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            *//*blunk list return*//*
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        page = mpoApplicationRepository.findApprovedListByInstituteId(pageable, instituteRepository.findOneByUserIsCurrentUser().getId(), mpoStatus.getCode());
                    }else if(SecurityUtils.isCurrentUserInRole("ROLE_DEO")){
                        page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, userRepository.findOneById(SecurityUtils.getCurrentUserId()).get().getDistrict().getId(), mpoStatus.getCode());
                    }else{
                        page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                    }



                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
            *//*blunk list return*//*
                    page = null;
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        page = mpoApplicationRepository.findPendingListByInstituteId(pageable, instituteRepository.findOneByUserIsCurrentUser().getId(), mpoStatus.getCode());
                    }else if(SecurityUtils.isCurrentUserInRole("ROLE_DEO")){
                        page = mpoApplicationRepository.findPendingListByDistrictId(pageable, userRepository.findOneById(SecurityUtils.getCurrentUserId()).get().getDistrict().getId(), mpoStatus.getCode());
                    }else {
                        page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                    }
                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                page = mpoApplicationRepository.findMpoListByStatus(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                page = mpoApplicationRepository.findMpoPendingListForAdmin(pageable, MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }*/

    /**
     * GET  /mpoCommitteeDescisions/:id -> get the "id" mpoCommitteeDescision.
     */
    @RequestMapping(value = "/mpoApplications/committee/pendingApplications/currentLogin",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplication> getCommitteePendingMpoApplicationByCurrentUser() {
        log.debug("REST request to get MpoCommitteeDescisions by current user");
        List<MpoCommitteeDescision> mpoCommitteeDescisions = mpoCommitteeDescisionRepository.findDescisionsByCurrentUser();
        List<Long> mpoCommitteeDescisionsIds = mpoCommitteeDescisionRepository.findMpoIdsByCurrentUser();
        String ids = StringUtils.join(mpoCommitteeDescisionsIds, ',');
        log.debug("<<<<<<<<<<<< total application in committee :"+mpoCommitteeDescisions.size());
        List<MpoApplication> mpoApplications = mpoApplicationRepository.getMpoListForCommitteeMember(MpoApplicationStatusType.APPROVEDBYDG.getCode(), mpoCommitteeDescisionRepository.findMpoIdsByCurrentUser());
        log.debug(">>>>>>>>>>>>>>> mpo applications size :"+mpoApplications.size());
        //List<MpoApplication> removeItems = new ArrayList<MpoApplication>();
        /*for(MpoApplication each:mpoApplications){
            log.debug(" >>>>>>>>>>"+" comes to parent for");
            for(MpoCommitteeDescision descision : mpoCommitteeDescisions){
                log.debug(" >>>>>>>>>>"+" comes to for in for");
                log.debug(" >>>>>>>>>>"+" main mpo application id :"+each.getId());
                log.debug(" >>>>>>>>>>"+" descision mpo application id :"+descision.getMpoApplication().getId());

                if(each.getId() == descision.getMpoApplication().getId()){
                    log.debug("{}{}{}{}{}{}{}{}{}{}{}{}{}{}  >>>>>>>>>>"+" comes to if");
                    removeItems.add(each);
                    break;
                }
            }
        }*/
        //mpoApplications.removeAll(removeItems);
        log.debug("LLKLKLLLKLKLK mpo application size after loop: "+mpoApplications.size());
        return mpoApplications;


    }
    @RequestMapping(value = "/generateSalary",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Map<String, Object>> generateSalary()
        throws URISyntaxException {
        return rptJdbcDao.generateSalary();
    }
    @RequestMapping(value = "/isSalaryGenerated/{year}/{month}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> isSalaryGenerateForMonthAndYear(@PathVariable int year,@PathVariable String month)
        throws URISyntaxException {
        Map map=new HashMap<>();
         map.put("salaryHave",rptJdbcDao.isSalaryGenerateForMonthAndYear(year,month));
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }
    //Disburse
    @RequestMapping(value = "/disburse/{year}/{month}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> disburseByMonthAndYear(@PathVariable int year,@PathVariable String month)
        throws URISyntaxException {
        Map map=new HashMap<>();
        map.put("salaryHave",rptJdbcDao.disburseByMonthAndYear(year,month));
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }

    @RequestMapping(value = "/mpoSalaryYearList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Integer> getMpoSalaryYearList()
        throws URISyntaxException {
        return getPreviousTenYearFromToday();
    }
    @RequestMapping(value = "/mpoSalaryMonthList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<String> getMpoSalaryMonthList()
        throws URISyntaxException {
        return getMonthList();
    }

    public String getCurrentUserRoleForMpo() {
        if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
            return "ROLE_INSTITUTE";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
            return "ROLE_DEO";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_FRONTDESK")) {
            return "ROLE_FRONTDESK";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_AD")) {
            return "ROLE_AD";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR")) {
            return "ROLE_DIRECTOR";
        } else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOCOMMITTEE")) {
            return "ROLE_MPOCOMMITTEE";
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
            return "ROLE_MANEGINGCOMMITTEE";
        }else if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            return "ROLE_DG";
        }else {
            return null;
        }

    }

    public List<Integer> getPreviousTenYearFromToday() {

        int year = Calendar.getInstance().get(Calendar.YEAR);
        int i=1;
        List<Integer> yearList=new ArrayList<>();
        yearList.add(year);
        while(i<10){
            yearList.add(year-i);
            i++;
        }


        return yearList;

    }
    public List<String> getMonthList() {

        List<String> monthList=new ArrayList<>();
        monthList.add("January");
        monthList.add("February");
        monthList.add("March");
        monthList.add("April");
        monthList.add("May");
        monthList.add("June");
        monthList.add("July");
        monthList.add("August");
        monthList.add("September");
        monthList.add("October");
        monthList.add("November");
        monthList.add("December");

        return monthList;

    }
}
