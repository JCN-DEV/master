package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveInitialize;
import gov.step.app.repository.AlmEmpLeaveInitializeRepository;
import gov.step.app.repository.search.AlmEmpLeaveInitializeSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmEmpLeaveInitialize.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveInitializeResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveInitializeResource.class);

    @Inject
    private AlmEmpLeaveInitializeRepository almEmpLeaveInitializeRepository;

    @Inject
    private AlmEmpLeaveInitializeSearchRepository almEmpLeaveInitializeSearchRepository;

    /**
     * POST  /almEmpLeaveInitializes -> Create a new almEmpLeaveInitialize.
     */
    @RequestMapping(value = "/almEmpLeaveInitializes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveInitialize> createAlmEmpLeaveInitialize(@RequestBody AlmEmpLeaveInitialize almEmpLeaveInitialize) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveInitialize : {}", almEmpLeaveInitialize);
        if (almEmpLeaveInitialize.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveInitialize cannot already have an ID").body(null);
        }
        AlmEmpLeaveInitialize result = almEmpLeaveInitializeRepository.save(almEmpLeaveInitialize);
        almEmpLeaveInitializeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveInitializes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveInitialize", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveInitializes -> Updates an existing almEmpLeaveInitialize.
     */
    @RequestMapping(value = "/almEmpLeaveInitializes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveInitialize> updateAlmEmpLeaveInitialize(@RequestBody AlmEmpLeaveInitialize almEmpLeaveInitialize) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveInitialize : {}", almEmpLeaveInitialize);
        if (almEmpLeaveInitialize.getId() == null) {
            return createAlmEmpLeaveInitialize(almEmpLeaveInitialize);
        }
        AlmEmpLeaveInitialize result = almEmpLeaveInitializeRepository.save(almEmpLeaveInitialize);
        almEmpLeaveInitializeSearchRepository.save(almEmpLeaveInitialize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveInitialize", almEmpLeaveInitialize.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveInitializes -> get all the almEmpLeaveInitializes.
     */
    @RequestMapping(value = "/almEmpLeaveInitializes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveInitialize>> getAllAlmEmpLeaveInitializes(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveInitialize> page = almEmpLeaveInitializeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveInitializes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveInitializes/:id -> get the "id" almEmpLeaveInitialize.
     */
    @RequestMapping(value = "/almEmpLeaveInitializes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveInitialize> getAlmEmpLeaveInitialize(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveInitialize : {}", id);
        return Optional.ofNullable(almEmpLeaveInitializeRepository.findOne(id))
            .map(almEmpLeaveInitialize -> new ResponseEntity<>(
                almEmpLeaveInitialize,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveInitializes/:id -> delete the "id" almEmpLeaveInitialize.
     */
    @RequestMapping(value = "/almEmpLeaveInitializes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveInitialize(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveInitialize : {}", id);
        almEmpLeaveInitializeRepository.delete(id);
        almEmpLeaveInitializeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveInitialize", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveInitializes/:query -> search for the almEmpLeaveInitialize corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveInitializes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveInitialize> searchAlmEmpLeaveInitializes(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveInitializeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
