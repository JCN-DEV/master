/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSemester;
import gov.step.app.domain.CmsSubject;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.repository.CmsSubjectRepository;
import gov.step.app.repository.search.CmsSubjectSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

*/
/**
 * REST controller for managing CmsSubject.
 *//*

@RestController
@RequestMapping("/api")
public class CmsSubjectResource {

    private final Logger log = LoggerFactory.getLogger(CmsSubjectResource.class);

    @Inject
    private CmsSubjectRepository cmsSubjectRepository;

    @Inject
    private CmsSubjectSearchRepository cmsSubjectSearchRepository;

    */
/**
     * POST  /cmsSubjects -> Create a new cmsSubject.
     *//*

    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> createCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws URISyntaxException {
        log.debug("REST request to save CmsSubject : {}", cmsSubject);
        if (cmsSubject.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSubject cannot already have an ID").body(null);
        }
        CmsSubject result = cmsSubjectRepository.save(cmsSubject);
        cmsSubjectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSubjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSubject", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsSubjects -> Updates an existing cmsSubject.
     *//*

    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> updateCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws URISyntaxException {
        log.debug("REST request to update CmsSubject : {}", cmsSubject);
        if (cmsSubject.getId() == null) {
            return createCmsSubject(cmsSubject);
        }
        CmsSubject result = cmsSubjectRepository.save(cmsSubject);
        cmsSubjectSearchRepository.save(cmsSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSubject", cmsSubject.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsSubjects -> get all the cmsSubjects.
     *//*

    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubject>> getAllCmsSubjects(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSubject> page = cmsSubjectRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSubjects -> get all the cmsSubjects.
     *//*

    @RequestMapping(value = "/cmsSubjects/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubject>> getAllCmsSubjects(Pageable pageable,@PathVariable Long id)
        throws URISyntaxException {
        Page<CmsSubject> page = cmsSubjectRepository.findAllSubjectByCurriculum(pageable,id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSubjects/:id -> get the "id" cmsSubject.
     *//*

    @RequestMapping(value = "/cmsSubjects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> getCmsSubject(@PathVariable Long id) {
        log.debug("REST request to get CmsSubject : {}", id);
        return Optional.ofNullable(cmsSubjectRepository.findOne(id))
            .map(cmsSubject -> new ResponseEntity<>(
                cmsSubject,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    */
/**
     * GET  /cmsSubjects/code/:code -> get the "code" cmsSubject.
     *//*

    @RequestMapping(value = "/cmsSubjects/getCmsSubByNameAndSyllabusAndCode/{cmsSyllabusId}/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> getCmsSubByNameAndSyllabusAndCode(@PathVariable Long cmsSyllabusId, @PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsSubject by cmsSyllabusId,code,name,cmsSyllabusId : {}, code : {},name : {}",cmsSyllabusId,code,name);
        return Optional.ofNullable(cmsSubjectRepository.findOneByNameAndSyllabusAndCode(cmsSyllabusId,code,name))
            .map(cmsSubject -> new ResponseEntity<>(
                cmsSubject,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

   */
/* @RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabusByNameAndVersion(@PathVariable Long cmsCurriculumId, @PathVariable String name, @PathVariable String version) {
        log.debug("REST request to get CmsSyllabus by cmsCurriculumId, version, name, cmsCurriculumId : {}, version : {}, name : {}", cmsCurriculumId, name,version);
        return Optional.ofNullable(cmsSyllabusRepository.findOneByNameAndVersion(cmsCurriculumId,  version,name))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*//*


    */
/**
     * DELETE  /cmsSubjects/:id -> delete the "id" cmsSubject.
     *//*

    @RequestMapping(value = "/cmsSubjects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSubject(@PathVariable Long id) {
        log.debug("REST request to delete CmsSubject : {}", id);
        cmsSubjectRepository.delete(id);
        cmsSubjectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSubject", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsSubjects/:query -> search for the cmsSubject corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsSubjects/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSubject> searchCmsSubjects(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSubjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/

package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSemester;
import gov.step.app.domain.CmsSubject;
import gov.step.app.domain.CmsSyllabus;
import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsSubjectRepository;
import gov.step.app.repository.search.CmsSubjectSearchRepository;
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

/**
 * REST controller for managing CmsSubject.
 */
