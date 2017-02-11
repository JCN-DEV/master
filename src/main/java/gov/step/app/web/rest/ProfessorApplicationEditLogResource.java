package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.ProfessorApplicationEditLog;
import gov.step.app.repository.ProfessorApplicationEditLogRepository;
import gov.step.app.repository.search.ProfessorApplicationEditLogSearchRepository;
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
 * REST controller for managing ProfessorApplicationEditLog.
 */
@RestController
@RequestMapping("/api")
public class ProfessorApplicationEditLogResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorApplicationEditLogResource.class);

    @Inject
    private ProfessorApplicationEditLogRepository professorApplicationEditLogRepository;

    @Inject
    private ProfessorApplicationEditLogSearchRepository professorApplicationEditLogSearchRepository;

    /**
     * POST  /professorApplicationEditLogs -> Create a new professorApplicationEditLog.
     */
    @RequestMapping(value = "/professorApplicationEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationEditLog> createProfessorApplicationEditLog(@RequestBody ProfessorApplicationEditLog professorApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to save ProfessorApplicationEditLog : {}", professorApplicationEditLog);
        if (professorApplicationEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new professorApplicationEditLog cannot already have an ID").body(null);
        }
        ProfessorApplicationEditLog result = professorApplicationEditLogRepository.save(professorApplicationEditLog);
        professorApplicationEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/professorApplicationEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professorApplicationEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professorApplicationEditLogs -> Updates an existing professorApplicationEditLog.
     */
    @RequestMapping(value = "/professorApplicationEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationEditLog> updateProfessorApplicationEditLog(@RequestBody ProfessorApplicationEditLog professorApplicationEditLog) throws URISyntaxException {
        log.debug("REST request to update ProfessorApplicationEditLog : {}", professorApplicationEditLog);
        if (professorApplicationEditLog.getId() == null) {
            return createProfessorApplicationEditLog(professorApplicationEditLog);
        }
        ProfessorApplicationEditLog result = professorApplicationEditLogRepository.save(professorApplicationEditLog);
        professorApplicationEditLogSearchRepository.save(professorApplicationEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professorApplicationEditLog", professorApplicationEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professorApplicationEditLogs -> get all the professorApplicationEditLogs.
     */
    @RequestMapping(value = "/professorApplicationEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessorApplicationEditLog>> getAllProfessorApplicationEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<ProfessorApplicationEditLog> page = professorApplicationEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professorApplicationEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professorApplicationEditLogs/:id -> get the "id" professorApplicationEditLog.
     */
    @RequestMapping(value = "/professorApplicationEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationEditLog> getProfessorApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to get ProfessorApplicationEditLog : {}", id);
        return Optional.ofNullable(professorApplicationEditLogRepository.findOne(id))
            .map(professorApplicationEditLog -> new ResponseEntity<>(
                professorApplicationEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professorApplicationEditLogs/:id -> delete the "id" professorApplicationEditLog.
     */
    @RequestMapping(value = "/professorApplicationEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfessorApplicationEditLog(@PathVariable Long id) {
        log.debug("REST request to delete ProfessorApplicationEditLog : {}", id);
        professorApplicationEditLogRepository.delete(id);
        professorApplicationEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professorApplicationEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/professorApplicationEditLogs/:query -> search for the professorApplicationEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/professorApplicationEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProfessorApplicationEditLog> searchProfessorApplicationEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(professorApplicationEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
