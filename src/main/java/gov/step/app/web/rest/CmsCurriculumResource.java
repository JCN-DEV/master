/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.repository.CmsCurriculumRepository;
import gov.step.app.repository.search.CmsCurriculumSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

*/
/**
 * REST controller for managing CmsCurriculum.
 *//*

@RestController
@RequestMapping("/api")
public class CmsCurriculumResource {

    private final Logger log = LoggerFactory.getLogger(CmsCurriculumResource.class);

    @Inject
    private CmsCurriculumRepository cmsCurriculumRepository;

    @Inject
    private CmsCurriculumSearchRepository cmsCurriculumSearchRepository;

    */
/**
     * POST  /cmsCurriculums -> Create a new cmsCurriculum.
     *//*

    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> createCmsCurriculum(@Valid @RequestBody CmsCurriculum cmsCurriculum) throws URISyntaxException {
        log.debug("REST request to save CmsCurriculum : {}", cmsCurriculum);
        if (cmsCurriculum.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsCurriculum cannot already have an ID").body(null);
        }
        CmsCurriculum result = cmsCurriculumRepository.save(cmsCurriculum);
        cmsCurriculumSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsCurriculums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsCurriculum", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsCurriculums -> Updates an existing cmsCurriculum.
     *//*

    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> updateCmsCurriculum(@Valid @RequestBody CmsCurriculum cmsCurriculum) throws URISyntaxException {
        log.debug("REST request to update CmsCurriculum : {}", cmsCurriculum);
        if (cmsCurriculum.getId() == null) {
            return createCmsCurriculum(cmsCurriculum);
        }
        CmsCurriculum result = cmsCurriculumRepository.save(cmsCurriculum);
        cmsCurriculumSearchRepository.save(cmsCurriculum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsCurriculum", cmsCurriculum.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsCurriculums -> get all the cmsCurriculums.
     *//*

    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> getAllCmsCurriculums(Pageable pageable)
        throws URISyntaxException {
        Page<CmsCurriculum> page = cmsCurriculumRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsCurriculums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsCurriculums/:id -> get the "id" cmsCurriculum.
     *//*

    @RequestMapping(value = "/cmsCurriculums/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> getCmsCurriculum(@PathVariable Long id) {
        log.debug("REST request to get CmsCurriculum : {}", id);
        return Optional.ofNullable(cmsCurriculumRepository.findOne(id))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/**
     * GET  /cmsCurriculums/code/:code -> get the "code" cmsCurriculum.
     *//*

    */
/*@RequestMapping(value = "/cmsCurriculums/code/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> getCmsCurriculumByCode(@PathVariable String code) {
        log.debug("REST request to get CmsCurriculum by code : {}", code);
        return Optional.ofNullable(cmsCurriculumRepository.findOneByCode(code))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*//*


    @RequestMapping(value = "/cmsCurriculums/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsCurriculumByCode( @RequestParam String value) {

        log.debug("REST request to get CmsCurriculum by code : {}", value);

        Optional<CmsCurriculum> cmsCurriculum = cmsCurriculumRepository.findOneByCode(value);

       */
/* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*//*


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsCurriculum)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }



    @RequestMapping(value = "/cmsCurriculums/name/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsCurriculumByName( @RequestParam String value) {

        log.debug("REST request to get CmsCurriculum by name : {}", value);

        Optional<CmsCurriculum> cmsCurriculum = cmsCurriculumRepository.findOneByName(value);
       */
/* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check name----"+Optional.empty().equals(cmsCurriculum));*//*


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsCurriculum)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }








    */
/**

     * DELETE  /cmsCurriculums/:id -> delete the "id" cmsCurriculum.
     *//*

    @RequestMapping(value = "/cmsCurriculums/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete CmsCurriculum : {}", id);
        cmsCurriculumRepository.delete(id);
        cmsCurriculumSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsCurriculum", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsCurriculums/:query -> search for the cmsCurriculum corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsCurriculums/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsCurriculum> searchCmsCurriculums(@PathVariable String query) {
        return StreamSupport
            .stream(cmsCurriculumSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/


package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsSemester;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.repository.CmsCurriculumRegCfgRepository;
import gov.step.app.repository.CmsCurriculumRepository;
import gov.step.app.repository.search.CmsCurriculumSearchRepository;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.hibernate.criterion.Order;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
 * REST controller for managing CmsCurriculum.
 */
@RestController
@RequestMapping("/api")
public class CmsCurriculumResource {

    private final Logger log = LoggerFactory.getLogger(CmsCurriculumResource.class);

    @Inject
    private CmsCurriculumRepository cmsCurriculumRepository;

    @Inject
    private CmsCurriculumRegCfgRepository cmsCurriculumRegCfgRepository;

    @Inject
    private CmsCurriculumSearchRepository cmsCurriculumSearchRepository;

