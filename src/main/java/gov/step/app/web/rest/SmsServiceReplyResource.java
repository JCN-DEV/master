package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsServiceReply;
import gov.step.app.repository.SmsServiceReplyRepository;
import gov.step.app.repository.search.SmsServiceReplySearchRepository;
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
 * REST controller for managing SmsServiceReply.
 */
@RestController
@RequestMapping("/api")
public class SmsServiceReplyResource {

    private final Logger log = LoggerFactory.getLogger(SmsServiceReplyResource.class);

    @Inject
    private SmsServiceReplyRepository smsServiceReplyRepository;

    @Inject
    private SmsServiceReplySearchRepository smsServiceReplySearchRepository;

    /**
     * POST  /smsServiceReplys -> Create a new smsServiceReply.
     */
    @RequestMapping(value = "/smsServiceReplys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceReply> createSmsServiceReply(@RequestBody SmsServiceReply smsServiceReply) throws URISyntaxException {
        log.debug("REST request to save SmsServiceReply : {}", smsServiceReply);
        if (smsServiceReply.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("smsServiceReply", "idexists", "A new smsServiceReply cannot already have an ID")).body(null);
        }
        SmsServiceReply result = smsServiceReplyRepository.save(smsServiceReply);
        smsServiceReplySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsServiceReplys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsServiceReply", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsServiceReplys -> Updates an existing smsServiceReply.
     */
    @RequestMapping(value = "/smsServiceReplys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceReply> updateSmsServiceReply(@RequestBody SmsServiceReply smsServiceReply) throws URISyntaxException {
        log.debug("REST request to update SmsServiceReply : {}", smsServiceReply);
        if (smsServiceReply.getId() == null) {
            return createSmsServiceReply(smsServiceReply);
        }
        SmsServiceReply result = smsServiceReplyRepository.save(smsServiceReply);
        smsServiceReplySearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsServiceReply", smsServiceReply.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsServiceReplys -> get all the smsServiceReplys.
     */
    @RequestMapping(value = "/smsServiceReplys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsServiceReply>> getAllSmsServiceReplys(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of SmsServiceReplys");
        Page<SmsServiceReply> page = smsServiceReplyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsServiceReplys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsServiceReplys/:id -> get the "id" smsServiceReply.
     */
    @RequestMapping(value = "/smsServiceReplys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsServiceReply> getSmsServiceReply(@PathVariable Long id) {
        log.debug("REST request to get SmsServiceReply : {}", id);
        SmsServiceReply smsServiceReply = smsServiceReplyRepository.findOne(id);
        return Optional.ofNullable(smsServiceReply)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsServiceReplys/:id -> delete the "id" smsServiceReply.
     */
    @RequestMapping(value = "/smsServiceReplys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsServiceReply(@PathVariable Long id) {
        log.debug("REST request to delete SmsServiceReply : {}", id);
        smsServiceReplyRepository.delete(id);
        smsServiceReplySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsServiceReply", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsServiceReplys/:query -> search for the smsServiceReply corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsServiceReplys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceReply> searchSmsServiceReplys(@PathVariable String query) {
        log.debug("REST request to search SmsServiceReplys for query {}", query);
        return StreamSupport
            .stream(smsServiceReplySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /smsServiceReplys/complaint/:id -> get the all reply by complaint.
     */
    @RequestMapping(value = "/smsServiceReplys/complaint/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceReply> getServiceReplyByComplaint(@PathVariable Long id)
        throws URISyntaxException
    {
        List<SmsServiceReply> serviceReplyes = smsServiceReplyRepository.findByComplaint(id);
        return  serviceReplyes;

    }

    /**
     * GET  /smsServiceReplys/complaint/:id -> get the all reply by complaint.
     */
    @RequestMapping(value = "/smsServiceReplys/department/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsServiceReply> getServiceReplyByDepartment(@PathVariable Long id)
        throws URISyntaxException
    {
        List<SmsServiceReply> serviceReplyes = smsServiceReplyRepository.findByDepartment(id);
        return  serviceReplyes;

    }
}
