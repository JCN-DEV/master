package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AllowanceSetup;
import gov.step.app.repository.AllowanceSetupRepository;
import gov.step.app.repository.search.AllowanceSetupSearchRepository;
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
 * REST controller for managing AllowanceSetup.
 */
@RestController
@RequestMapping("/api")
public class AllowanceSetupResource {

    private final Logger log = LoggerFactory.getLogger(AllowanceSetupResource.class);

    @Inject
    private AllowanceSetupRepository allowanceSetupRepository;

    @Inject
    private AllowanceSetupSearchRepository allowanceSetupSearchRepository;

    /**
     * POST  /allowanceSetups -> Create a new allowanceSetup.
     */
    @RequestMapping(value = "/allowanceSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllowanceSetup> createAllowanceSetup(@Valid @RequestBody AllowanceSetup allowanceSetup) throws URISyntaxException {
        log.debug("REST request to save AllowanceSetup : {}", allowanceSetup);
        if (allowanceSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new allowanceSetup cannot already have an ID").body(null);
        }
        AllowanceSetup result = allowanceSetupRepository.save(allowanceSetup);
        allowanceSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/allowanceSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("allowanceSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /allowanceSetups -> Updates an existing allowanceSetup.
     */
    @RequestMapping(value = "/allowanceSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllowanceSetup> updateAllowanceSetup(@Valid @RequestBody AllowanceSetup allowanceSetup) throws URISyntaxException {
        log.debug("REST request to update AllowanceSetup : {}", allowanceSetup);
        if (allowanceSetup.getId() == null) {
            return createAllowanceSetup(allowanceSetup);
        }
        AllowanceSetup result = allowanceSetupRepository.save(allowanceSetup);
        allowanceSetupSearchRepository.save(allowanceSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("allowanceSetup", allowanceSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /allowanceSetups -> get all the allowanceSetups.
     */
    @RequestMapping(value = "/allowanceSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AllowanceSetup>> getAllAllowanceSetups(Pageable pageable)
        throws URISyntaxException {
        Page<AllowanceSetup> page = allowanceSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/allowanceSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /allowanceSetups/:id -> get the "id" allowanceSetup.
     */
    @RequestMapping(value = "/allowanceSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AllowanceSetup> getAllowanceSetup(@PathVariable Long id) {
        log.debug("REST request to get AllowanceSetup : {}", id);
        return Optional.ofNullable(allowanceSetupRepository.findOne(id))
            .map(allowanceSetup -> new ResponseEntity<>(
                allowanceSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /allowanceSetups/:id -> delete the "id" allowanceSetup.
     */
    @RequestMapping(value = "/allowanceSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAllowanceSetup(@PathVariable Long id) {
        log.debug("REST request to delete AllowanceSetup : {}", id);
        allowanceSetupRepository.delete(id);
        allowanceSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("allowanceSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/allowanceSetups/:query -> search for the allowanceSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/allowanceSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AllowanceSetup> searchAllowanceSetups(@PathVariable String query) {
        return StreamSupport
            .stream(allowanceSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
