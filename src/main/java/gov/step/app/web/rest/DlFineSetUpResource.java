package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlFineSetUp;
import gov.step.app.repository.DlFineSetUpRepository;
import gov.step.app.repository.search.DlFineSetUpSearchRepository;
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
 * REST controller for managing DlFineSetUp.
 */
@RestController
@RequestMapping("/api")
public class DlFineSetUpResource {

    private final Logger log = LoggerFactory.getLogger(DlFineSetUpResource.class);

    @Inject
    private DlFineSetUpRepository dlFineSetUpRepository;

    @Inject
    private DlFineSetUpSearchRepository dlFineSetUpSearchRepository;

    /**
     * POST  /dlFineSetUps -> Create a new dlFineSetUp.
     */
    @RequestMapping(value = "/dlFineSetUps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFineSetUp> createDlFineSetUp(@RequestBody DlFineSetUp dlFineSetUp) throws URISyntaxException {
        log.debug("REST request to save DlFineSetUp : {}", dlFineSetUp);
        if (dlFineSetUp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlFineSetUp cannot already have an ID").body(null);
        }
        DlFineSetUp result = dlFineSetUpRepository.save(dlFineSetUp);
        dlFineSetUpSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlFineSetUps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlFineSetUp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlFineSetUps -> Updates an existing dlFineSetUp.
     */
    @RequestMapping(value = "/dlFineSetUps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFineSetUp> updateDlFineSetUp(@RequestBody DlFineSetUp dlFineSetUp) throws URISyntaxException {
        log.debug("REST request to update DlFineSetUp : {}", dlFineSetUp);
        if (dlFineSetUp.getId() == null) {
            return createDlFineSetUp(dlFineSetUp);
        }
        DlFineSetUp result = dlFineSetUpRepository.save(dlFineSetUp);
        dlFineSetUpSearchRepository.save(dlFineSetUp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlFineSetUp", dlFineSetUp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlFineSetUps -> get all the dlFineSetUps.
     */
    @RequestMapping(value = "/dlFineSetUps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlFineSetUp>> getAllDlFineSetUps(Pageable pageable)
        throws URISyntaxException {
        Page<DlFineSetUp> page = dlFineSetUpRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlFineSetUps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlFineSetUps/:id -> get the "id" dlFineSetUp.
     */
    @RequestMapping(value = "/dlFineSetUps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFineSetUp> getDlFineSetUp(@PathVariable Long id) {
        log.debug("REST request to get DlFineSetUp : {}", id);
        return Optional.ofNullable(dlFineSetUpRepository.findOne(id))
            .map(dlFineSetUp -> new ResponseEntity<>(
                dlFineSetUp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlFineSetUps/:id -> delete the "id" dlFineSetUp.
     */
    @RequestMapping(value = "/dlFineSetUps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlFineSetUp(@PathVariable Long id) {
        log.debug("REST request to delete DlFineSetUp : {}", id);
        dlFineSetUpRepository.delete(id);
        dlFineSetUpSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlFineSetUp", id.toString())).build();
    }




//    @RequestMapping(value = "/dlFineSetUp/findFineInfoBySCatId/{id}",
//        method = RequestMethod.GET,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<DlFineSetUp> findFineInfoBySCatId(@PathVariable Long id) throws Exception {
//        List<DlFineSetUp> dlFineSetUp = dlFineSetUpRepository.findFineInfoBySCatId(id);
//        return dlFineSetUp;
//    }


    @RequestMapping(value = "/dlFineSetUp/findFineInfoBySCatId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlFineSetUp> findFineInfoBySCatId(@PathVariable Long id) {

        return Optional.ofNullable(dlFineSetUpRepository.findFineInfoBySCatId(id))
            .map(dlFineSetUp -> new ResponseEntity<>(
                dlFineSetUp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * SEARCH  /_search/dlFineSetUps/:query -> search for the dlFineSetUp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlFineSetUps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlFineSetUp> searchDlFineSetUps(@PathVariable String query) {
        return StreamSupport
            .stream(dlFineSetUpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
