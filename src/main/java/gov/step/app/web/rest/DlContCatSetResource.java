package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContCatSet;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.domain.DlContUpld;
import gov.step.app.repository.DlContCatSetRepository;
import gov.step.app.repository.search.DlContCatSetSearchRepository;
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
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlContCatSet.
 */
@RestController
@RequestMapping("/api")
public class DlContCatSetResource {

    private final Logger log = LoggerFactory.getLogger(DlContCatSetResource.class);

    @Inject
    private DlContCatSetRepository dlContCatSetRepository;

    @Inject
    private DlContCatSetSearchRepository dlContCatSetSearchRepository;

    /**
     * POST  /dlContCatSets -> Create a new dlContCatSet.
     */
    @RequestMapping(value = "/dlContCatSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContCatSet> createDlContCatSet(@RequestBody DlContCatSet dlContCatSet) throws URISyntaxException {
        log.debug("REST request to save DlContCatSet : {}", dlContCatSet);
        if (dlContCatSet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContCatSet cannot already have an ID").body(null);
        }
        DlContCatSet result = dlContCatSetRepository.save(dlContCatSet);
        dlContCatSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContCatSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContCatSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlContCatSets -> Updates an existing dlContCatSet.
     */
    @RequestMapping(value = "/dlContCatSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContCatSet> updateDlContCatSet(@RequestBody DlContCatSet dlContCatSet) throws URISyntaxException {
        log.debug("REST request to update DlContCatSet : {}", dlContCatSet);
        if (dlContCatSet.getId() == null) {
            return createDlContCatSet(dlContCatSet);
        }
        DlContCatSet result = dlContCatSetRepository.save(dlContCatSet);
        dlContCatSetSearchRepository.save(dlContCatSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContCatSet", dlContCatSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContCatSets -> get all the dlContCatSets.
     */
    @RequestMapping(value = "/dlContCatSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContCatSet>> getAllDlContCatSets(Pageable pageable)
        throws URISyntaxException {
        Page<DlContCatSet> page = dlContCatSetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContCatSets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlContCatSets/:id -> get the "id" dlContCatSet.
     */
    @RequestMapping(value = "/dlContCatSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContCatSet> getDlContCatSet(@PathVariable Long id) {
        log.debug("REST request to get DlContCatSet : {}", id);
        return Optional.ofNullable(dlContCatSetRepository.findOne(id))
            .map(dlContCatSet -> new ResponseEntity<>(
                dlContCatSet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/dlContCatSets/dlContCatSetUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlFileTypeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContCatSet> dlContCatSet = dlContCatSetRepository.findOneByfileType(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContCatSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }@RequestMapping(value = "/dlContCatSets/dlContCatSetCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlFileTypeCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContCatSet> dlContCatSet = dlContCatSetRepository.findOneByCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContCatSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }
///category
    @RequestMapping(value = "dlContCatSets/allActiveTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContCatSet>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<DlContCatSet> page = dlContCatSetRepository.activecontenttypes(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "api/dlContCatSets/allActiveTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * DELETE  /dlContCatSets/:id -> delete the "id" dlContCatSet.
     */
    @RequestMapping(value = "/dlContCatSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContCatSet(@PathVariable Long id) {
        log.debug("REST request to delete DlContCatSet : {}", id);
        dlContCatSetRepository.delete(id);
        dlContCatSetSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContCatSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContCatSets/:query -> search for the dlContCatSet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContCatSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContCatSet> searchDlContCatSets(@PathVariable String query) {
        return StreamSupport
            .stream(dlContCatSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