@RestController
@RequestMapping("/api")
public class CmsSubjectResource {

    private final Logger log = LoggerFactory.getLogger(CmsSubjectResource.class);

    @Inject
    private CmsSubjectRepository cmsSubjectRepository;

    @Inject
    private CmsSubjectSearchRepository cmsSubjectSearchRepository;

    /**
     * POST  /cmsSubjects -> Create a new cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> createCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws URISyntaxException {
        log.debug("REST request to save CmsSubject : {}", cmsSubject);
        if (cmsSubject.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSubject cannot already have an ID").body(null);
        }
        CmsSubject result = cmsSubjectRepository.save(cmsSubject);
        cmsSubjectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSubjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSubject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsSubjects -> Updates an existing cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> updateCmsSubject(@Valid @RequestBody CmsSubject cmsSubject) throws URISyntaxException {
        log.debug("REST request to update CmsSubject : {}", cmsSubject);
        if (cmsSubject.getId() == null) {
            return createCmsSubject(cmsSubject);
        }
        CmsSubject result = cmsSubjectRepository.save(cmsSubject);
        cmsSubjectSearchRepository.save(cmsSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSubject", cmsSubject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsSubjects -> get all the cmsSubjects.
     */
    @RequestMapping(value = "/cmsSubjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubject>> getAllCmsSubjects(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSubject> page = cmsSubjectRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsSubjects/:id -> get the "id" cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> getCmsSubject(@PathVariable Long id) {
        log.debug("REST request to get CmsSubject : {}", id);
        return Optional.ofNullable(cmsSubjectRepository.findOne(id))
            .map(cmsSubject -> new ResponseEntity<>(
                cmsSubject,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /cmsSubjects/code/:code -> get the "code" cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects/getCmsSubByNameAndSyllabusAndCode/{cmsSyllabusId}/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubject> getCmsSubByNameAndSyllabusAndCode(@PathVariable Long cmsSyllabusId, @PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsSubject by cmsSyllabusId,code,name,cmsSyllabusId : {}, code : {},name : {}",cmsSyllabusId,code,name);
        return Optional.ofNullable(cmsSubjectRepository.findOneByNameAndSyllabusAndCode(cmsSyllabusId,code,name))
            .map(cmsSubject -> new ResponseEntity<>(
                cmsSubject,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }



    @RequestMapping(value = "/cmsSubjects/name/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getcmsSubjectByName( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<CmsSubject> cmsSubject = cmsSubjectRepository.findOneByName(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsSubject)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }


 @RequestMapping(value = "/cmsSubjects/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getcmsSubjectByCode( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<CmsSubject> cmsSubject = cmsSubjectRepository.findOneByCode(value);



        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsSubject)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * GET  /cmsSubjects/code/:code -> get the "code" cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSubject> getCmsSubsByCurriculum(@PathVariable Long id) {
        log.debug("REST request to get CmsSubjects by cmsCurriculum",id);
        return cmsSubjectRepository.findSubjectsByCurriculum(id);
    }

    @RequestMapping(value = "cmsSubjects/allActivecmsSubjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubject>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<CmsSubject> page = cmsSubjectRepository.activecmsSubjects(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/cmsSubjects/allActivecmsSubjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

   /* @RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSyllabus> getCmsSyllabusByNameAndVersion(@PathVariable Long cmsCurriculumId, @PathVariable String name, @PathVariable String version) {
        log.debug("REST request to get CmsSyllabus by cmsCurriculumId, version, name, cmsCurriculumId : {}, version : {}, name : {}", cmsCurriculumId, name,version);
        return Optional.ofNullable(cmsSyllabusRepository.findOneByNameAndVersion(cmsCurriculumId,  version,name))
            .map(cmsSyllabus -> new ResponseEntity<>(
                cmsSyllabus,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    /**
     * DELETE  /cmsSubjects/:id -> delete the "id" cmsSubject.
     */
    @RequestMapping(value = "/cmsSubjects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSubject(@PathVariable Long id) {
        log.debug("REST request to delete CmsSubject : {}", id);
        cmsSubjectRepository.delete(id);
        cmsSubjectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSubject", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsSubjects/:query -> search for the cmsSubject corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsSubjects/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSubject> searchCmsSubjects(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSubjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

