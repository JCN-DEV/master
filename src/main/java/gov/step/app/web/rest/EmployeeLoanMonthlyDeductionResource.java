package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanMonthlyDeduction;
import gov.step.app.repository.EmployeeLoanMonthlyDeductionRepository;
import gov.step.app.repository.search.EmployeeLoanMonthlyDeductionSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing EmployeeLoanMonthlyDeduction.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanMonthlyDeductionResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanMonthlyDeductionResource.class);

    @Inject
    private EmployeeLoanMonthlyDeductionRepository employeeLoanMonthlyDeductionRepository;

    @Inject
    private EmployeeLoanMonthlyDeductionSearchRepository employeeLoanMonthlyDeductionSearchRepository;

    /**
     * POST  /employeeLoanMonthlyDeductions -> Create a new employeeLoanMonthlyDeduction.
     */
    @RequestMapping(value = "/employeeLoanMonthlyDeductions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanMonthlyDeduction> createEmployeeLoanMonthlyDeduction(@Valid @RequestBody EmployeeLoanMonthlyDeduction employeeLoanMonthlyDeduction) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanMonthlyDeduction : {}", employeeLoanMonthlyDeduction);
        if (employeeLoanMonthlyDeduction.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanMonthlyDeduction cannot already have an ID").body(null);
        }


        DateResource dateResource = new DateResource();
        employeeLoanMonthlyDeduction.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanMonthlyDeduction.setCreateDate(dateResource.getDateAsLocalDate());
        employeeLoanMonthlyDeduction.setModifiedDeductionAmount(null);
        employeeLoanMonthlyDeduction.setTotalDeductedLoanAmount(0D);
        employeeLoanMonthlyDeduction.setDeductionStatus(1);
        employeeLoanMonthlyDeduction.setTotalDeductedInterestAmount(0D);
        employeeLoanMonthlyDeduction.setStatus(true);

        // Calculate Interest Amount
        Double interestAmount = (employeeLoanMonthlyDeduction.getTotalLoanAmountApproved()*employeeLoanMonthlyDeduction.getLoanRequisitionForm().getEmployeeLoanRulesSetup().getInterest())/100D;
        employeeLoanMonthlyDeduction.setTotalInterestAmount(interestAmount);

        EmployeeLoanMonthlyDeduction result = employeeLoanMonthlyDeductionRepository.save(employeeLoanMonthlyDeduction);
        employeeLoanMonthlyDeductionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanMonthlyDeductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanMonthlyDeduction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanMonthlyDeductions -> Updates an existing employeeLoanMonthlyDeduction.
     */
    @RequestMapping(value = "/employeeLoanMonthlyDeductions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanMonthlyDeduction> updateEmployeeLoanMonthlyDeduction(@Valid @RequestBody EmployeeLoanMonthlyDeduction employeeLoanMonthlyDeduction) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanMonthlyDeduction : {}", employeeLoanMonthlyDeduction);
        if (employeeLoanMonthlyDeduction.getId() == null) {
            return createEmployeeLoanMonthlyDeduction(employeeLoanMonthlyDeduction);
        }
        EmployeeLoanMonthlyDeduction result = employeeLoanMonthlyDeductionRepository.save(employeeLoanMonthlyDeduction);
        employeeLoanMonthlyDeductionSearchRepository.save(employeeLoanMonthlyDeduction);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanMonthlyDeduction", employeeLoanMonthlyDeduction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanMonthlyDeductions -> get all the employeeLoanMonthlyDeductions.
     */
    @RequestMapping(value = "/employeeLoanMonthlyDeductions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanMonthlyDeduction>> getAllEmployeeLoanMonthlyDeductions(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanMonthlyDeduction> page = employeeLoanMonthlyDeductionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanMonthlyDeductions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanMonthlyDeductions/:id -> get the "id" employeeLoanMonthlyDeduction.
     */
    @RequestMapping(value = "/employeeLoanMonthlyDeductions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanMonthlyDeduction> getEmployeeLoanMonthlyDeduction(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanMonthlyDeduction : {}", id);
        return Optional.ofNullable(employeeLoanMonthlyDeductionRepository.findOne(id))
            .map(employeeLoanMonthlyDeduction -> new ResponseEntity<>(
                employeeLoanMonthlyDeduction,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanMonthlyDeductions/:id -> delete the "id" employeeLoanMonthlyDeduction.
     */
    @RequestMapping(value = "/employeeLoanMonthlyDeductions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanMonthlyDeduction(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanMonthlyDeduction : {}", id);
        employeeLoanMonthlyDeductionRepository.delete(id);
        employeeLoanMonthlyDeductionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanMonthlyDeduction", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanMonthlyDeductions/:query -> search for the employeeLoanMonthlyDeduction corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanMonthlyDeductions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanMonthlyDeduction> searchEmployeeLoanMonthlyDeductions(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanMonthlyDeductionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
