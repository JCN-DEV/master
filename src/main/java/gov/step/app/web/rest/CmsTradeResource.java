/*
package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsTradeRepository;
import gov.step.app.repository.search.CmsTradeSearchRepository;
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
 * REST controller for managing CmsTrade.
 *//*

@RestController
@RequestMapping("/api")
public class CmsTradeResource {

    private final Logger log = LoggerFactory.getLogger(CmsTradeResource.class);

    @Inject
    private CmsTradeRepository cmsTradeRepository;

    @Inject
    private CmsTradeSearchRepository cmsTradeSearchRepository;

    */
/**
     * POST  /cmsTrades -> Create a new cmsTrade.
     *//*

    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> createCmsTrade(@Valid @RequestBody CmsTrade cmsTrade) throws URISyntaxException {
        log.debug("REST request to save CmsTrade : {}", cmsTrade);
        if (cmsTrade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsTrade cannot already have an ID").body(null);
        }
        CmsTrade result = cmsTradeRepository.save(cmsTrade);
        cmsTradeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsTrades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsTrade", result.getId().toString()))
            .body(result);
    }

    */
/**
     * PUT  /cmsTrades -> Updates an existing cmsTrade.
     *//*

    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> updateCmsTrade(@Valid @RequestBody CmsTrade cmsTrade) throws URISyntaxException {
        log.debug("REST request to update CmsTrade : {}", cmsTrade);
        if (cmsTrade.getId() == null) {
            return createCmsTrade(cmsTrade);
        }
        CmsTrade result = cmsTradeRepository.save(cmsTrade);
        cmsTradeSearchRepository.save(cmsTrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsTrade", cmsTrade.getId().toString()))
            .body(result);
    }

    */
/**
     * GET  /cmsTrades -> get all the cmsTrades.
     *//*

    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllCmsTrades(Pageable pageable)
        throws URISyntaxException {
        Page<CmsTrade> page = cmsTradeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
    */
/**
     * GET  /cmsTrades -> get all the cmsTrades.
     *//*

    @RequestMapping(value = "/cmsTrades/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllCmsTrades(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsTrade> page = cmsTradeRepository.findAllByCurriculum(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    */
/**
     * GET  /cmsTrades/:id -> get the "id" cmsTrade.
     *//*

    @RequestMapping(value = "/cmsTrades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> getCmsTrade(@PathVariable Long id) {
        log.debug("REST request to get CmsTrade : {}", id);
        return Optional.ofNullable(cmsTradeRepository.findOne(id))
            .map(cmsTrade -> new ResponseEntity<>(
                cmsTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    */
/**
     * GET  /cmsTradess/code/:code -> get the "code" cmsTrade.
     *//*

    */
/*@RequestMapping(value = "/cmsTrades/code/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> getCmsTradeByCode(@PathVariable String code) {
        log.debug("REST request to get CmsTrade by code : {}", code);
        return Optional.ofNullable(cmsTradeRepository.findOneByCode(code))
            .map(cmsTrade -> new ResponseEntity<>(
                cmsTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*//*




    @RequestMapping(value = "/cmsTrades/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsTradeByCode( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<CmsTrade> cmsTrade = cmsTradeRepository.findOneByCode(value);

       */
/* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*//*


        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsTrade)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }




    */
/**
     * DELETE  /cmsTrades/:id -> delete the "id" cmsTrade.
     *//*

    @RequestMapping(value = "/cmsTrades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsTrade(@PathVariable Long id) {
        log.debug("REST request to delete CmsTrade : {}", id);
        cmsTradeRepository.delete(id);
        cmsTradeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsTrade", id.toString())).build();
    }

    */
/**
     * SEARCH  /_search/cmsTrades/:query -> search for the cmsTrade corresponding
     * to the query.
     *//*

    @RequestMapping(value = "/_search/cmsTrades/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsTrade> searchCmsTrades(@PathVariable String query) {
        return StreamSupport
            .stream(cmsTradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
*/


package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;

