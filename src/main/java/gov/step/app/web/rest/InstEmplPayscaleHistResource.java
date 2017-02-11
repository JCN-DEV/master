package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplPayscaleHist;
import gov.step.app.repository.InstEmplPayscaleHistRepository;
import gov.step.app.repository.search.InstEmplPayscaleHistSearchRepository;
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
 * REST controller for managing InstEmplPayscaleHist.
 */
@RestController
@RequestMapping("/api")
public class InstEmplPayscaleHistResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplPayscaleHistResource.class);

    @Inject
    private InstEmplPayscaleHistRepository instEmplPayscaleHistRepository;

    @Inject
    private InstEmplPayscaleHistSearchRepository instEmplPayscaleHistSearchRepository;

    /**
     * POST  /instEmplPayscaleHists -> Create a new instEmplPayscaleHist.
     */
    @RequestMapping(value = "/instEmplPayscaleHists",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplPayscaleHist> createInstEmplPayscaleHist(@RequestBody InstEmplPayscaleHist instEmplPayscaleHist) throws URISyntaxException {
        log.debug("REST request to save InstEmplPayscaleHist : {}", instEmplPayscaleHist);
        if (instEmplPayscaleHist.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplPayscaleHist cannot already have an ID").body(null);
        }
        InstEmplPayscaleHist result = instEmplPayscaleHistRepository.save(instEmplPayscaleHist);
        instEmplPayscaleHistSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplPayscaleHists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplPayscaleHist", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplPayscaleHists -> Updates an existing instEmplPayscaleHist.
     */
    @RequestMapping(value = "/instEmplPayscaleHists",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplPayscaleHist> updateInstEmplPayscaleHist(@RequestBody InstEmplPayscaleHist instEmplPayscaleHist) throws URISyntaxException {
        log.debug("REST request to update InstEmplPayscaleHist : {}", instEmplPayscaleHist);
        if (instEmplPayscaleHist.getId() == null) {
            return createInstEmplPayscaleHist(instEmplPayscaleHist);
        }
        InstEmplPayscaleHist result = instEmplPayscaleHistRepository.save(instEmplPayscaleHist);
        instEmplPayscaleHistSearchRepository.save(instEmplPayscaleHist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplPayscaleHist", instEmplPayscaleHist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplPayscaleHists -> get all the instEmplPayscaleHists.
     */
    @RequestMapping(value = "/instEmplPayscaleHists",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplPayscaleHist>> getAllInstEmplPayscaleHists(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplPayscaleHist> page = instEmplPayscaleHistRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplPayscaleHists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplPayscaleHists/:id -> get the "id" instEmplPayscaleHist.
     */
    @RequestMapping(value = "/instEmplPayscaleHists/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplPayscaleHist> getInstEmplPayscaleHist(@PathVariable Long id) {
        log.debug("REST request to get InstEmplPayscaleHist : {}", id);
        return Optional.ofNullable(instEmplPayscaleHistRepository.findOne(id))
            .map(instEmplPayscaleHist -> new ResponseEntity<>(
                instEmplPayscaleHist,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmplPayscaleHists/:id -> delete the "id" instEmplPayscaleHist.
     */
    @RequestMapping(value = "/instEmplPayscaleHists/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplPayscaleHist(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplPayscaleHist : {}", id);
        instEmplPayscaleHistRepository.delete(id);
        instEmplPayscaleHistSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplPayscaleHist", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplPayscaleHists/:query -> search for the instEmplPayscaleHist corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplPayscaleHists/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplPayscaleHist> searchInstEmplPayscaleHists(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplPayscaleHistSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
