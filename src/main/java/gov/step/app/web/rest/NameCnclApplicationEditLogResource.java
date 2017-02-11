package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.NameCnclApplicationEditLog;
import gov.step.app.repository.NameCnclApplicationEditLogRepository;
import gov.step.app.repository.search.NameCnclApplicationEditLogSearchRepository;
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
 * REST controller for managing NameCnclApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class NameCnclApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(NameCnclApplicationEditLogResource.class);

    @Inject
    private NameCnclApplicationEditLogRepository nameCnclApplicationEditLogRepository;

    @Inject
    private NameCnclApplicationEditLogSearchRepository nameCnclApplicationEditLogSearchRepository;

    /**
     * POST  /nameCnclApplicationEditLogs -> Create a new nameCnclApplicationEditLog.
     */
    @RequestMapping(value = "/nameCnclApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationEditLog> createNameCnclApplicationEditLog(@RequestBody NameCnclApplicationEditLog nameCnclApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save NameCnclApplicationEditLog : {}", nameCnclApplicationEditLog);
        if (nameCnclApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new nameCnclApplicationEditLog cannot already have an ID").body(null);
        }
        NameCnclApplicationEditLog result = nameCnclApplicationEditLogRepository.save(nameCnclApplicationEditLog);
        nameCnclApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/nameCnclApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nameCnclApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nameCnclApplicationEditLogs -> Updates an existing nameCnclApplicationEditLog.
     */
    @RequestMapping(value = "/nameCnclApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationEditLog> updateNameCnclApplicationEditLog(@RequestBody NameCnclApplicationEditLog nameCnclApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update NameCnclApplicationEditLog : {}", nameCnclApplicationEditLog);
        if (nameCnclApplicationEditLog.getId() == null) {
            return createNameCnclApplicationEditLog(nameCnclApplicationEditLog);
        }
        NameCnclApplicationEditLog result = nameCnclApplicationEditLogRepository.save(nameCnclApplicationEditLog);
        nameCnclApplicationEditLogSearchRepository.save(nameCnclApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nameCnclApplicationEditLog", nameCnclApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nameCnclApplicationEditLogs -> get all the nameCnclApplicationEditLogs.
     */
    @RequestMapping(value = "/nameCnclApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NameCnclApplicationEditLog>> getAllNameCnclApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<NameCnclApplicationEditLog> page = nameCnclApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nameCnclApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nameCnclApplicationEditLogs/:id -> get the "id" nameCnclApplicationEditLog.
     */
    @RequestMapping(value = "/nameCnclApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationEditLog> getNameCnclApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get NameCnclApplicationEditLog : {}", id);
        return Optional.ofNullable(nameCnclApplicationEditLogRepository.findOne(id))
            .map(nameCnclApplicationEditLog -> new ResponseEntity<>(
                nameCnclApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nameCnclApplicationEditLogs/:id -> delete the "id" nameCnclApplicationEditLog.
     */
    @RequestMapping(value = "/nameCnclApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNameCnclApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete NameCnclApplicationEditLog : {}", id);
        nameCnclApplicationEditLogRepository.delete(id);
        nameCnclApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nameCnclApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/nameCnclApplicationEditLogs/:query -> search for the nameCnclApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/nameCnclApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NameCnclApplicationEditLog> searchNameCnclApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(nameCnclApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
