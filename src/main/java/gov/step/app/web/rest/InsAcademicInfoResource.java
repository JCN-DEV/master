package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InsAcademicInfo;
import gov.step.app.domain.InstAdmInfo;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.domain.Institute;
import gov.step.app.repository.InsAcademicInfoRepository;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.InsAcademicInfoSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InsAcademicInfo.
 */
@RestController
@RequestMapping("/api")
public class InsAcademicInfoResource {

    private final Logger log = LoggerFactory.getLogger(InsAcademicInfoResource.class);

    @Inject
    private InsAcademicInfoRepository insAcademicInfoRepository;

    @Inject
    private InsAcademicInfoSearchRepository insAcademicInfoSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    @Inject
    private InstituteRepository instituteRepository;
    /**
     * POST  /insAcademicInfos -> Create a new insAcademicInfo.
     */
    @RequestMapping(value = "/insAcademicInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfo> createInsAcademicInfo(@RequestBody InsAcademicInfo insAcademicInfo) throws URISyntaxException {
        log.debug("REST request to save InsAcademicInfo : {}", insAcademicInfo);
        if (insAcademicInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new insAcademicInfo cannot already have an ID").body(null);
        }
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if(instGenInfo!=null){
            log.debug("institute ---"+instGenInfo.getInstitute());
            log.debug("institute -id--"+instGenInfo.getInstitute().getId());
            insAcademicInfo.setInstitute(instGenInfo.getInstitute());
            InsAcademicInfo result = insAcademicInfoRepository.save(insAcademicInfo);
            instGenInfo.setStatus((instGenInfo.getStatus()+1));
            instGenInfoRepository.save(instGenInfo);
            insAcademicInfoSearchRepository.save(result);
            return ResponseEntity.created(new URI("/api/insAcademicInfos/" + result.getId()))
                .headers(HeaderUtil.createEntityCreationAlert("insAcademicInfo", result.getId().toString()))
                .body(result);
        }else{
            return ResponseEntity.badRequest().header("Failure", "You dont have access to add Institute Academic Info").body(null);

        }

    }

    /**
     * PUT  /insAcademicInfos -> Updates an existing insAcademicInfo.
     */
    @RequestMapping(value = "/insAcademicInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfo> updateInsAcademicInfo(@RequestBody InsAcademicInfo insAcademicInfo) throws URISyntaxException {
        log.debug("REST request to update InsAcademicInfo : {}", insAcademicInfo);
        if (insAcademicInfo.getId() == null) {
            return createInsAcademicInfo(insAcademicInfo);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        InsAcademicInfo tmpinsAcademicInfo=insAcademicInfoRepository.findOne(insAcademicInfo.getId());
        Institute institute=null;
        InsAcademicInfo result=null;
        if(instGenInfo!=null){
            /*if(insAcademicInfo.getStatus()==1){
                instGenInfo.setStatus((instGenInfo.getStatus()+1));
                instGenInfoRepository.save(instGenInfo);
                insAcademicInfo.setStatus(2);
            }
            if(insAcademicInfo.getStatus()==2){
                instGenInfo.setStatus((instGenInfo.getStatus()-1));
                instGenInfoRepository.save(instGenInfo);
                insAcademicInfo.setStatus(1);
            }
            if(insAcademicInfo.getStatus()==3){
                insAcademicInfo.setStatus(2);
            }*/

            if(insAcademicInfo.getStatus() !=null && insAcademicInfo.getStatus()==3){
                log.debug("edit-------");
                if(tmpinsAcademicInfo.getStatus() !=null && tmpinsAcademicInfo.getStatus()==1){
                    instGenInfo.setStatus((instGenInfo.getStatus()+1));
                    instGenInfoRepository.save(instGenInfo);
                    insAcademicInfo.setStatus(3);
                }
                //instGenInfoRepository.save(instGenInfo);
            }/*else{

                if(tmpinsAcademicInfo.getStatus() !=null && tmpinsAcademicInfo.getStatus()==3){
                    tmpinsAcademicInfo.setStatus(3);
                    instGenInfoRepository.save(instGenInfo);
                }
            }*/
            result = insAcademicInfoRepository.save(insAcademicInfo);
            insAcademicInfoSearchRepository.save(result);
        }else{
            result = insAcademicInfoRepository.save(insAcademicInfo);
            instGenInfo=instGenInfoRepository.findByInstituteId(result.getInstitute().getId());
            if(insAcademicInfo.getStatus()==1){
                if(tmpinsAcademicInfo.getStatus() !=null && tmpinsAcademicInfo.getStatus() !=1){
                    instGenInfo.setStatus((instGenInfo.getStatus()-1));
                    instGenInfoRepository.save(instGenInfo);
                }
                institute=instituteRepository.findOne(result.getInstitute().getId());

                if(institute !=null){
                    institute.setCounselorName(result.getCounselorName());
                    institute.setCurriculum(result.getCurriculum());
                    institute.setTotalTechTradeNo(result.getTotalTechTradeNo());
                    institute.setTradeTechDetails(result.getTradeTechDetails());
                    institute.setTotalStudent(result.getTotalStudent());
                    instituteRepository.save(institute);
                }
            }

        }

        insAcademicInfoSearchRepository.save(result);

        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("insAcademicInfo", insAcademicInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /insAcademicInfos -> get all the insAcademicInfos.
     */
    @RequestMapping(value = "/insAcademicInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InsAcademicInfo>> getAllInsAcademicInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InsAcademicInfo> page = insAcademicInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/insAcademicInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /insAcademicInfos/:id -> get the "id" insAcademicInfo.
     */
    @RequestMapping(value = "/insAcademicInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfo> getInsAcademicInfo(@PathVariable Long id) {
        log.debug("REST request to get InsAcademicInfo : {}", id);

        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();

       InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            log.debug("userName id-------------------------"+userId);
            log.debug("code id-------------------------"+userName);
            return Optional.ofNullable(insAcademicInfoRepository.findByInstituteId(instGenInfo.getInstitute().getId()))
                .map(insAcademicInfo -> new ResponseEntity<>(
                    insAcademicInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else{
            return Optional.ofNullable(insAcademicInfoRepository.findOne(id))
                .map(insAcademicInfo -> new ResponseEntity<>(
                    insAcademicInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }



    }

    /**
     * DELETE  /insAcademicInfos/:id -> delete the "id" insAcademicInfo.
     */
    @RequestMapping(value = "/insAcademicInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInsAcademicInfo(@PathVariable Long id) {
        log.debug("REST request to delete InsAcademicInfo : {}", id);
        insAcademicInfoRepository.delete(id);
        insAcademicInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("insAcademicInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/insAcademicInfos/:query -> search for the insAcademicInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/insAcademicInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InsAcademicInfo> searchInsAcademicInfos(@PathVariable String query) {
        return StreamSupport
            .stream(insAcademicInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/insAcademicInfoByInstituteId/{instituteId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InsAcademicInfo> getInsAcademicInfoByInstituteId(@PathVariable Long instituteId) {
        log.debug("REST request to get InsAcademicInfo : {}", instituteId);

        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
            return Optional.ofNullable(insAcademicInfoRepository.findOne(instituteId))
                .map(insAcademicInfo -> new ResponseEntity<>(
                    insAcademicInfo,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
