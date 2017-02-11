package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmployee;
import gov.step.app.domain.InstLevel;
import gov.step.app.domain.User;
import gov.step.app.repository.InstLevelRepository;
import gov.step.app.repository.search.InstLevelSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstLevel.
 */
@RestController
@RequestMapping("/api")
public class InstLevelResource {

    private final Logger log = LoggerFactory.getLogger(InstLevelResource.class);

    @Inject
    private InstLevelRepository instLevelRepository;

    @Inject
    private InstLevelSearchRepository instLevelSearchRepository;

    /**
     * POST  /instLevels -> Create a new instLevel.
     */
    @RequestMapping(value = "/instLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevel> createInstLevel(@RequestBody InstLevel instLevel) throws URISyntaxException {
        log.debug("REST request to save InstLevel : {}", instLevel);
        if (instLevel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLevel cannot already have an ID").body(null);
        }
        InstLevel result = instLevelRepository.save(instLevel);
        instLevelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLevels -> Updates an existing instLevel.
     */
    @RequestMapping(value = "/instLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevel> updateInstLevel(@RequestBody InstLevel instLevel) throws URISyntaxException {
        log.debug("REST request to update InstLevel : {}", instLevel);
        if (instLevel.getId() == null) {
            return createInstLevel(instLevel);
        }
        InstLevel result = instLevelRepository.save(instLevel);
        instLevelSearchRepository.save(instLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLevel", instLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLevels -> get all the instLevels.
     */
    @RequestMapping(value = "/instLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLevel>> getAllInstLevels(Pageable pageable)
        throws URISyntaxException {
        Page<InstLevel> page = instLevelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLevels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /instLevels -> get all the instLevels.
     */
    @RequestMapping(value = "/instLevels/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLevel>> findAllInstLevlsByType(Pageable pageable)
        throws URISyntaxException {
        Page<InstLevel> page = instLevelRepository.findAllInstLevlsByType(Boolean.TRUE, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLevels/active");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLevels/:id -> get the "id" instLevel.
     */
    @RequestMapping(value = "/instLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevel> getInstLevel(@PathVariable Long id) {
        log.debug("REST request to get InstLevel : {}", id);
        return Optional.ofNullable(instLevelRepository.findOne(id))
            .map(instLevel -> new ResponseEntity<>(
                instLevel,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

/**
     * GET  /instLevels/:name -> get the "name" instLevel.
     */
    @RequestMapping(value = "/instLevels/byName/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevel> getInstLevel(@PathVariable String name) {
        log.debug("REST request to get InstLevel : {}", name);
        return Optional.ofNullable(instLevelRepository.findInstLevelByName(name))
            .map(instLevel -> new ResponseEntity<>(
                instLevel,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "/instLevels/instCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getInstCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<InstLevel> instLevel = instLevelRepository.findOneByCode(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(instLevel)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/instLevels/instNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getInstNameUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<InstLevel> instLevel = instLevelRepository.findOneByName(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(instLevel)){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * DELETE  /instLevels/:id -> delete the "id" instLevel.
     */
    @RequestMapping(value = "/instLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLevel(@PathVariable Long id) {
        log.debug("REST request to delete InstLevel : {}", id);
        instLevelRepository.delete(id);
        instLevelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLevel", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLevels/:query -> search for the instLevel corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLevels/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLevel> searchInstLevels(@PathVariable String query) {
        return StreamSupport
            .stream(instLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
