package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeMpoApplicationStatus;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InformationCorrectionSearchRepository;
import gov.step.app.repository.search.InstEmployeeSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.RptJdbcDao;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing InformationCorrection.
 */
@RestController
@RequestMapping("/api")
public class InformationCorrectionResource {

    private final Logger log = LoggerFactory.getLogger(InformationCorrectionResource.class);

    @Inject
    private InformationCorrectionRepository informationCorrectionRepository;

    @Inject
    private InformationCorrectionSearchRepository informationCorrectionSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    @Inject
    private InformationCorrectionStatusLogRepository informationCorrectionStatusLogRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private RptJdbcDao rptJdbcDao;

    @Inject
    private MpoApplicationResource mpoApplicationResource;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstEmployeeSearchRepository instEmployeeSearchRepository;

    @Inject
    private InstEmplBankInfoRepository instEmplBankInfoRepository;

    /**
     * POST  /informationCorrections -> Create a new informationCorrection.
     */
    @RequestMapping(value = "/informationCorrections",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrection> createInformationCorrection(@Valid @RequestBody InformationCorrection informationCorrection) throws URISyntaxException {
        log.debug("REST request to save InformationCorrection : {}", informationCorrection);
        if (informationCorrection.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new informationCorrection cannot already have an ID").body(null);
        }
        //InstEmployee instEmployee=null;
        String userName = SecurityUtils.getCurrentUserLogin();
        log.debug("********************"+userName);
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmployeeresult.setMpoAppStatus(EmployeeMpoApplicationStatus.APPLIED.getCode());
        instEmployeeresult=instEmployeeRepository.save(instEmployeeresult);
        log.debug("&&&&&&&&&&&&&&&&&&&"+instEmployeeresult.toString());
        informationCorrection.setInstEmployee(instEmployeeresult);
        informationCorrection.setCreatedDate(LocalDate.now());
        informationCorrection.setModifiedDate(LocalDate.now());
        informationCorrection.setInstEmployee(instEmployeeresult);
        informationCorrection.setStatus(MpoApplicationStatusType.COMPLITE.getCode());

        InformationCorrection result = informationCorrectionRepository.save(informationCorrection);
        informationCorrectionSearchRepository.save(result);

        log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+result.toString());
        InformationCorrectionStatusLog informationCorrectionStatusLog = new InformationCorrectionStatusLog();
        informationCorrectionStatusLog.setFromDate(DateResource.getDateAsLocalDate());
        informationCorrectionStatusLog.setInformationCorrection(result);
        informationCorrectionStatusLog.setStatus(result.getStatus());
        informationCorrectionStatusLog.setRemarks(MpoApplicationStatusType.COMPLITE.getRemarks());
        //mpoApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        informationCorrectionStatusLogRepository.save(informationCorrectionStatusLog);


        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(instEmployeeresult.getName()+" has applied for mpo.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.dashboard");
        notificationSteps.setUserId(instEmployeeresult.getInstitute().getUser().getId());
        notificationStepRepository.save(notificationSteps);

        return ResponseEntity.created(new URI("/api/informationCorrections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("informationCorrection", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /informationCorrections -> Updates an existing informationCorrection.
     */
    @RequestMapping(value = "/informationCorrections",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrection> updateInformationCorrection(@Valid @RequestBody InformationCorrection informationCorrection) throws URISyntaxException {
        log.debug("REST request to update InformationCorrection : {}", informationCorrection);
        if (informationCorrection.getId() == null) {
            return createInformationCorrection(informationCorrection);
        }

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG) && informationCorrection.getDgFinalApproval()) {
            InstEmployee instEmployee = informationCorrection.getInstEmployee();
            informationCorrection.setStatus(MpoApplicationStatusType.APPROVEDBYDG.getCode());
            if(informationCorrection.getName() != null){
                instEmployee.setName(informationCorrection.getName());
            }
            if(informationCorrection.getDob() != null){
                instEmployee.setDob(informationCorrection.getDob());
            }
            if(informationCorrection.getIndexNo() != null){
                instEmployee.setIndexNo(informationCorrection.getIndexNo());
            }
            if(informationCorrection.getInstEmplDesignation() != null){
                instEmployee.setInstEmplDesignation(informationCorrection.getInstEmplDesignation());
            }
            instEmployeeRepository.save(instEmployee);
            instEmployeeSearchRepository.save(instEmployee);

            if(informationCorrection.getBankAccountNo() != null){
                InstEmplBankInfo instEmplBankInfo = instEmplBankInfoRepository.findByInstEmployeeId(instEmployee.getId());
                instEmplBankInfo.setBankAccountNo(informationCorrection.getBankAccountNo());
                instEmplBankInfoRepository.save(instEmplBankInfo);
            }

        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
            informationCorrection.setStatus(MpoApplicationStatusType.APPROVEDBYDEO.getCode());
        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)) {
            informationCorrection.setStatus(MpoApplicationStatusType.SUMMARIZEDBYAD.getCode());
        }else{
            informationCorrection.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(informationCorrection.getStatus()).getCode());

        }
        InformationCorrection result = informationCorrectionRepository.save(informationCorrection);
        informationCorrectionSearchRepository.save(informationCorrection);


        InformationCorrectionStatusLog informationCorrectionStatusLog = new InformationCorrectionStatusLog();
        informationCorrectionStatusLog.setFromDate(DateResource.getDateAsLocalDate());
        informationCorrectionStatusLog.setInformationCorrection(result);
        informationCorrectionStatusLog.setStatus(result.getStatus());
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            informationCorrectionStatusLog.setRemarks(MpoApplicationStatusType.APPROVEDBYINSTITUTE.getRemarks());
        }else{
            informationCorrectionStatusLog.setRemarks(MpoApplicationStatusType.fromInt(informationCorrectionStatusLog.getStatus()).getRemarks());

        }

