package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Designation;
import gov.step.app.repository.DesignationRepository;
import gov.step.app.repository.search.DesignationSearchRepository;
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
 * REST controller for managing Designation.
 */
@RestController
@RequestMapping("/api")
public class DesignationResource {

    private final Logger log = LoggerFactory.getLogger(DesignationResource.class);

    @Inject
    private DesignationRepository designationRepository;

    @Inject
    private DesignationSearchRepository designationSearchRepository;

    /**
     * POST  /designations -> Create a new designation.
     */
    @RequestMapping(value = "/designations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Designation> createDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to save Designation : {}", designation);
        if (designation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new designation cannot already have an ID").body(null);
        }
        Designation result = designationRepository.save(designation);
        designationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/designations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("designation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /designations -> Updates an existing designation.
     */
    @RequestMapping(value = "/designations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Designation> updateDesignation(@Valid @RequestBody Designation designation) throws URISyntaxException {
        log.debug("REST request to update Designation : {}", designation);
        if (designation.getId() == null) {
            return createDesignation(designation);
        }
        Designation result = designationRepository.save(designation);
        designationSearchRepository.save(designation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("designation", designation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /designations -> get all the designations.
     */
    @RequestMapping(value = "/designations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Designation>> getAllDesignations(Pageable pageable)
        throws URISyntaxException {
        Page<Designation> page = designationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/designations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /designations/:id -> get the "id" designation.
     */
    @RequestMapping(value = "/designations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Designation> getDesignation(@PathVariable Long id) {
        log.debug("REST request to get Designation : {}", id);
        return Optional.ofNullable(designationRepository.findOne(id))
            .map(designation -> new ResponseEntity<>(
                designation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /designations/:id -> delete the "id" designation.
     */
    @RequestMapping(value = "/designations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDesignation(@PathVariable Long id) {
        log.debug("REST request to delete Designation : {}", id);
        designationRepository.delete(id);
        designationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("designation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/designations/:query -> search for the designation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/designations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Designation> searchDesignations(@PathVariable String query) {
        return StreamSupport
            .stream(designationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
