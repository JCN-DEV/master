package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmpCount;
import gov.step.app.domain.InstGenInfo;
import gov.step.app.domain.Institute;
import gov.step.app.repository.InstEmpCountRepository;
import gov.step.app.repository.InstGenInfoRepository;
import gov.step.app.repository.search.InstEmpCountSearchRepository;
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
 * REST controller for managing InstEmpCount.
 */
@RestController
@RequestMapping("/api")
public class InstEmpCountResource {

    private final Logger log = LoggerFactory.getLogger(InstEmpCountResource.class);

    @Inject
    private InstEmpCountRepository instEmpCountRepository;

    @Inject
    private InstEmpCountSearchRepository instEmpCountSearchRepository;

    @Inject
    private InstGenInfoRepository instGenInfoRepository;

    /**
     * POST  /instEmpCounts -> Create a new instEmpCount.
     */
    @RequestMapping(value = "/instEmpCounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpCount> createInstEmpCount(@RequestBody InstEmpCount instEmpCount) throws URISyntaxException {
        log.debug("REST request to save InstEmpCount : {}", instEmpCount);
        if (instEmpCount.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmpCount cannot already have an ID").body(null);
        }
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        instEmpCount.setInstitute(instGenInfo.getInstitute());
        InstEmpCount result = instEmpCountRepository.save(instEmpCount);
        instGenInfo.setStatus((instGenInfo.getStatus()+1));
        instGenInfoRepository.save(instGenInfo);
        instEmpCountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmpCounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmpCount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmpCounts -> Updates an existing instEmpCount.
     */
    @RequestMapping(value = "/instEmpCounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpCount> updateInstEmpCount(@RequestBody InstEmpCount instEmpCount) throws URISyntaxException {
        log.debug("REST request to update InstEmpCount : {}", instEmpCount);
        if (instEmpCount.getId() == null) {
            return createInstEmpCount(instEmpCount);
        }
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(SecurityUtils.getCurrentUserLogin());
        InstEmpCount tmpInstEmpCount=instEmpCountRepository.findOne(instEmpCount.getId());
        Institute institute=null;
        InstEmpCount result=null;
        if(instGenInfo!=null){
        /*if(instEmpCount.getStatus()==1){
            instGenInfo.setStatus((instGenInfo.getStatus()+1));
            instGenInfoRepository.save(instGenInfo);
            instEmpCount.setStatus(2);
        }
        if(instEmpCount.getStatus()==2){
            instGenInfo.setStatus((instGenInfo.getStatus()-1));
            instGenInfoRepository.save(instGenInfo);
            instEmpCount.setStatus(1);
        }
        if(instEmpCount.getStatus()==3){
            instEmpCount.setStatus(2);
        }*/

            if(instEmpCount.getStatus()!=null && instEmpCount.getStatus()==3){
                log.debug("edit-------");
                if(tmpInstEmpCount.getStatus() !=null && tmpInstEmpCount.getStatus()==1){
                    instGenInfo.setStatus((instGenInfo.getStatus()+1));
                    instGenInfoRepository.save(instGenInfo);

                }
                instEmpCount.setStatus(3);

                //instGenInfoRepository.save(instGenInfo);
            }else{

                if(tmpInstEmpCount.getStatus() !=null && tmpInstEmpCount.getStatus()==3){
                    instEmpCount.setStatus(3);
                    instGenInfoRepository.save(instGenInfo);
                }
            }
            result = instEmpCountRepository.save(instEmpCount);
        }else{

            instGenInfo=instGenInfoRepository.findByInstituteId(instEmpCount.getInstitute().getId());
            if(instEmpCount.getStatus()==1){
                log.debug("----------------emp count id---------"+instEmpCount.getId()+" and status---------"+instEmpCount.getStatus());

                //result=instEmpCountRepository.findOne(instEmpCount.getId());
                if(tmpInstEmpCount.getStatus()==null || tmpInstEmpCount.getStatus() !=1){
                    log.debug("----------------emp count id---------"+instEmpCount.getId()+" and status---------"+instEmpCount.getStatus());
                    instGenInfo.setStatus((instGenInfo.getStatus()-1));
                    instGenInfoRepository.save(instGenInfo);
                    //institute=instituteRepository.findOne(result.getInstitute().getId());
                }
                //instGenInfo.setStatus((instGenInfo.getStatus()+1));
                //instGenInfoRepository.save(instGenInfo);
                //instEmpCount.setStatus(2);
                result = instEmpCountRepository.save(instEmpCount);
            }
            /*if(instEmpCount.getStatus()==2){
                instGenInfo.setStatus((instGenInfo.getStatus()-1));
                instGenInfoRepository.save(instGenInfo);
                instEmpCount.setStatus(1);
            }
            if(instEmpCount.getStatus()==3){
                instEmpCount.setStatus(2);
            }*/


        }

        instEmpCountSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmpCount", instEmpCount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmpCounts -> get all the instEmpCounts.
     */
    @RequestMapping(value = "/instEmpCounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmpCount>> getAllInstEmpCounts(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmpCount> page = instEmpCountRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmpCounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmpCounts/:id -> get the "id" instEmpCount.
     */
    @RequestMapping(value = "/instEmpCounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpCount> getInstEmpCount(@PathVariable Long id) {
        log.debug("REST request to get InstEmpCount : {}", id);
        String userName= SecurityUtils.getCurrentUserLogin();
        Long userId= SecurityUtils.getCurrentUserId();
        log.debug("userName id-------------------------"+userId);
        log.debug("code id-------------------------"+userName);
        InstGenInfo instGenInfo=instGenInfoRepository.findByCodeIgnoreCase(userName);
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
            return Optional.ofNullable(instEmpCountRepository.findOneByInstituteId(instGenInfo.getInstitute().getId()))
                .map(instEmpCount -> new ResponseEntity<>(
                    instEmpCount,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }else {
            return Optional.ofNullable(instEmpCountRepository.findOne(id))
                .map(instEmpCount -> new ResponseEntity<>(
                    instEmpCount,
                    HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
        }

    }

    /**
     * DELETE  /instEmpCounts/:id -> delete the "id" instEmpCount.
     */
    @RequestMapping(value = "/instEmpCounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmpCount(@PathVariable Long id) {
        log.debug("REST request to delete InstEmpCount : {}", id);
        instEmpCountRepository.delete(id);
        instEmpCountSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmpCount", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmpCounts/:query -> search for the instEmpCount corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmpCounts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpCount> searchInstEmpCounts(@PathVariable String query) {
        return StreamSupport
            .stream(instEmpCountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /instEmpCounts/:id -> get the "id" instEmpCount.
     */
    @RequestMapping(value = "/instEmpCountByInstituteId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpCount> getInstEmpCountByInstituteId(@PathVariable Long instituteId) {
        log.debug("REST request to get InstEmpCount : {}", instituteId);
        return Optional.ofNullable(instEmpCountRepository.findOneByInstituteId(instituteId))
            .map(instEmpCount -> new ResponseEntity<>(
                instEmpCount,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * PUT  /mpoApplications -> Updates an existing mpoApplication.
     */
    @RequestMapping(value = "/instEmpCounts/decline/{id}",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpCount> declineMpoApplication(@PathVariable Long id,@RequestBody String cause)
        throws URISyntaxException {
        InstEmpCount instEmpCount=null;
        if(id>0){
            instEmpCount=instEmpCountRepository.findOne(id);
        }
        log.debug("REST request to update insAcademicInfo : {}--------", instEmpCount);
        log.debug("REST request to update cause : {}--------", cause);
        if (instEmpCount == null) {
            return ResponseEntity.badRequest().header("Failure", "A new inst AdmInfo cannot decline ").body(null);
        }else{
            instEmpCount.setDeclineRemarks(cause);
            instEmpCount.setStatus(2);
            InstEmpCount result= instEmpCountRepository.save(instEmpCount);
            return ResponseEntity.ok()
                .headers(HeaderUtil.createEntityUpdateAlert("result", result.getId().toString()))
                .body(null);
        }
    }
}
