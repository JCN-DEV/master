package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteLand;
import gov.step.app.repository.InstituteLandRepository;
import gov.step.app.repository.search.InstituteLandSearchRepository;
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
 * REST controller for managing InstituteLand.
 */
@RestController
@RequestMapping("/api")
public class InstituteLandResource {

    private final Logger log = LoggerFactory.getLogger(InstituteLandResource.class);

    @Inject
    private InstituteLandRepository instituteLandRepository;

    @Inject
    private InstituteLandSearchRepository instituteLandSearchRepository;

    /**
     * POST  /instituteLands -> Create a new instituteLand.
     */
    @RequestMapping(value = "/instituteLands",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLand> createInstituteLand(@Valid @RequestBody InstituteLand instituteLand) throws URISyntaxException {
        log.debug("REST request to save InstituteLand : {}", instituteLand);
        if (instituteLand.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteLand cannot already have an ID").body(null);
        }
        InstituteLand result = instituteLandRepository.save(instituteLand);
        instituteLandSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteLands/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteLand", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteLands -> Updates an existing instituteLand.
     */
    @RequestMapping(value = "/instituteLands",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLand> updateInstituteLand(@Valid @RequestBody InstituteLand instituteLand) throws URISyntaxException {
        log.debug("REST request to update InstituteLand : {}", instituteLand);
        if (instituteLand.getId() == null) {
            return createInstituteLand(instituteLand);
        }
        InstituteLand result = instituteLandRepository.save(instituteLand);
        instituteLandSearchRepository.save(instituteLand);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteLand", instituteLand.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteLands -> get all the instituteLands.
     */
    @RequestMapping(value = "/instituteLands",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteLand>> getAllInstituteLands(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteLand> page = instituteLandRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteLands");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteLands/:id -> get the "id" instituteLand.
     */
    @RequestMapping(value = "/instituteLands/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLand> getInstituteLand(@PathVariable Long id) {
        log.debug("REST request to get InstituteLand : {}", id);
        return Optional.ofNullable(instituteLandRepository.findOne(id))
            .map(instituteLand -> new ResponseEntity<>(
                instituteLand,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteLands/:id -> delete the "id" instituteLand.
     */
    @RequestMapping(value = "/instituteLands/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteLand(@PathVariable Long id) {
        log.debug("REST request to delete InstituteLand : {}", id);
        instituteLandRepository.delete(id);
        instituteLandSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteLand", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteLands/:query -> search for the instituteLand corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteLands/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteLand> searchInstituteLands(@PathVariable String query) {
        return StreamSupport
            .stream(instituteLandSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
