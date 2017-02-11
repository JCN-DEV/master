package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeAnotherApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.NameCnclApplicationSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing NameCnclApplication.
 */
@RestController
@RequestMapping("/api")
public class NameCnclApplicationResource {

    private final Logger log = LoggerFactory.getLogger(NameCnclApplicationResource.class);

    @Inject
    private NameCnclApplicationRepository nameCnclApplicationRepository;

    @Inject
    private NameCnclApplicationSearchRepository nameCnclApplicationSearchRepository;

    @Inject
    private NameCnclApplicationStatusLogRepository nameCnclApplicationStatusLogRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private MpoApplicationResource mpoApplicationResource;

    @Inject
    private UserRepository userRepository;

    @Inject
    private InstituteRepository instituteRepository;

    /**
     * POST  /nameCnclApplications -> Create a new nameCnclApplication.
     */
    @RequestMapping(value = "/nameCnclApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplication> createNameCnclApplication(@RequestBody NameCnclApplication nameCnclApplication) throws URISyntaxException {
        log.debug("REST request to save NameCnclApplication : {}", nameCnclApplication);
        if (nameCnclApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new nameCnclApplication cannot already have an ID").body(null);
        }

        nameCnclApplication.setStatus(MpoApplicationStatusType.APPROVEDBYDEO.getCode());
        NameCnclApplication result = nameCnclApplicationRepository.save(nameCnclApplication);
        nameCnclApplicationSearchRepository.save(result);


        NameCnclApplicationStatusLog nameCnclApplicationStatusLog = new NameCnclApplicationStatusLog();
        nameCnclApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        nameCnclApplicationStatusLog.setNameCnclApplication(result);
        nameCnclApplicationStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        nameCnclApplicationStatusLog.setStatus(result.getStatus());
        nameCnclApplicationStatusLogRepository.save(nameCnclApplicationStatusLog);

        result.getInstEmployee().setNameCnclAppStatus(EmployeeAnotherApplicationStatus.PENDING.getCode());
        instEmployeeRepository.save(result.getInstEmployee());
        return ResponseEntity.created(new URI("/api/nameCnclApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nameCnclApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nameCnclApplications -> Updates an existing nameCnclApplication.
     */
    @RequestMapping(value = "/nameCnclApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplication> updateNameCnclApplication(@RequestBody NameCnclApplication nameCnclApplication) throws URISyntaxException {
        log.debug("REST request to update NameCnclApplication : {}", nameCnclApplication);
        if (nameCnclApplication.getId() == null) {
            return createNameCnclApplication(nameCnclApplication);
        }

        NameCnclApplication result = null;
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)) {
            nameCnclApplication.setStatus(MpoApplicationStatusType.SUMMARIZEDBYAD.getCode());
            result = nameCnclApplicationRepository.save(nameCnclApplication);
            nameCnclApplicationSearchRepository.save(result);

        }else{
            nameCnclApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(nameCnclApplication.getStatus()).getCode());
            result = nameCnclApplicationRepository.save(nameCnclApplication);
            nameCnclApplicationSearchRepository.save(result);

        }



        NameCnclApplicationStatusLog nameCnclApplicationStatusLog = new NameCnclApplicationStatusLog();
        nameCnclApplicationStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        nameCnclApplicationStatusLog.setNameCnclApplication(result);
        nameCnclApplicationStatusLog.setRemarks(MpoApplicationStatusType.fromInt(result.getStatus()).getRemarks());
        nameCnclApplicationStatusLog.setStatus(result.getStatus());
        nameCnclApplicationStatusLogRepository.save(nameCnclApplicationStatusLog);


        if (SecurityUtils.isCurrentUserInRole("ROLE_DG")) {
            result.getInstEmployee().setNameCnclAppStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());
            result.getInstEmployee().setMpoActive(false);
            instEmployeeRepository.save(result.getInstEmployee());
        }
        nameCnclApplicationSearchRepository.save(nameCnclApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nameCnclApplication", nameCnclApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nameCnclApplications -> get all the nameCnclApplications.
     */
    @RequestMapping(value = "/nameCnclApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NameCnclApplication>> getAllNameCnclApplications(Pageable pageable)
        throws URISyntaxException {
        Page<NameCnclApplication> page = nameCnclApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nameCnclApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nameCnclApplications/:id -> get the "id" nameCnclApplication.
     */
    @RequestMapping(value = "/nameCnclApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplication> getNameCnclApplication(@PathVariable Long id) {
        log.debug("REST request to get NameCnclApplication : {}", id);
        return Optional.ofNullable(nameCnclApplicationRepository.findOne(id))
            .map(nameCnclApplication -> new ResponseEntity<>(
                nameCnclApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nameCnclApplications/:id -> delete the "id" nameCnclApplication.
     */
    @RequestMapping(value = "/nameCnclApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNameCnclApplication(@PathVariable Long id) {
        log.debug("REST request to delete NameCnclApplication : {}", id);
        nameCnclApplicationRepository.delete(id);
        nameCnclApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nameCnclApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/nameCnclApplications/:query -> search for the nameCnclApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/nameCnclApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NameCnclApplication> searchNameCnclApplications(@PathVariable String query) {
        return StreamSupport
            .stream(nameCnclApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }



    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     *//*
    @RequestMapping(value = "/nameCnclApplications/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplication> getTimeScaleApplicationByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(nameCnclApplicationRepository.findByInstEmployeeCode(code))
            .map(mpoApplication -> new ResponseEntity<>(
                mpoApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       *//* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*//*

    }*/

    @RequestMapping(value = "/nameCnclApplications/nameCnclList/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getTimescaleList(Pageable pageable, @PathVariable Boolean status)
        //public ResponseEntity<List<MpoApplication>> getMpoList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get NameCancelApplication list");
        //Page<MpoApplication> page=null;
        List<Map<String,Object>> page=null;

        //List<TimeScaleApplication> timeScaleList = new ArrayList<TimeScaleApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        User deo=null;
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
                            page = rptJdbcDao.findNameCnclApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deo=(userRepository.findOneByLogin(userName)).get();
                        if(deo!=null && deo.getDistrict() !=null && deo.getDistrict().getId()>0){
                            //page = mpoApplicationRepository.findPendingListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findNameCnclApprovedListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {

                        //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                        page = rptJdbcDao.findNameCnclListByPayScaleAssignStatus(EmployeeAnotherApplicationStatus.PAYSCALEASSIGNED.getCode());

                    }else{
                        //page = mpoApplicationRepository.findMpoListByApproveStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findNameCnclApproveListByStatus(mpoStatus.getCode());
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
                            page = rptJdbcDao.findNameCnclPendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            //page = rptJdbcDao.findTimeScalePendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_DEO")) {
                        deo=(userRepository.findOneByLogin(userName)).get();
                        if(deo!=null){
                            //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                            page = rptJdbcDao.findNameCnclPendingListByDistrictId(deo.getDistrict().getId(), mpoStatus.getCode());
                        }
                    }else if (SecurityUtils.isCurrentUserInRole("ROLE_MPOADMIN")) {

                        //page = mpoApplicationRepository.findApprovedListByDistrictId(pageable, deo.getDistrict().getId(), mpoStatus.getCode());
                        page = rptJdbcDao.findNameCnclListByPayScaleAssignStatus(EmployeeAnotherApplicationStatus.APPROVED.getCode());

                    }else{
                        //page = mpoApplicationRepository.findMpoListByStatus(pageable, mpoStatus.getCode());
                        page = rptJdbcDao.findNameCnclPendingListByStatus(mpoStatus.getCode());
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

    //find all name cancel applications of current institute
    @RequestMapping(value = "/nameCnclApplications/institute/current",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NameCnclApplication>> getAllAppliclatonOfCurrentInstitute(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get All NameCncl list of current Institute");

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();

        Page<NameCnclApplication> page = nameCnclApplicationRepository.findApplicationsByInstitute(institute.getId(), pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nameCnclApplications/institute/current");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);

    }


    //find application by instEmployee code
    @RequestMapping(value = "/nameCnclApplications/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplication> getNameCnclApplicationByEmployeeCode(@PathVariable String code) {
        log.debug("REST request to get NameCnclApplication : {}", code);

        return Optional.ofNullable(nameCnclApplicationRepository.findByInstEmployeeCode(code))
            .map(nameCnclApplication -> new ResponseEntity<>(
                nameCnclApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
