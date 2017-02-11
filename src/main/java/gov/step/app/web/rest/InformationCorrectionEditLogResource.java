package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InformationCorrectionEditLog;
import gov.step.app.repository.InformationCorrectionEditLogRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.InformationCorrectionEditLogSearchRepository;
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
 * REST controller for managing InformationCorrectionEditLog.
 */
@RestController
@RequestMapping("/api")
public class InformationCorrectionEditLogResource {

    private final Logger log = LoggerFactory.getLogger(InformationCorrectionEditLogResource.class);

    @Inject
    private InformationCorrectionEditLogRepository informationCorrectionEditLogRepository;

    @Inject
    private InformationCorrectionEditLogSearchRepository informationCorrectionEditLogSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /informationCorrectionEditLogs -> Create a new informationCorrectionEditLog.
     */
    @RequestMapping(value = "/informationCorrectionEditLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionEditLog> createInformationCorrectionEditLog(@RequestBody InformationCorrectionEditLog informationCorrectionEditLog) throws URISyntaxException {
        log.debug("REST request to save InformationCorrectionEditLog : {}", informationCorrectionEditLog);
        if (informationCorrectionEditLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new informationCorrectionEditLog cannot already have an ID").body(null);
        }
        informationCorrectionEditLog.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        informationCorrectionEditLog.setCreateDate(LocalDate.now());
        InformationCorrectionEditLog result = informationCorrectionEditLogRepository.save(informationCorrectionEditLog);
        informationCorrectionEditLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/informationCorrectionEditLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("informationCorrectionEditLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /informationCorrectionEditLogs -> Updates an existing informationCorrectionEditLog.
     */
    @RequestMapping(value = "/informationCorrectionEditLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionEditLog> updateInformationCorrectionEditLog(@RequestBody InformationCorrectionEditLog informationCorrectionEditLog) throws URISyntaxException {
        log.debug("REST request to update InformationCorrectionEditLog : {}", informationCorrectionEditLog);
        if (informationCorrectionEditLog.getId() == null) {
            return createInformationCorrectionEditLog(informationCorrectionEditLog);
        }
        informationCorrectionEditLog.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        informationCorrectionEditLog.setUpdateDate(LocalDate.now());
        InformationCorrectionEditLog result = informationCorrectionEditLogRepository.save(informationCorrectionEditLog);
        informationCorrectionEditLogSearchRepository.save(informationCorrectionEditLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("informationCorrectionEditLog", informationCorrectionEditLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /informationCorrectionEditLogs -> get all the informationCorrectionEditLogs.
     */
    @RequestMapping(value = "/informationCorrectionEditLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InformationCorrectionEditLog>> getAllInformationCorrectionEditLogs(Pageable pageable)
        throws URISyntaxException {
        Page<InformationCorrectionEditLog> page = informationCorrectionEditLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/informationCorrectionEditLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /informationCorrectionEditLogs/:id -> get the "id" informationCorrectionEditLog.
     */
    @RequestMapping(value = "/informationCorrectionEditLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InformationCorrectionEditLog> getInformationCorrectionEditLog(@PathVariable Long id) {
        log.debug("REST request to get InformationCorrectionEditLog : {}", id);
        return Optional.ofNullable(informationCorrectionEditLogRepository.findOne(id))
            .map(informationCorrectionEditLog -> new ResponseEntity<>(
                informationCorrectionEditLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /informationCorrectionEditLogs/:id -> delete the "id" informationCorrectionEditLog.
     */
    @RequestMapping(value = "/informationCorrectionEditLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInformationCorrectionEditLog(@PathVariable Long id) {
        log.debug("REST request to delete InformationCorrectionEditLog : {}", id);
        informationCorrectionEditLogRepository.delete(id);
        informationCorrectionEditLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("informationCorrectionEditLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/informationCorrectionEditLogs/:query -> search for the informationCorrectionEditLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/informationCorrectionEditLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InformationCorrectionEditLog> searchInformationCorrectionEditLogs(@PathVariable String query) {
        return StreamSupport
            .stream(informationCorrectionEditLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
