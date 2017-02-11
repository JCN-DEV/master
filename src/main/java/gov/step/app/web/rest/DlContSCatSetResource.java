package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContCatSet;
import gov.step.app.domain.DlContSCatSet;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.repository.DlContSCatSetRepository;
import gov.step.app.repository.search.DlContSCatSetSearchRepository;
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
 * REST controller for managing DlContSCatSet.
 */
@RestController
@RequestMapping("/api")
public class DlContSCatSetResource {

    private final Logger log = LoggerFactory.getLogger(DlContSCatSetResource.class);

    @Inject
    private DlContSCatSetRepository dlContSCatSetRepository;

    @Inject
    private DlContSCatSetSearchRepository dlContSCatSetSearchRepository;

    /**
     * POST  /dlContSCatSets -> Create a new dlContSCatSet.
     */
    @RequestMapping(value = "/dlContSCatSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContSCatSet> createDlContSCatSet(@RequestBody DlContSCatSet dlContSCatSet) throws URISyntaxException {
        log.debug("REST request to save DlContSCatSet : {}", dlContSCatSet);
        if (dlContSCatSet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContSCatSet cannot already have an ID").body(null);
        }
        DlContSCatSet result = dlContSCatSetRepository.save(dlContSCatSet);
        dlContSCatSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContSCatSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContSCatSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlContSCatSets -> Updates an existing dlContSCatSet.
     */
    @RequestMapping(value = "/dlContSCatSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContSCatSet> updateDlContSCatSet(@RequestBody DlContSCatSet dlContSCatSet) throws URISyntaxException {
        log.debug("REST request to update DlContSCatSet : {}", dlContSCatSet);
        if (dlContSCatSet.getId() == null) {
            return createDlContSCatSet(dlContSCatSet);
        }
        DlContSCatSet result = dlContSCatSetRepository.save(dlContSCatSet);
        dlContSCatSetSearchRepository.save(dlContSCatSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContSCatSet", dlContSCatSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContSCatSets -> get all the dlContSCatSets.
     */
    @RequestMapping(value = "/dlContSCatSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContSCatSet>> getAllDlContSCatSets(Pageable pageable)
        throws URISyntaxException {
        Page<DlContSCatSet> page = dlContSCatSetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContSCatSets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
////anik
    @RequestMapping(value = "/dlContSCatSets/allActiveSubCategory",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContSCatSet>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<DlContSCatSet> page = dlContSCatSetRepository.activeSubCategory(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContSCatSets/allActiveSubCategory");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }




    /**
     * GET  /dlContSCatSets/:id -> get the "id" dlContSCatSet.
     */
    @RequestMapping(value = "/dlContSCatSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContSCatSet> getDlContSCatSet(@PathVariable Long id) {
        log.debug("REST request to get DlContSCatSet : {}", id);
        return Optional.ofNullable(dlContSCatSetRepository.findOne(id))
            .map(dlContSCatSet -> new ResponseEntity<>(
                dlContSCatSet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @RequestMapping(value = "/dlContSCatSets/dlContSCatSetCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContSCatSetByCode( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContCatSet> dlContSCatSet = dlContSCatSetRepository.findOneByCode(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContSCatSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/dlContSCatSets/dlContSCatSetNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContSCatSetByName( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by name : {}", value);
        Optional<DlContCatSet> dlContSCatSet = dlContSCatSetRepository.findOneByName(value);
        Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContSCatSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    /**
     * DELETE  /dlContSCatSets/:id -> delete the "id" dlContSCatSet.
     */
    @RequestMapping(value = "/dlContSCatSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContSCatSet(@PathVariable Long id) {
        log.debug("REST request to delete DlContSCatSet : {}", id);
        dlContSCatSetRepository.delete(id);
        dlContSCatSetSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContSCatSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContSCatSets/:query -> search for the dlContSCatSet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContSCatSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContSCatSet> searchDlContSCatSets(@PathVariable String query) {
        return StreamSupport
            .stream(dlContSCatSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
