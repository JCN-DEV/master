package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AclEntry;
import gov.step.app.repository.AclEntryRepository;
import java.util.stream.StreamSupport;
import gov.step.app.repository.search.AclEntrySearchRepository;
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
 * REST controller for managing AclEntry.
 */
@RestController
@RequestMapping("/api")
public class AclEntryResource {

    private final Logger log = LoggerFactory.getLogger(AclEntryResource.class);

    @Inject
    private AclEntryRepository aclEntryRepository;

    @Inject
    private AclEntrySearchRepository aclEntrySearchRepository;

    /**
     * POST  /aclEntrys -> Create a new aclEntry.
     */
    @RequestMapping(value = "/aclEntrys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclEntry> createAclEntry(@Valid @RequestBody AclEntry aclEntry) throws URISyntaxException {
        log.debug("REST request to save AclEntry : {}", aclEntry);
        if (aclEntry.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aclEntry cannot already have an ID").body(null);
        }
        AclEntry result = aclEntryRepository.save(aclEntry);
        aclEntrySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aclEntrys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aclEntry", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aclEntrys -> Updates an existing aclEntry.
     */
    @RequestMapping(value = "/aclEntrys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclEntry> updateAclEntry(@Valid @RequestBody AclEntry aclEntry) throws URISyntaxException {
        log.debug("REST request to update AclEntry : {}", aclEntry);
        if (aclEntry.getId() == null) {
            return createAclEntry(aclEntry);
        }
        AclEntry result = aclEntryRepository.save(aclEntry);
        aclEntrySearchRepository.save(aclEntry);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aclEntry", aclEntry.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aclEntrys -> get all the aclEntrys.
     */
    @RequestMapping(value = "/aclEntrys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AclEntry>> getAllAclEntrys(Pageable pageable)
        throws URISyntaxException {
        Page<AclEntry> page = aclEntryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aclEntrys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aclEntrys/:id -> get the "id" aclEntry.
     */
    @RequestMapping(value = "/aclEntrys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AclEntry> getAclEntry(@PathVariable Long id) {
        log.debug("REST request to get AclEntry : {}", id);
        return Optional.ofNullable(aclEntryRepository.findOne(id))
            .map(aclEntry -> new ResponseEntity<>(
                aclEntry,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aclEntrys/:id -> delete the "id" aclEntry.
     */
    @RequestMapping(value = "/aclEntrys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAclEntry(@PathVariable Long id) {
        log.debug("REST request to delete AclEntry : {}", id);
        aclEntryRepository.delete(id);
        aclEntrySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aclEntry", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aclEntrys/:query -> search for the aclEntry corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aclEntrys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AclEntry> searchAclEntrys(@PathVariable String query) {
        return StreamSupport
            .stream(aclEntrySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    

}
