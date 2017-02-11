package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanTypeSetup;
import gov.step.app.repository.EmployeeLoanTypeSetupRepository;
import gov.step.app.repository.search.EmployeeLoanTypeSetupSearchRepository;
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
 * REST controller for managing EmployeeLoanTypeSetup.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanTypeSetupResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanTypeSetupResource.class);

    @Inject
    private EmployeeLoanTypeSetupRepository employeeLoanTypeSetupRepository;

    @Inject
    private EmployeeLoanTypeSetupSearchRepository employeeLoanTypeSetupSearchRepository;

    /**
     * POST  /employeeLoanTypeSetups -> Create a new employeeLoanTypeSetup.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanTypeSetup> createEmployeeLoanTypeSetup(@Valid @RequestBody EmployeeLoanTypeSetup employeeLoanTypeSetup) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanTypeSetup : {}", employeeLoanTypeSetup);
        if (employeeLoanTypeSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanTypeSetup cannot already have an ID").body(null);
        }
        DateResource dateResource =  new DateResource();
        employeeLoanTypeSetup.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanTypeSetup.setCreateDate(dateResource.getDateAsLocalDate());
        EmployeeLoanTypeSetup result = employeeLoanTypeSetupRepository.save(employeeLoanTypeSetup);
        employeeLoanTypeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanTypeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanTypeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanTypeSetups -> Updates an existing employeeLoanTypeSetup.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanTypeSetup> updateEmployeeLoanTypeSetup(@Valid @RequestBody EmployeeLoanTypeSetup employeeLoanTypeSetup) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanTypeSetup : {}", employeeLoanTypeSetup);
        if (employeeLoanTypeSetup.getId() == null) {
            return createEmployeeLoanTypeSetup(employeeLoanTypeSetup);
        }
        DateResource dateResource =  new DateResource();
        employeeLoanTypeSetup.setUpdateBy(SecurityUtils.getCurrentUserId());
        employeeLoanTypeSetup.setUpdateDate(dateResource.getDateAsLocalDate());
        EmployeeLoanTypeSetup result = employeeLoanTypeSetupRepository.save(employeeLoanTypeSetup);
        employeeLoanTypeSetupSearchRepository.save(employeeLoanTypeSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanTypeSetup", employeeLoanTypeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanTypeSetups -> get all the employeeLoanTypeSetups.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanTypeSetup>> getAllEmployeeLoanTypeSetups(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanTypeSetup> page = employeeLoanTypeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanTypeSetups/:id -> get the "id" employeeLoanTypeSetup.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanTypeSetup> getEmployeeLoanTypeSetup(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanTypeSetup : {}", id);
        return Optional.ofNullable(employeeLoanTypeSetupRepository.findOne(id))
            .map(employeeLoanTypeSetup -> new ResponseEntity<>(
                employeeLoanTypeSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanTypeSetups/:id -> delete the "id" employeeLoanTypeSetup.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanTypeSetup(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanTypeSetup : {}", id);
        employeeLoanTypeSetupRepository.delete(id);
        employeeLoanTypeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanTypeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanTypeSetups/:query -> search for the employeeLoanTypeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanTypeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanTypeSetup> searchEmployeeLoanTypeSetups(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanTypeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    // Check Unique LoanTypeCode

    @RequestMapping(value = "/employeeLoanTypeSetups/isLoanTypeCodeUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> isLoanTypeCodeUnique( @RequestParam String value) {
        log.debug("REST request to get loanType by code : {}", value);
        Optional<EmployeeLoanTypeSetup> employeeLoanTypeSetup = employeeLoanTypeSetupRepository.findOneByLoanTypeCode(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(employeeLoanTypeSetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    // Check Unique LoanTypeName
    @RequestMapping(value = "/employeeLoanTypeSetups/isLoanTypeNameUnique/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<java.util.Map> isLoanTypeNameUnique(@RequestParam String value) {
        log.debug("REST request to isLoanTypeNameUnique : {}", value);
        Optional<EmployeeLoanTypeSetup> employeeLoanTypeSetup = employeeLoanTypeSetupRepository.findOneByLoanTypeName(value);
        java.util.Map map =new HashMap();
        map.put("value",value);
        if(Optional.empty().equals(employeeLoanTypeSetup)){
            map.put("isValid",true);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<java.util.Map>(map,HttpStatus.OK);
        }
    }

    /**
     * GET  /employeeLoanTypeSetups -> get Active  the employeeLoanTypeSetups.
     */
    @RequestMapping(value = "/employeeLoanTypeSetups/getActiveLoanTypeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanTypeSetup>> getAllByActiveStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("GET Active  loanType by code ");
        Page<EmployeeLoanTypeSetup> page = employeeLoanTypeSetupRepository.findByActiveStatus(pageable,true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }



}
