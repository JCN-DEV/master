package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeAnotherApplicationStatus;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.ProfessorApplicationSearchRepository;
import gov.step.app.repository.search.ProfessorApplicationStatusLogSearchRepository;
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
 * REST controller for managing ProfessorApplication.
 */
@RestController
@RequestMapping("/api")
public class ProfessorApplicationResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorApplicationResource.class);

    @Inject
    private ProfessorApplicationRepository professorApplicationRepository;

    @Inject
    private ProfessorApplicationSearchRepository professorApplicationSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private ProfessorApplicationStatusLogRepository professorApplicationStatusLogRepository;

    @Inject
    private ProfessorApplicationStatusLogSearchRepository professorApplicationStatusLogSearchRepository;

    @Inject
    private MpoApplicationResource mpoApplicationResource;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstMemShipRepository instMemShipRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private DeoHistLogRepository deoHistLogRepository;

    /**
     * POST  /professorApplications -> Create a new professorApplication.
     */
    @RequestMapping(value = "/professorApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> createProfessorApplication(@Valid @RequestBody ProfessorApplication professorApplication) throws URISyntaxException {
        log.debug("REST request to save ProfessorApplication : {}", professorApplication);
        if (professorApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new professorApplication cannot already have an ID").body(null);
        }

        InstEmployee instEmployee = null;
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmployeeresult.setPrincipleAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
        instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);

        //File file = timeScaleApplicationDto.getDisActionFile();

        //TimeScaleApplication tsa = convertTimeScaleToDomain(timeScaleApplicationDto);
        String filepath="/backup/mpo/";

        if(professorApplication.getDisActionFile() != null){
            String filename=SecurityUtils.getCurrentUserLogin();
            try {
                // AttachmentUtil.saveAttachment(filepath,filename,);
                professorApplication.setDisActionFileName(AttachmentUtil.saveAttachmentWithoutExtension(filepath, filename, professorApplication.getDisActionFile()));
            } catch (Exception e) {
                e.printStackTrace();
            }
            // tsa.setDisActionFileName(filename);
        }

