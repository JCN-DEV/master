package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContentSetup;
import gov.step.app.repository.DlContentSetupRepository;
import gov.step.app.repository.search.DlContentSetupSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DlContentSetup.
 */
@RestController
@RequestMapping("/api")
public class DlContentSetupResource {

    private final Logger log = LoggerFactory.getLogger(DlContentSetupResource.class);

    @Inject
    private DlContentSetupRepository dlContentSetupRepository;

    @Inject
    private DlContentSetupSearchRepository dlContentSetupSearchRepository;

    /**
     * POST  /dlContentSetups -> Create a new dlContentSetup.
     */
    @RequestMapping(value = "/dlContentSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentSetup> createDlContentSetup(@Valid @RequestBody DlContentSetup dlContentSetup) throws URISyntaxException {
        log.debug("REST request to save DlContentSetup : {}", dlContentSetup);
        if (dlContentSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContentSetup cannot already have an ID").body(null);
        }
        DlContentSetup result = dlContentSetupRepository.save(dlContentSetup);
        dlContentSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContentSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContentSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlContentSetups -> Updates an existing dlContentSetup.
     */
    @RequestMapping(value = "/dlContentSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentSetup> updateDlContentSetup(@Valid @RequestBody DlContentSetup dlContentSetup) throws URISyntaxException {
        log.debug("REST request to update DlContentSetup : {}", dlContentSetup);
        if (dlContentSetup.getId() == null) {
            return createDlContentSetup(dlContentSetup);
        }
        DlContentSetup result = dlContentSetupRepository.save(dlContentSetup);
        dlContentSetupSearchRepository.save(dlContentSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContentSetup", dlContentSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContentSetups -> get all the dlContentSetups.
     */
    @RequestMapping(value = "/dlContentSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContentSetup>> getAllDlContentSetups(Pageable pageable)
        throws URISyntaxException {
        Page<DlContentSetup> page = dlContentSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContentSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlContentSetups/:id -> get the "id" dlContentSetup.
     */
    @RequestMapping(value = "/dlContentSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentSetup> getDlContentSetup(@PathVariable Long id) {
        log.debug("REST request to get DlContentSetup : {}", id);
        return Optional.ofNullable(dlContentSetupRepository.findOne(id))
            .map(dlContentSetup -> new ResponseEntity<>(
                dlContentSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlContentSetups/:id -> delete the "id" dlContentSetup.
     */
    @RequestMapping(value = "/dlContentSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContentSetup(@PathVariable Long id) {
        log.debug("REST request to delete DlContentSetup : {}", id);
        dlContentSetupRepository.delete(id);
        dlContentSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContentSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContentSetups/:query -> search for the dlContentSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContentSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContentSetup> searchDlContentSetups(@PathVariable String query) {
        return StreamSupport
            .stream(dlContentSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
