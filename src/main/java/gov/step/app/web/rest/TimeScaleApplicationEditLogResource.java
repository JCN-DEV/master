package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TimeScaleApplicationEditLog;
import gov.step.app.repository.TimeScaleApplicationEditLogRepository;
import gov.step.app.repository.search.TimeScaleApplicationEditLogSearchRepository;
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
 * REST controller for managing TimeScaleApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class TimeScaleApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(TimeScaleApplicationEditLogResource.class);

    @Inject
    private TimeScaleApplicationEditLogRepository timeScaleApplicationEditLogRepository;

    @Inject
    private TimeScaleApplicationEditLogSearchRepository timeScaleApplicationEditLogSearchRepository;

    /**
     * POST  /timeScaleApplicationEditLogs -> Create a new timeScaleApplicationEditLog.
     */
    @RequestMapping(value = "/timeScaleApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationEditLog> createTimeScaleApplicationEditLog(@RequestBody TimeScaleApplicationEditLog timeScaleApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save TimeScaleApplicationEditLog : {}", timeScaleApplicationEditLog);
        if (timeScaleApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new timeScaleApplicationEditLog cannot already have an ID").body(null);
        }
        TimeScaleApplicationEditLog result = timeScaleApplicationEditLogRepository.save(timeScaleApplicationEditLog);
        timeScaleApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timeScaleApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScaleApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeScaleApplicationEditLogs -> Updates an existing timeScaleApplicationEditLog.
     */
    @RequestMapping(value = "/timeScaleApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationEditLog> updateTimeScaleApplicationEditLog(@RequestBody TimeScaleApplicationEditLog timeScaleApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update TimeScaleApplicationEditLog : {}", timeScaleApplicationEditLog);
        if (timeScaleApplicationEditLog.getId() == null) {
            return createTimeScaleApplicationEditLog(timeScaleApplicationEditLog);
        }
        TimeScaleApplicationEditLog result = timeScaleApplicationEditLogRepository.save(timeScaleApplicationEditLog);
        timeScaleApplicationEditLogSearchRepository.save(timeScaleApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplicationEditLog", timeScaleApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeScaleApplicationEditLogs -> get all the timeScaleApplicationEditLogs.
     */
    @RequestMapping(value = "/timeScaleApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeScaleApplicationEditLog>> getAllTimeScaleApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<TimeScaleApplicationEditLog> page = timeScaleApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timeScaleApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timeScaleApplicationEditLogs/:id -> get the "id" timeScaleApplicationEditLog.
     */
    @RequestMapping(value = "/timeScaleApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationEditLog> getTimeScaleApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get TimeScaleApplicationEditLog : {}", id);
        return Optional.ofNullable(timeScaleApplicationEditLogRepository.findOne(id))
            .map(timeScaleApplicationEditLog -> new ResponseEntity<>(
                timeScaleApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeScaleApplicationEditLogs/:id -> delete the "id" timeScaleApplicationEditLog.
     */
    @RequestMapping(value = "/timeScaleApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeScaleApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete TimeScaleApplicationEditLog : {}", id);
        timeScaleApplicationEditLogRepository.delete(id);
        timeScaleApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeScaleApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/timeScaleApplicationEditLogs/:query -> search for the timeScaleApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/timeScaleApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeScaleApplicationEditLog> searchTimeScaleApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(timeScaleApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
