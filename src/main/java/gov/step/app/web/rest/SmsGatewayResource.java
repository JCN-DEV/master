package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SmsGateway;
import gov.step.app.repository.SmsGatewayRepository;
import gov.step.app.repository.search.SmsGatewaySearchRepository;
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
 * REST controller for managing SmsGateway.
 */
@RestController
@RequestMapping("/api")
public class SmsGatewayResource {

    private final Logger log = LoggerFactory.getLogger(SmsGatewayResource.class);

    @Inject
    private SmsGatewayRepository smsGatewayRepository;

    @Inject
    private SmsGatewaySearchRepository smsGatewaySearchRepository;

    /**
     * POST  /smsGateways -> Create a new smsGateway.
     */
    @RequestMapping(value = "/smsGateways",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsGateway> createSmsGateway(@Valid @RequestBody SmsGateway smsGateway) throws URISyntaxException {
        log.debug("REST request to save SmsGateway : {}", smsGateway);
        if (smsGateway.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new smsGateway cannot already have an ID").body(null);
        }
        SmsGateway result = smsGatewayRepository.save(smsGateway);
        smsGatewaySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/smsGateways/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("smsGateway", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /smsGateways -> Updates an existing smsGateway.
     */
    @RequestMapping(value = "/smsGateways",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsGateway> updateSmsGateway(@Valid @RequestBody SmsGateway smsGateway) throws URISyntaxException {
        log.debug("REST request to update SmsGateway : {}", smsGateway);
        if (smsGateway.getId() == null) {
            return createSmsGateway(smsGateway);
        }
        SmsGateway result = smsGatewayRepository.save(smsGateway);
        smsGatewaySearchRepository.save(smsGateway);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("smsGateway", smsGateway.getId().toString()))
            .body(result);
    }

    /**
     * GET  /smsGateways -> get all the smsGateways.
     */
    @RequestMapping(value = "/smsGateways",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SmsGateway>> getAllSmsGateways(Pageable pageable)
        throws URISyntaxException {
        Page<SmsGateway> page = smsGatewayRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/smsGateways");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /smsGateways/:id -> get the "id" smsGateway.
     */
    @RequestMapping(value = "/smsGateways/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SmsGateway> getSmsGateway(@PathVariable Long id) {
        log.debug("REST request to get SmsGateway : {}", id);
        return Optional.ofNullable(smsGatewayRepository.findOne(id))
            .map(smsGateway -> new ResponseEntity<>(
                smsGateway,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /smsGateways/:id -> delete the "id" smsGateway.
     */
    @RequestMapping(value = "/smsGateways/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSmsGateway(@PathVariable Long id) {
        log.debug("REST request to delete SmsGateway : {}", id);
        smsGatewayRepository.delete(id);
        smsGatewaySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("smsGateway", id.toString())).build();
    }

    /**
     * SEARCH  /_search/smsGateways/:query -> search for the smsGateway corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/smsGateways/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SmsGateway> searchSmsGateways(@PathVariable String query) {
        return StreamSupport
            .stream(smsGatewaySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