    /**
     * POST  /cmsCurriculums -> Create a new cmsCurriculum.
     */
    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> createCmsCurriculum(@Valid @RequestBody CmsCurriculum cmsCurriculum) throws URISyntaxException {
        log.debug("REST request to save CmsCurriculum : {}", cmsCurriculum);
        if (cmsCurriculum.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsCurriculum cannot already have an ID").body(null);
        }
        CmsCurriculum result = cmsCurriculumRepository.save(cmsCurriculum);
        cmsCurriculumSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsCurriculums/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsCurriculum", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsCurriculums -> Updates an existing cmsCurriculum.
     */
    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> updateCmsCurriculum(@Valid @RequestBody CmsCurriculum cmsCurriculum) throws URISyntaxException {
        log.debug("REST request to update CmsCurriculum : {}", cmsCurriculum);
        if (cmsCurriculum.getId() == null) {
            return createCmsCurriculum(cmsCurriculum);
        }
        CmsCurriculum result = cmsCurriculumRepository.save(cmsCurriculum);
        cmsCurriculumSearchRepository.save(cmsCurriculum);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsCurriculum", cmsCurriculum.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsCurriculums -> get all the cmsCurriculums.
     */
    @RequestMapping(value = "/cmsCurriculums",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> getAllCmsCurriculums(Pageable pageable, Sort sort)
        throws URISyntaxException {
        Page<CmsCurriculum> page = cmsCurriculumRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsCurriculums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsCurriculums/:id -> get the "id" cmsCurriculum.
     */
   /* @RequestMapping(value = "/cmsCurriculums/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> getCmsCurriculum(@PathVariable Long id) {
        log.debug("REST request to get CmsCurriculum : {}", id);
        return Optional.ofNullable(cmsCurriculumRepository.findOne(id))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @RequestMapping(value = "/cmsCurriculums/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> getCmsCurriculum(@PathVariable Long id) {
        log.debug("REST request to get CmsCurriculum : {}", id);
        return Optional.ofNullable(cmsCurriculumRepository.findOne(id))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /*@RequestMapping(value = "/cmsCurriculums/code/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculum> getCmsCurriculumByCode(@PathVariable String code) {
        log.debug("REST request to get CmsCurriculum by code : {}", code);
        return Optional.ofNullable(cmsCurriculumRepository.findOneByCode(code))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @RequestMapping(value = "/cmsCurriculums/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsCurriculumByCode( @RequestParam String value) {

        log.debug("REST request to get CmsCurriculum by code : {}", value);

        Optional<CmsCurriculum> cmsCurriculum = cmsCurriculumRepository.findOneByCode(value);


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsCurriculum)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/cmsCurriculums/noFountInRegCfg",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsCurriculum> getCmsCurriculumByCode() {

        log.debug("REST request to get CmsCurriculum by code : {}");
        List<CmsCurriculum> curriculums =  cmsCurriculumRegCfgRepository.findCurriculumListFound();
        if(curriculums.size() < 1){
            return null;
        }
        log.debug("**************** : total curriculum :"+curriculums.size());
        for(CmsCurriculum each:curriculums){
            log.debug(">>>>>>>>>>>>>>>>>>>>>>>>>>> curri name :"+each.getName());
        }
        List<Long> ids = new ArrayList<>();
        //StringBuilder result = new StringBuilder();
        for(CmsCurriculum each : curriculums) {
            ids.add(each.getId());

        }

        return cmsCurriculumRepository.findSelectedCuriculums(ids);


    }

    @RequestMapping(value = "/cmsCurriculums/findAllcmsCurriculumByOrderID",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> findAllcmsCurriculumByOrderID(Pageable pageable) throws Exception{
    /*public ResponseEntity<gov.step.app.domain.CmsCurriculum> findAllcmsCurriculumByOrderID() {*/
        log.debug("REST request to get CmsCurriculum order by ID");/*
        return Optional.ofNullable(cmsCurriculumRepository.findAllcmsCurriculumByOrderID())
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));*/
        Page<CmsCurriculum> page = cmsCurriculumRepository.findAllcmsCurriculumByOrderID(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/cmsCurriculums/findAllcmsCurriculumByOrderID");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


/*

    @RequestMapping(value = "/cmsCurriculum/getCmsCurriculumByCodeAndName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<gov.step.app.domain.CmsCurriculum> getCmsCurriculumByCodeAndName(@PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsCurriculum by code,name, code : {},name : {}",code,name);
        return Optional.ofNullable(cmsCurriculumRepository.findOneByCodeAndName(code,name))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
*/




    @RequestMapping(value = "/cmsCurriculums/name/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsCurriculumByName( @RequestParam String value) {

        log.debug("REST request to get CmsCurriculum by name : {}", value);

        Optional<CmsCurriculum> cmsCurriculum = cmsCurriculumRepository.findOneByName(value);

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsCurriculum)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }



    @RequestMapping(value = "cmsCurriculums/allActivecmsCurriculums",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculum>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<CmsCurriculum> page = cmsCurriculumRepository.activecmsCurriculums(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/cmsCurriculums/allActivecmsCurriculums");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    /**
     * GET  /iamsAppForms/getIamsAppFormByInstName/{instName} cmsCurriculum.

     */

    @RequestMapping(value = "/cmsCurriculums/getIamsAppFormByInstName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<gov.step.app.domain.CmsCurriculum> getIamsAppFormByInstName(@PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsCurriculum by code,name, code : {},name : {}",code,name);
        return Optional.ofNullable(cmsCurriculumRepository.findOneByCodeAndName(code,name))
            .map(cmsCurriculum -> new ResponseEntity<>(
                cmsCurriculum,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }








    /**

     * DELETE  /cmsCurriculums/:id -> delete the "id" cmsCurriculum.
     */
    @RequestMapping(value = "/cmsCurriculums/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsCurriculum(@PathVariable Long id) {
        log.debug("REST request to delete CmsCurriculum : {}", id);
        cmsCurriculumRepository.delete(id);
        cmsCurriculumSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsCurriculum", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsCurriculums/:query -> search for the cmsCurriculum corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsCurriculums/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsCurriculum> searchCmsCurriculums(@PathVariable String query) {
        return StreamSupport
            .stream(cmsCurriculumSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

