package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplWithhelHist;
import gov.step.app.repository.InstEmplWithhelHistRepository;
import gov.step.app.repository.search.InstEmplWithhelHistSearchRepository;
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
 * REST controller for managing InstEmplWithhelHist.
 */
@RestController
@RequestMapping("/api")
public class InstEmplWithhelHistResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplWithhelHistResource.class);

    @Inject
    private InstEmplWithhelHistRepository instEmplWithhelHistRepository;

    @Inject
    private InstEmplWithhelHistSearchRepository instEmplWithhelHistSearchRepository;

    /**
     * POST  /instEmplWithhelHists -> Create a new instEmplWithhelHist.
     */
    @RequestMapping(value = "/instEmplWithhelHists",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplWithhelHist> createInstEmplWithhelHist(@Valid @RequestBody InstEmplWithhelHist instEmplWithhelHist) throws URISyntaxException {
        log.debug("REST request to save InstEmplWithhelHist : {}", instEmplWithhelHist);
        if (instEmplWithhelHist.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplWithhelHist cannot already have an ID").body(null);
        }
        InstEmplWithhelHist result = instEmplWithhelHistRepository.save(instEmplWithhelHist);
        instEmplWithhelHistSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplWithhelHists/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplWithhelHist", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplWithhelHists -> Updates an existing instEmplWithhelHist.
     */
    @RequestMapping(value = "/instEmplWithhelHists",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplWithhelHist> updateInstEmplWithhelHist(@Valid @RequestBody InstEmplWithhelHist instEmplWithhelHist) throws URISyntaxException {
        log.debug("REST request to update InstEmplWithhelHist : {}", instEmplWithhelHist);
        if (instEmplWithhelHist.getId() == null) {
            return createInstEmplWithhelHist(instEmplWithhelHist);
        }
        InstEmplWithhelHist result = instEmplWithhelHistRepository.save(instEmplWithhelHist);
        instEmplWithhelHistSearchRepository.save(instEmplWithhelHist);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplWithhelHist", instEmplWithhelHist.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplWithhelHists -> get all the instEmplWithhelHists.
     */
    @RequestMapping(value = "/instEmplWithhelHists",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplWithhelHist>> getAllInstEmplWithhelHists(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplWithhelHist> page = instEmplWithhelHistRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplWithhelHists");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplWithhelHists/:id -> get the "id" instEmplWithhelHist.
     */
    @RequestMapping(value = "/instEmplWithhelHists/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplWithhelHist> getInstEmplWithhelHist(@PathVariable Long id) {
        log.debug("REST request to get InstEmplWithhelHist : {}", id);
        return Optional.ofNullable(instEmplWithhelHistRepository.findOne(id))
            .map(instEmplWithhelHist -> new ResponseEntity<>(
                instEmplWithhelHist,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmplWithhelHists/:id -> delete the "id" instEmplWithhelHist.
     */
    @RequestMapping(value = "/instEmplWithhelHists/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplWithhelHist(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplWithhelHist : {}", id);
        instEmplWithhelHistRepository.delete(id);
        instEmplWithhelHistSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplWithhelHist", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplWithhelHists/:query -> search for the instEmplWithhelHist corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplWithhelHists/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplWithhelHist> searchInstEmplWithhelHists(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplWithhelHistSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