import gov.step.app.domain.CmsCurriculum;
import gov.step.app.domain.CmsTrade;
import gov.step.app.repository.CmsTradeRepository;
import gov.step.app.repository.search.CmsTradeSearchRepository;
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
 * REST controller for managing CmsTrade.
 */
@RestController
@RequestMapping("/api")
public class CmsTradeResource {

    private final Logger log = LoggerFactory.getLogger(CmsTradeResource.class);

    @Inject
    private CmsTradeRepository cmsTradeRepository;

    @Inject
    private CmsTradeSearchRepository cmsTradeSearchRepository;

    /**
     * POST  /cmsTrades -> Create a new cmsTrade.
     */
    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> createCmsTrade(@Valid @RequestBody CmsTrade cmsTrade) throws URISyntaxException {
        log.debug("REST request to save CmsTrade : {}", cmsTrade);
        if (cmsTrade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsTrade cannot already have an ID").body(null);
        }
        CmsTrade result = cmsTradeRepository.save(cmsTrade);
        cmsTradeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsTrades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsTrade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsTrades -> Updates an existing cmsTrade.
     */
    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> updateCmsTrade(@Valid @RequestBody CmsTrade cmsTrade) throws URISyntaxException {
        log.debug("REST request to update CmsTrade : {}", cmsTrade);
        if (cmsTrade.getId() == null) {
            return createCmsTrade(cmsTrade);
        }
        CmsTrade result = cmsTradeRepository.save(cmsTrade);
        cmsTradeSearchRepository.save(cmsTrade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsTrade", cmsTrade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsTrades -> get all the cmsTrades.
     */
    @RequestMapping(value = "/cmsTrades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllCmsTrades(Pageable pageable)
        throws URISyntaxException {
        Page<CmsTrade> page = cmsTradeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsTrades/:id -> get the "id" cmsTrade.
     */
    @RequestMapping(value = "/cmsTrades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsTrade> getCmsTrade(@PathVariable Long id) {
        log.debug("REST request to get CmsTrade : {}", id);
        return Optional.ofNullable(cmsTradeRepository.findOne(id))
            .map(cmsTrade -> new ResponseEntity<>(
                cmsTrade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /cmsTradess/code/:code -> get the "code" cmsTrade.
     */
    @RequestMapping(value = "/cmsTrades/curriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsTrade> getCmsTradeByCurriculum(@PathVariable Long id) {
        log.debug("REST request to get CmsTrade by code : {}", id);
        return cmsTradeRepository.findAllByCurriculum(id);
    }



    @RequestMapping(value = "/cmsTrades/code/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsTradeByCode( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<CmsTrade> cmsTrade = cmsTradeRepository.findOneByCode(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsTrade)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/cmsTrades/name/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getCmsTradeByName( @RequestParam String value) {

        log.debug("REST request to get cmsTrade by code : {}", value);

        Optional<CmsTrade> cmsTrade = cmsTradeRepository.findOneByName(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/

        Map map =new HashMap();

        map.put("value",value);

        if(Optional.empty().equals(cmsTrade)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }




    /**
     * DELETE  /cmsTrades/:id -> delete the "id" cmsTrade.
     */
    @RequestMapping(value = "/cmsTrades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsTrade(@PathVariable Long id) {
        log.debug("REST request to delete CmsTrade : {}", id);
        cmsTradeRepository.delete(id);
        cmsTradeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsTrade", id.toString())).build();
    }

    @RequestMapping(value = "cmsTrades/allActivecmsTrades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<CmsTrade> page = cmsTradeRepository.activecmsTrades(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/cmsTrades/allActivecmsTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /cmsTrades -> get all the cmsTrades.
     */
    @RequestMapping(value = "/cmsTrades/cmsCurriculum/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsTrade>> getAllCmsTrades(Pageable pageable, @PathVariable Long id)
        throws URISyntaxException {
        Page<CmsTrade> page = cmsTradeRepository.findAllByCurriculum(pageable, id);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsTrades");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * SEARCH  /_search/cmsTrades/:query -> search for the cmsTrade corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsTrades/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsTrade> searchCmsTrades(@PathVariable String query) {
        return StreamSupport
            .stream(cmsTradeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
