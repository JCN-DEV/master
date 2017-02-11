package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracRightsLog;
import gov.step.app.repository.UmracRightsLogRepository;
import gov.step.app.repository.search.UmracRightsLogSearchRepository;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing UmracRightsLog.
 */
@RestController
@RequestMapping("/api")
public class UmracRightsLogResource {

    private final Logger log = LoggerFactory.getLogger(UmracRightsLogResource.class);

    @Inject
    private UmracRightsLogRepository umracRightsLogRepository;

    @Inject
    private UmracRightsLogSearchRepository umracRightsLogSearchRepository;

    /**
     * POST  /umracRightsLogs -> Create a new umracRightsLog.
     */
    @RequestMapping(value = "/umracRightsLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsLog> createUmracRightsLog(@RequestBody UmracRightsLog umracRightsLog) throws URISyntaxException {
        log.debug("REST request to save UmracRightsLog : {}", umracRightsLog);
        if (umracRightsLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracRightsLog cannot already have an ID").body(null);
        }
        umracRightsLog.setChangeBy(Long.parseLong("1"));
        TransactionIdResource tir = new TransactionIdResource();
        umracRightsLog.setRightId(tir.getGeneratedid("RHLOG"));
        DateResource dr = new DateResource();
        umracRightsLog.setChangeDate(dr.getDateAsLocalDate());
        UmracRightsLog result = umracRightsLogRepository.save(umracRightsLog);
        umracRightsLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracRightsLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracRightsLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracRightsLogs -> Updates an existing umracRightsLog.
     */
    @RequestMapping(value = "/umracRightsLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsLog> updateUmracRightsLog(@RequestBody UmracRightsLog umracRightsLog) throws URISyntaxException {
        log.debug("REST request to update UmracRightsLog : {}", umracRightsLog);
        if (umracRightsLog.getId() == null) {
            return createUmracRightsLog(umracRightsLog);
        }
        umracRightsLog.setUpdatedBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracRightsLog.setUpdatedTime(dr.getDateAsLocalDate());
        UmracRightsLog result = umracRightsLogRepository.save(umracRightsLog);
        umracRightsLogSearchRepository.save(umracRightsLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracRightsLog", umracRightsLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracRightsLogs -> get all the umracRightsLogs.
     */
    @RequestMapping(value = "/umracRightsLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracRightsLog>> getAllUmracRightsLogs(Pageable pageable)
        throws URISyntaxException {
        Page<UmracRightsLog> page = umracRightsLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracRightsLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracRightsLogs/:id -> get the "id" umracRightsLog.
     */
    @RequestMapping(value = "/umracRightsLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsLog> getUmracRightsLog(@PathVariable Long id) {
        log.debug("REST request to get UmracRightsLog : {}", id);
        return Optional.ofNullable(umracRightsLogRepository.findOne(id))
            .map(umracRightsLog -> new ResponseEntity<>(
                umracRightsLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracRightsLogs/:id -> delete the "id" umracRightsLog.
     */
    @RequestMapping(value = "/umracRightsLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracRightsLog(@PathVariable Long id) {
        log.debug("REST request to delete UmracRightsLog : {}", id);
        umracRightsLogRepository.delete(id);
        umracRightsLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracRightsLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracRightsLogs/:query -> search for the umracRightsLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracRightsLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracRightsLog> searchUmracRightsLogs(@PathVariable String query) {
        return StreamSupport
            .stream(umracRightsLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
