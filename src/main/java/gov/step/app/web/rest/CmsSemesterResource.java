/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSemester;
import gov.step.app.repository.CmsSemesterRepository;
import gov.step.app.repository.search.CmsSemesterSearchRepository;
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
 * REST controller for managing CmsSemester.
 *//*

@RestController
@RequestMapping("/api")
public class CmsSemesterResource {

    private final Logger log = LoggerFactory.getLogger(CmsSemesterResource.class);

    @Inject
    private CmsSemesterRepository cmsSemesterRepository;

    @Inject
    private CmsSemesterSearchRepository cmsSemesterSearchRepository;

    */
/**
     * POST  /cmsSemesters -> Create a new cmsSemester.
     *//*

    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> createCmsSemester(@Valid @RequestBody CmsSemester cmsSemester) throws URISyntaxException {
        log.debug("REST request to save CmsSemester : {}", cmsSemester);
        if (cmsSemester.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSemester cannot already have an ID").body(null);
        }
        CmsSemester result = cmsSemesterRepository.save(cmsSemester);
        cmsSemesterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSemesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSemester", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsSemesters -> Updates an existing cmsSemester.
     *//*

    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> updateCmsSemester(@Valid @RequestBody CmsSemester cmsSemester) throws URISyntaxException {
        log.debug("REST request to update CmsSemester : {}", cmsSemester);
        if (cmsSemester.getId() == null) {
            return createCmsSemester(cmsSemester);
        }
        CmsSemester result = cmsSemesterRepository.save(cmsSemester);
        cmsSemesterSearchRepository.save(cmsSemester);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSemester", cmsSemester.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsSemesters -> get all the cmsSemesters.
     *//*

    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSemester>> getAllCmsSemesters(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSemester> page = cmsSemesterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSemesters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSemesters/:id -> get the "id" cmsSemester.
     *//*

    @RequestMapping(value = "/cmsSemesters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> getCmsSemester(@PathVariable Long id) {
        log.debug("REST request to get CmsSemester : {}", id);
        return Optional.ofNullable(cmsSemesterRepository.findOne(id))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/*@RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
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
     * GET  /cmsSemesters/getCmsSemesterByCodeAndName/:code/:name -> get the "code" and "name" cmsSemester.
     *//*

   */
/* @RequestMapping(value = "/cmsSemesters/getCmsSemesterByCodeAndName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> getCmsSemesterByCodeAndName(@PathVariable String code, @PathVariable String name) {
        log.debug("REST request to get CmsSemester by code, name, code : {}, name : {}", code, name);
        return Optional.ofNullable(cmsSemesterRepository.findOneByCodeAndName(code, name))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*//*

    @RequestMapping(value = "/cmsSemesters/getCmsSemesterByCodeAndName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<gov.step.app.domain.CmsSemester> getCmsSemesterByCodeAndName(@PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsSemester by code,name, code : {},name : {}",code,name);
        return Optional.ofNullable(cmsSemesterRepository.findOneByCodeAndName(code,name))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/**
     *
     * DELETE  /cmsSemesters/:id -> delete the "id" cmsSemester.
     *//*

    @RequestMapping(value = "/cmsSemesters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSemester(@PathVariable Long id) {
        log.debug("REST request to delete CmsSemester : {}", id);
        cmsSemesterRepository.delete(id);
        cmsSemesterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSemester", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsSemesters/:query -> search for the cmsSemester corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsSemesters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSemester> searchCmsSemesters(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSemesterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/


package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSemester;
import gov.step.app.repository.CmsSemesterRepository;
import gov.step.app.repository.search.CmsSemesterSearchRepository;
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

/**
 * REST controller for managing CmsSemester.
 */
@RestController
@RequestMapping("/api")
public class CmsSemesterResource {

    private final Logger log = LoggerFactory.getLogger(CmsSemesterResource.class);

    @Inject
    private CmsSemesterRepository cmsSemesterRepository;

    @Inject
    private CmsSemesterSearchRepository cmsSemesterSearchRepository;

    /**
     * POST  /cmsSemesters -> Create a new cmsSemester.
     */
    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> createCmsSemester(@Valid @RequestBody CmsSemester cmsSemester) throws URISyntaxException {
        log.debug("REST request to save CmsSemester : {}", cmsSemester);
        if (cmsSemester.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSemester cannot already have an ID").body(null);
        }
        CmsSemester result = cmsSemesterRepository.save(cmsSemester);
        cmsSemesterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSemesters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSemester", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsSemesters -> Updates an existing cmsSemester.
     */
    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> updateCmsSemester(@Valid @RequestBody CmsSemester cmsSemester) throws URISyntaxException {
        log.debug("REST request to update CmsSemester : {}", cmsSemester);
        if (cmsSemester.getId() == null) {
            return createCmsSemester(cmsSemester);
        }
        CmsSemester result = cmsSemesterRepository.save(cmsSemester);
        cmsSemesterSearchRepository.save(cmsSemester);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSemester", cmsSemester.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsSemesters -> get all the cmsSemesters.
     */
    @RequestMapping(value = "/cmsSemesters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSemester>> getAllCmsSemesters(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSemester> page = cmsSemesterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSemesters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsSemesters/:id -> get the "id" cmsSemester.
     */
    @RequestMapping(value = "/cmsSemesters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> getCmsSemester(@PathVariable Long id) {
        log.debug("REST request to get CmsSemester : {}", id);
        return Optional.ofNullable(cmsSemesterRepository.findOne(id))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /*@RequestMapping(value = "/cmsSyllabuss/getCmsSyllabusByNameAndVersion/{cmsCurriculumId}/{name}/{version}",
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
     * GET  /cmsSemesters/getCmsSemesterByCodeAndName/:code/:name -> get the "code" and "name" cmsSemester.
     */
   /* @RequestMapping(value = "/cmsSemesters/getCmsSemesterByCodeAndName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSemester> getCmsSemesterByCodeAndName(@PathVariable String code, @PathVariable String name) {
        log.debug("REST request to get CmsSemester by code, name, code : {}, name : {}", code, name);
        return Optional.ofNullable(cmsSemesterRepository.findOneByCodeAndName(code, name))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/
    @RequestMapping(value = "/cmsSemesters/getCmsSemesterByCodeAndName/{code}/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<gov.step.app.domain.CmsSemester> getCmsSemesterByCodeAndName(@PathVariable String code,@PathVariable String name) {
        log.debug("REST request to get CmsSemester by code,name, code : {},name : {}",code,name);
        return Optional.ofNullable(cmsSemesterRepository.findOneByCodeAndName(code,name))
            .map(cmsSemester -> new ResponseEntity<>(
                cmsSemester,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     *
     * DELETE  /cmsSemesters/:id -> delete the "id" cmsSemester.
     */
    @RequestMapping(value = "/cmsSemesters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSemester(@PathVariable Long id) {
        log.debug("REST request to delete CmsSemester : {}", id);
        cmsSemesterRepository.delete(id);
        cmsSemesterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSemester", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsSemesters/:query -> search for the cmsSemester corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsSemesters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSemester> searchCmsSemesters(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSemesterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

