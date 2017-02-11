package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PrincipalRequirement;
import gov.step.app.repository.PrincipalRequirementRepository;
import gov.step.app.repository.search.PrincipalRequirementSearchRepository;
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
 * REST controller for managing PrincipalRequirement.
 */
@RestController
@RequestMapping("/api")
public class PrincipalRequirementResource {

    private final Logger log = LoggerFactory.getLogger(PrincipalRequirementResource.class);

    @Inject
    private PrincipalRequirementRepository principalRequirementRepository;

    @Inject
    private PrincipalRequirementSearchRepository principalRequirementSearchRepository;

    /**
     * POST /principalRequirements -> Create a new principalRequirement.
     */
    @RequestMapping(value = "/principalRequirements", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrincipalRequirement> createPrincipalRequirement(
        @RequestBody PrincipalRequirement principalRequirement) throws URISyntaxException {
        log.debug("REST request to save PrincipalRequirement : {}", principalRequirement);
        if (principalRequirement.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new principalRequirement cannot already have an ID")
                .body(null);
        }
        PrincipalRequirement result = principalRequirementRepository.save(principalRequirement);
        principalRequirementSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/principalRequirements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("principalRequirement", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT /principalRequirements -> Updates an existing principalRequirement.
     */
    @RequestMapping(value = "/principalRequirements", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrincipalRequirement> updatePrincipalRequirement(
        @RequestBody PrincipalRequirement principalRequirement) throws URISyntaxException {
        log.debug("REST request to update PrincipalRequirement : {}", principalRequirement);
        if (principalRequirement.getId() == null) {
            return createPrincipalRequirement(principalRequirement);
        }
        PrincipalRequirement result = principalRequirementRepository.save(principalRequirement);
        principalRequirementSearchRepository.save(principalRequirement);
        return ResponseEntity.ok().headers(
            HeaderUtil.createEntityUpdateAlert("principalRequirement", principalRequirement.getId().toString()))
            .body(result);
    }

    /**
     * GET /principalRequirements -> get all the principalRequirements.
     */
    @RequestMapping(value = "/principalRequirements", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrincipalRequirement>> getAllPrincipalRequirements(Pageable pageable)
        throws URISyntaxException {
        Page<PrincipalRequirement> page = principalRequirementRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/principalRequirements");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /principalRequirements/:id -> get the "id" principalRequirement.
     */
    @RequestMapping(value = "/principalRequirements/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrincipalRequirement> getPrincipalRequirement(@PathVariable Long id) {
        log.debug("REST request to get PrincipalRequirement : {}", id);
        return Optional.ofNullable(principalRequirementRepository.findOne(id))
            .map(principalRequirement -> new ResponseEntity<>(principalRequirement, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /principalRequirements/:id -> delete the "id"
     * principalRequirement.
     */
    @RequestMapping(value = "/principalRequirements/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrincipalRequirement(@PathVariable Long id) {
        log.debug("REST request to delete PrincipalRequirement : {}", id);
        principalRequirementRepository.delete(id);
        principalRequirementSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("principalRequirement", id.toString()))
            .build();
    }

    /**
     * SEARCH /_search/principalRequirements/:query -> search for the
     * principalRequirement corresponding to the query.
     */
    @RequestMapping(value = "/_search/principalRequirements/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrincipalRequirement> searchPrincipalRequirements(@PathVariable String query) {
        return StreamSupport
            .stream(principalRequirementSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
