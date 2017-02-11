package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceForward;
import gov.step.app.repository.SmsServiceForwardRepository;
import gov.step.app.repository.search.SmsServiceForwardSearchRepository;
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
 * REST controller for managing SmsServiceForward.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceForwardResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceForwardResource.class);

    @Inject
    private SmsServiceForwardRepository smsServiceForwardRepository;

    @Inject
    private SmsServiceForwardSearchRepository smsServiceForwardSearchRepository;

    /**
     * POST  /smsServiceForwards -> Create a new smsServiceForward.
     */
    @RequestMapping(value = "/smsServiceForwards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceForward> createSmsServiceForward(@RequestBody SmsServiceForward smsServiceForward) throws URISyntaxException {
        log.debug("REST request to save SmsServiceForward : {}", smsServiceForward);
        if (smsServiceForward.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceForward", "idexists", "A new smsServiceForward cannot already have an ID")).body(null);
        }
        SmsServiceForward result = smsServiceForwardRepository.save(smsServiceForward);
        smsServiceForwardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceForward", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceForwards -> Updates an existing smsServiceForward.
     */
    @RequestMapping(value = "/smsServiceForwards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceForward> updateSmsServiceForward(@RequestBody SmsServiceForward smsServiceForward) throws URISyntaxException {
        log.debug("REST request to update SmsServiceForward : {}", smsServiceForward);
        if (smsServiceForward.getId() == null) {
            return createSmsServiceForward(smsServiceForward);
        }
        SmsServiceForward result = smsServiceForwardRepository.save(smsServiceForward);
        smsServiceForwardSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceForward", smsServiceForward.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceForwards -> get all the smsServiceForwards.
     */
    @RequestMapping(value = "/smsServiceForwards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceForward>> getAllSmsServiceForwards(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceForwards");
        Page<SmsServiceForward> page = smsServiceForwardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceForwards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceForwards/:id -> get the "id" smsServiceForward.
     */
    @RequestMapping(value = "/smsServiceForwards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceForward> getSmsServiceForward(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceForward : {}", id);
        SmsServiceForward smsServiceForward = smsServiceForwardRepository.findOne(id);
        return Optional.ofNullable(smsServiceForward)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsServiceForwards/:id -> delete the "id" smsServiceForward.
     */
    @RequestMapping(value = "/smsServiceForwards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceForward(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceForward : {}", id);
        smsServiceForwardRepository.delete(id);
        smsServiceForwardSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceForward", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceForwards/:query -> search for the smsServiceForward corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceForwards/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceForward> searchSmsServiceForwards(@PathVariable String query) {
        log.debug("REST request to search SmsServiceForwards for query {}", query);
        return StreamSupport
            .stream(smsServiceForwardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /smsServiceForwards/complaint/:id -> get the all forward by complaint.
     */
    @RequestMapping(value = "/smsServiceForwards/complaint/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceForward> getServiceForwardsByComplaint(@PathVariable Long id)
        throws URISyntaxException {
        List<SmsServiceForward> serviceForwards = smsServiceForwardRepository.findByComplaint(id);
        return  serviceForwards;

    }
}
