package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceName;
import gov.step.app.repository.SmsServiceNameRepository;
import gov.step.app.repository.search.SmsServiceNameSearchRepository;
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
 * REST controller for managing SmsServiceName.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceNameResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceNameResource.class);
        
    @Inject
    private SmsServiceNameRepository smsServiceNameRepository;
    
    @Inject
    private SmsServiceNameSearchRepository smsServiceNameSearchRepository;
    
    /**
     * POST  /smsServiceNames -> Create a new smsServiceName.
     */
    @RequestMapping(value = "/smsServiceNames",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceName> createSmsServiceName(@Valid @RequestBody SmsServiceName smsServiceName) throws URISyntaxException {
        log.debug("REST request to save SmsServiceName : {}", smsServiceName);
        if (smsServiceName.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceName", "idexists", "A new smsServiceName cannot already have an ID")).body(null);
        }
        SmsServiceName result = smsServiceNameRepository.save(smsServiceName);
        smsServiceNameSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceNames/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceName", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceNames -> Updates an existing smsServiceName.
     */
    @RequestMapping(value = "/smsServiceNames",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceName> updateSmsServiceName(@Valid @RequestBody SmsServiceName smsServiceName) throws URISyntaxException {
        log.debug("REST request to update SmsServiceName : {}", smsServiceName);
        if (smsServiceName.getId() == null) {
            return createSmsServiceName(smsServiceName);
        }
        SmsServiceName result = smsServiceNameRepository.save(smsServiceName);
        smsServiceNameSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceName", smsServiceName.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceNames -> get all the smsServiceNames.
     */
    @RequestMapping(value = "/smsServiceNames",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceName>> getAllSmsServiceNames(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceNames");
        Page<SmsServiceName> page = smsServiceNameRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceNames");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceNames/:id -> get the "id" smsServiceName.
     */
    @RequestMapping(value = "/smsServiceNames/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceName> getSmsServiceName(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceName : {}", id);
        SmsServiceName smsServiceName = smsServiceNameRepository.findOne(id);
        return Optional.ofNullable(smsServiceName)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsServiceNames/:id -> delete the "id" smsServiceName.
     */
    @RequestMapping(value = "/smsServiceNames/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceName(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceName : {}", id);
        smsServiceNameRepository.delete(id);
        smsServiceNameSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceName", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceNames/:query -> search for the smsServiceName corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceNames/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceName> searchSmsServiceNames(@PathVariable String query) {
        log.debug("REST request to search SmsServiceNames for query {}", query);
        return StreamSupport
            .stream(smsServiceNameSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
