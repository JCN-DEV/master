package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplHist;
import gov.step.app.repository.InstEmplHistRepository;
import gov.step.app.repository.search.InstEmplHistSearchRepository;
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
 * REST controller for managing InstEmplHist.
 */
@RestController
@RequestMapping("/api")
public class InstEmplHistResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplHistResource.class);

    @Inject
    private InstEmplHistRepository instEmplHistRepository;

    @Inject
    private InstEmplHistSearchRepository instEmplHistSearchRepository;

    /**
     * POST  /instEmplHists -> Create a new instEmplHist.
     */
    @RequestMapping(value = "/instEmplHists",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplHist> createInstEmplHist(@RequestBody InstEmplHist instEmplHist) throws URISyntaxException {
        log.debug("REST request to save InstEmplHist : {}", instEmplHist);
        if (instEmplHist.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplHist cannot already have an ID").body(null);
        }
        InstEmplHist result = instEmplHistRepository.save(instEmplHist);
        instEmplHistSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplHists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplHist", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplHists -> Updates an existing instEmplHist.
     */
    @RequestMapping(value = "/instEmplHists",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplHist> updateInstEmplHist(@RequestBody InstEmplHist instEmplHist) throws URISyntaxException {
        log.debug("REST request to update InstEmplHist : {}", instEmplHist);
        if (instEmplHist.getId() == null) {
            return createInstEmplHist(instEmplHist);
        }
        InstEmplHist result = instEmplHistRepository.save(instEmplHist);
        instEmplHistSearchRepository.save(instEmplHist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplHist", instEmplHist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplHists -> get all the instEmplHists.
     */
    @RequestMapping(value = "/instEmplHists",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplHist>> getAllInstEmplHists(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplHist> page = instEmplHistRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplHists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplHists/:id -> get the "id" instEmplHist.
     */
    @RequestMapping(value = "/instEmplHists/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplHist> getInstEmplHist(@PathVariable Long id) {
        log.debug("REST request to get InstEmplHist : {}", id);
        return Optional.ofNullable(instEmplHistRepository.findOne(id))
            .map(instEmplHist -> new ResponseEntity<>(
                instEmplHist,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmplHists/:id -> delete the "id" instEmplHist.
     */
    @RequestMapping(value = "/instEmplHists/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplHist(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplHist : {}", id);
        instEmplHistRepository.delete(id);
        instEmplHistSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplHist", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplHists/:query -> search for the instEmplHist corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplHists/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplHist> searchInstEmplHists(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplHistSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
