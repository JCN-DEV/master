package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstFinancialInfoSearchRepository;
import gov.step.app.repository.search.InstFinancialInfoTempSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstFinancialInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstFinancialInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstFinancialInfoTempResource.class);

    @Inject
    private InstFinancialInfoTempRepository instFinancialInfoTempRepository;

    @Inject
    private InstFinancialInfoTempSearchRepository instFinancialInfoTempSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstFinancialInfoRepository instFinancialInfoRepository;

    @Inject
    private InstFinancialInfoSearchRepository instFinancialInfoSearchRepository;

    @Inject
    private InstituteFinancialInfoRepository instituteFinancialInfoRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private UserRepository userRepository;

    private InstituteFinancialInfo instituteFinancialInfo=null;

    /**
     * POST  /instFinancialInfoTemps -> Create a new instFinancialInfoTemp.
     */
    @RequestMapping(value = "/instFinancialInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfoTemp> createInstFinancialInfoTemp(@Valid @RequestBody InstFinancialInfoTemp instFinancialInfoTemp) throws URISyntaxException {
        /*log.debug("REST request to save InstFinancialInfoTemp : {}", instFinancialInfoTemp);
        if (instFinancialInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instFinancialInfoTemp cannot already have an ID").body(null);
        }
        InstFinancialInfoTemp result = instFinancialInfoTempRepository.save(instFinancialInfoTemp);
        instFinancialInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instFinancialInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instFinancialInfoTemp", result.getId().toString()))
            .body(result);*/
        log.debug("REST request to save InstFinancialInfo : {}", instFinancialInfoTemp);
        if (instFinancialInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instFinancialInfo cannot already have an ID").body(null);
        }
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);

        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        instFinancialInfoTemp.setInstitute(instGenInfo.getInstitute());
        instFinancialInfoTemp.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        instFinancialInfoTemp.setCreateDate(LocalDate.now());
        InstFinancialInfoTemp result = instFinancialInfoTempRepository.save(instFinancialInfoTemp);

        if(instFinancialInfoTempRepository.findListByInstituteId(result.getInstitute().getId()).size()>1){
            instGenInfo.setStatus((instGenInfo.getStatus()+1));
        }

        if(instGenInfo!=null){
            log.debug("institute--------------"+instGenInfo);
            instGenInfoRepository.save(instGenInfo);
        }
        instFinancialInfoTempSearchRepository.save(result);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setFinancialInfo(1);
            instituteStatusRepository.save(instituteStatus);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);
        return ResponseEntity.created(new URI("/api/instFinancialInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instFinancialInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instFinancialInfoTemps -> Updates an existing instFinancialInfoTemp.
     */
    @RequestMapping(value = "/instFinancialInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfoTemp> updateInstFinancialInfoTemp(@Valid @RequestBody InstFinancialInfoTemp instFinancialInfoTemp) throws URISyntaxException {
        /*log.debug("REST request to update InstFinancialInfoTemp : {}", instFinancialInfoTemp);
        if (instFinancialInfoTemp.getId() == null) {
            return createInstFinancialInfoTemp(instFinancialInfoTemp);
        }
        InstFinancialInfoTemp result = instFinancialInfoTempRepository.save(instFinancialInfoTemp);
        instFinancialInfoTempSearchRepository.save(instFinancialInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instFinancialInfoTemp", instFinancialInfoTemp.getId().toString()))
            .body(result);*/
        log.debug("REST request to update InstFinancialInfo : {}", instFinancialInfoTemp);
        if (instFinancialInfoTemp.getId() == null) {
            return createInstFinancialInfoTemp(instFinancialInfoTemp);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());

        InstFinancialInfo tmpInstFinancialInfo = instFinancialInfoRepository.findOne(instFinancialInfoTemp.getId());
        if(instFinancialInfoTemp.getStatus()!=null && instFinancialInfoTemp.getStatus()==3){
            log.debug("edit-------");
            if(tmpInstFinancialInfo.getStatus()==1){
                log.debug(" changing institute gen info status------------------");
                instGenInfo.setStatus((instGenInfo.getStatus()+1));
                instGenInfoRepository.save(instGenInfo);
                instFinancialInfoTemp.setStatus(3);
            }

            //instGenInfoRepository.save(instGenInfo);
        }
        instFinancialInfoTemp.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        instFinancialInfoTemp.setUpdateDate(LocalDate.now());
        InstFinancialInfoTemp result = instFinancialInfoTempRepository.save(instFinancialInfoTemp);
        instFinancialInfoTempSearchRepository.save(instFinancialInfoTemp);

        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null){
            instituteStatus.setFinancialInfo(1);
            instituteStatusRepository.save(instituteStatus);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instFinancialInfo", instFinancialInfoTemp.getId().toString()))
            .body(result);
    }

    @RequestMapping(value = "/instFinancialInfoTemps/approve",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstFinancialInfo> approveInstInfraInfo(@RequestBody Long id) throws URISyntaxException {
        log.debug("REST request to approve Inst Infra Info : {}", id);

        InstGenInfo instGenInfo=null;
        Institute institute=null;

        List<InstFinancialInfo> instFinancialInfos=instFinancialInfoRepository.findListByInstituteId(id);



        if(instFinancialInfos !=null && instFinancialInfos.size()>0){
            institute=instFinancialInfos.get(0).getInstitute();
            instGenInfo=instGenInfoRepository.findByInstituteId(institute.getId());
            for (InstFinancialInfo instFinancialInfo:instFinancialInfos){

                if(instFinancialInfo.getInstituteFinancialInfo() !=null && instFinancialInfo.getInstituteFinancialInfo().getId()>0){
                    log.debug("approved after edit for "+instFinancialInfo.getInstituteFinancialInfo().getId());
                    instituteFinancialInfo=null;
                    instituteFinancialInfo=instituteFinancialInfoRepository.findOne(instFinancialInfo.getInstituteFinancialInfo().getId());
                    if(instituteFinancialInfo!=null){
                        log.debug("change value for  "+instituteFinancialInfo.getId());
                        instituteFinancialInfo=getCopyFromTmpInstFinInfoToOriginal(instituteFinancialInfo,instFinancialInfo);
                        log.debug("change save for  "+instituteFinancialInfo.getId());
                        instFinancialInfo.setStatus(1);
                        instFinancialInfoRepository.save(instFinancialInfo);
                        instituteFinancialInfo=instituteFinancialInfoRepository.save(instituteFinancialInfo);
                        instFinancialInfo.setInstituteFinancialInfo(instituteFinancialInfo);
                    }
                }else{
                    log.debug("first time approved");
                    instFinancialInfo.setStatus(1);
                    instituteFinancialInfo=this.getCopyFromTmpInstFinInfoToOriginal(instFinancialInfo);
                    if(instituteFinancialInfo!=null){
                        instituteFinancialInfo=instituteFinancialInfoRepository.save(instituteFinancialInfo);
                        instFinancialInfo.setInstituteFinancialInfo(instituteFinancialInfo);
                        instFinancialInfoRepository.save(instFinancialInfo);
                    }
                }
            }
            instGenInfo.setStatus((instGenInfo.getStatus()-1));
            instGenInfoRepository.save(instGenInfo);
        }
        return instFinancialInfos ;

    }

    private InstituteFinancialInfo getCopyFromTmpInstFinInfoToOriginal(InstFinancialInfo instFinancialInfo){
        instituteFinancialInfo=null;
        instituteFinancialInfo=new InstituteFinancialInfo();
        instituteFinancialInfo.setBankName(instFinancialInfo.getBankName());
        instituteFinancialInfo.setBranchName(instFinancialInfo.getBranchName());
        instituteFinancialInfo.setAccountType(instFinancialInfo.getAccountType());
        instituteFinancialInfo.setAccountNo(instFinancialInfo.getAccountNo());
        instituteFinancialInfo.setIssueDate(instFinancialInfo.getIssueDate());
        instituteFinancialInfo.setExpireDate(instFinancialInfo.getExpireDate());
        instituteFinancialInfo.setAmount(instFinancialInfo.getAmount());
        return instituteFinancialInfo;
    }
    private InstituteFinancialInfo getCopyFromTmpInstFinInfoToOriginal(InstituteFinancialInfo instituteFinancialInfo,InstFinancialInfo instFinancialInfo){
        instituteFinancialInfo.setBankName(instFinancialInfo.getBankName());
        instituteFinancialInfo.setBranchName(instFinancialInfo.getBranchName());
        instituteFinancialInfo.setAccountType(instFinancialInfo.getAccountType());
        instituteFinancialInfo.setAccountNo(instFinancialInfo.getAccountNo());
        instituteFinancialInfo.setIssueDate(instFinancialInfo.getIssueDate());
        instituteFinancialInfo.setExpireDate(instFinancialInfo.getExpireDate());
        instituteFinancialInfo.setAmount(instFinancialInfo.getAmount());
        return instituteFinancialInfo;
    }

    /**
     * GET  /instFinancialInfoTemps -> get all the instFinancialInfoTemps.
     */
    @RequestMapping(value = "/instFinancialInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstFinancialInfoTemp>> getAllInstFinancialInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstFinancialInfoTemp> page = instFinancialInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instFinancialInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instFinancialInfoTemps/:id -> get the "id" instFinancialInfoTemp.
     */
    @RequestMapping(value = "/instFinancialInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    /*public ResponseEntity<InstFinancialInfoTemp> getInstFinancialInfoTemp(@PathVariable Long id) {*/
    public List<InstFinancialInfoTemp> getInstFinancialInfoTemp(@PathVariable Long id) {
        /*log.debug("REST request to get InstFinancialInfoTemp : {}", id);
        return Optional.ofNullable(instFinancialInfoTempRepository.findOne(id))
            .map(instFinancialInfoTemp -> new ResponseEntity<>(
                instFinancialInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        log.debug("REST request to get InstFinancialInfo : {}", id);String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);

        /*if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
            log.debug("----------institute Id-------------------------------------------------"+instGenInfo.getInstitute().getId());
            return Optional.ofNullable(instFinancialInfoRepository.findByInstituteId(instGenInfo.getInstitute().getId()))
                .map(instFinancialInfo -> new ResponseEntity<>(
                    instFinancialInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else {
            log.debug("----------institute Id--------------all");
            return Optional.ofNullable(instFinancialInfoRepository.findOne(id))
                .map(instFinancialInfo -> new ResponseEntity<>(
                    instFinancialInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }*/
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
            log.debug("----------institute Id-------------------------------------------------"+instGenInfo.getInstitute().getId());
            return instFinancialInfoTempRepository.findListByInstituteId(instGenInfo.getInstitute().getId());
        }else {
            log.debug("----------institute Id--------------all");
            return instFinancialInfoTempRepository.findListByInstituteId(id);
        }
    }

    /**
     * GET  /instFinancialInfos/:id -> get the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfoTemps/one/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfoTemp> getSingleInstFinancialInfo(@PathVariable Long id) {
        log.debug("REST request to get InstFinancialInfo : {}", id);
        return Optional.ofNullable(instFinancialInfoTempRepository.findOne(id))
            .map(instFinancialInfoTemp -> new ResponseEntity<>(
                instFinancialInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instFinancialInfoTemps/:id -> delete the "id" instFinancialInfoTemp.
     */
    @RequestMapping(value = "/instFinancialInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstFinancialInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstFinancialInfoTemp : {}", id);
        instFinancialInfoTempRepository.delete(id);
        instFinancialInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instFinancialInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instFinancialInfoTemps/:query -> search for the instFinancialInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instFinancialInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstFinancialInfoTemp> searchInstFinancialInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instFinancialInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /instFinancialInfos/:id -> get the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialTempInfoByInstituteId/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstFinancialInfoTemp> getInstFinancialInfoByInstitute(@PathVariable Long instituteId) {
        log.debug("REST request to get InstFinancialInfo : {}", instituteId);
        /*return Optional.ofNullable(instFinancialInfoRepository.findByInstituteId(instituteId))
            .map(instFinancialInfo -> new ResponseEntity<>(
                instFinancialInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        List<InstFinancialInfoTemp> instFinancialInfoList=instFinancialInfoTempRepository.findListByInstituteId(instituteId);
        if(instFinancialInfoList !=null && instFinancialInfoList.size()>0){
            //return instFinancialInfoRepository.findListByInstituteId(instituteId);
            return instFinancialInfoList;
        }else {
            return new ArrayList<InstFinancialInfoTemp>();
        }

    }
    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instFinancialInfoTemps/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfoTemp> declineMpoApplication(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {

        List<InstFinancialInfoTemp> instFinancialInfos=null;

        if(id>0){
            instFinancialInfos=instFinancialInfoTempRepository.findListByInstituteId(id);
        }

        if(instFinancialInfos !=null && instFinancialInfos.size()>0){
            for (InstFinancialInfoTemp instFinancialInfoTemp:instFinancialInfos){
                instFinancialInfoTemp.setDeclineRemarks(cause);
                instFinancialInfoTemp.setStatus(2);
                instFinancialInfoTempRepository.save(instFinancialInfoTemp);
            }
        }else{
            return ResponseEntity.badRequest().header("Failure", "A new inst financial  cannot decline ").body(null);
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityDeclineAlert("result", id.toString()))
            .body(null);
    }
}
