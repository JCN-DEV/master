package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.repository.*;
import gov.step.app.repository.search.InstAdmInfoTempSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstAdmInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstAdmInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstAdmInfoTempResource.class);

    @Inject
    private InstAdmInfoTempRepository instAdmInfoTempRepository;

    @Inject
    private InstAdmInfoTempSearchRepository instAdmInfoTempSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private InstGovernBodyRepository instituteGovernBodyRepository;

    @Inject
    private InstituteStatusRepository instituteStatusRepository;

    /**
     * POST  /instAdmInfoTemps -> Create a new instAdmInfoTemp.
     */
    /*@RequestMapping(value = "/instAdmInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> createInstAdmInfoTemp(@Valid @RequestBody InstAdmInfoTemp instAdmInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstAdmInfoTemp : {}", instAdmInfoTemp);
        if (instAdmInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instAdmInfoTemp cannot already have an ID").body(null);
        }
        InstAdmInfoTemp result = instAdmInfoTempRepository.save(instAdmInfoTemp);
        instAdmInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instAdmInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instAdmInfoTemp", result.getId().toString()))
            .body(result);
    }*/


    @RequestMapping(value = "/instAdmInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> createInstAdmInfoTemp(@Valid @RequestBody InstAdmInfoTemp instAdmInfo) throws URISyntaxException {
        log.debug("REST request to save InstAdmInfo : {}", instAdmInfo);
        if (instAdmInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instAdmInfo cannot already have an ID").body(null);
        }

        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        instAdmInfo.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        InstAdmInfoTemp result = instAdmInfoTempRepository.save(instAdmInfo);
        instAdmInfoTempSearchRepository.save(result);


        InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
        if(instituteStatus != null) {
            instituteStatus.setAdmInfo(1);
            instituteStatus.setUpdatedDate(LocalDate.now());
            instituteStatusRepository.save(instituteStatus);
        }
        return ResponseEntity.created(new URI("/api/instAdmInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instAdmInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instAdmInfoTemps -> Updates an existing instAdmInfoTemp.
     */
    @RequestMapping(value = "/instAdmInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> updateInstAdmInfoTemp(@Valid @RequestBody InstAdmInfoTemp instAdmInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstAdmInfoTemp : {}", instAdmInfoTemp);
        if (instAdmInfoTemp.getId() == null) {
            return createInstAdmInfoTemp(instAdmInfoTemp);
        }
        Institute institute = instituteRepository.findOneByUserIsCurrentUser();
        institute.setInfoEditStatus("Pending");
        instituteRepository.save(institute);

        InstAdmInfoTemp result = instAdmInfoTempRepository.save(instAdmInfoTemp);
        instAdmInfoTempSearchRepository.save(instAdmInfoTemp);

        if(SecurityUtils.isCurrentUserInRole("ROLE_INSTITUTE")){
            InstituteStatus instituteStatus = instituteStatusRepository.findOneByCurrentInstitute();
            if(instituteStatus != null) {
                instituteStatus.setAdmInfo(1);
                instituteStatus.setUpdatedDate(LocalDate.now());
                instituteStatusRepository.save(instituteStatus);
            }
        }

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instAdmInfoTemp", instAdmInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instAdmInfoTemps -> get all the instAdmInfoTemps.
     */
    @RequestMapping(value = "/instAdmInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstAdmInfoTemp>> getAllInstAdmInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstAdmInfoTemp> page = instAdmInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instAdmInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
        * GET  /instAdmInfos/:id -> get the "id" instAdmInfo.
    */
    @RequestMapping(value = "/instAdmInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> getInstAdmInfo(@PathVariable Long id) {
        log.debug("REST request to get InstAdmInfo : {}", id);
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            return Optional.ofNullable(instAdmInfoTempRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()))
                .map(instAdmInfo -> new ResponseEntity<>(
                    instAdmInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else{
            return Optional.ofNullable(instAdmInfoTempRepository.findOne(id))
                .map(instAdmInfo -> new ResponseEntity<>(
                    instAdmInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

    /**
     * GET  /instAdmInfoTemps/:id -> get the "id" instAdmInfoTemp.
     */
   /* @RequestMapping(value = "/instAdmInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> getInstAdmInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstAdmInfoTemp : {}", id);
        return Optional.ofNullable(instAdmInfoTempRepository.findOne(id))
            .map(instAdmInfoTemp -> new ResponseEntity<>(
                instAdmInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    /**
     * DELETE  /instAdmInfoTemps/:id -> delete the "id" instAdmInfoTemp.
     */
    @RequestMapping(value = "/instAdmInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstAdmInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstAdmInfoTemp : {}", id);
        instAdmInfoTempRepository.delete(id);
        instAdmInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instAdmInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instAdmInfoTemps/:query -> search for the instAdmInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instAdmInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstAdmInfoTemp> searchInstAdmInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instAdmInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }









    /**
     * PUT  /instAdmInfos -> Updates an existing instAdmInfo.
     *//*
    @RequestMapping(value = "/instAdmInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfoTemp> updateInstAdmInfo(@Valid @RequestBody InstAdmInfoTemp instAdmInfo) throws URISyntaxException {
        log.debug("REST request to update InstAdmInfo : {}", instAdmInfo);
        if (instAdmInfo.getId() == null) {
            return createInstAdmInfoTemp(instAdmInfo);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        Institute institute=null;
        InstAdmInfoTemp result=null;
        InstAdmInfoTemp tmpInstAdmInfo=instAdmInfoTempRepository.findOne(instAdmInfo.getId());
        if(instGenInfo !=null){

            if(instAdmInfo.getStatus()!=null && instAdmInfo.getStatus()==3){
                log.debug("edit-------");
                if(tmpInstAdmInfo.getStatus()==1){
                    instGenInfo.setStatus((instGenInfo.getStatus()+1));
                    instGenInfoRepository.save(instGenInfo);
                }
                instAdmInfo.setStatus(3);
                //instGenInfoRepository.save(instGenInfo);
            }else{

                if(tmpInstAdmInfo.getStatus() !=null && tmpInstAdmInfo.getStatus()==3){
                    // instAdmInfo.setStatus(0);
                    instGenInfoRepository.save(instGenInfo);
                }
            }
            result= instAdmInfoTempRepository.save(instAdmInfo);
            instAdmInfoTempSearchRepository.save(result);
        }else{
            result=instAdmInfoTempRepository.save(instAdmInfo);
            instGenInfo=instGenInfoRepository.findByInstituteId(result.getInstitute().getId());
            if(instAdmInfo.getStatus()==1){
                if(tmpInstAdmInfo.getStatus() !=null && tmpInstAdmInfo.getStatus()!=1){
                    instGenInfo.setStatus((instGenInfo.getStatus()-1));
                    instGenInfoRepository.save(instGenInfo);
                }

                institute=instituteRepository.findOne(result.getInstitute().getId());
                if(institute !=null){
                    institute.setAdminCounselorName(result.getAdminCounselorName());
                    institute.setCounselorMobileNo(result.getCounselorMobileNo());
                    institute.setInsHeadName(result.getInsHeadName());
                    institute.setInsHeadMobileNo(result.getInsHeadMobileNo());
                    institute.setDeoName(result.getDeoName());
                    institute.setDeoMobileNo(result.getDeoMobileNo());
                    instituteRepository.save(institute);
                    List<InstituteGovernBody> instituteGovernBodyList=null;
                    List<InstGovernBody> instGovernBodyList=null;
                    instGovernBodyList= instGovernBodyRepository.findListByInstituteId(institute.getId());
                    instituteGovernBodyList=instituteGovernBodyRepository.findListByInstituteId(institute.getId());
                    if(instituteGovernBodyList!=null && instituteGovernBodyList.size()>0){
                            *//*Approve after Edit *//*
                        for(InstGovernBody instGovbd:instGovernBodyList){
                            instGovernBody=instituteGovernBodyRepository.findOne(instGovbd.getInstituteGovernBody().getId());
                            if(instGovernBody==null){
                                instGovernBody=getCopyFromTmpInstGovBdToOriginal(instGovbd);
                                if(instGovernBody!=null){
                                    instGovernBody= instituteGovernBodyRepository.save(instGovernBody);
                                    instGovbd.setInstituteGovernBody(instGovernBody);
                                    instGovernBodyRepository.save(instGovbd);
                                }
                            }else{
                                instituteGovernBodyRepository.save(changeInstGovernBody(instGovernBody,instGovbd));
                            }
                        }

                    }else{

                        for(InstGovernBody instGovbd:instGovernBodyList){
                            instGovernBody=getCopyFromTmpInstGovBdToOriginal(instGovbd);
                            if(instGovernBody!=null){
                                instGovernBody= instituteGovernBodyRepository.save(instGovernBody);
                                instGovbd.setInstituteGovernBody(instGovernBody);
                                instGovernBodyRepository.save(instGovbd);
                            }
                        }
                    }

                }
            }
            if(instAdmInfo.getStatus()==2){
                instGenInfo.setStatus((instGenInfo.getStatus()+1));
                instGenInfoRepository.save(instGenInfo);
            }
            if(instAdmInfo.getStatus()==3){
                instAdmInfo.setStatus(2);
            }

        }



        instAdmInfoSearchRepository.save(instAdmInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instAdmInfo", instAdmInfo.getId().toString()))
            .body(result);
    }

    *//**
     * GET  /instAdmInfos -> get all the instAdmInfos.
     *//*
    @RequestMapping(value = "/instAdmInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstAdmInfo>> getAllInstAdmInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstAdmInfo> page = instAdmInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instAdmInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    *//**
     * GET  /instAdmInfos/:id -> get the "id" instAdmInfo.
     *//*
    @RequestMapping(value = "/instAdmInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfo> getInstAdmInfo(@PathVariable Long id) {
        log.debug("REST request to get InstAdmInfo : {}", id);
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            return Optional.ofNullable(instAdmInfoRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()))
                .map(instAdmInfo -> new ResponseEntity<>(
                    instAdmInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else{
            return Optional.ofNullable(instAdmInfoRepository.findOne(id))
                .map(instAdmInfo -> new ResponseEntity<>(
                    instAdmInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

    *//**
     * DELETE  /instAdmInfos/:id -> delete the "id" instAdmInfo.
     *//*
    @RequestMapping(value = "/instAdmInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstAdmInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstAdmInfo : {}", id);
        instAdmInfoRepository.delete(id);
        instAdmInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instAdmInfo", id.toString())).build();
    }

    *//**
     * SEARCH  /_search/instAdmInfos/:query -> search for the instAdmInfo corresponding
     * to the query.
     *//*
    @RequestMapping(value = "/_search/instAdmInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstAdmInfo> searchInstAdmInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instAdmInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    *//**
     * DELETE  /instAdmInfos/:id -> delete the "id" instAdmInfo.
     *//*
    @RequestMapping(value = "/instAdmInfoByInstituteId/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfo> getInstAdmInfoByInstGeninfoId(@PathVariable Long instituteId) {
        log.debug("REST request to get InstAdmInfo : {}", instituteId);
        return Optional.ofNullable(instAdmInfoRepository.findOneByInstituteId(instituteId))
            .map(instAdmInfo -> new ResponseEntity<>(
                instAdmInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    *//**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     *//*
    @RequestMapping(value = "/instAdmInfos/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAdmInfo> declineMpoApplication(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {
        InstAdmInfo instAdmInfo=null;
        if(id>0){
            instAdmInfo=instAdmInfoRepository.findOne(id);
        }
        log.debug("REST request to update instAdmInfo : {}--------", instAdmInfo);
        log.debug("REST request to update cause : {}--------", cause);
        if (instAdmInfo == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst AdmInfo cannot decline ").body(null);
        }else{
            instAdmInfo.setDeclineRemarks(cause);
            instAdmInfo.setStatus(2);
            InstAdmInfo result= instAdmInfoRepository.save(instAdmInfo);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
    private InstituteGovernBody getCopyFromTmpInstGovBdToOriginal(InstGovernBody instGovernBodyparm){
        instGovernBody=null;
        instGovernBody=new InstituteGovernBody();
        instGovernBody.setName(instGovernBodyparm.getName());
        instGovernBody.setPosition(instGovernBodyparm.getPosition());
        instGovernBody.setMobileNo(instGovernBodyparm.getMobileNo());
        instGovernBody.setInstitute(instGovernBodyparm.getInstitute());
        return instGovernBody;
    }
    private InstituteGovernBody changeInstGovernBody(InstituteGovernBody instituteGovernBody,InstGovernBody instGovernBodyparm){

        instituteGovernBody.setName(instGovernBodyparm.getName());
        instituteGovernBody.setPosition(instGovernBodyparm.getPosition());
        instituteGovernBody.setMobileNo(instGovernBodyparm.getMobileNo());
        instituteGovernBody.setInstitute(instGovernBodyparm.getInstitute());
        return instituteGovernBody;
    }*/
}
