package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AuditLogHistory;
import gov.step.app.repository.AuditLogHistoryRepository;
import gov.step.app.repository.search.AuditLogHistorySearchRepository;
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
 * REST controller for managing AuditLogHistory.
 */
@RestController
@RequestMapping("/api")
public class AuditLogHistoryResource {

    private final Logger log = LoggerFactory.getLogger(AuditLogHistoryResource.class);

    @Inject
    private AuditLogHistoryRepository auditLogHistoryRepository;

    @Inject
    private AuditLogHistorySearchRepository auditLogHistorySearchRepository;

    /**
     * POST  /auditLogHistorys -> Create a new auditLogHistory.
     */
    @RequestMapping(value = "/auditLogHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLogHistory> createAuditLogHistory(@RequestBody AuditLogHistory auditLogHistory) throws URISyntaxException {
        log.debug("REST request to save AuditLogHistory : {}", auditLogHistory);
        if (auditLogHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new auditLogHistory cannot already have an ID").body(null);
        }
        AuditLogHistory result = auditLogHistoryRepository.save(auditLogHistory);
        auditLogHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/auditLogHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("auditLogHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /auditLogHistorys -> Updates an existing auditLogHistory.
     */
    @RequestMapping(value = "/auditLogHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLogHistory> updateAuditLogHistory(@RequestBody AuditLogHistory auditLogHistory) throws URISyntaxException {
        log.debug("REST request to update AuditLogHistory : {}", auditLogHistory);
        if (auditLogHistory.getId() == null) {
            return createAuditLogHistory(auditLogHistory);
        }
        AuditLogHistory result = auditLogHistoryRepository.save(auditLogHistory);
        auditLogHistorySearchRepository.save(auditLogHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("auditLogHistory", auditLogHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /auditLogHistorys -> get all the auditLogHistorys.
     */
    @RequestMapping(value = "/auditLogHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AuditLogHistory>> getAllAuditLogHistorys(Pageable pageable)
        throws URISyntaxException {
        Page<AuditLogHistory> page = auditLogHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/auditLogHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /auditLogHistorys/:id -> get the "id" auditLogHistory.
     */
    @RequestMapping(value = "/auditLogHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AuditLogHistory> getAuditLogHistory(@PathVariable Long id) {
        log.debug("REST request to get AuditLogHistory : {}", id);
        return Optional.ofNullable(auditLogHistoryRepository.findOne(id))
            .map(auditLogHistory -> new ResponseEntity<>(
                auditLogHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /auditLogHistorys/:id -> delete the "id" auditLogHistory.
     */
    @RequestMapping(value = "/auditLogHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAuditLogHistory(@PathVariable Long id) {
        log.debug("REST request to delete AuditLogHistory : {}", id);
        auditLogHistoryRepository.delete(id);
        auditLogHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("auditLogHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/auditLogHistorys/:query -> search for the auditLogHistory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/auditLogHistorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AuditLogHistory> searchAuditLogHistorys(@PathVariable String query) {
        return StreamSupport
            .stream(auditLogHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