//        attachment.setFileContentName(AttachmentUtil.saveAttachment(filepath, filename, attachment.getFile()));

        professorApplication.setDisActionFile(null);
        professorApplication.setInstEmployee(instEmployeeresult);
        professorApplication.setDateCrated(LocalDate.now());
        professorApplication.setDateModified(LocalDate.now());
        professorApplication.setStatus(MpoApplicationStatusType.COMPLITE.getCode());
        ProfessorApplication result = professorApplicationRepository.save(professorApplication);
        professorApplicationSearchRepository.save(result);

        ProfessorApplicationStatusLog proffesorApplicationStatusLog = new ProfessorApplicationStatusLog();
        proffesorApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        proffesorApplicationStatusLog.setProfessorApplication(result);
        proffesorApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        proffesorApplicationStatusLog.setStatus(result.getStatus());
        professorApplicationStatusLogRepository.save(proffesorApplicationStatusLog);

        professorApplicationStatusLogSearchRepository.save(proffesorApplicationStatusLog);
        instEmployeeresult.setPrincipleAppStatus(EmployeeAnotherApplicationStatus.PENDING.getCode());
        instEmployeeRepository.save(instEmployeeresult);
        return ResponseEntity.created(new URI("/api/professorApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professorApplications", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professorApplications -> Updates an existing professorApplication.
     */
    @RequestMapping(value = "/professorApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> updateProfessorApplication(@Valid @RequestBody ProfessorApplication professorApplication) throws URISyntaxException {
        log.debug("REST request to update ProfessorApplication : {}", professorApplication);
        if (professorApplication.getId() == null) {
            return createProfessorApplication(professorApplication);
        }
        /*ProfessorApplication result = professorApplicationRepository.save(professorApplication);
        professorApplicationSearchRepository.save(professorApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professorApplication", professorApplication.getId().toString()))
            .body(result);
    }*/

        InstEmployee instEmployee=null;
        professorApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(professorApplication.getStatus()).getCode());
        ProfessorApplication result = professorApplicationRepository.save(professorApplication);

        ProfessorApplicationStatusLog professorApplicationStatusLog = new ProfessorApplicationStatusLog();
        professorApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        professorApplicationStatusLog.setProfessorApplication(professorApplication);
        professorApplicationStatusLog.setStatus(result.getStatus());
        professorApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(professorApplicationStatusLog.getStatus()).getRemarks());
        professorApplicationStatusLogRepository.save(professorApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setPrincipleAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }
        professorApplicationSearchRepository.save(professorApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplication", professorApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professorApplications -> get all the professorApplications.
     */
    @RequestMapping(value = "/professorApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessorApplication>> getAllProfessorApplications(Pageable pageable)
        throws URISyntaxException {
        Page<ProfessorApplication> page = professorApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professorApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professorApplications/:id -> get the "id" professorApplication.
     */
    @RequestMapping(value = "/professorApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> getProfessorApplication(@PathVariable Long id) {
        log.debug("REST request to get ProfessorApplication : {}", id);
        return Optional.ofNullable(professorApplicationRepository.findOne(id))
            .map(professorApplication -> new ResponseEntity<>(
                professorApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    /**
     * PUT  /timeScaleApplications -> Updates an existing timeScaleApplication.
     */
    @RequestMapping(value = "/professorApplications/forward/{forwardTo}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> forwardAPApplication(@Valid @RequestBody ProfessorApplication professorApplication, @PathVariable Integer forwardTo, @RequestParam(required = false, name = "cause") String cause, @RequestParam(required = false, name = "memoNo") String memoNo) throws URISyntaxException {
        log.debug("REST request to update professorApplication : {}", professorApplication);
        if (professorApplication.getId() == null) {
            return createProfessorApplication(professorApplication);
        }
        InstEmployee instEmployee=null;
        professorApplication.setStatus(MpoApplicationStatusType.prevMpoApplicationStatusType(forwardTo).getCode());
        professorApplication.setDateModified(LocalDate.now());
        if(memoNo != null){
            professorApplication.setMemoNo(memoNo);
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANEGINGCOMMITTEE)){
            professorApplication.setCommitteeApproveDate(LocalDate.now());
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DEO)){
            professorApplication.setDeoApproveDate(LocalDate.now());
        }
        ProfessorApplication result = professorApplicationRepository.save(professorApplication);
        professorApplicationSearchRepository.save(result);

        ProfessorApplicationStatusLog professorApplicationStatusLog = new ProfessorApplicationStatusLog();
        professorApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        professorApplicationStatusLog.setProfessorApplication(professorApplication);
        professorApplicationStatusLog.setStatus(result.getStatus());
        professorApplicationStatusLog.setRemarks(
            MpoApplicationStatusType.getMpoApplicationStatusTypeByRole(mpoApplicationResource.getCurrentUserRoleForMpo()).getViewMessage() +
                " Forwarded to " +
                MpoApplicationStatusType.fromInt(forwardTo).getViewMessage());
        //timeScaleApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());

        professorApplicationStatusLogRepository.save(professorApplicationStatusLog);
        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setPrincipleAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            instEmployeeRepository.save(result.getInstEmployee());
        }

        professorApplicationStatusLogSearchRepository.save(professorApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professorApplication", professorApplication.getId().toString()))
            .body(result);
    }



    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/professorApplications/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> declinePrincipleApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        ProfessorApplication professorApplication=null;
        InstEmployee instEmployee=null;
        ProfessorApplication result = null;
        ProfessorApplicationStatusLog professorApplicationStatusLog = new ProfessorApplicationStatusLog();
        if(id>0){
            professorApplication=professorApplicationRepository.findOne(id);
            professorApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(professorApplication.getStatus()).getDeclineRemarks());
            //timeScaleApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
            if(professorApplication.getInstEmployee()!=null){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to mpoapplication condition");
                instEmployee=professorApplication.getInstEmployee();
                instEmployee.setTimescaleAppStatus(EmployeeMpoApplicationStatus.DECLINED.getCode());
                instEmployeeRepository.save(instEmployee);
            }
            professorApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = professorApplicationRepository.save(professorApplication);
            professorApplicationSearchRepository.save(result);
        }


        log.debug("REST request to update professorApplication : {}--------", professorApplication);
        log.debug("REST request to update cause : {}--------", cause);
        if (professorApplication == null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }

        professorApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        professorApplicationStatusLog.setCause(cause);
        professorApplicationStatusLog.setProfessorApplication(result);
        professorApplicationStatusLog.setStatus(result.getStatus());
        //mpoApplicationStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        professorApplicationStatusLogRepository.save(professorApplicationStatusLog);

        //timeScaleApplicationst.save(mpoApplication);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your Assistance Professor Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("apapplication");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professorApplication", professorApplication.getId().toString()))
            .body(null);
    }




    /**
     * DELETE  /professorApplications/:id -> delete the "id" professorApplication.
     */
    @RequestMapping(value = "/professorApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfessorApplication(@PathVariable Long id) {
        log.debug("REST request to delete ProfessorApplication : {}", id);
        professorApplicationRepository.delete(id);
        professorApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professorApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/professorApplications/:query -> search for the professorApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/professorApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProfessorApplication> searchProfessorApplications(@PathVariable String query) {
        return StreamSupport
            .stream(professorApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/professorApplications/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplication> getProffesorApplicationByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(professorApplicationRepository.findByInstEmployeeCode(code))
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }

    @RequestMapping(value = "/professorApplications/professorList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getProfessorApplicationList(Pageable pageable, @PathVariable Boolean status)
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
                            page = rptJdbcDao.findPrincipleScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPrincipleScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            page = rptJdbcDao.findPrincipleScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findPrincipleScaleApproveListByStatus(mpoStatus.getCode());
                    }


                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                    if (status) {
                        if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                            page = rptJdbcDao.findPrincipleScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findPrincipleScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
                    }
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            page = rptJdbcDao.findPrincipleScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPrincipleScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            page = rptJdbcDao.findPrincipleScalePendingListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findPrincipleScalePendingListByStatus(mpoStatus.getCode());
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                page = rptJdbcDao.findPrincipleScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                page = rptJdbcDao.findPrincipleScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            }

        }
        if(page !=null){
            //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplications/mpoList");
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }
    @RequestMapping(value = "/professorApplications/applications/{status}/{levelId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getProfessorApplicationListByLevel(Pageable pageable, @PathVariable Boolean status,@PathVariable Long levelId)
        throws URISyntaxException {
        log.debug("REST request to get Timescale list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

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
                            page = rptJdbcDao.findPrincipleScaleApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPrincipleScaleApprovedListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict !=null){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findPrincipleScaleApprovedListByDistrictId(deoDistrict.getId(), mpoStatus.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findPrincipleScaleApproveListByStatus(mpoStatus.getCode());
                    }


                }
            } else {
                mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                if (mpoStatus == null) {
           /* blunk list return*/
                    page = null;
                    if (status) {
                        if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {
                            page = rptJdbcDao.findPrincipleScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());
                        }
                    }else{
                        page = rptJdbcDao.findPrincipleScaleListByPayPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
                    }
                } else {
                    if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                        instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                        if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                            page = rptJdbcDao.findPrincipleScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MANEGINGCOMMITTEE")) {
                        page = rptJdbcDao.findPrincipleScalePendingListByInstituteId(instMemShipRepository.findCurrentMemberInstitute().getId(), mpoStatus.getCode());

                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deoDistrict=deoHistLogRepository.findDistrictByCurrentDeo();
                        if(deoDistrict!=null){
                            page = rptJdbcDao.findPrinciplePendingListByDistrictIdAndTeacherLevel(deoDistrict.getId(), mpoStatus.getCode(), levelId);
                        }
                    }else{
                        page = rptJdbcDao.findProfessorPendingListByStatusAndLevel(mpoStatus.getCode(), levelId);
                    }

                }
            }


        }
        if (SecurityUtils.isCurrentUserInRole("ROLE_ADMIN")) {
            if (status) {
                page = rptJdbcDao.findPrincipleScaleApproveListByStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());

            } else {
                page = rptJdbcDao.findPrincipleScalePendingListForAdmin(MpoApplicationStatusType.APPROVEDBYDG.getCode());
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



    @RequestMapping(value = "/professorApplications/professorApplicationsList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplication>> getMpoPayScaleList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        Page<MpoApplication> page = null;

        List<ProfessorApplication> professorApplicationList = new ArrayList<ProfessorApplication>();

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
    @RequestMapping(value = "/professorApplicationByEmployee/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessorApplicationStatusLog>> getProfessorApplicationStatusLogByInstEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        //Page<APScaleApplicationStatusLog> page = aPScaleApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        List<ProfessorApplicationStatusLog> list = professorApplicationStatusLogRepository.findStatusLogByEmployeeCode(code);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        if(list !=null && list.size()>0){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }



    @RequestMapping(value = "/professorApplications/summaryList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getPrincipleSummarylList(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get MPO summary list");

        MpoApplicationStatusType mpoStatus = MpoApplicationStatusType.previousRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
        List<Map<String,Object>> page=null;
        if (SecurityUtils.isCurrentUserInRole("ROLE_AD") || SecurityUtils.isCurrentUserInRole("ROLE_DIRECTOR") || SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            page = rptJdbcDao.findPrincipleSummaryShitByStatus(MpoApplicationStatusType.APPROVEDBYAD.getCode());
        }
        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }


}
