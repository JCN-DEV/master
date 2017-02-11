package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BEdApplicationStatusLog;
import gov.step.app.domain.TimeScaleApplicationStatusLog;
import gov.step.app.repository.BEdApplicationStatusLogRepository;
import gov.step.app.repository.search.BEdApplicationStatusLogSearchRepository;
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
 * REST controller for managing BEdApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class BEdApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(BEdApplicationStatusLogResource.class);

    @Inject
    private BEdApplicationStatusLogRepository bEdApplicationStatusLogRepository;

    @Inject
    private BEdApplicationStatusLogSearchRepository bEdApplicationStatusLogSearchRepository;

    /**
     * POST  /bEdApplicationStatusLogs -> Create a new bEdApplicationStatusLog.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationStatusLog> createBEdApplicationStatusLog(@RequestBody BEdApplicationStatusLog bEdApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save BEdApplicationStatusLog : {}", bEdApplicationStatusLog);
        if (bEdApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new bEdApplicationStatusLog cannot already have an ID").body(null);
        }
        BEdApplicationStatusLog result = bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);
        bEdApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/bEdApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("bEdApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /bEdApplicationStatusLogs -> Updates an existing bEdApplicationStatusLog.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationStatusLog> updateBEdApplicationStatusLog(@RequestBody BEdApplicationStatusLog bEdApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update BEdApplicationStatusLog : {}", bEdApplicationStatusLog);
        if (bEdApplicationStatusLog.getId() == null) {
            return createBEdApplicationStatusLog(bEdApplicationStatusLog);
        }
        BEdApplicationStatusLog result = bEdApplicationStatusLogRepository.save(bEdApplicationStatusLog);
        bEdApplicationStatusLogSearchRepository.save(bEdApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("bEdApplicationStatusLog", bEdApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /bEdApplicationStatusLogs -> get all the bEdApplicationStatusLogs.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BEdApplicationStatusLog>> getAllBEdApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<BEdApplicationStatusLog> page = bEdApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bEdApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /bEdApplicationStatusLogs/:id -> get the "id" bEdApplicationStatusLog.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<BEdApplicationStatusLog> getBEdApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get BEdApplicationStatusLog : {}", id);
        return Optional.ofNullable(bEdApplicationStatusLogRepository.findOne(id))
            .map(bEdApplicationStatusLog -> new ResponseEntity<>(
                bEdApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /bEdApplicationStatusLogs/:id -> delete the "id" bEdApplicationStatusLog.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteBEdApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete BEdApplicationStatusLog : {}", id);
        bEdApplicationStatusLogRepository.delete(id);
        bEdApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("bEdApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/bEdApplicationStatusLogs/:query -> search for the bEdApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/bEdApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<BEdApplicationStatusLog> searchBEdApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(bEdApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/bEdApplicationStatusLogs/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<BEdApplicationStatusLog>> getTimescaleApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<BEdApplicationStatusLog> page = bEdApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/bEdApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
