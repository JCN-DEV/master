package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlSalaryGenerationLog;
import gov.step.app.repository.payroll.PrlSalaryGenerationLogRepository;
import gov.step.app.repository.search.payroll.PrlSalaryGenerationLogSearchRepository;
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
 * REST controller for managing PrlSalaryGenerationLog.
 */
@RestController
@RequestMapping("/api")
public class PrlSalaryGenerationLogResource {

    private final Logger log = LoggerFactory.getLogger(PrlSalaryGenerationLogResource.class);

    @Inject
    private PrlSalaryGenerationLogRepository prlSalaryGenerationLogRepository;

    @Inject
    private PrlSalaryGenerationLogSearchRepository prlSalaryGenerationLogSearchRepository;

    /**
     * POST  /prlSalaryGenerationLogs -> Create a new prlSalaryGenerationLog.
     */
    @RequestMapping(value = "/prlSalaryGenerationLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryGenerationLog> createPrlSalaryGenerationLog(@RequestBody PrlSalaryGenerationLog prlSalaryGenerationLog) throws URISyntaxException {
        log.debug("REST request to save PrlSalaryGenerationLog : {}", prlSalaryGenerationLog);
        if (prlSalaryGenerationLog.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlSalaryGenerationLog", "idexists", "A new prlSalaryGenerationLog cannot already have an ID")).body(null);
        }
        PrlSalaryGenerationLog result = prlSalaryGenerationLogRepository.save(prlSalaryGenerationLog);
        prlSalaryGenerationLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlSalaryGenerationLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlSalaryGenerationLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlSalaryGenerationLogs -> Updates an existing prlSalaryGenerationLog.
     */
    @RequestMapping(value = "/prlSalaryGenerationLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryGenerationLog> updatePrlSalaryGenerationLog(@RequestBody PrlSalaryGenerationLog prlSalaryGenerationLog) throws URISyntaxException {
        log.debug("REST request to update PrlSalaryGenerationLog : {}", prlSalaryGenerationLog);
        if (prlSalaryGenerationLog.getId() == null) {
            return createPrlSalaryGenerationLog(prlSalaryGenerationLog);
        }
        PrlSalaryGenerationLog result = prlSalaryGenerationLogRepository.save(prlSalaryGenerationLog);
        prlSalaryGenerationLogSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlSalaryGenerationLog", prlSalaryGenerationLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlSalaryGenerationLogs -> get all the prlSalaryGenerationLogs.
     */
    @RequestMapping(value = "/prlSalaryGenerationLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlSalaryGenerationLog>> getAllPrlSalaryGenerationLogs(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlSalaryGenerationLogs");
        Page<PrlSalaryGenerationLog> page = prlSalaryGenerationLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlSalaryGenerationLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlSalaryGenerationLogs/:id -> get the "id" prlSalaryGenerationLog.
     */
    @RequestMapping(value = "/prlSalaryGenerationLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryGenerationLog> getPrlSalaryGenerationLog(@PathVariable Long id) {
        log.debug("REST request to get PrlSalaryGenerationLog : {}", id);
        PrlSalaryGenerationLog prlSalaryGenerationLog = prlSalaryGenerationLogRepository.findOne(id);
        return Optional.ofNullable(prlSalaryGenerationLog)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlSalaryGenerationLogs/:id -> delete the "id" prlSalaryGenerationLog.
     */
    @RequestMapping(value = "/prlSalaryGenerationLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlSalaryGenerationLog(@PathVariable Long id) {
        log.debug("REST request to delete PrlSalaryGenerationLog : {}", id);
        prlSalaryGenerationLogRepository.delete(id);
        prlSalaryGenerationLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlSalaryGenerationLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlSalaryGenerationLogs/:query -> search for the prlSalaryGenerationLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlSalaryGenerationLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlSalaryGenerationLog> searchPrlSalaryGenerationLogs(@PathVariable String query) {
        log.debug("REST request to search PrlSalaryGenerationLogs for query {}", query);
        return StreamSupport
            .stream(prlSalaryGenerationLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
