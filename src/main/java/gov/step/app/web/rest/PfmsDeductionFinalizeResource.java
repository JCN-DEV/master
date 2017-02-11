package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsDeductionFinalize;
import gov.step.app.repository.PfmsDeductionFinalizeRepository;
import gov.step.app.repository.search.PfmsDeductionFinalizeSearchRepository;
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
 * REST controller for managing PfmsDeductionFinalize.
 */
@RestController
@RequestMapping("/api")
public class PfmsDeductionFinalizeResource {

    private final Logger log = LoggerFactory.getLogger(PfmsDeductionFinalizeResource.class);

    @Inject
    private PfmsDeductionFinalizeRepository pfmsDeductionFinalizeRepository;

    @Inject
    private PfmsDeductionFinalizeSearchRepository pfmsDeductionFinalizeSearchRepository;

    /**
     * POST  /pfmsDeductionFinalizes -> Create a new pfmsDeductionFinalize.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeductionFinalize> createPfmsDeductionFinalize(@Valid @RequestBody PfmsDeductionFinalize pfmsDeductionFinalize) throws URISyntaxException {
        log.debug("REST request to save PfmsDeductionFinalize : {}", pfmsDeductionFinalize);
        if (pfmsDeductionFinalize.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsDeductionFinalize cannot already have an ID").body(null);
        }
        PfmsDeductionFinalize result = pfmsDeductionFinalizeRepository.save(pfmsDeductionFinalize);
        pfmsDeductionFinalizeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsDeductionFinalizes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsDeductionFinalize", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsDeductionFinalizes -> Updates an existing pfmsDeductionFinalize.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeductionFinalize> updatePfmsDeductionFinalize(@Valid @RequestBody PfmsDeductionFinalize pfmsDeductionFinalize) throws URISyntaxException {
        log.debug("REST request to update PfmsDeductionFinalize : {}", pfmsDeductionFinalize);
        if (pfmsDeductionFinalize.getId() == null) {
            return createPfmsDeductionFinalize(pfmsDeductionFinalize);
        }
        PfmsDeductionFinalize result = pfmsDeductionFinalizeRepository.save(pfmsDeductionFinalize);
        pfmsDeductionFinalizeSearchRepository.save(pfmsDeductionFinalize);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsDeductionFinalize", pfmsDeductionFinalize.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsDeductionFinalizes -> get all the pfmsDeductionFinalizes.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsDeductionFinalize>> getAllPfmsDeductionFinalizes(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsDeductionFinalize> page = pfmsDeductionFinalizeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsDeductionFinalizes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsDeductionFinalizes/:id -> get the "id" pfmsDeductionFinalize.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeductionFinalize> getPfmsDeductionFinalize(@PathVariable Long id) {
        log.debug("REST request to get PfmsDeductionFinalize : {}", id);
        return Optional.ofNullable(pfmsDeductionFinalizeRepository.findOne(id))
            .map(pfmsDeductionFinalize -> new ResponseEntity<>(
                pfmsDeductionFinalize,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsDeductionFinalizes/:id -> delete the "id" pfmsDeductionFinalize.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsDeductionFinalize(@PathVariable Long id) {
        log.debug("REST request to delete PfmsDeductionFinalize : {}", id);
        pfmsDeductionFinalizeRepository.delete(id);
        pfmsDeductionFinalizeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsDeductionFinalize", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsDeductionFinalizes/:query -> search for the pfmsDeductionFinalize corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsDeductionFinalizes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsDeductionFinalize> searchPfmsDeductionFinalizes(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsDeductionFinalizeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsDeductionFinalizeListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsDeductionFinalizeListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsDeductionFinalize> getPfmsDeductionFinalizeListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsDeductionFinalizeByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsDeductionFinalizeRepository.getPfmsDeductionFinalizeListByEmployee(employeeInfoId);

    }
}
