package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLand;
import gov.step.app.repository.InstLandRepository;
import gov.step.app.repository.search.InstLandSearchRepository;
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
 * REST controller for managing InstLand.
 */
@RestController
@RequestMapping("/api")
public class InstLandResource {

    private final Logger log = LoggerFactory.getLogger(InstLandResource.class);

    @Inject
    private InstLandRepository instLandRepository;

    @Inject
    private InstLandSearchRepository instLandSearchRepository;

    /**
     * POST  /instLands -> Create a new instLand.
     */
    @RequestMapping(value = "/instLands",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLand> createInstLand(@Valid @RequestBody InstLand instLand) throws URISyntaxException {
        log.debug("REST request to save InstLand : {}", instLand);
        if (instLand.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLand cannot already have an ID").body(null);
        }
        InstLand result = instLandRepository.save(instLand);
        instLandSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLand", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLands -> Updates an existing instLand.
     */
    @RequestMapping(value = "/instLands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLand> updateInstLand(@Valid @RequestBody InstLand instLand) throws URISyntaxException {
        log.debug("REST request to update InstLand : {}", instLand);
        if (instLand.getId() == null) {
            return createInstLand(instLand);
        }
        InstLand result = instLandRepository.save(instLand);
        instLandSearchRepository.save(instLand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLand", instLand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLands -> get all the instLands.
     */
    @RequestMapping(value = "/instLands",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLand>> getAllInstLands(Pageable pageable)
        throws URISyntaxException {
        Page<InstLand> page = instLandRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLands/:id -> get the "id" instLand.
     */
    @RequestMapping(value = "/instLands/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLand> getInstLand(@PathVariable Long id) {
        log.debug("REST request to get InstLand : {}", id);
        return Optional.ofNullable(instLandRepository.findOne(id))
            .map(instLand -> new ResponseEntity<>(
                instLand,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instLands/:id -> delete the "id" instLand.
     */
    @RequestMapping(value = "/instLands/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLand(@PathVariable Long id) {
        log.debug("REST request to delete InstLand : {}", id);
        instLandRepository.delete(id);
        instLandSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLand", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLands/:query -> search for the instLand corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLands/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLand> searchInstLands(@PathVariable String query) {
        return StreamSupport
            .stream(instLandSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
