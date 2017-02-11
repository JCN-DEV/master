package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.APScaleApplicationEditLog;
import gov.step.app.repository.APScaleApplicationEditLogRepository;
import gov.step.app.repository.search.APScaleApplicationEditLogSearchRepository;
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
 * REST controller for managing APScaleApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class APScaleApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(APScaleApplicationEditLogResource.class);

    @Inject
    private APScaleApplicationEditLogRepository aPScaleApplicationEditLogRepository;

    @Inject
    private APScaleApplicationEditLogSearchRepository aPScaleApplicationEditLogSearchRepository;

    /**
     * POST  /aPScaleApplicationEditLogs -> Create a new aPScaleApplicationEditLog.
     */
    @RequestMapping(value = "/aPScaleApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationEditLog> createAPScaleApplicationEditLog(@RequestBody APScaleApplicationEditLog aPScaleApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save APScaleApplicationEditLog : {}", aPScaleApplicationEditLog);
        if (aPScaleApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aPScaleApplicationEditLog cannot already have an ID").body(null);
        }
        APScaleApplicationEditLog result = aPScaleApplicationEditLogRepository.save(aPScaleApplicationEditLog);
        aPScaleApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aPScaleApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aPScaleApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aPScaleApplicationEditLogs -> Updates an existing aPScaleApplicationEditLog.
     */
    @RequestMapping(value = "/aPScaleApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationEditLog> updateAPScaleApplicationEditLog(@RequestBody APScaleApplicationEditLog aPScaleApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update APScaleApplicationEditLog : {}", aPScaleApplicationEditLog);
        if (aPScaleApplicationEditLog.getId() == null) {
            return createAPScaleApplicationEditLog(aPScaleApplicationEditLog);
        }
        APScaleApplicationEditLog result = aPScaleApplicationEditLogRepository.save(aPScaleApplicationEditLog);
        aPScaleApplicationEditLogSearchRepository.save(aPScaleApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aPScaleApplicationEditLog", aPScaleApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aPScaleApplicationEditLogs -> get all the aPScaleApplicationEditLogs.
     */
    @RequestMapping(value = "/aPScaleApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<APScaleApplicationEditLog>> getAllAPScaleApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<APScaleApplicationEditLog> page = aPScaleApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aPScaleApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aPScaleApplicationEditLogs/:id -> get the "id" aPScaleApplicationEditLog.
     */
    @RequestMapping(value = "/aPScaleApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationEditLog> getAPScaleApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get APScaleApplicationEditLog : {}", id);
        return Optional.ofNullable(aPScaleApplicationEditLogRepository.findOne(id))
            .map(aPScaleApplicationEditLog -> new ResponseEntity<>(
                aPScaleApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aPScaleApplicationEditLogs/:id -> delete the "id" aPScaleApplicationEditLog.
     */
    @RequestMapping(value = "/aPScaleApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAPScaleApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete APScaleApplicationEditLog : {}", id);
        aPScaleApplicationEditLogRepository.delete(id);
        aPScaleApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aPScaleApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aPScaleApplicationEditLogs/:query -> search for the aPScaleApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aPScaleApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<APScaleApplicationEditLog> searchAPScaleApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(aPScaleApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
