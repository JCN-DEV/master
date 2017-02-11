package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TimeScaleApplication;
import gov.step.app.domain.TimeScaleApplicationStatusLog;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.TimeScaleApplicationRepository;
import gov.step.app.repository.TimeScaleApplicationStatusLogRepository;
import gov.step.app.repository.search.TimeScaleApplicationStatusLogSearchRepository;
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
 * REST controller for managing TimeScaleApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class TimeScaleApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(TimeScaleApplicationStatusLogResource.class);

    @Inject
    private TimeScaleApplicationStatusLogRepository timeScaleApplicationStatusLogRepository;

    @Inject
    private TimeScaleApplicationStatusLogSearchRepository timeScaleApplicationStatusLogSearchRepository;

    @Inject
    private TimeScaleApplicationRepository timeScaleApplicationRepository;
    /**
     * POST  /timeScaleApplicationStatusLogs -> Create a new timeScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/timeScaleApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationStatusLog> createTimeScaleApplicationStatusLog(@RequestBody TimeScaleApplicationStatusLog timeScaleApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save TimeScaleApplicationStatusLog : {}", timeScaleApplicationStatusLog);
        if (timeScaleApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new timeScaleApplicationStatusLog cannot already have an ID").body(null);
        }
        if(timeScaleApplicationStatusLog.getStatus()== MpoApplicationStatusType.DECLINED.getCode()){
            TimeScaleApplication timeScaleApplication= timeScaleApplicationStatusLog.getTimeScaleApplicationId();
            timeScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(timeScaleApplication.getStatus()).getDeclineRemarks());
            timeScaleApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            timeScaleApplicationRepository.save(timeScaleApplication);
        }
        TimeScaleApplicationStatusLog result = timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);
        timeScaleApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timeScaleApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScaleApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /timeScaleApplicationStatusLogs -> Updates an existing timeScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/timeScaleApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationStatusLog> updateTimeScaleApplicationStatusLog(@RequestBody TimeScaleApplicationStatusLog timeScaleApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update TimeScaleApplicationStatusLog : {}", timeScaleApplicationStatusLog);
        if (timeScaleApplicationStatusLog.getId() == null) {
            return createTimeScaleApplicationStatusLog(timeScaleApplicationStatusLog);
        }
        TimeScaleApplicationStatusLog result = timeScaleApplicationStatusLogRepository.save(timeScaleApplicationStatusLog);
        timeScaleApplicationStatusLogSearchRepository.save(timeScaleApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("timeScaleApplicationStatusLog", timeScaleApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /timeScaleApplicationStatusLogs -> get all the timeScaleApplicationStatusLogs.
     */
    @RequestMapping(value = "/timeScaleApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeScaleApplicationStatusLog>> getAllTimeScaleApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<TimeScaleApplicationStatusLog> page = timeScaleApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/timeScaleApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /timeScaleApplicationStatusLogs/:id -> get the "id" timeScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/timeScaleApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TimeScaleApplicationStatusLog> getTimeScaleApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get TimeScaleApplicationStatusLog : {}", id);
        return Optional.ofNullable(timeScaleApplicationStatusLogRepository.findOne(id))
            .map(timeScaleApplicationStatusLog -> new ResponseEntity<>(
                timeScaleApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /timeScaleApplicationStatusLogs/:id -> delete the "id" timeScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/timeScaleApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTimeScaleApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete TimeScaleApplicationStatusLog : {}", id);
        timeScaleApplicationStatusLogRepository.delete(id);
        timeScaleApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("timeScaleApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/timeScaleApplicationStatusLogs/:query -> search for the timeScaleApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/timeScaleApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TimeScaleApplicationStatusLog> searchTimeScaleApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(timeScaleApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/timescaleApplicationStatusLogs/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TimeScaleApplicationStatusLog>> getTimescaleApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<TimeScaleApplicationStatusLog> page = timeScaleApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
