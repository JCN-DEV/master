package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmOnDutyLeaveApp;
import gov.step.app.repository.AlmOnDutyLeaveAppRepository;
import gov.step.app.repository.search.AlmOnDutyLeaveAppSearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmOnDutyLeaveApp.
 */
@RestController
@RequestMapping("/api")
public class AlmOnDutyLeaveAppResource {

    private final Logger log = LoggerFactory.getLogger(AlmOnDutyLeaveAppResource.class);

    @Inject
    private AlmOnDutyLeaveAppRepository almOnDutyLeaveAppRepository;

    @Inject
    private AlmOnDutyLeaveAppSearchRepository almOnDutyLeaveAppSearchRepository;

    /**
     * POST  /almOnDutyLeaveApps -> Create a new almOnDutyLeaveApp.
     */
    @RequestMapping(value = "/almOnDutyLeaveApps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmOnDutyLeaveApp> createAlmOnDutyLeaveApp(@Valid @RequestBody AlmOnDutyLeaveApp almOnDutyLeaveApp) throws URISyntaxException {
        log.debug("REST request to save AlmOnDutyLeaveApp : {}", almOnDutyLeaveApp);
        if (almOnDutyLeaveApp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almOnDutyLeaveApp cannot already have an ID").body(null);
        }
        AlmOnDutyLeaveApp result = almOnDutyLeaveAppRepository.save(almOnDutyLeaveApp);
        almOnDutyLeaveAppSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almOnDutyLeaveApps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almOnDutyLeaveApp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almOnDutyLeaveApps -> Updates an existing almOnDutyLeaveApp.
     */
    @RequestMapping(value = "/almOnDutyLeaveApps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmOnDutyLeaveApp> updateAlmOnDutyLeaveApp(@Valid @RequestBody AlmOnDutyLeaveApp almOnDutyLeaveApp) throws URISyntaxException {
        log.debug("REST request to update AlmOnDutyLeaveApp : {}", almOnDutyLeaveApp);
        if (almOnDutyLeaveApp.getId() == null) {
            return createAlmOnDutyLeaveApp(almOnDutyLeaveApp);
        }
        AlmOnDutyLeaveApp result = almOnDutyLeaveAppRepository.save(almOnDutyLeaveApp);
        almOnDutyLeaveAppSearchRepository.save(almOnDutyLeaveApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almOnDutyLeaveApp", almOnDutyLeaveApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almOnDutyLeaveApps -> get all the almOnDutyLeaveApps.
     */
    @RequestMapping(value = "/almOnDutyLeaveApps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmOnDutyLeaveApp>> getAllAlmOnDutyLeaveApps(Pageable pageable)
        throws URISyntaxException {
        Page<AlmOnDutyLeaveApp> page = almOnDutyLeaveAppRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almOnDutyLeaveApps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almOnDutyLeaveApps/:id -> get the "id" almOnDutyLeaveApp.
     */
    @RequestMapping(value = "/almOnDutyLeaveApps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmOnDutyLeaveApp> getAlmOnDutyLeaveApp(@PathVariable Long id) {
        log.debug("REST request to get AlmOnDutyLeaveApp : {}", id);
        return Optional.ofNullable(almOnDutyLeaveAppRepository.findOne(id))
            .map(almOnDutyLeaveApp -> new ResponseEntity<>(
                almOnDutyLeaveApp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almOnDutyLeaveApps/:id -> delete the "id" almOnDutyLeaveApp.
     */
    @RequestMapping(value = "/almOnDutyLeaveApps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmOnDutyLeaveApp(@PathVariable Long id) {
        log.debug("REST request to delete AlmOnDutyLeaveApp : {}", id);
        almOnDutyLeaveAppRepository.delete(id);
        almOnDutyLeaveAppSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almOnDutyLeaveApp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almOnDutyLeaveApps/:query -> search for the almOnDutyLeaveApp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almOnDutyLeaveApps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmOnDutyLeaveApp> searchAlmOnDutyLeaveApps(@PathVariable String query) {
        return StreamSupport
            .stream(almOnDutyLeaveAppSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
