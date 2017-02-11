package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.QrcodeGenLog;
import gov.step.app.repository.QrcodeGenLogRepository;
import gov.step.app.repository.search.QrcodeGenLogSearchRepository;
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
 * REST controller for managing QrcodeGenLog.
 */
@RestController
@RequestMapping("/api")
public class QrcodeGenLogResource {

    private final Logger log = LoggerFactory.getLogger(QrcodeGenLogResource.class);

    @Inject
    private QrcodeGenLogRepository qrcodeGenLogRepository;

    @Inject
    private QrcodeGenLogSearchRepository qrcodeGenLogSearchRepository;

    /**
     * POST  /qrcodeGenLogs -> Create a new qrcodeGenLog.
     */
    @RequestMapping(value = "/qrcodeGenLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QrcodeGenLog> createQrcodeGenLog(@RequestBody QrcodeGenLog qrcodeGenLog) throws URISyntaxException {
        log.debug("REST request to save QrcodeGenLog : {}", qrcodeGenLog);
        if (qrcodeGenLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new qrcodeGenLog cannot already have an ID").body(null);
        }
        QrcodeGenLog result = qrcodeGenLogRepository.save(qrcodeGenLog);
        qrcodeGenLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/qrcodeGenLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("qrcodeGenLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /qrcodeGenLogs -> Updates an existing qrcodeGenLog.
     */
    @RequestMapping(value = "/qrcodeGenLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QrcodeGenLog> updateQrcodeGenLog(@RequestBody QrcodeGenLog qrcodeGenLog) throws URISyntaxException {
        log.debug("REST request to update QrcodeGenLog : {}", qrcodeGenLog);
        if (qrcodeGenLog.getId() == null) {
            return createQrcodeGenLog(qrcodeGenLog);
        }
        QrcodeGenLog result = qrcodeGenLogRepository.save(qrcodeGenLog);
        qrcodeGenLogSearchRepository.save(qrcodeGenLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("qrcodeGenLog", qrcodeGenLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /qrcodeGenLogs -> get all the qrcodeGenLogs.
     */
    @RequestMapping(value = "/qrcodeGenLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<QrcodeGenLog>> getAllQrcodeGenLogs(Pageable pageable)
        throws URISyntaxException {
        Page<QrcodeGenLog> page = qrcodeGenLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/qrcodeGenLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /qrcodeGenLogs/:id -> get the "id" qrcodeGenLog.
     */
    @RequestMapping(value = "/qrcodeGenLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<QrcodeGenLog> getQrcodeGenLog(@PathVariable Long id) {
        log.debug("REST request to get QrcodeGenLog : {}", id);
        return Optional.ofNullable(qrcodeGenLogRepository.findOne(id))
            .map(qrcodeGenLog -> new ResponseEntity<>(
                qrcodeGenLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /qrcodeGenLogs/:id -> delete the "id" qrcodeGenLog.
     */
    @RequestMapping(value = "/qrcodeGenLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQrcodeGenLog(@PathVariable Long id) {
        log.debug("REST request to delete QrcodeGenLog : {}", id);
        qrcodeGenLogRepository.delete(id);
        qrcodeGenLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("qrcodeGenLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/qrcodeGenLogs/:query -> search for the qrcodeGenLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/qrcodeGenLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<QrcodeGenLog> searchQrcodeGenLogs(@PathVariable String query) {
        return StreamSupport
            .stream(qrcodeGenLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
