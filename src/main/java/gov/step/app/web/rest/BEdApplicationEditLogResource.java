package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BEdApplicationEditLog;
import gov.step.app.repository.BEdApplicationEditLogRepository;
import gov.step.app.repository.search.BEdApplicationEditLogSearchRepository;
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
 * REST controller for managing BEdApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class BEdApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(BEdApplicationEditLogResource.class);

    @Inject
    private BEdApplicationEditLogRepository bEdApplicationEditLogRepository;

    @Inject
    private BEdApplicationEditLogSearchRepository bEdApplicationEditLogSearchRepository;

    /**
     * POST  /bEdApplicationEditLogs -> Create a new bEdApplicationEditLog.
     */
    @RequestMapping(value = "/bEdApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationEditLog> createBEdApplicationEditLog(@RequestBody BEdApplicationEditLog bEdApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save BEdApplicationEditLog : {}", bEdApplicationEditLog);
        if (bEdApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bEdApplicationEditLog cannot already have an ID").body(null);
        }
        BEdApplicationEditLog result = bEdApplicationEditLogRepository.save(bEdApplicationEditLog);
        bEdApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bEdApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bEdApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bEdApplicationEditLogs -> Updates an existing bEdApplicationEditLog.
     */
    @RequestMapping(value = "/bEdApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationEditLog> updateBEdApplicationEditLog(@RequestBody BEdApplicationEditLog bEdApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update BEdApplicationEditLog : {}", bEdApplicationEditLog);
        if (bEdApplicationEditLog.getId() == null) {
            return createBEdApplicationEditLog(bEdApplicationEditLog);
        }
        BEdApplicationEditLog result = bEdApplicationEditLogRepository.save(bEdApplicationEditLog);
        bEdApplicationEditLogSearchRepository.save(bEdApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bEdApplicationEditLog", bEdApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bEdApplicationEditLogs -> get all the bEdApplicationEditLogs.
     */
    @RequestMapping(value = "/bEdApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BEdApplicationEditLog>> getAllBEdApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<BEdApplicationEditLog> page = bEdApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bEdApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bEdApplicationEditLogs/:id -> get the "id" bEdApplicationEditLog.
     */
    @RequestMapping(value = "/bEdApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationEditLog> getBEdApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get BEdApplicationEditLog : {}", id);
        return Optional.ofNullable(bEdApplicationEditLogRepository.findOne(id))
            .map(bEdApplicationEditLog -> new ResponseEntity<>(
                bEdApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bEdApplicationEditLogs/:id -> delete the "id" bEdApplicationEditLog.
     */
    @RequestMapping(value = "/bEdApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBEdApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete BEdApplicationEditLog : {}", id);
        bEdApplicationEditLogRepository.delete(id);
        bEdApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bEdApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bEdApplicationEditLogs/:query -> search for the bEdApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bEdApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BEdApplicationEditLog> searchBEdApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(bEdApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
