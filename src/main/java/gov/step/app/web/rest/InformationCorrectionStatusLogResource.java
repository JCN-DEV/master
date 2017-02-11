package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InformationCorrectionStatusLog;
import gov.step.app.repository.InformationCorrectionStatusLogRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.InformationCorrectionStatusLogSearchRepository;
import gov.step.app.security.SecurityUtils;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InformationCorrectionStatusLog.
 */
@RestController
@RequestMapping("/api")
public class InformationCorrectionStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(InformationCorrectionStatusLogResource.class);

    @Inject
    private InformationCorrectionStatusLogRepository informationCorrectionStatusLogRepository;

    @Inject
    private InformationCorrectionStatusLogSearchRepository informationCorrectionStatusLogSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /informationCorrectionStatusLogs -> Create a new informationCorrectionStatusLog.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionStatusLog> createInformationCorrectionStatusLog(@RequestBody InformationCorrectionStatusLog informationCorrectionStatusLog) throws URISyntaxException {
        log.debug("REST request to save InformationCorrectionStatusLog : {}", informationCorrectionStatusLog);
        if (informationCorrectionStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new informationCorrectionStatusLog cannot already have an ID").body(null);
        }
        informationCorrectionStatusLog.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        informationCorrectionStatusLog.setCreateDate(LocalDate.now());
        InformationCorrectionStatusLog result = informationCorrectionStatusLogRepository.save(informationCorrectionStatusLog);
        informationCorrectionStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/informationCorrectionStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("informationCorrectionStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /informationCorrectionStatusLogs -> Updates an existing informationCorrectionStatusLog.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionStatusLog> updateInformationCorrectionStatusLog(@RequestBody InformationCorrectionStatusLog informationCorrectionStatusLog) throws URISyntaxException {
        log.debug("REST request to update InformationCorrectionStatusLog : {}", informationCorrectionStatusLog);
        if (informationCorrectionStatusLog.getId() == null) {
            return createInformationCorrectionStatusLog(informationCorrectionStatusLog);
        }
        informationCorrectionStatusLog.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        informationCorrectionStatusLog.setUpdateDate(LocalDate.now());
        InformationCorrectionStatusLog result = informationCorrectionStatusLogRepository.save(informationCorrectionStatusLog);
        informationCorrectionStatusLogSearchRepository.save(informationCorrectionStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("informationCorrectionStatusLog", informationCorrectionStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /informationCorrectionStatusLogs -> get all the informationCorrectionStatusLogs.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InformationCorrectionStatusLog>> getAllInformationCorrectionStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<InformationCorrectionStatusLog> page = informationCorrectionStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/informationCorrectionStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /informationCorrectionStatusLogs/:id -> get the "id" informationCorrectionStatusLog.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionStatusLog> getInformationCorrectionStatusLog(@PathVariable Long id) {
        log.debug("REST request to get InformationCorrectionStatusLog : {}", id);
        return Optional.ofNullable(informationCorrectionStatusLogRepository.findOne(id))
            .map(informationCorrectionStatusLog -> new ResponseEntity<>(
                informationCorrectionStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /informationCorrectionStatusLogs/:id -> delete the "id" informationCorrectionStatusLog.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInformationCorrectionStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete InformationCorrectionStatusLog : {}", id);
        informationCorrectionStatusLogRepository.delete(id);
        informationCorrectionStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("informationCorrectionStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/informationCorrectionStatusLogs/:query -> search for the informationCorrectionStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/informationCorrectionStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InformationCorrectionStatusLog> searchInformationCorrectionStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(informationCorrectionStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/informationCorrectionStatusLogs/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InformationCorrectionStatusLog>> getMpoApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<InformationCorrectionStatusLog> page = informationCorrectionStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/informationCorrectionStatusLogs/instEmployee/");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
