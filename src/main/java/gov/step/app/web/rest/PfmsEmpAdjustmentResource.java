package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsEmpAdjustment;
import gov.step.app.repository.PfmsEmpAdjustmentRepository;
import gov.step.app.repository.search.PfmsEmpAdjustmentSearchRepository;
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
 * REST controller for managing PfmsEmpAdjustment.
 */
@RestController
@RequestMapping("/api")
public class PfmsEmpAdjustmentResource {

    private final Logger log = LoggerFactory.getLogger(PfmsEmpAdjustmentResource.class);

    @Inject
    private PfmsEmpAdjustmentRepository pfmsEmpAdjustmentRepository;

    @Inject
    private PfmsEmpAdjustmentSearchRepository pfmsEmpAdjustmentSearchRepository;

    /**
     * POST  /pfmsEmpAdjustments -> Create a new pfmsEmpAdjustment.
     */
    @RequestMapping(value = "/pfmsEmpAdjustments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpAdjustment> createPfmsEmpAdjustment(@Valid @RequestBody PfmsEmpAdjustment pfmsEmpAdjustment) throws URISyntaxException {
        log.debug("REST request to save PfmsEmpAdjustment : {}", pfmsEmpAdjustment);
        if (pfmsEmpAdjustment.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsEmpAdjustment cannot already have an ID").body(null);
        }
        PfmsEmpAdjustment result = pfmsEmpAdjustmentRepository.save(pfmsEmpAdjustment);
        pfmsEmpAdjustmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsEmpAdjustments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsEmpAdjustment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsEmpAdjustments -> Updates an existing pfmsEmpAdjustment.
     */
    @RequestMapping(value = "/pfmsEmpAdjustments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpAdjustment> updatePfmsEmpAdjustment(@Valid @RequestBody PfmsEmpAdjustment pfmsEmpAdjustment) throws URISyntaxException {
        log.debug("REST request to update PfmsEmpAdjustment : {}", pfmsEmpAdjustment);
        if (pfmsEmpAdjustment.getId() == null) {
            return createPfmsEmpAdjustment(pfmsEmpAdjustment);
        }
        PfmsEmpAdjustment result = pfmsEmpAdjustmentRepository.save(pfmsEmpAdjustment);
        pfmsEmpAdjustmentSearchRepository.save(pfmsEmpAdjustment);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsEmpAdjustment", pfmsEmpAdjustment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsEmpAdjustments -> get all the pfmsEmpAdjustments.
     */
    @RequestMapping(value = "/pfmsEmpAdjustments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsEmpAdjustment>> getAllPfmsEmpAdjustments(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsEmpAdjustment> page = pfmsEmpAdjustmentRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsEmpAdjustments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsEmpAdjustments/:id -> get the "id" pfmsEmpAdjustment.
     */
    @RequestMapping(value = "/pfmsEmpAdjustments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpAdjustment> getPfmsEmpAdjustment(@PathVariable Long id) {
        log.debug("REST request to get PfmsEmpAdjustment : {}", id);
        return Optional.ofNullable(pfmsEmpAdjustmentRepository.findOne(id))
            .map(pfmsEmpAdjustment -> new ResponseEntity<>(
                pfmsEmpAdjustment,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsEmpAdjustments/:id -> delete the "id" pfmsEmpAdjustment.
     */
    @RequestMapping(value = "/pfmsEmpAdjustments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsEmpAdjustment(@PathVariable Long id) {
        log.debug("REST request to delete PfmsEmpAdjustment : {}", id);
        pfmsEmpAdjustmentRepository.delete(id);
        pfmsEmpAdjustmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsEmpAdjustment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsEmpAdjustments/:query -> search for the pfmsEmpAdjustment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsEmpAdjustments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpAdjustment> searchPfmsEmpAdjustments(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsEmpAdjustmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsEmpAdjustmentListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsEmpAdjustmentListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpAdjustment> getPfmsEmpAdjustmentListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsEmpAdjustmentByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsEmpAdjustmentRepository.getPfmsEmpAdjustmentListByEmployee(employeeInfoId);

    }
}
