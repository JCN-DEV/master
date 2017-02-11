package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EmployeeLoanCheckRegister;
import gov.step.app.repository.EmployeeLoanCheckRegisterRepository;
import gov.step.app.repository.search.EmployeeLoanCheckRegisterSearchRepository;
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
 * REST controller for managing EmployeeLoanCheckRegister.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanCheckRegisterResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanCheckRegisterResource.class);

    @Inject
    private EmployeeLoanCheckRegisterRepository employeeLoanCheckRegisterRepository;

    @Inject
    private EmployeeLoanCheckRegisterSearchRepository employeeLoanCheckRegisterSearchRepository;

    /**
     * POST  /employeeLoanCheckRegisters -> Create a new employeeLoanCheckRegister.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanCheckRegister> createEmployeeLoanCheckRegister(@Valid @RequestBody EmployeeLoanCheckRegister employeeLoanCheckRegister) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanCheckRegister : {}", employeeLoanCheckRegister);
        if (employeeLoanCheckRegister.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanCheckRegister cannot already have an ID").body(null);
        }
        DateResource dateResource = new DateResource();
        employeeLoanCheckRegister.setCreateDate(dateResource.getDateAsLocalDate());
        employeeLoanCheckRegister.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanCheckRegister.setStatus(1);
        EmployeeLoanCheckRegister result = employeeLoanCheckRegisterRepository.save(employeeLoanCheckRegister);
        employeeLoanCheckRegisterSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanCheckRegisters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanCheckRegister", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanCheckRegisters -> Updates an existing employeeLoanCheckRegister.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanCheckRegister> updateEmployeeLoanCheckRegister(@Valid @RequestBody EmployeeLoanCheckRegister employeeLoanCheckRegister) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanCheckRegister : {}", employeeLoanCheckRegister);
        if (employeeLoanCheckRegister.getId() == null) {
            return createEmployeeLoanCheckRegister(employeeLoanCheckRegister);
        }
        DateResource dateResource = new DateResource();
        employeeLoanCheckRegister.setUpdateDate(dateResource.getDateAsLocalDate());
        employeeLoanCheckRegister.setUpdateBy(SecurityUtils.getCurrentUserId());
        EmployeeLoanCheckRegister result = employeeLoanCheckRegisterRepository.save(employeeLoanCheckRegister);
        employeeLoanCheckRegisterSearchRepository.save(employeeLoanCheckRegister);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanCheckRegister", employeeLoanCheckRegister.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanCheckRegisters -> get all the employeeLoanCheckRegisters.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanCheckRegister>> getAllEmployeeLoanCheckRegisters(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanCheckRegister> page = employeeLoanCheckRegisterRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanCheckRegisters");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanCheckRegisters/:id -> get the "id" employeeLoanCheckRegister.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanCheckRegister> getEmployeeLoanCheckRegister(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanCheckRegister : {}", id);
        return Optional.ofNullable(employeeLoanCheckRegisterRepository.findOne(id))
            .map(employeeLoanCheckRegister -> new ResponseEntity<>(
                employeeLoanCheckRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanCheckRegisters/:id -> delete the "id" employeeLoanCheckRegister.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanCheckRegister(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanCheckRegister : {}", id);
        employeeLoanCheckRegisterRepository.delete(id);
        employeeLoanCheckRegisterSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanCheckRegister", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanCheckRegisters/:query -> search for the employeeLoanCheckRegister corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanCheckRegisters/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanCheckRegister> searchEmployeeLoanCheckRegisters(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanCheckRegisterSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /employeeLoanCheckRegisters/:id -> get the "id" employeeLoanCheckRegister.
     */
    @RequestMapping(value = "/employeeLoanCheckRegisters/findByCheckNumber/{checkNumber}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanCheckRegister> getEmployeeLoanCheckRegister(@PathVariable String checkNumber) {
        log.debug("REST request to get EmployeeLoanCheckRegister : {}", checkNumber);
        return Optional.ofNullable(employeeLoanCheckRegisterRepository.findByCheckNumber(checkNumber))
            .map(employeeLoanCheckRegister -> new ResponseEntity<>(
                employeeLoanCheckRegister,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
