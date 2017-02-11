package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsEmpMembershipForm;
import gov.step.app.repository.PfmsEmpMembershipFormRepository;
import gov.step.app.repository.search.PfmsEmpMembershipFormSearchRepository;
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
 * REST controller for managing PfmsEmpMembershipForm.
 */
@RestController
@RequestMapping("/api")
public class PfmsEmpMembershipFormResource {

    private final Logger log = LoggerFactory.getLogger(PfmsEmpMembershipFormResource.class);

    @Inject
    private PfmsEmpMembershipFormRepository pfmsEmpMembershipFormRepository;

    @Inject
    private PfmsEmpMembershipFormSearchRepository pfmsEmpMembershipFormSearchRepository;

    /**
     * POST  /pfmsEmpMembershipForms -> Create a new pfmsEmpMembershipForm.
     */
    @RequestMapping(value = "/pfmsEmpMembershipForms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembershipForm> createPfmsEmpMembershipForm(@Valid @RequestBody PfmsEmpMembershipForm pfmsEmpMembershipForm) throws URISyntaxException {
        log.debug("REST request to save PfmsEmpMembershipForm : {}", pfmsEmpMembershipForm);
        if (pfmsEmpMembershipForm.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsEmpMembershipForm cannot already have an ID").body(null);
        }
        PfmsEmpMembershipForm result = pfmsEmpMembershipFormRepository.save(pfmsEmpMembershipForm);
        pfmsEmpMembershipFormSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsEmpMembershipForms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsEmpMembershipForm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsEmpMembershipForms -> Updates an existing pfmsEmpMembershipForm.
     */
    @RequestMapping(value = "/pfmsEmpMembershipForms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembershipForm> updatePfmsEmpMembershipForm(@Valid @RequestBody PfmsEmpMembershipForm pfmsEmpMembershipForm) throws URISyntaxException {
        log.debug("REST request to update PfmsEmpMembershipForm : {}", pfmsEmpMembershipForm);
        if (pfmsEmpMembershipForm.getId() == null) {
            return createPfmsEmpMembershipForm(pfmsEmpMembershipForm);
        }
        PfmsEmpMembershipForm result = pfmsEmpMembershipFormRepository.save(pfmsEmpMembershipForm);
        pfmsEmpMembershipFormSearchRepository.save(pfmsEmpMembershipForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsEmpMembershipForm", pfmsEmpMembershipForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsEmpMembershipForms -> get all the pfmsEmpMembershipForms.
     */
    @RequestMapping(value = "/pfmsEmpMembershipForms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsEmpMembershipForm>> getAllPfmsEmpMembershipForms(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsEmpMembershipForm> page = pfmsEmpMembershipFormRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsEmpMembershipForms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsEmpMembershipForms/:id -> get the "id" pfmsEmpMembershipForm.
     */
    @RequestMapping(value = "/pfmsEmpMembershipForms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMembershipForm> getPfmsEmpMembershipForm(@PathVariable Long id) {
        log.debug("REST request to get PfmsEmpMembershipForm : {}", id);
        return Optional.ofNullable(pfmsEmpMembershipFormRepository.findOne(id))
            .map(pfmsEmpMembershipForm -> new ResponseEntity<>(
                pfmsEmpMembershipForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsEmpMembershipForms/:id -> delete the "id" pfmsEmpMembershipForm.
     */
    @RequestMapping(value = "/pfmsEmpMembershipForms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsEmpMembershipForm(@PathVariable Long id) {
        log.debug("REST request to delete PfmsEmpMembershipForm : {}", id);
        pfmsEmpMembershipFormRepository.delete(id);
        pfmsEmpMembershipFormSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsEmpMembershipForm", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsEmpMembershipForms/:query -> search for the pfmsEmpMembershipForm corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsEmpMembershipForms/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMembershipForm> searchPfmsEmpMembershipForms(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsEmpMembershipFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsEmpMembershipFormListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsEmpMembershipFormListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMembershipForm> getPfmsEmpMembershipFormListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsEmpMembershipFormByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsEmpMembershipFormRepository.getPfmsEmpMembershipFormListByEmployee(employeeInfoId);

    }
}
