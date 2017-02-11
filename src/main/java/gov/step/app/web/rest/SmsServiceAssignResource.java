package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceAssign;
import gov.step.app.repository.SmsServiceAssignRepository;
import gov.step.app.repository.search.SmsServiceAssignSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing SmsServiceAssign.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceAssignResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceAssignResource.class);
        
    @Inject
    private SmsServiceAssignRepository smsServiceAssignRepository;
    
    @Inject
    private SmsServiceAssignSearchRepository smsServiceAssignSearchRepository;
    
    /**
     * POST  /smsServiceAssigns -> Create a new smsServiceAssign.
     */
    @RequestMapping(value = "/smsServiceAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceAssign> createSmsServiceAssign(@RequestBody SmsServiceAssign smsServiceAssign) throws URISyntaxException {
        log.debug("REST request to save SmsServiceAssign : {}", smsServiceAssign);
        if (smsServiceAssign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceAssign", "idexists", "A new smsServiceAssign cannot already have an ID")).body(null);
        }
        SmsServiceAssign result = smsServiceAssignRepository.save(smsServiceAssign);
        smsServiceAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceAssigns -> Updates an existing smsServiceAssign.
     */
    @RequestMapping(value = "/smsServiceAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceAssign> updateSmsServiceAssign(@RequestBody SmsServiceAssign smsServiceAssign) throws URISyntaxException {
        log.debug("REST request to update SmsServiceAssign : {}", smsServiceAssign);
        if (smsServiceAssign.getId() == null) {
            return createSmsServiceAssign(smsServiceAssign);
        }
        SmsServiceAssign result = smsServiceAssignRepository.save(smsServiceAssign);
        smsServiceAssignSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceAssign", smsServiceAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceAssigns -> get all the smsServiceAssigns.
     */
    @RequestMapping(value = "/smsServiceAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceAssign>> getAllSmsServiceAssigns(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceAssigns");
        Page<SmsServiceAssign> page = smsServiceAssignRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceAssigns/:id -> get the "id" smsServiceAssign.
     */
    @RequestMapping(value = "/smsServiceAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceAssign> getSmsServiceAssign(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceAssign : {}", id);
        SmsServiceAssign smsServiceAssign = smsServiceAssignRepository.findOne(id);
        return Optional.ofNullable(smsServiceAssign)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsServiceAssigns/:id -> delete the "id" smsServiceAssign.
     */
    @RequestMapping(value = "/smsServiceAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceAssign(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceAssign : {}", id);
        smsServiceAssignRepository.delete(id);
        smsServiceAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceAssigns/:query -> search for the smsServiceAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceAssign> searchSmsServiceAssigns(@PathVariable String query) {
        log.debug("REST request to search SmsServiceAssigns for query {}", query);
        return StreamSupport
            .stream(smsServiceAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
