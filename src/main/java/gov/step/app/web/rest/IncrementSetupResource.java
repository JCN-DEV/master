package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.IncrementSetup;
import gov.step.app.repository.IncrementSetupRepository;
import gov.step.app.repository.search.IncrementSetupSearchRepository;
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
 * REST controller for managing IncrementSetup.
 */
@RestController
@RequestMapping("/api")
public class IncrementSetupResource {

    private final Logger log = LoggerFactory.getLogger(IncrementSetupResource.class);

    @Inject
    private IncrementSetupRepository incrementSetupRepository;

    @Inject
    private IncrementSetupSearchRepository incrementSetupSearchRepository;

    /**
     * POST  /incrementSetups -> Create a new incrementSetup.
     */
    @RequestMapping(value = "/incrementSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IncrementSetup> createIncrementSetup(@Valid @RequestBody IncrementSetup incrementSetup) throws URISyntaxException {
        log.debug("REST request to save IncrementSetup : {}", incrementSetup);
        if (incrementSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new incrementSetup cannot already have an ID").body(null);
        }
        IncrementSetup result = incrementSetupRepository.save(incrementSetup);
        incrementSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/incrementSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("incrementSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /incrementSetups -> Updates an existing incrementSetup.
     */
    @RequestMapping(value = "/incrementSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IncrementSetup> updateIncrementSetup(@Valid @RequestBody IncrementSetup incrementSetup) throws URISyntaxException {
        log.debug("REST request to update IncrementSetup : {}", incrementSetup);
        if (incrementSetup.getId() == null) {
            return createIncrementSetup(incrementSetup);
        }
        IncrementSetup result = incrementSetupRepository.save(incrementSetup);
        incrementSetupSearchRepository.save(incrementSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("incrementSetup", incrementSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /incrementSetups -> get all the incrementSetups.
     */
    @RequestMapping(value = "/incrementSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<IncrementSetup>> getAllIncrementSetups(Pageable pageable)
        throws URISyntaxException {
        Page<IncrementSetup> page = incrementSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/incrementSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /incrementSetups/:id -> get the "id" incrementSetup.
     */
    @RequestMapping(value = "/incrementSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<IncrementSetup> getIncrementSetup(@PathVariable Long id) {
        log.debug("REST request to get IncrementSetup : {}", id);
        return Optional.ofNullable(incrementSetupRepository.findOne(id))
            .map(incrementSetup -> new ResponseEntity<>(
                incrementSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /incrementSetups/:id -> delete the "id" incrementSetup.
     */
    @RequestMapping(value = "/incrementSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteIncrementSetup(@PathVariable Long id) {
        log.debug("REST request to delete IncrementSetup : {}", id);
        incrementSetupRepository.delete(id);
        incrementSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("incrementSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/incrementSetups/:query -> search for the incrementSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/incrementSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<IncrementSetup> searchIncrementSetups(@PathVariable String query) {
        return StreamSupport
            .stream(incrementSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
