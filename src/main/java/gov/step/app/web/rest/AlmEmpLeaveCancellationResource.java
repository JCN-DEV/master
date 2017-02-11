package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveCancellation;
import gov.step.app.repository.AlmEmpLeaveCancellationRepository;
import gov.step.app.repository.search.AlmEmpLeaveCancellationSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmEmpLeaveCancellation.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveCancellationResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveCancellationResource.class);

    @Inject
    private AlmEmpLeaveCancellationRepository almEmpLeaveCancellationRepository;

    @Inject
    private AlmEmpLeaveCancellationSearchRepository almEmpLeaveCancellationSearchRepository;

    /**
     * POST  /almEmpLeaveCancellations -> Create a new almEmpLeaveCancellation.
     */
    @RequestMapping(value = "/almEmpLeaveCancellations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveCancellation> createAlmEmpLeaveCancellation(@Valid @RequestBody AlmEmpLeaveCancellation almEmpLeaveCancellation) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveCancellation : {}", almEmpLeaveCancellation);
        if (almEmpLeaveCancellation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveCancellation cannot already have an ID").body(null);
        }
        AlmEmpLeaveCancellation result = almEmpLeaveCancellationRepository.save(almEmpLeaveCancellation);
        almEmpLeaveCancellationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveCancellations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveCancellation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveCancellations -> Updates an existing almEmpLeaveCancellation.
     */
    @RequestMapping(value = "/almEmpLeaveCancellations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveCancellation> updateAlmEmpLeaveCancellation(@Valid @RequestBody AlmEmpLeaveCancellation almEmpLeaveCancellation) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveCancellation : {}", almEmpLeaveCancellation);
        if (almEmpLeaveCancellation.getId() == null) {
            return createAlmEmpLeaveCancellation(almEmpLeaveCancellation);
        }
        AlmEmpLeaveCancellation result = almEmpLeaveCancellationRepository.save(almEmpLeaveCancellation);
        almEmpLeaveCancellationSearchRepository.save(almEmpLeaveCancellation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveCancellation", almEmpLeaveCancellation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveCancellations -> get all the almEmpLeaveCancellations.
     */
    @RequestMapping(value = "/almEmpLeaveCancellations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveCancellation>> getAllAlmEmpLeaveCancellations(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveCancellation> page = almEmpLeaveCancellationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveCancellations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveCancellations/:id -> get the "id" almEmpLeaveCancellation.
     */
    @RequestMapping(value = "/almEmpLeaveCancellations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveCancellation> getAlmEmpLeaveCancellation(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveCancellation : {}", id);
        return Optional.ofNullable(almEmpLeaveCancellationRepository.findOne(id))
            .map(almEmpLeaveCancellation -> new ResponseEntity<>(
                almEmpLeaveCancellation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveCancellations/:id -> delete the "id" almEmpLeaveCancellation.
     */
    @RequestMapping(value = "/almEmpLeaveCancellations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveCancellation(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveCancellation : {}", id);
        almEmpLeaveCancellationRepository.delete(id);
        almEmpLeaveCancellationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveCancellation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveCancellations/:query -> search for the almEmpLeaveCancellation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveCancellations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveCancellation> searchAlmEmpLeaveCancellations(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveCancellationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
