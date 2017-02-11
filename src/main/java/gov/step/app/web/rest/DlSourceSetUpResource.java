package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContTypeSet;
import gov.step.app.domain.DlSourceSetUp;
import gov.step.app.repository.DlSourceSetUpRepository;
import gov.step.app.repository.search.DlSourceSetUpSearchRepository;
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
 * REST controller for managing DlSourceSetUp.
 */
@RestController
@RequestMapping("/api")
public class DlSourceSetUpResource {

    private final Logger log = LoggerFactory.getLogger(DlSourceSetUpResource.class);

    @Inject
    private DlSourceSetUpRepository dlSourceSetUpRepository;

    @Inject
    private DlSourceSetUpSearchRepository dlSourceSetUpSearchRepository;

    /**
     * POST  /dlSourceSetUps -> Create a new dlSourceSetUp.
     */
    @RequestMapping(value = "/dlSourceSetUps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlSourceSetUp> createDlSourceSetUp(@RequestBody DlSourceSetUp dlSourceSetUp) throws URISyntaxException {
        log.debug("REST request to save DlSourceSetUp : {}", dlSourceSetUp);
        if (dlSourceSetUp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlSourceSetUp cannot already have an ID").body(null);
        }
        DlSourceSetUp result = dlSourceSetUpRepository.save(dlSourceSetUp);
        dlSourceSetUpSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlSourceSetUps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlSourceSetUp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlSourceSetUps -> Updates an existing dlSourceSetUp.
     */
    @RequestMapping(value = "/dlSourceSetUps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlSourceSetUp> updateDlSourceSetUp(@RequestBody DlSourceSetUp dlSourceSetUp) throws URISyntaxException {
        log.debug("REST request to update DlSourceSetUp : {}", dlSourceSetUp);
        if (dlSourceSetUp.getId() == null) {
            return createDlSourceSetUp(dlSourceSetUp);
        }
        DlSourceSetUp result = dlSourceSetUpRepository.save(dlSourceSetUp);
        dlSourceSetUpSearchRepository.save(dlSourceSetUp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlSourceSetUp", dlSourceSetUp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlSourceSetUps -> get all the dlSourceSetUps.
     */
    @RequestMapping(value = "/dlSourceSetUps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlSourceSetUp>> getAllDlSourceSetUps(Pageable pageable)
        throws URISyntaxException {
        Page<DlSourceSetUp> page = dlSourceSetUpRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlSourceSetUps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    @RequestMapping(value = "/dlSourceSetup/allSourceSetup",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlSourceSetUp>> activeSourceTypes(Pageable pageable)
        throws Exception {
        Page<DlSourceSetUp> page = dlSourceSetUpRepository.activeSourceTypes(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlSourceSetup/allSourceSetup");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlSourceSetUps/:id -> get the "id" dlSourceSetUp.
     */
    @RequestMapping(value = "/dlSourceSetUps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlSourceSetUp> getDlSourceSetUp(@PathVariable Long id) {
        log.debug("REST request to get DlSourceSetUp : {}", id);
        return Optional.ofNullable(dlSourceSetUpRepository.findOne(id))
            .map(dlSourceSetUp -> new ResponseEntity<>(
                dlSourceSetUp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlSourceSetUps/:id -> delete the "id" dlSourceSetUp.
     */
    @RequestMapping(value = "/dlSourceSetUps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlSourceSetUp(@PathVariable Long id) {
        log.debug("REST request to delete DlSourceSetUp : {}", id);
        dlSourceSetUpRepository.delete(id);
        dlSourceSetUpSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlSourceSetUp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlSourceSetUps/:query -> search for the dlSourceSetUp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlSourceSetUps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlSourceSetUp> searchDlSourceSetUps(@PathVariable String query) {
        return StreamSupport
            .stream(dlSourceSetUpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
