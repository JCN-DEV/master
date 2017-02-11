package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.BEdApplicationStatusLog;
import gov.step.app.domain.NameCnclApplicationStatusLog;
import gov.step.app.repository.NameCnclApplicationStatusLogRepository;
import gov.step.app.repository.search.NameCnclApplicationStatusLogSearchRepository;
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
 * REST controller for managing NameCnclApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class NameCnclApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(NameCnclApplicationStatusLogResource.class);

    @Inject
    private NameCnclApplicationStatusLogRepository nameCnclApplicationStatusLogRepository;

    @Inject
    private NameCnclApplicationStatusLogSearchRepository nameCnclApplicationStatusLogSearchRepository;

    /**
     * POST  /nameCnclApplicationStatusLogs -> Create a new nameCnclApplicationStatusLog.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationStatusLog> createNameCnclApplicationStatusLog(@RequestBody NameCnclApplicationStatusLog nameCnclApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save NameCnclApplicationStatusLog : {}", nameCnclApplicationStatusLog);
        if (nameCnclApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new nameCnclApplicationStatusLog cannot already have an ID").body(null);
        }
        NameCnclApplicationStatusLog result = nameCnclApplicationStatusLogRepository.save(nameCnclApplicationStatusLog);
        nameCnclApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/nameCnclApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("nameCnclApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /nameCnclApplicationStatusLogs -> Updates an existing nameCnclApplicationStatusLog.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationStatusLog> updateNameCnclApplicationStatusLog(@RequestBody NameCnclApplicationStatusLog nameCnclApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update NameCnclApplicationStatusLog : {}", nameCnclApplicationStatusLog);
        if (nameCnclApplicationStatusLog.getId() == null) {
            return createNameCnclApplicationStatusLog(nameCnclApplicationStatusLog);
        }
        NameCnclApplicationStatusLog result = nameCnclApplicationStatusLogRepository.save(nameCnclApplicationStatusLog);
        nameCnclApplicationStatusLogSearchRepository.save(nameCnclApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("nameCnclApplicationStatusLog", nameCnclApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /nameCnclApplicationStatusLogs -> get all the nameCnclApplicationStatusLogs.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NameCnclApplicationStatusLog>> getAllNameCnclApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<NameCnclApplicationStatusLog> page = nameCnclApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nameCnclApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /nameCnclApplicationStatusLogs/:id -> get the "id" nameCnclApplicationStatusLog.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NameCnclApplicationStatusLog> getNameCnclApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get NameCnclApplicationStatusLog : {}", id);
        return Optional.ofNullable(nameCnclApplicationStatusLogRepository.findOne(id))
            .map(nameCnclApplicationStatusLog -> new ResponseEntity<>(
                nameCnclApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /nameCnclApplicationStatusLogs/:id -> delete the "id" nameCnclApplicationStatusLog.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNameCnclApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete NameCnclApplicationStatusLog : {}", id);
        nameCnclApplicationStatusLogRepository.delete(id);
        nameCnclApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("nameCnclApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/nameCnclApplicationStatusLogs/:query -> search for the nameCnclApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/nameCnclApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NameCnclApplicationStatusLog> searchNameCnclApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(nameCnclApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/nameCnclApplicationStatusLogs/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<NameCnclApplicationStatusLog>> getNameCnclApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<NameCnclApplicationStatusLog> page = nameCnclApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/nameCnclApplicationStatusLogs/instEmployee/"+code);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
