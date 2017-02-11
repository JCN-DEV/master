package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlExistingBookIssue;
import gov.step.app.repository.DlExistingBookIssueRepository;
import gov.step.app.repository.search.DlExistingBookIssueSearchRepository;
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
 * REST controller for managing DlExistingBookIssue.
 */
@RestController
@RequestMapping("/api")
public class DlExistingBookIssueResource {

    private final Logger log = LoggerFactory.getLogger(DlExistingBookIssueResource.class);

    @Inject
    private DlExistingBookIssueRepository dlExistingBookIssueRepository;

    @Inject
    private DlExistingBookIssueSearchRepository dlExistingBookIssueSearchRepository;

    /**
     * POST  /dlExistingBookIssues -> Create a new dlExistingBookIssue.
     */
    @RequestMapping(value = "/dlExistingBookIssues",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistingBookIssue> createDlExistingBookIssue(@Valid @RequestBody DlExistingBookIssue dlExistingBookIssue) throws URISyntaxException {
        log.debug("REST request to save DlExistingBookIssue : {}", dlExistingBookIssue);
        if (dlExistingBookIssue.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlExistingBookIssue cannot already have an ID").body(null);
        }
        DlExistingBookIssue result = dlExistingBookIssueRepository.save(dlExistingBookIssue);
        dlExistingBookIssueSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlExistingBookIssues/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlExistingBookIssue", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlExistingBookIssues -> Updates an existing dlExistingBookIssue.
     */
    @RequestMapping(value = "/dlExistingBookIssues",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistingBookIssue> updateDlExistingBookIssue(@Valid @RequestBody DlExistingBookIssue dlExistingBookIssue) throws URISyntaxException {
        log.debug("REST request to update DlExistingBookIssue : {}", dlExistingBookIssue);
        if (dlExistingBookIssue.getId() == null) {
            return createDlExistingBookIssue(dlExistingBookIssue);
        }
        DlExistingBookIssue result = dlExistingBookIssueRepository.save(dlExistingBookIssue);
        dlExistingBookIssueSearchRepository.save(dlExistingBookIssue);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlExistingBookIssue", dlExistingBookIssue.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlExistingBookIssues -> get all the dlExistingBookIssues.
     */
    @RequestMapping(value = "/dlExistingBookIssues",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlExistingBookIssue>> getAllDlExistingBookIssues(Pageable pageable)
        throws URISyntaxException {
        Page<DlExistingBookIssue> page = dlExistingBookIssueRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlExistingBookIssues");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlExistingBookIssues/:id -> get the "id" dlExistingBookIssue.
     */
    @RequestMapping(value = "/dlExistingBookIssues/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistingBookIssue> getDlExistingBookIssue(@PathVariable Long id) {
        log.debug("REST request to get DlExistingBookIssue : {}", id);
        return Optional.ofNullable(dlExistingBookIssueRepository.findOne(id))
            .map(dlExistingBookIssue -> new ResponseEntity<>(
                dlExistingBookIssue,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlExistingBookIssues/:id -> delete the "id" dlExistingBookIssue.
     */
    @RequestMapping(value = "/dlExistingBookIssues/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlExistingBookIssue(@PathVariable Long id) {
        log.debug("REST request to delete DlExistingBookIssue : {}", id);
        dlExistingBookIssueRepository.delete(id);
        dlExistingBookIssueSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlExistingBookIssue", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlExistingBookIssues/:query -> search for the dlExistingBookIssue corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlExistingBookIssues/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlExistingBookIssue> searchDlExistingBookIssues(@PathVariable String query) {
        return StreamSupport
            .stream(dlExistingBookIssueSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
