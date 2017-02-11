package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplDesignation;
import gov.step.app.domain.InstLevel;
import gov.step.app.domain.Institute;
import gov.step.app.domain.enumeration.designationType;
import gov.step.app.repository.InstEmplDesignationRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.InstEmplDesignationSearchRepository;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmplDesignation.
 */
@RestController
@RequestMapping("/api")
public class InstEmplDesignationResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplDesignationResource.class);

    @Inject
    private InstEmplDesignationRepository instEmplDesignationRepository;

    @Inject
    private InstEmplDesignationSearchRepository instEmplDesignationSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    /**
     * POST  /instEmplDesignations -> Create a new instEmplDesignation.
     */
    @RequestMapping(value = "/instEmplDesignations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplDesignation> createInstEmplDesignation(@Valid @RequestBody InstEmplDesignation instEmplDesignation) throws URISyntaxException {
        log.debug("REST request to save InstEmplDesignation : {}", instEmplDesignation);
        if (instEmplDesignation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplDesignation cannot already have an ID").body(null);
        }
        InstEmplDesignation result = instEmplDesignationRepository.save(instEmplDesignation);
        instEmplDesignationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplDesignations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplDesignation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplDesignations -> Updates an existing instEmplDesignation.
     */
    @RequestMapping(value = "/instEmplDesignations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplDesignation> updateInstEmplDesignation(@Valid @RequestBody InstEmplDesignation instEmplDesignation) throws URISyntaxException {
        log.debug("REST request to update InstEmplDesignation : {}", instEmplDesignation);
        if (instEmplDesignation.getId() == null) {
            return createInstEmplDesignation(instEmplDesignation);
        }
        InstEmplDesignation result = instEmplDesignationRepository.save(instEmplDesignation);
        instEmplDesignationSearchRepository.save(instEmplDesignation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplDesignation", instEmplDesignation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations.
     */
    @RequestMapping(value = "/instEmplDesignations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplDesignation>> getAllInstEmplDesignations(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplDesignation> page = instEmplDesignationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplDesignations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations.
     */
    @RequestMapping(value = "/instEmplDesignations/instLevel/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplDesignation>> getInstEmplDesignationsByInstLevel(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<InstEmplDesignation> page = instEmplDesignationRepository.findAllByInstLevelForTeacher(id, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplDesignations/instLevel/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations for teacher by institute.
     */
    @RequestMapping(value = "/instEmplDesignations/teacher/byInstLevelAndType/designation/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplDesignation>> getAllInstEmplDesignationsByInstLevel(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        /*Institute institute = instituteRepository.findOne(id);
        if(institute == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }*/
        Page<InstEmplDesignation> page = instEmplDesignationRepository.findAllByInstLevelAndType(id, designationType.Teacher, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplDesignations/instLevel/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations.
     */
    @RequestMapping(value = "/instEmplDesignations/designationType/{type}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplDesignation>> getInstEmplDesignationsByInstLevel(Pageable pageable, @PathVariable designationType type)
        throws URISyntaxException {
        Page<InstEmplDesignation> page = instEmplDesignationRepository.findAllByDesignationTypeAndStatus(type, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplDesignations/designationType/"+type);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations.
     */
    @RequestMapping(value = "/allInstEmplDesignationsList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplDesignation> getAllInstEmplDesignationList(Pageable pageable)
        throws URISyntaxException {
        List<InstEmplDesignation> instEmplDesignationList = instEmplDesignationRepository.findAllInstEmplDesignationList();
       if(instEmplDesignationList !=null && instEmplDesignationList.size()>0){
           return instEmplDesignationList;
       }else{
           return new ArrayList<>();
       }

    }

    /**
     * GET  /instEmplDesignations -> get all the instEmplDesignations.
     */
    @RequestMapping(value = "/allInstEmplDesignationsList/status/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplDesignation> getAllActiveInstEmplDesignationList()
        throws URISyntaxException {
        List<InstEmplDesignation> instEmplDesignationList = instEmplDesignationRepository.findAllActiveDesignation();
       if(instEmplDesignationList !=null && instEmplDesignationList.size()>0){
           return instEmplDesignationList;
       }else{
           return new ArrayList<>();
       }

    }
    /*@RequestMapping(value = "/instEmplDesignations/instCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getInstCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<InstEmplDesignation> instEmplDesignation = instEmplDesignationRepository.findOneByCode(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(instEmplDesignation)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }*/


    /**
     * Unique  /InstEmplDesignation/code/ -> search for the instCategory Unique
     * to the query.
     * Md. Amanur Rahman
     */

    @RequestMapping(value = "/instEmplDesignations/instCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> findOneByCodeUniqueDes(@RequestParam String value) {
        log.debug("REST request to get InstEmplDesignationUnique by code : {}", value);
        InstEmplDesignation instDesignation = instEmplDesignationRepository.findOneByCode(value);
        Map map =new HashMap();
        map.put("value",value);
        if(instDesignation == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }



    @RequestMapping(value = "/instEmplDesignations/instNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getInstNameUnique( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<InstEmplDesignation> instEmplDesignation = instEmplDesignationRepository.findOneByName(value);


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(instEmplDesignation)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * GET  /instEmplDesignations/:id -> get the "id" instEmplDesignation.
     */
    @RequestMapping(value = "/instEmplDesignations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplDesignation> getInstEmplDesignation(@PathVariable Long id) {
        log.debug("REST request to get InstEmplDesignation : {}", id);
        return Optional.ofNullable(instEmplDesignationRepository.findOne(id))
            .map(instEmplDesignation -> new ResponseEntity<>(
                instEmplDesignation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmplDesignations/:id -> delete the "id" instEmplDesignation.
     */
    @RequestMapping(value = "/instEmplDesignations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplDesignation(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplDesignation : {}", id);
        instEmplDesignationRepository.delete(id);
        instEmplDesignationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplDesignation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplDesignations/:query -> search for the instEmplDesignation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplDesignations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplDesignation> searchInstEmplDesignations(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplDesignationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
