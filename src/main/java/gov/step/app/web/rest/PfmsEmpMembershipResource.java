package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsEmpMembership;
import gov.step.app.repository.PfmsEmpMembershipRepository;
import gov.step.app.repository.search.PfmsEmpMembershipSearchRepository;
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
 * REST controller for managing PfmsEmpMembership.
 */
@RestController
@RequestMapping("/api")
public class PfmsEmpMembershipResource {

    private final Logger log = LoggerFactory.getLogger(PfmsEmpMembershipResource.class);

    @Inject
    private PfmsEmpMembershipRepository pfmsEmpMembershipRepository;

    @Inject
    private PfmsEmpMembershipSearchRepository pfmsEmpMembershipSearchRepository;

    /**
     * POST  /pfmsEmpMemberships -> Create a new pfmsEmpMembership.
     */
    @RequestMapping(value = "/pfmsEmpMemberships",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembership> createPfmsEmpMembership(@Valid @RequestBody PfmsEmpMembership pfmsEmpMembership) throws URISyntaxException {
        log.debug("REST request to save PfmsEmpMembership : {}", pfmsEmpMembership);
        if (pfmsEmpMembership.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsEmpMembership cannot already have an ID").body(null);
        }
        PfmsEmpMembership result = pfmsEmpMembershipRepository.save(pfmsEmpMembership);
        pfmsEmpMembershipSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsEmpMemberships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsEmpMembership", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsEmpMemberships -> Updates an existing pfmsEmpMembership.
     */
    @RequestMapping(value = "/pfmsEmpMemberships",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembership> updatePfmsEmpMembership(@Valid @RequestBody PfmsEmpMembership pfmsEmpMembership) throws URISyntaxException {
        log.debug("REST request to update PfmsEmpMembership : {}", pfmsEmpMembership);
        if (pfmsEmpMembership.getId() == null) {
            return createPfmsEmpMembership(pfmsEmpMembership);
        }
        PfmsEmpMembership result = pfmsEmpMembershipRepository.save(pfmsEmpMembership);
        pfmsEmpMembershipSearchRepository.save(pfmsEmpMembership);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsEmpMembership", pfmsEmpMembership.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsEmpMemberships -> get all the pfmsEmpMemberships.
     */
    @RequestMapping(value = "/pfmsEmpMemberships",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsEmpMembership>> getAllPfmsEmpMemberships(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsEmpMembership> page = pfmsEmpMembershipRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsEmpMemberships");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsEmpMemberships/:id -> get the "id" pfmsEmpMembership.
     */
    @RequestMapping(value = "/pfmsEmpMemberships/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembership> getPfmsEmpMembership(@PathVariable Long id) {
        log.debug("REST request to get PfmsEmpMembership : {}", id);
        return Optional.ofNullable(pfmsEmpMembershipRepository.findOne(id))
            .map(pfmsEmpMembership -> new ResponseEntity<>(
                pfmsEmpMembership,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsEmpMemberships/:id -> delete the "id" pfmsEmpMembership.
     */
    @RequestMapping(value = "/pfmsEmpMemberships/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsEmpMembership(@PathVariable Long id) {
        log.debug("REST request to delete PfmsEmpMembership : {}", id);
        pfmsEmpMembershipRepository.delete(id);
        pfmsEmpMembershipSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsEmpMembership", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsEmpMemberships/:query -> search for the pfmsEmpMembership corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsEmpMemberships/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMembership> searchPfmsEmpMemberships(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsEmpMembershipSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsEmpMembershipListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsEmpMembershipListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMembership> getPfmsEmpMembershipListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsEmpMembershipByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsEmpMembershipRepository.getPfmsEmpMembershipListByEmployee(employeeInfoId);

    }
}
