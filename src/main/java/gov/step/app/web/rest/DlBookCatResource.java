package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookCat;
import gov.step.app.repository.DlBookCatRepository;
import gov.step.app.repository.search.DlBookCatSearchRepository;
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
 * REST controller for managing DlBookCat.
 */
@RestController
@RequestMapping("/api")
public class DlBookCatResource {

    private final Logger log = LoggerFactory.getLogger(DlBookCatResource.class);

    @Inject
    private DlBookCatRepository dlBookCatRepository;

    @Inject
    private DlBookCatSearchRepository dlBookCatSearchRepository;

    /**
     * POST  /dlBookCats -> Create a new dlBookCat.
     */
    @RequestMapping(value = "/dlBookCats",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookCat> createDlBookCat(@Valid @RequestBody DlBookCat dlBookCat) throws URISyntaxException {
        log.debug("REST request to save DlBookCat : {}", dlBookCat);
        if (dlBookCat.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookCat cannot already have an ID").body(null);
        }
        DlBookCat result = dlBookCatRepository.save(dlBookCat);
        dlBookCatSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookCats/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookCat", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookCats -> Updates an existing dlBookCat.
     */
    @RequestMapping(value = "/dlBookCats",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookCat> updateDlBookCat(@Valid @RequestBody DlBookCat dlBookCat) throws URISyntaxException {
        log.debug("REST request to update DlBookCat : {}", dlBookCat);
        if (dlBookCat.getId() == null) {
            return createDlBookCat(dlBookCat);
        }
        DlBookCat result = dlBookCatRepository.save(dlBookCat);
        dlBookCatSearchRepository.save(dlBookCat);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookCat", dlBookCat.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookCats -> get all the dlBookCats.
     */
    @RequestMapping(value = "/dlBookCats",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookCat>> getAllDlBookCats(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookCat> page = dlBookCatRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookCats");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlBookCats/:id -> get the "id" dlBookCat.
     */
    @RequestMapping(value = "/dlBookCats/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookCat> getDlBookCat(@PathVariable Long id) {
        log.debug("REST request to get DlBookCat : {}", id);
        return Optional.ofNullable(dlBookCatRepository.findOne(id))
            .map(dlBookCat -> new ResponseEntity<>(
                dlBookCat,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlBookCats/:id -> delete the "id" dlBookCat.
     */
    @RequestMapping(value = "/dlBookCats/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookCat(@PathVariable Long id) {
        log.debug("REST request to delete DlBookCat : {}", id);
        dlBookCatRepository.delete(id);
        dlBookCatSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookCat", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookCats/:query -> search for the dlBookCat corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookCats/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookCat> searchDlBookCats(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookCatSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
