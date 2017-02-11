package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAddressInfoLog;
import gov.step.app.repository.HrEmpAddressInfoLogRepository;
import gov.step.app.repository.search.HrEmpAddressInfoLogSearchRepository;
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
 * REST controller for managing HrEmpAddressInfoLog.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAddressInfoLogResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAddressInfoLogResource.class);
        
    @Inject
    private HrEmpAddressInfoLogRepository hrEmpAddressInfoLogRepository;
    
    @Inject
    private HrEmpAddressInfoLogSearchRepository hrEmpAddressInfoLogSearchRepository;
    
    /**
     * POST  /hrEmpAddressInfoLogs -> Create a new hrEmpAddressInfoLog.
     */
    @RequestMapping(value = "/hrEmpAddressInfoLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfoLog> createHrEmpAddressInfoLog(@Valid @RequestBody HrEmpAddressInfoLog hrEmpAddressInfoLog) throws URISyntaxException {
        log.debug("REST request to save HrEmpAddressInfoLog : {}", hrEmpAddressInfoLog);
        if (hrEmpAddressInfoLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAddressInfoLog", "idexists", "A new hrEmpAddressInfoLog cannot already have an ID")).body(null);
        }
        HrEmpAddressInfoLog result = hrEmpAddressInfoLogRepository.save(hrEmpAddressInfoLog);
        hrEmpAddressInfoLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAddressInfoLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAddressInfoLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAddressInfoLogs -> Updates an existing hrEmpAddressInfoLog.
     */
    @RequestMapping(value = "/hrEmpAddressInfoLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfoLog> updateHrEmpAddressInfoLog(@Valid @RequestBody HrEmpAddressInfoLog hrEmpAddressInfoLog) throws URISyntaxException {
        log.debug("REST request to update HrEmpAddressInfoLog : {}", hrEmpAddressInfoLog);
        if (hrEmpAddressInfoLog.getId() == null) {
            return createHrEmpAddressInfoLog(hrEmpAddressInfoLog);
        }
        HrEmpAddressInfoLog result = hrEmpAddressInfoLogRepository.save(hrEmpAddressInfoLog);
        hrEmpAddressInfoLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAddressInfoLog", hrEmpAddressInfoLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAddressInfoLogs -> get all the hrEmpAddressInfoLogs.
     */
    @RequestMapping(value = "/hrEmpAddressInfoLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAddressInfoLog>> getAllHrEmpAddressInfoLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAddressInfoLogs");
        Page<HrEmpAddressInfoLog> page = hrEmpAddressInfoLogRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAddressInfoLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAddressInfoLogs/:id -> get the "id" hrEmpAddressInfoLog.
     */
    @RequestMapping(value = "/hrEmpAddressInfoLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAddressInfoLog> getHrEmpAddressInfoLog(@PathVariable Long id) {
        log.debug("REST request to get HrEmpAddressInfoLog : {}", id);
        HrEmpAddressInfoLog hrEmpAddressInfoLog = hrEmpAddressInfoLogRepository.findOne(id);
        return Optional.ofNullable(hrEmpAddressInfoLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAddressInfoLogs/:id -> delete the "id" hrEmpAddressInfoLog.
     */
    @RequestMapping(value = "/hrEmpAddressInfoLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAddressInfoLog(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpAddressInfoLog : {}", id);
        hrEmpAddressInfoLogRepository.delete(id);
        hrEmpAddressInfoLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAddressInfoLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAddressInfoLogs/:query -> search for the hrEmpAddressInfoLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAddressInfoLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAddressInfoLog> searchHrEmpAddressInfoLogs(@PathVariable String query) {
        log.debug("REST request to search HrEmpAddressInfoLogs for query {}", query);
        return StreamSupport
            .stream(hrEmpAddressInfoLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
