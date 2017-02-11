package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.ProfessorApplication;
import gov.step.app.domain.ProfessorApplicationStatusLog;
import gov.step.app.domain.TimeScaleApplication;
import gov.step.app.domain.TimeScaleApplicationStatusLog;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.ProfessorApplicationRepository;
import gov.step.app.repository.ProfessorApplicationStatusLogRepository;
import gov.step.app.repository.search.ProfessorApplicationStatusLogSearchRepository;
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
 * REST controller for managing ProfessorApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class ProfessorApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(ProfessorApplicationStatusLogResource.class);

    @Inject
    private ProfessorApplicationStatusLogRepository professorApplicationStatusLogRepository;

    @Inject
    private ProfessorApplicationStatusLogSearchRepository professorApplicationStatusLogSearchRepository;

    @Inject
    private ProfessorApplicationRepository professorApplicationRepository;

    /**
     * POST  /professorApplicationStatusLogs -> Create a new professorApplicationStatusLog.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationStatusLog> createProfessorApplicationStatusLog(@RequestBody ProfessorApplicationStatusLog professorApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save ProfessorApplicationStatusLog : {}", professorApplicationStatusLog);
        if (professorApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new professorApplicationStatusLog cannot already have an ID").body(null);
        }
        /*ProfessorApplicationStatusLog result = professorApplicationStatusLogRepository.save(professorApplicationStatusLog);
        professorApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/professorApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professorApplicationStatusLog", result.getId().toString()))
            .body(result);
    }*/
        if(professorApplicationStatusLog.getStatus()== MpoApplicationStatusType.DECLINED.getCode()){
            ProfessorApplication professorApplication= professorApplicationStatusLog.getProfessorApplication();
            professorApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(professorApplication.getStatus()).getDeclineRemarks());
            professorApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            professorApplicationRepository.save(professorApplication);
        }
        ProfessorApplicationStatusLog result = professorApplicationStatusLogRepository.save(professorApplicationStatusLog);
        professorApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timeScaleApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScaleApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professorApplicationStatusLogs -> Updates an existing professorApplicationStatusLog.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationStatusLog> updateProfessorApplicationStatusLog(@RequestBody ProfessorApplicationStatusLog professorApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update ProfessorApplicationStatusLog : {}", professorApplicationStatusLog);
        if (professorApplicationStatusLog.getId() == null) {
            return createProfessorApplicationStatusLog(professorApplicationStatusLog);
        }
        ProfessorApplicationStatusLog result = professorApplicationStatusLogRepository.save(professorApplicationStatusLog);
        professorApplicationStatusLogSearchRepository.save(professorApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professorApplicationStatusLog", professorApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professorApplicationStatusLogs -> get all the professorApplicationStatusLogs.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessorApplicationStatusLog>> getAllProfessorApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<ProfessorApplicationStatusLog> page = professorApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professorApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professorApplicationStatusLogs/:id -> get the "id" professorApplicationStatusLog.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessorApplicationStatusLog> getProfessorApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get ProfessorApplicationStatusLog : {}", id);
        return Optional.ofNullable(professorApplicationStatusLogRepository.findOne(id))
            .map(professorApplicationStatusLog -> new ResponseEntity<>(
                professorApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professorApplicationStatusLogs/:id -> delete the "id" professorApplicationStatusLog.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfessorApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete ProfessorApplicationStatusLog : {}", id);
        professorApplicationStatusLogRepository.delete(id);
        professorApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professorApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/professorApplicationStatusLogs/:query -> search for the professorApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/professorApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProfessorApplicationStatusLog> searchProfessorApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(professorApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/professorApplicationStatusLogs/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessorApplicationStatusLog>> getProfessorApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<ProfessorApplicationStatusLog> page = professorApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
