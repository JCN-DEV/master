/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSubAssign;
import gov.step.app.repository.CmsSubAssignRepository;
import gov.step.app.repository.search.CmsSubAssignSearchRepository;
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
 * REST controller for managing CmsSubAssign.
 *//*

@RestController
@RequestMapping("/api")
public class CmsSubAssignResource {

    private final Logger log = LoggerFactory.getLogger(CmsSubAssignResource.class);

    @Inject
    private CmsSubAssignRepository cmsSubAssignRepository;

    @Inject
    private CmsSubAssignSearchRepository cmsSubAssignSearchRepository;

    */
/**
     * POST  /cmsSubAssigns -> Create a new cmsSubAssign.
     *//*

    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> createCmsSubAssign(@Valid @RequestBody CmsSubAssign cmsSubAssign) throws URISyntaxException {
        log.debug("REST request to save CmsSubAssign : {}", cmsSubAssign);
        if (cmsSubAssign.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSubAssign cannot already have an ID").body(null);
        }
        CmsSubAssign result = cmsSubAssignRepository.save(cmsSubAssign);
        cmsSubAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSubAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSubAssign", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsSubAssigns -> Updates an existing cmsSubAssign.
     *//*

    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> updateCmsSubAssign(@Valid @RequestBody CmsSubAssign cmsSubAssign) throws URISyntaxException {
        log.debug("REST request to update CmsSubAssign : {}", cmsSubAssign);
        if (cmsSubAssign.getId() == null) {
            return createCmsSubAssign(cmsSubAssign);
        }
        CmsSubAssign result = cmsSubAssignRepository.save(cmsSubAssign);
        cmsSubAssignSearchRepository.save(cmsSubAssign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSubAssign", cmsSubAssign.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsSubAssigns -> get all the cmsSubAssigns.
     *//*

    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubAssign>> getAllCmsSubAssigns(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSubAssign> page = cmsSubAssignRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSubAssigns -> get all the cmsSubAssigns.
     *//*

    @RequestMapping(value = "/cmsSubAssigns/cmsTrade/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubAssign>> getAllCmsSubAssigns(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsSubAssign> page = cmsSubAssignRepository.findAllByTrade(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubAssigns/cmsTrade/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsSubAssigns/:id -> get the "id" cmsSubAssign.
     *//*

    @RequestMapping(value = "/cmsSubAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> getCmsSubAssign(@PathVariable Long id) {
        log.debug("REST request to get CmsSubAssign : {}", id);
        return Optional.ofNullable(cmsSubAssignRepository.findOne(id))
            .map(cmsSubAssign -> new ResponseEntity<>(
                cmsSubAssign,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/**
     * DELETE  /cmsSubAssigns/:id -> delete the "id" cmsSubAssign.
     *//*

    @RequestMapping(value = "/cmsSubAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSubAssign(@PathVariable Long id) {
        log.debug("REST request to delete CmsSubAssign : {}", id);
        cmsSubAssignRepository.delete(id);
        cmsSubAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSubAssign", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsSubAssigns/:query -> search for the cmsSubAssign corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsSubAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSubAssign> searchCmsSubAssigns(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSubAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/


package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsSubAssign;
import gov.step.app.domain.CmsSubject;
import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsSubAssignRepository;
import gov.step.app.repository.search.CmsSubAssignSearchRepository;
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
 * REST controller for managing CmsSubAssign.
 */
@RestController
@RequestMapping("/api")
public class CmsSubAssignResource {

    private final Logger log = LoggerFactory.getLogger(CmsSubAssignResource.class);

    @Inject
    private CmsSubAssignRepository cmsSubAssignRepository;

    @Inject
    private CmsSubAssignSearchRepository cmsSubAssignSearchRepository;

    /**
     * POST  /cmsSubAssigns -> Create a new cmsSubAssign.
     */
    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> createCmsSubAssign(@Valid @RequestBody CmsSubAssign cmsSubAssign) throws URISyntaxException {
        log.debug("REST request to save CmsSubAssign : {}", cmsSubAssign);
        if (cmsSubAssign.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsSubAssign cannot already have an ID").body(null);
        }
        System.out.println("BURGER------------------------------");
        System.out.println(cmsSubAssign.getCmsTrade().getId().toString()+"---"+cmsSubAssign.getCmsTrade().getCode().toString());
        //CmsTrade trade = new CmsTrade();
        //trade.setId((long) 1);
        //cmsSubAssign.setCmsTrade(trade);
        CmsSubAssign result = cmsSubAssignRepository.save(cmsSubAssign);
        cmsSubAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsSubAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsSubAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsSubAssigns -> Updates an existing cmsSubAssign.
     */
    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> updateCmsSubAssign(@Valid @RequestBody CmsSubAssign cmsSubAssign) throws URISyntaxException {
        log.debug("REST request to update CmsSubAssign : {}", cmsSubAssign);
        if (cmsSubAssign.getId() == null) {
            return createCmsSubAssign(cmsSubAssign);
        }
        CmsSubAssign result = cmsSubAssignRepository.save(cmsSubAssign);
        cmsSubAssignSearchRepository.save(cmsSubAssign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsSubAssign", cmsSubAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsSubAssigns -> get all the cmsSubAssigns.
     */
    @RequestMapping(value = "/cmsSubAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubAssign>> getAllCmsSubAssigns(Pageable pageable)
        throws URISyntaxException {
        Page<CmsSubAssign> page = cmsSubAssignRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




    //////////added by Bappy when transfered
    /**
     * GET  /cmsSubAssigns -> get all the cmsSubAssigns.
     */
    @RequestMapping(value = "/cmsSubAssigns/cmsTrade/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubAssign>> getAllCmsSubAssigns(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsSubAssign> page = cmsSubAssignRepository.findAllByTrade(id,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsSubAssigns/cmsTrade/"+id);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsSubAssigns/:id -> get the "id" cmsSubAssign.
     */
    @RequestMapping(value = "/cmsSubAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsSubAssign> getCmsSubAssign(@PathVariable Long id) {
        log.debug("REST request to get CmsSubAssign : {}", id);
        return Optional.ofNullable(cmsSubAssignRepository.findOne(id))
            .map(cmsSubAssign -> new ResponseEntity<>(
                cmsSubAssign,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /cmsSubAssigns/:id -> get the "id" cmsSubAssign.
     */
    @RequestMapping(value = "/cmsSubjectByTrade/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsSubject>> getCmsSubjectByTrade(@PathVariable Long id) {
        log.debug("REST request to get CmsSubjectByTrade : {}", id);
        List<CmsSubject> subjectList=cmsSubAssignRepository.findCmsSubjectByTrade(id);
        if(subjectList!=null && subjectList.size()>0){
            return new ResponseEntity<List<CmsSubject>>(subjectList,HttpStatus.OK);
        }else{
            return new ResponseEntity<List<CmsSubject>>(new ArrayList<CmsSubject>(),HttpStatus.NO_CONTENT);
        }
    }

    /**
     * DELETE  /cmsSubAssigns/:id -> delete the "id" cmsSubAssign.
     */
    @RequestMapping(value = "/cmsSubAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsSubAssign(@PathVariable Long id) {
        log.debug("REST request to delete CmsSubAssign : {}", id);
        cmsSubAssignRepository.delete(id);
        cmsSubAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsSubAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsSubAssigns/:query -> search for the cmsSubAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsSubAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsSubAssign> searchCmsSubAssigns(@PathVariable String query) {
        return StreamSupport
            .stream(cmsSubAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}

