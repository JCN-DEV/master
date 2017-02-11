package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanRulesSetup;
import gov.step.app.repository.EmployeeLoanRulesSetupRepository;
import gov.step.app.repository.search.EmployeeLoanRulesSetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EmployeeLoanRulesSetup.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanRulesSetupResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanRulesSetupResource.class);

    @Inject
    private EmployeeLoanRulesSetupRepository employeeLoanRulesSetupRepository;

    @Inject
    private EmployeeLoanRulesSetupSearchRepository employeeLoanRulesSetupSearchRepository;

    /**
     * POST  /employeeLoanRulesSetups -> Create a new employeeLoanRulesSetup.
     */
    @RequestMapping(value = "/employeeLoanRulesSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRulesSetup> createEmployeeLoanRulesSetup(@Valid @RequestBody EmployeeLoanRulesSetup employeeLoanRulesSetup) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanRulesSetup : {}", employeeLoanRulesSetup);
        if (employeeLoanRulesSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanRulesSetup cannot already have an ID").body(null);
        }
        DateResource dataResource =  new DateResource();
        employeeLoanRulesSetup.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanRulesSetup.setCreateDate(dataResource.getDateAsLocalDate());

        EmployeeLoanRulesSetup result = employeeLoanRulesSetupRepository.save(employeeLoanRulesSetup);
        employeeLoanRulesSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanRulesSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanRulesSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanRulesSetups -> Updates an existing employeeLoanRulesSetup.
     */
    @RequestMapping(value = "/employeeLoanRulesSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRulesSetup> updateEmployeeLoanRulesSetup(@Valid @RequestBody EmployeeLoanRulesSetup employeeLoanRulesSetup) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanRulesSetup : {}", employeeLoanRulesSetup);
        if (employeeLoanRulesSetup.getId() == null) {
            return createEmployeeLoanRulesSetup(employeeLoanRulesSetup);
        }
        DateResource dataResource =  new DateResource();
        employeeLoanRulesSetup.setUpdateBy(SecurityUtils.getCurrentUserId());
        employeeLoanRulesSetup.setUpdateDate(dataResource.getDateAsLocalDate());

        EmployeeLoanRulesSetup result = employeeLoanRulesSetupRepository.save(employeeLoanRulesSetup);
        employeeLoanRulesSetupSearchRepository.save(employeeLoanRulesSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanRulesSetup", employeeLoanRulesSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanRulesSetups -> get all the employeeLoanRulesSetups.
     */
    @RequestMapping(value = "/employeeLoanRulesSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanRulesSetup>> getAllEmployeeLoanRulesSetups(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanRulesSetup> page = employeeLoanRulesSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanRulesSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanRulesSetups/:id -> get the "id" employeeLoanRulesSetup.
     */
    @RequestMapping(value = "/employeeLoanRulesSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRulesSetup> getEmployeeLoanRulesSetup(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanRulesSetup : {}", id);
        return Optional.ofNullable(employeeLoanRulesSetupRepository.findOne(id))
            .map(employeeLoanRulesSetup -> new ResponseEntity<>(
                employeeLoanRulesSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanRulesSetups/:id -> delete the "id" employeeLoanRulesSetup.
     */
    @RequestMapping(value = "/employeeLoanRulesSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanRulesSetup(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanRulesSetup : {}", id);
        employeeLoanRulesSetupRepository.delete(id);
        employeeLoanRulesSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanRulesSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanRulesSetups/:query -> search for the employeeLoanRulesSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanRulesSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanRulesSetup> searchEmployeeLoanRulesSetups(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanRulesSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/employeeLoanRulesSetups/findLoanRulesSetup/{loanTypeID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanRulesSetup> findLoanRulesSetup(@PathVariable Long loanTypeID) {
        log.debug("REST request to get EmployeeLoanRulesSetupByType : {}", loanTypeID);

        return employeeLoanRulesSetupRepository.findByLoanType(loanTypeID);
    }

    @RequestMapping(value = "/employeeLoanRulesSetups/findLoanRulesSetupById/{loanRulesId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public EmployeeLoanRulesSetup findLoanRulesSetupById(@PathVariable Long loanRulesId) {
        log.debug("REST request to get EmployeeLoanRulesSetupById : {}", loanRulesId);

        return employeeLoanRulesSetupRepository.findOne(loanRulesId);
    }


    // Check Unique LoanTypeName
    @RequestMapping(value = "/employeeLoanTypeSetups/isLoanNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> isLoanNameUnique(@RequestParam String value) {
        log.debug("REST request to isLoanNameUnique : {}", value);
        Optional<EmployeeLoanRulesSetup> employeeLoanRulesSetup = employeeLoanRulesSetupRepository.findByLoanName(value);
        java.util.Map map =new HashMap();
        map.put("value",value);

        if(Optional.empty().equals(employeeLoanRulesSetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }



}
