package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoApplicationEditLog;
import gov.step.app.repository.MpoApplicationEditLogRepository;
import gov.step.app.repository.search.MpoApplicationEditLogSearchRepository;
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
 * REST controller for managing MpoApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class MpoApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(MpoApplicationEditLogResource.class);

    @Inject
    private MpoApplicationEditLogRepository mpoApplicationEditLogRepository;

    @Inject
    private MpoApplicationEditLogSearchRepository mpoApplicationEditLogSearchRepository;

    /**
     * POST  /mpoApplicationEditLogs -> Create a new mpoApplicationEditLog.
     */
    @RequestMapping(value = "/mpoApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationEditLog> createMpoApplicationEditLog(@RequestBody MpoApplicationEditLog mpoApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save MpoApplicationEditLog : {}", mpoApplicationEditLog);
        if (mpoApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplicationEditLog cannot already have an ID").body(null);
        }
        MpoApplicationEditLog result = mpoApplicationEditLogRepository.save(mpoApplicationEditLog);
        mpoApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoApplicationEditLogs -> Updates an existing mpoApplicationEditLog.
     */
    @RequestMapping(value = "/mpoApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationEditLog> updateMpoApplicationEditLog(@RequestBody MpoApplicationEditLog mpoApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update MpoApplicationEditLog : {}", mpoApplicationEditLog);
        if (mpoApplicationEditLog.getId() == null) {
            return createMpoApplicationEditLog(mpoApplicationEditLog);
        }
        MpoApplicationEditLog result = mpoApplicationEditLogRepository.save(mpoApplicationEditLog);
        mpoApplicationEditLogSearchRepository.save(mpoApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoApplicationEditLog", mpoApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoApplicationEditLogs -> get all the mpoApplicationEditLogs.
     */
    @RequestMapping(value = "/mpoApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplicationEditLog>> getAllMpoApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<MpoApplicationEditLog> page = mpoApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoApplicationEditLogs/:id -> get the "id" mpoApplicationEditLog.
     */
    @RequestMapping(value = "/mpoApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationEditLog> getMpoApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get MpoApplicationEditLog : {}", id);
        return Optional.ofNullable(mpoApplicationEditLogRepository.findOne(id))
            .map(mpoApplicationEditLog -> new ResponseEntity<>(
                mpoApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoApplicationEditLogs/:id -> delete the "id" mpoApplicationEditLog.
     */
    @RequestMapping(value = "/mpoApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete MpoApplicationEditLog : {}", id);
        mpoApplicationEditLogRepository.delete(id);
        mpoApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoApplicationEditLogs/:query -> search for the mpoApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplicationEditLog> searchMpoApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(mpoApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
