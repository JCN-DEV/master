package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstFinancialInfoSearchRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstFinancialInfo.
 */
@RestController
@RequestMapping("/api")
public class InstFinancialInfoResource {

    //InstFinancialInfoTempResource

    private final Logger log = LoggerFactory.getLogger(InstFinancialInfoResource.class);

    @Inject
    private InstFinancialInfoRepository instFinancialInfoRepository;

    @Inject
    private InstFinancialInfoSearchRepository instFinancialInfoSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstituteFinancialInfoRepository instituteFinancialInfoRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    @Inject
    private InstituteRepository instituteRepository;

    private InstituteFinancialInfo instituteFinancialInfo=null;


    /**
     * POST  /instFinancialInfos -> Create a new instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfo> createInstFinancialInfo(@Valid @RequestBody InstFinancialInfo instFinancialInfo) throws URISyntaxException {
        log.debug("REST request to save InstFinancialInfo : {}", instFinancialInfo);
        if (instFinancialInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instFinancialInfo cannot already have an ID").body(null);
        }
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);

        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        instFinancialInfo.setInstitute(instGenInfo.getInstitute());
        InstFinancialInfo result = instFinancialInfoRepository.save(instFinancialInfo);

        if(instFinancialInfoRepository.findListByInstituteId(result.getInstitute().getId()).size()>1){
            instGenInfo.setStatus((instGenInfo.getStatus()+1));
        }

        if(instGenInfo!=null){
            log.debug("institute--------------"+instGenInfo);
            instGenInfoRepository.save(instGenInfo);
        }
        //Save and Update Approve Related field
        if(result != null){
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            institute.setInfoEditStatus("Pending");
            Institute instituteSaveResult = instituteRepository.save(institute);
            if(instituteSaveResult != null ){
                InstituteStatus instituteStatus =  instituteStatusRepository.findOneByCurrentInstitute();
                if(instituteStatus != null) {
                    instituteStatus.setFinancialInfo(1);
                    InstituteStatus instituteStatusSaveResult = instituteStatusRepository.save(instituteStatus);
                }
            }
        }

        instFinancialInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instFinancialInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instFinancialInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instFinancialInfos -> Updates an existing instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfo> updateInstFinancialInfo(@Valid @RequestBody InstFinancialInfo instFinancialInfo) throws URISyntaxException {
        log.debug("REST request to update InstFinancialInfo : {}", instFinancialInfo);
        if (instFinancialInfo.getId() == null) {
            return createInstFinancialInfo(instFinancialInfo);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        /*if(instFinancialInfo.getStatus()==1){
            instGenInfo.setStatus((instGenInfo.getStatus()+1));
            instGenInfoRepository.save(instGenInfo);
            instFinancialInfo.setStatus(2);
        }
        if(instFinancialInfo.getStatus()==2){
            instGenInfo.setStatus((instGenInfo.getStatus()-1));
            instGenInfoRepository.save(instGenInfo);
            instFinancialInfo.setStatus(1);
        }
        if(instFinancialInfo.getStatus()==3){
            instFinancialInfo.setStatus(2);
        }
        InstFinancialInfo result = instFinancialInfoRepository.save(instFinancialInfo);
        instFinancialInfoSearchRepository.save(instFinancialInfo);*/
        InstFinancialInfo tmpInstFinancialInfo = instFinancialInfoRepository.findOne(instFinancialInfo.getId());
        if(instFinancialInfo.getStatus()!=null && instFinancialInfo.getStatus()==3){
            log.debug("edit-------");
            if(tmpInstFinancialInfo.getStatus()==1){
                log.debug(" changing institute gen info status------------------");
                instGenInfo.setStatus((instGenInfo.getStatus()+1));
                instGenInfoRepository.save(instGenInfo);
                instFinancialInfo.setStatus(3);
            }

            //instGenInfoRepository.save(instGenInfo);
        }
        InstFinancialInfo result = instFinancialInfoRepository.save(instFinancialInfo);
        //Save and Update Approve Related field
        if(result != null){
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            institute.setInfoEditStatus("Pending");
            Institute instituteSaveResult = instituteRepository.save(institute);
            if(instituteSaveResult != null ){
                InstituteStatus instituteStatus =  instituteStatusRepository.findOneByCurrentInstitute();
                if(instituteStatus != null) {
                    instituteStatus.setFinancialInfo(1);
                    InstituteStatus instituteStatusSaveResult = instituteStatusRepository.save(instituteStatus);
                }
            }
        }
        instFinancialInfoSearchRepository.save(instFinancialInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instFinancialInfo", instFinancialInfo.getId().toString()))
            .body(result);
    }
    @RequestMapping(value = "/instFinancialInfos/approve",
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

    /**
     * GET  /instFinancialInfos -> get all the instFinancialInfos.
     */
    @RequestMapping(value = "/instFinancialInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstFinancialInfo>> getAllInstFinancialInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstFinancialInfo> page = instFinancialInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instFinancialInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instFinancialInfos/:id -> get the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    /*public ResponseEntity<InstFinancialInfo> getInstFinancialInfo(@PathVariable Long id) {*/
    public List<InstFinancialInfo> getInstFinancialInfo(@PathVariable Long id) {
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
            return instFinancialInfoRepository.findListByInstituteId(instGenInfo.getInstitute().getId());
        }else {
            log.debug("----------institute Id--------------all");
            return instFinancialInfoRepository.findListByInstituteId(id);
        }

    }


    /**
     * GET  /instFinancialInfos/:id -> get the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfos/one/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfo> getSingleInstFinancialInfo(@PathVariable Long id) {
        log.debug("REST request to get InstFinancialInfo : {}", id);
        return Optional.ofNullable(instFinancialInfoRepository.findOne(id))
            .map(instFinancialInfo -> new ResponseEntity<>(
                instFinancialInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * DELETE  /instFinancialInfos/:id -> delete the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstFinancialInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstFinancialInfo : {}", id);
        instFinancialInfoRepository.delete(id);
        instFinancialInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instFinancialInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instFinancialInfos/:query -> search for the instFinancialInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instFinancialInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstFinancialInfo> searchInstFinancialInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instFinancialInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /instFinancialInfos/:id -> get the "id" instFinancialInfo.
     */
    @RequestMapping(value = "/instFinancialInfoByInstituteId/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstFinancialInfo> getInstFinancialInfoByInstitute(@PathVariable Long instituteId) {
        log.debug("REST request to get InstFinancialInfo : {}", instituteId);
        /*return Optional.ofNullable(instFinancialInfoRepository.findByInstituteId(instituteId))
            .map(instFinancialInfo -> new ResponseEntity<>(
                instFinancialInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        List<InstFinancialInfo> instFinancialInfoList=instFinancialInfoRepository.findListByInstituteId(instituteId);
        if(instFinancialInfoList !=null && instFinancialInfoList.size()>0){
            //return instFinancialInfoRepository.findListByInstituteId(instituteId);
            return instFinancialInfoList;
        }else {
            return new ArrayList<InstFinancialInfo>();
        }

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
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instFinancialInfos/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstFinancialInfo> declineMpoApplication(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {

        List<InstFinancialInfo> instFinancialInfos=null;

        if(id>0){
            instFinancialInfos=instFinancialInfoRepository.findListByInstituteId(id);
        }

        if(instFinancialInfos !=null && instFinancialInfos.size()>0){
            for (InstFinancialInfo instFinancialInfo:instFinancialInfos){
                instFinancialInfo.setDeclineRemarks(cause);
                instFinancialInfo.setStatus(2);
                instFinancialInfoRepository.save(instFinancialInfo);
            }
        }else{
            return ResponseEntity.badRequest().header("Failure", "A new inst financial  cannot decline ").body(null);
        }
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityDeclineAlert("result", id.toString()))
                .body(null);
        }

}
