package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanBillRegister;
import gov.step.app.repository.EmployeeLoanBillRegisterRepository;
import gov.step.app.repository.search.EmployeeLoanBillRegisterSearchRepository;
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
 * REST controller for managing EmployeeLoanBillRegister.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanBillRegisterResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanBillRegisterResource.class);

    @Inject
    private EmployeeLoanBillRegisterRepository employeeLoanBillRegisterRepository;

    @Inject
    private EmployeeLoanBillRegisterSearchRepository employeeLoanBillRegisterSearchRepository;

    /**
     * POST  /employeeLoanBillRegisters -> Create a new employeeLoanBillRegister.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanBillRegister> createEmployeeLoanBillRegister(@Valid @RequestBody EmployeeLoanBillRegister employeeLoanBillRegister) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanBillRegister : {}", employeeLoanBillRegister);
        if (employeeLoanBillRegister.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanBillRegister cannot already have an ID").body(null);
        }
        DateResource dateResource = new DateResource();
        employeeLoanBillRegister.setCreateDate(dateResource.getDateAsLocalDate());
        employeeLoanBillRegister.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanBillRegister.setStatus(1);
        EmployeeLoanBillRegister result = employeeLoanBillRegisterRepository.save(employeeLoanBillRegister);
        employeeLoanBillRegisterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanBillRegisters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanBillRegister", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanBillRegisters -> Updates an existing employeeLoanBillRegister.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanBillRegister> updateEmployeeLoanBillRegister(@Valid @RequestBody EmployeeLoanBillRegister employeeLoanBillRegister) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanBillRegister : {}", employeeLoanBillRegister);
        if (employeeLoanBillRegister.getId() == null) {
            return createEmployeeLoanBillRegister(employeeLoanBillRegister);
        }
        DateResource dateResource = new DateResource();
        employeeLoanBillRegister.setUpdateDate(dateResource.getDateAsLocalDate());
        employeeLoanBillRegister.setUpdateBy(SecurityUtils.getCurrentUserId());
        EmployeeLoanBillRegister result = employeeLoanBillRegisterRepository.save(employeeLoanBillRegister);
        employeeLoanBillRegisterSearchRepository.save(employeeLoanBillRegister);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanBillRegister", employeeLoanBillRegister.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanBillRegisters -> get all the employeeLoanBillRegisters.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanBillRegister>> getAllEmployeeLoanBillRegisters(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanBillRegister> page = employeeLoanBillRegisterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanBillRegisters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanBillRegisters/:id -> get the "id" employeeLoanBillRegister.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanBillRegister> getEmployeeLoanBillRegister(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanBillRegister : {}", id);
        return Optional.ofNullable(employeeLoanBillRegisterRepository.findOne(id))
            .map(employeeLoanBillRegister -> new ResponseEntity<>(
                employeeLoanBillRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanBillRegisters/:id -> delete the "id" employeeLoanBillRegister.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanBillRegister(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanBillRegister : {}", id);
        employeeLoanBillRegisterRepository.delete(id);
        employeeLoanBillRegisterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanBillRegister", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanBillRegisters/:query -> search for the employeeLoanBillRegister corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanBillRegisters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanBillRegister> searchEmployeeLoanBillRegisters(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanBillRegisterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /employeeLoanBillRegisters/:id -> get the "id" employeeLoanBillRegister.
     */
    @RequestMapping(value = "/employeeLoanBillRegisters/findLoanBillRegister/{loanRequisitionID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanBillRegister> getBillRegisterByLoanReqID(@PathVariable Long loanRequisitionID) {
        log.debug("REST request to get EmployeeLoanBillRegister : {}", loanRequisitionID);
        return Optional.ofNullable(employeeLoanBillRegisterRepository.findByLoanRequisitionID(loanRequisitionID))
            .map(employeeLoanBillRegister -> new ResponseEntity<>(
                employeeLoanBillRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * GET  /employeeLoanBillRegisters/:id -> get the "id" employeeLoanBillRegister.
     */

    @RequestMapping(value = "/employeeLoanBillRegisters/findLoanBillRegisterByBillNo/{billNo}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanBillRegister> getBillRegisterByBillNo(@PathVariable String billNo) {
        log.debug("REST request to get EmployeeLoanBillRegister : {}", billNo);
        return Optional.ofNullable(employeeLoanBillRegisterRepository.findByBillNo(billNo))
            .map(employeeLoanBillRegister -> new ResponseEntity<>(
                employeeLoanBillRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
