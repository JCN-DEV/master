package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DearnessAssign;
import gov.step.app.repository.DearnessAssignRepository;
import gov.step.app.repository.search.DearnessAssignSearchRepository;
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
 * REST controller for managing DearnessAssign.
 */
@RestController
@RequestMapping("/api")
public class DearnessAssignResource {

    private final Logger log = LoggerFactory.getLogger(DearnessAssignResource.class);

    @Inject
    private DearnessAssignRepository dearnessAssignRepository;

    @Inject
    private DearnessAssignSearchRepository dearnessAssignSearchRepository;

    /**
     * POST  /dearnessAssigns -> Create a new dearnessAssign.
     */
    @RequestMapping(value = "/dearnessAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DearnessAssign> createDearnessAssign(@Valid @RequestBody DearnessAssign dearnessAssign) throws URISyntaxException {
        log.debug("REST request to save DearnessAssign : {}", dearnessAssign);
        if (dearnessAssign.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dearnessAssign cannot already have an ID").body(null);
        }
        DearnessAssign result = dearnessAssignRepository.save(dearnessAssign);
        dearnessAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dearnessAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dearnessAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dearnessAssigns -> Updates an existing dearnessAssign.
     */
    @RequestMapping(value = "/dearnessAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DearnessAssign> updateDearnessAssign(@Valid @RequestBody DearnessAssign dearnessAssign) throws URISyntaxException {
        log.debug("REST request to update DearnessAssign : {}", dearnessAssign);
        if (dearnessAssign.getId() == null) {
            return createDearnessAssign(dearnessAssign);
        }
        DearnessAssign result = dearnessAssignRepository.save(dearnessAssign);
        dearnessAssignSearchRepository.save(dearnessAssign);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dearnessAssign", dearnessAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dearnessAssigns -> get all the dearnessAssigns.
     */
    @RequestMapping(value = "/dearnessAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DearnessAssign>> getAllDearnessAssigns(Pageable pageable)
        throws URISyntaxException {
        Page<DearnessAssign> page = dearnessAssignRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dearnessAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dearnessAssigns/:id -> get the "id" dearnessAssign.
     */
    @RequestMapping(value = "/dearnessAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DearnessAssign> getDearnessAssign(@PathVariable Long id) {
        log.debug("REST request to get DearnessAssign : {}", id);
        return Optional.ofNullable(dearnessAssignRepository.findOne(id))
            .map(dearnessAssign -> new ResponseEntity<>(
                dearnessAssign,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dearnessAssigns/:id -> delete the "id" dearnessAssign.
     */
    @RequestMapping(value = "/dearnessAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDearnessAssign(@PathVariable Long id) {
        log.debug("REST request to delete DearnessAssign : {}", id);
        dearnessAssignRepository.delete(id);
        dearnessAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dearnessAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dearnessAssigns/:query -> search for the dearnessAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dearnessAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DearnessAssign> searchDearnessAssigns(@PathVariable String query) {
        return StreamSupport
            .stream(dearnessAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
