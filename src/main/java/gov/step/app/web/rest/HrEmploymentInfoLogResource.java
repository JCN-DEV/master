package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmploymentInfoLog;
import gov.step.app.repository.HrEmploymentInfoLogRepository;
import gov.step.app.repository.search.HrEmploymentInfoLogSearchRepository;
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
 * REST controller for managing HrEmploymentInfoLog.
 */
@RestController
@RequestMapping("/api")
public class HrEmploymentInfoLogResource {

    private final Logger log = LoggerFactory.getLogger(HrEmploymentInfoLogResource.class);
        
    @Inject
    private HrEmploymentInfoLogRepository hrEmploymentInfoLogRepository;
    
    @Inject
    private HrEmploymentInfoLogSearchRepository hrEmploymentInfoLogSearchRepository;
    
    /**
     * POST  /hrEmploymentInfoLogs -> Create a new hrEmploymentInfoLog.
     */
    @RequestMapping(value = "/hrEmploymentInfoLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfoLog> createHrEmploymentInfoLog(@Valid @RequestBody HrEmploymentInfoLog hrEmploymentInfoLog) throws URISyntaxException {
        log.debug("REST request to save HrEmploymentInfoLog : {}", hrEmploymentInfoLog);
        if (hrEmploymentInfoLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmploymentInfoLog", "idexists", "A new hrEmploymentInfoLog cannot already have an ID")).body(null);
        }
        HrEmploymentInfoLog result = hrEmploymentInfoLogRepository.save(hrEmploymentInfoLog);
        hrEmploymentInfoLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmploymentInfoLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmploymentInfoLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmploymentInfoLogs -> Updates an existing hrEmploymentInfoLog.
     */
    @RequestMapping(value = "/hrEmploymentInfoLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfoLog> updateHrEmploymentInfoLog(@Valid @RequestBody HrEmploymentInfoLog hrEmploymentInfoLog) throws URISyntaxException {
        log.debug("REST request to update HrEmploymentInfoLog : {}", hrEmploymentInfoLog);
        if (hrEmploymentInfoLog.getId() == null) {
            return createHrEmploymentInfoLog(hrEmploymentInfoLog);
        }
        HrEmploymentInfoLog result = hrEmploymentInfoLogRepository.save(hrEmploymentInfoLog);
        hrEmploymentInfoLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmploymentInfoLog", hrEmploymentInfoLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmploymentInfoLogs -> get all the hrEmploymentInfoLogs.
     */
    @RequestMapping(value = "/hrEmploymentInfoLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmploymentInfoLog>> getAllHrEmploymentInfoLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmploymentInfoLogs");
        Page<HrEmploymentInfoLog> page = hrEmploymentInfoLogRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmploymentInfoLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmploymentInfoLogs/:id -> get the "id" hrEmploymentInfoLog.
     */
    @RequestMapping(value = "/hrEmploymentInfoLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmploymentInfoLog> getHrEmploymentInfoLog(@PathVariable Long id) {
        log.debug("REST request to get HrEmploymentInfoLog : {}", id);
        HrEmploymentInfoLog hrEmploymentInfoLog = hrEmploymentInfoLogRepository.findOne(id);
        return Optional.ofNullable(hrEmploymentInfoLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmploymentInfoLogs/:id -> delete the "id" hrEmploymentInfoLog.
     */
    @RequestMapping(value = "/hrEmploymentInfoLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmploymentInfoLog(@PathVariable Long id) {
        log.debug("REST request to delete HrEmploymentInfoLog : {}", id);
        hrEmploymentInfoLogRepository.delete(id);
        hrEmploymentInfoLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmploymentInfoLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmploymentInfoLogs/:query -> search for the hrEmploymentInfoLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmploymentInfoLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmploymentInfoLog> searchHrEmploymentInfoLogs(@PathVariable String query) {
        log.debug("REST request to search HrEmploymentInfoLogs for query {}", query);
        return StreamSupport
            .stream(hrEmploymentInfoLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