        //mpoApplicationStatusLog.set(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        informationCorrectionStatusLogRepository.save(informationCorrectionStatusLog);


        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("informationCorrection", informationCorrection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /informationCorrections -> get all the informationCorrections.
     */
    @RequestMapping(value = "/informationCorrections",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InformationCorrection>> getAllInformationCorrections(Pageable pageable)
        throws URISyntaxException {
        Page<InformationCorrection> page = informationCorrectionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/informationCorrections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /informationCorrections/:id -> get the "id" informationCorrection.
     */
    @RequestMapping(value = "/informationCorrections/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrection> getInformationCorrection(@PathVariable Long id) {
        log.debug("REST request to get InformationCorrection : {}", id);
        return Optional.ofNullable(informationCorrectionRepository.findOne(id))
            .map(informationCorrection -> new ResponseEntity<>(
                informationCorrection,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/informationCorrections/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrection> declineMpoApplication(@PathVariable Long id,@RequestParam(name = "cause") String cause) throws URISyntaxException {
        InformationCorrection informationCorrection=null;
        InstEmployee instEmployee=null;
        InformationCorrection result = null;
        InformationCorrectionStatusLog informationCorrectionStatusLog = new InformationCorrectionStatusLog();
        if(id>0){
            informationCorrection=informationCorrectionRepository.findOne(id);
            informationCorrectionStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(informationCorrection.getStatus()).getDeclineRemarks());
            //informationCorrectionStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
            if(informationCorrection.getInstEmployee()!=null){
                log.debug(">>>>>>>>>>>>>>>>>>>>>>>     comes to informationCorrection condition");
                instEmployee=informationCorrection.getInstEmployee();
                instEmployeeRepository.save(instEmployee);
            }
            informationCorrection.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            result = informationCorrectionRepository.save(informationCorrection);
            informationCorrectionSearchRepository.save(result);
        }


        log.debug("REST request to update informationCorrection : {}--------", informationCorrection);
        log.debug("REST request to update cause : {}--------", cause);
        if (informationCorrection == null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplication cannot already have an ID").body(null);
        }

        //mpoApplication.setStatus(MpoApplicationStatusType.nextMpoApplicationStatusType(mpoApplication.getStatus()).getCode());

        informationCorrectionStatusLog.setFromDate((new Date()).toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        informationCorrectionStatusLog.setCause(cause);
        informationCorrectionStatusLog.setInformationCorrection(result);
        informationCorrectionStatusLog.setStatus(result.getStatus());
        //informationCorrectionStatusLog.setUser(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        informationCorrectionStatusLogRepository.save(informationCorrectionStatusLog);

        informationCorrectionSearchRepository.save(informationCorrection);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Your Information Correction Application has declined");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("mpo.application");
        notificationSteps.setUserId(instEmployee.getUser().getId());
        notificationStepRepository.save(notificationSteps);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("information correction", informationCorrection.getId().toString()))
            .body(null);
    }

    /**
     * DELETE  /informationCorrections/:id -> delete the "id" informationCorrection.
     */
    @RequestMapping(value = "/informationCorrections/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInformationCorrection(@PathVariable Long id) {
        log.debug("REST request to delete InformationCorrection : {}", id);
        informationCorrectionRepository.delete(id);
        informationCorrectionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("informationCorrection", id.toString())).build();
    }

    /**
     * SEARCH  /_search/informationCorrections/:query -> search for the informationCorrection corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/informationCorrections/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InformationCorrection> searchInformationCorrections(@PathVariable String query) {
        return StreamSupport
            .stream(informationCorrectionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplications/instEmployee/code -> get the  mpoApplication by instEmpoyee code
     */
    @RequestMapping(value = "/informationCorrections/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrection> getInformationCorrectionByEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        return Optional.ofNullable(informationCorrectionRepository.findByInstEmployeeCode(code))
            .map(informationCorrection -> new ResponseEntity<>(
                informationCorrection,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NO_CONTENT));
       /* MpoApplication mpoApplication = mpoApplicationRepository.findByInstEmployeeCode(code);
        return mpoApplication;*/

    }

    @RequestMapping(value = "/informationCorrections/applications/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getInfoCorrectionList(Pageable pageable, @PathVariable Boolean status)
        throws URISyntaxException {
        log.debug("REST request to get pending MPO list");
        List<Map<String,Object>> page=null;

        List<MpoApplication> mpoList = new ArrayList<MpoApplication>();

        String currentUserStatus = mpoApplicationResource.getCurrentUserRoleForMpo();

        MpoApplicationStatusType mpoStatus = null;
        String  userName=SecurityUtils.getCurrentUserLogin();
        InstGenInfo instGenInfo = null;

        if (currentUserStatus == null) {
            //*blunk list return*//*
            page = null;
        } else {
                if (status) {
                    mpoStatus = MpoApplicationStatusType.currentRoleMpoApplicationStatusType(mpoApplicationResource.getCurrentUserRoleForMpo());
                    log.debug("<<<<<<<<<<<<<<<<<<>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> : mpo status - "+mpoStatus);
                    if (mpoStatus == null) {
            /*blunk list return*/
                        page = null;
                    } else {
                        if (SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")) {
                            log.debug("-------------information correction status code----------------"+mpoStatus.getCode());
                            instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
                            if(instGenInfo!=null && instGenInfo.getInstitute() !=null && instGenInfo.getInstitute().getId()>0){
                                page = rptJdbcDao.findInfoCorrectionApprovedListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            }
                        }else{
                            page = rptJdbcDao.findInfoCorrectionApprovedList(mpoStatus.getCode());
                        }

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
                                page = rptJdbcDao.findInfoCorrectionPendingListByInstituteId(instGenInfo.getInstitute().getId(), mpoStatus.getCode());
                            }
                        }else{
                            page = rptJdbcDao.findInfoCorrectionPendingList(mpoStatus.getCode());
                        }

                    }
                }

        }

        if(page !=null){
            return new ResponseEntity<>(page,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }

    }
}
