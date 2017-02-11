package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceDepartment;
import gov.step.app.repository.SmsServiceDepartmentRepository;
import gov.step.app.repository.search.SmsServiceDepartmentSearchRepository;
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
 * REST controller for managing SmsServiceDepartment.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceDepartmentResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceDepartmentResource.class);
        
    @Inject
    private SmsServiceDepartmentRepository smsServiceDepartmentRepository;
    
    @Inject
    private SmsServiceDepartmentSearchRepository smsServiceDepartmentSearchRepository;
    
    /**
     * POST  /smsServiceDepartments -> Create a new smsServiceDepartment.
     */
    @RequestMapping(value = "/smsServiceDepartments",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceDepartment> createSmsServiceDepartment(@Valid @RequestBody SmsServiceDepartment smsServiceDepartment) throws URISyntaxException {
        log.debug("REST request to save SmsServiceDepartment : {}", smsServiceDepartment);
        if (smsServiceDepartment.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceDepartment", "idexists", "A new smsServiceDepartment cannot already have an ID")).body(null);
        }
        SmsServiceDepartment result = smsServiceDepartmentRepository.save(smsServiceDepartment);
        smsServiceDepartmentSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceDepartments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceDepartment", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceDepartments -> Updates an existing smsServiceDepartment.
     */
    @RequestMapping(value = "/smsServiceDepartments",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceDepartment> updateSmsServiceDepartment(@Valid @RequestBody SmsServiceDepartment smsServiceDepartment) throws URISyntaxException {
        log.debug("REST request to update SmsServiceDepartment : {}", smsServiceDepartment);
        if (smsServiceDepartment.getId() == null) {
            return createSmsServiceDepartment(smsServiceDepartment);
        }
        SmsServiceDepartment result = smsServiceDepartmentRepository.save(smsServiceDepartment);
        smsServiceDepartmentSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceDepartment", smsServiceDepartment.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceDepartments -> get all the smsServiceDepartments.
     */
    @RequestMapping(value = "/smsServiceDepartments",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceDepartment>> getAllSmsServiceDepartments(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceDepartments");
        Page<SmsServiceDepartment> page = smsServiceDepartmentRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceDepartments");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceDepartments/:id -> get the "id" smsServiceDepartment.
     */
    @RequestMapping(value = "/smsServiceDepartments/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceDepartment> getSmsServiceDepartment(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceDepartment : {}", id);
        SmsServiceDepartment smsServiceDepartment = smsServiceDepartmentRepository.findOne(id);
        return Optional.ofNullable(smsServiceDepartment)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsServiceDepartments/:id -> delete the "id" smsServiceDepartment.
     */
    @RequestMapping(value = "/smsServiceDepartments/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceDepartment(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceDepartment : {}", id);
        smsServiceDepartmentRepository.delete(id);
        smsServiceDepartmentSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceDepartment", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceDepartments/:query -> search for the smsServiceDepartment corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceDepartments/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceDepartment> searchSmsServiceDepartments(@PathVariable String query) {
        log.debug("REST request to search SmsServiceDepartments for query {}", query);
        return StreamSupport
            .stream(smsServiceDepartmentSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
