package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.domain.DlContUpld;
import gov.step.app.repository.DlContTypeSetRepository;
import gov.step.app.repository.search.DlContTypeSetSearchRepository;
import gov.step.app.web.rest.util.AttachmentUtil;
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
 * REST controller for managing DlContTypeSet.
 */
@RestController
@RequestMapping("/api")
public class DlContTypeSetResource {

    private final Logger log = LoggerFactory.getLogger(DlContTypeSetResource.class);

    @Inject
    private DlContTypeSetRepository dlContTypeSetRepository;

    @Inject
    private DlContTypeSetSearchRepository dlContTypeSetSearchRepository;

    /**
     * POST  /dlContTypeSets -> Create a new dlContTypeSet.
     */
    @RequestMapping(value = "/dlContTypeSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContTypeSet> createDlContTypeSet(@RequestBody DlContTypeSet dlContTypeSet) throws URISyntaxException {
        log.debug("REST request to save DlContTypeSet : {}", dlContTypeSet);
        if (dlContTypeSet.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContTypeSet cannot already have an ID").body(null);
        }
        DlContTypeSet result = dlContTypeSetRepository.save(dlContTypeSet);
        dlContTypeSetSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContTypeSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContTypeSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlContTypeSets -> Updates an existing dlContTypeSet.
     */
    @RequestMapping(value = "/dlContTypeSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContTypeSet> updateDlContTypeSet(@RequestBody DlContTypeSet dlContTypeSet) throws URISyntaxException {
        log.debug("REST request to update DlContTypeSet : {}", dlContTypeSet);
        if (dlContTypeSet.getId() == null) {
            return createDlContTypeSet(dlContTypeSet);
        }
        DlContTypeSet result = dlContTypeSetRepository.save(dlContTypeSet);
        dlContTypeSetSearchRepository.save(dlContTypeSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContTypeSet", dlContTypeSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContTypeSets -> get all the dlContTypeSets.
     */
    @RequestMapping(value = "/dlContTypeSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContTypeSet>> getAllDlContTypeSets(Pageable pageable)
        throws URISyntaxException {
        Page<DlContTypeSet> page = dlContTypeSetRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContTypeSets");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlContTypeSets/:id -> get the "id" dlContTypeSet.
     */
    @RequestMapping(value = "/dlContTypeSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContTypeSet> getDlContTypeSet(@PathVariable Long id) {
        log.debug("REST request to get DlContTypeSet : {}", id);
        return Optional.ofNullable(dlContTypeSetRepository.findOne(id))
            .map(dlContTypeSet -> new ResponseEntity<>(
                dlContTypeSet,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @RequestMapping(value = "dlContTypeSets/allActiveTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContTypeSet>> getAllDlContUpldsbyUser(Pageable pageable)
        throws Exception {
        Page<DlContTypeSet> page = dlContTypeSetRepository.activecontenttypes(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/apidlContTypeSets/allActiveTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/dlContTypeSets/dlContTypeSetNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContTypeSetUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContTypeSet> dlContTypeSet = dlContTypeSetRepository.findOneByName(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContTypeSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }
    @RequestMapping(value = "/dlContTypeSets/dlContTypeSetCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> getDlContTypeCodeUnique( @RequestParam String value) {
        log.debug("REST request to get cmsTrade by code : {}", value);
        Optional<DlContTypeSet> dlContTypeSet = dlContTypeSetRepository.findOneByCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(dlContTypeSet)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }




    /**
     * DELETE  /dlContTypeSets/:id -> delete the "id" dlContTypeSet.
     */
    @RequestMapping(value = "/dlContTypeSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContTypeSet(@PathVariable Long id) {
        log.debug("REST request to delete DlContTypeSet : {}", id);
        dlContTypeSetRepository.delete(id);
        dlContTypeSetSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContTypeSet", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContTypeSets/:query -> search for the dlContTypeSet corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContTypeSets/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContTypeSet> searchDlContTypeSets(@PathVariable String query) {
        return StreamSupport
            .stream(dlContTypeSetSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
