package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpProfMemberInfoLog;
import gov.step.app.repository.HrEmpProfMemberInfoLogRepository;
import gov.step.app.repository.search.HrEmpProfMemberInfoLogSearchRepository;
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
 * REST controller for managing HrEmpProfMemberInfoLog.
 */
@RestController
@RequestMapping("/api")
public class HrEmpProfMemberInfoLogResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpProfMemberInfoLogResource.class);
        
    @Inject
    private HrEmpProfMemberInfoLogRepository hrEmpProfMemberInfoLogRepository;
    
    @Inject
    private HrEmpProfMemberInfoLogSearchRepository hrEmpProfMemberInfoLogSearchRepository;
    
    /**
     * POST  /hrEmpProfMemberInfoLogs -> Create a new hrEmpProfMemberInfoLog.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfoLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfoLog> createHrEmpProfMemberInfoLog(@Valid @RequestBody HrEmpProfMemberInfoLog hrEmpProfMemberInfoLog) throws URISyntaxException {
        log.debug("REST request to save HrEmpProfMemberInfoLog : {}", hrEmpProfMemberInfoLog);
        if (hrEmpProfMemberInfoLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpProfMemberInfoLog", "idexists", "A new hrEmpProfMemberInfoLog cannot already have an ID")).body(null);
        }
        HrEmpProfMemberInfoLog result = hrEmpProfMemberInfoLogRepository.save(hrEmpProfMemberInfoLog);
        hrEmpProfMemberInfoLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpProfMemberInfoLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpProfMemberInfoLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpProfMemberInfoLogs -> Updates an existing hrEmpProfMemberInfoLog.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfoLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfoLog> updateHrEmpProfMemberInfoLog(@Valid @RequestBody HrEmpProfMemberInfoLog hrEmpProfMemberInfoLog) throws URISyntaxException {
        log.debug("REST request to update HrEmpProfMemberInfoLog : {}", hrEmpProfMemberInfoLog);
        if (hrEmpProfMemberInfoLog.getId() == null) {
            return createHrEmpProfMemberInfoLog(hrEmpProfMemberInfoLog);
        }
        HrEmpProfMemberInfoLog result = hrEmpProfMemberInfoLogRepository.save(hrEmpProfMemberInfoLog);
        hrEmpProfMemberInfoLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpProfMemberInfoLog", hrEmpProfMemberInfoLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpProfMemberInfoLogs -> get all the hrEmpProfMemberInfoLogs.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfoLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpProfMemberInfoLog>> getAllHrEmpProfMemberInfoLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpProfMemberInfoLogs");
        Page<HrEmpProfMemberInfoLog> page = hrEmpProfMemberInfoLogRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpProfMemberInfoLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpProfMemberInfoLogs/:id -> get the "id" hrEmpProfMemberInfoLog.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfoLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpProfMemberInfoLog> getHrEmpProfMemberInfoLog(@PathVariable Long id) {
        log.debug("REST request to get HrEmpProfMemberInfoLog : {}", id);
        HrEmpProfMemberInfoLog hrEmpProfMemberInfoLog = hrEmpProfMemberInfoLogRepository.findOne(id);
        return Optional.ofNullable(hrEmpProfMemberInfoLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpProfMemberInfoLogs/:id -> delete the "id" hrEmpProfMemberInfoLog.
     */
    @RequestMapping(value = "/hrEmpProfMemberInfoLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpProfMemberInfoLog(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpProfMemberInfoLog : {}", id);
        hrEmpProfMemberInfoLogRepository.delete(id);
        hrEmpProfMemberInfoLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpProfMemberInfoLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpProfMemberInfoLogs/:query -> search for the hrEmpProfMemberInfoLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpProfMemberInfoLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpProfMemberInfoLog> searchHrEmpProfMemberInfoLogs(@PathVariable String query) {
        log.debug("REST request to search HrEmpProfMemberInfoLogs for query {}", query);
        return StreamSupport
            .stream(hrEmpProfMemberInfoLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
