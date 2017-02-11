package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpChildrenInfoLog;
import gov.step.app.repository.HrEmpChildrenInfoLogRepository;
import gov.step.app.repository.search.HrEmpChildrenInfoLogSearchRepository;
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
 * REST controller for managing HrEmpChildrenInfoLog.
 */
@RestController
@RequestMapping("/api")
public class HrEmpChildrenInfoLogResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpChildrenInfoLogResource.class);

    @Inject
    private HrEmpChildrenInfoLogRepository hrEmpChildrenInfoLogRepository;

    @Inject
    private HrEmpChildrenInfoLogSearchRepository hrEmpChildrenInfoLogSearchRepository;

    /**
     * POST  /hrEmpChildrenInfoLogs -> Create a new hrEmpChildrenInfoLog.
     */
    @RequestMapping(value = "/hrEmpChildrenInfoLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfoLog> createHrEmpChildrenInfoLog(@Valid @RequestBody HrEmpChildrenInfoLog hrEmpChildrenInfoLog) throws URISyntaxException {
        log.debug("REST request to save HrEmpChildrenInfoLog : {}", hrEmpChildrenInfoLog);
        if (hrEmpChildrenInfoLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpChildrenInfoLog", "idexists", "A new hrEmpChildrenInfoLog cannot already have an ID")).body(null);
        }
        HrEmpChildrenInfoLog result = hrEmpChildrenInfoLogRepository.save(hrEmpChildrenInfoLog);
        hrEmpChildrenInfoLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpChildrenInfoLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpChildrenInfoLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpChildrenInfoLogs -> Updates an existing hrEmpChildrenInfoLog.
     */
    @RequestMapping(value = "/hrEmpChildrenInfoLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfoLog> updateHrEmpChildrenInfoLog(@Valid @RequestBody HrEmpChildrenInfoLog hrEmpChildrenInfoLog) throws URISyntaxException {
        log.debug("REST request to update HrEmpChildrenInfoLog : {}", hrEmpChildrenInfoLog);
        if (hrEmpChildrenInfoLog.getId() == null) {
            return createHrEmpChildrenInfoLog(hrEmpChildrenInfoLog);
        }
        HrEmpChildrenInfoLog result = hrEmpChildrenInfoLogRepository.save(hrEmpChildrenInfoLog);
        hrEmpChildrenInfoLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpChildrenInfoLog", hrEmpChildrenInfoLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpChildrenInfoLogs -> get all the hrEmpChildrenInfoLogs.
     */
    @RequestMapping(value = "/hrEmpChildrenInfoLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpChildrenInfoLog>> getAllHrEmpChildrenInfoLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpChildrenInfoLogs");
        Page<HrEmpChildrenInfoLog> page = hrEmpChildrenInfoLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpChildrenInfoLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpChildrenInfoLogs/:id -> get the "id" hrEmpChildrenInfoLog.
     */
    @RequestMapping(value = "/hrEmpChildrenInfoLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpChildrenInfoLog> getHrEmpChildrenInfoLog(@PathVariable Long id) {
        log.debug("REST request to get HrEmpChildrenInfoLog : {}", id);
        HrEmpChildrenInfoLog hrEmpChildrenInfoLog = hrEmpChildrenInfoLogRepository.findOne(id);
        return Optional.ofNullable(hrEmpChildrenInfoLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpChildrenInfoLogs/:id -> delete the "id" hrEmpChildrenInfoLog.
     */
    @RequestMapping(value = "/hrEmpChildrenInfoLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpChildrenInfoLog(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpChildrenInfoLog : {}", id);
        hrEmpChildrenInfoLogRepository.delete(id);
        hrEmpChildrenInfoLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpChildrenInfoLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpChildrenInfoLogs/:query -> search for the hrEmpChildrenInfoLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpChildrenInfoLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpChildrenInfoLog> searchHrEmpChildrenInfoLogs(@PathVariable String query) {
        log.debug("REST request to search HrEmpChildrenInfoLogs for query {}", query);
        return StreamSupport
            .stream(hrEmpChildrenInfoLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
