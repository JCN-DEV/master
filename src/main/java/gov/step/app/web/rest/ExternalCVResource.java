package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.ExternalCV;
import gov.step.app.repository.ExternalCVRepository;
import gov.step.app.repository.search.ExternalCVSearchRepository;
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
 * REST controller for managing ExternalCV.
 */
@RestController
@RequestMapping("/api")
public class ExternalCVResource {

    private final Logger log = LoggerFactory.getLogger(ExternalCVResource.class);

    @Inject
    private ExternalCVRepository externalCVRepository;

    @Inject
    private ExternalCVSearchRepository externalCVSearchRepository;

    /**
     * POST  /externalCVs -> Create a new externalCV.
     */
    @RequestMapping(value = "/externalCVs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExternalCV> createExternalCV(@Valid @RequestBody ExternalCV externalCV) throws URISyntaxException {
        log.debug("REST request to save ExternalCV : {}", externalCV);
        if (externalCV.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new externalCV cannot already have an ID").body(null);
        }
        ExternalCV result = externalCVRepository.save(externalCV);
        externalCVSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/externalCVs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("externalCV", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /externalCVs -> Updates an existing externalCV.
     */
    @RequestMapping(value = "/externalCVs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExternalCV> updateExternalCV(@Valid @RequestBody ExternalCV externalCV) throws URISyntaxException {
        log.debug("REST request to update ExternalCV : {}", externalCV);
        if (externalCV.getId() == null) {
            return createExternalCV(externalCV);
        }
        ExternalCV result = externalCVRepository.save(externalCV);
        externalCVSearchRepository.save(externalCV);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("externalCV", externalCV.getId().toString()))
            .body(result);
    }

    /**
     * GET  /externalCVs -> get all the externalCVs.
     */
    @RequestMapping(value = "/externalCVs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ExternalCV>> getAllExternalCVs(Pageable pageable)
        throws URISyntaxException {
        Page<ExternalCV> page = externalCVRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/externalCVs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /externalCVs/:id -> get the "id" externalCV.
     */
    @RequestMapping(value = "/externalCVs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ExternalCV> getExternalCV(@PathVariable Long id) {
        log.debug("REST request to get ExternalCV : {}", id);
        return Optional.ofNullable(externalCVRepository.findOne(id))
            .map(externalCV -> new ResponseEntity<>(
                externalCV,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /externalCVs/:id -> delete the "id" externalCV.
     */
    @RequestMapping(value = "/externalCVs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExternalCV(@PathVariable Long id) {
        log.debug("REST request to delete ExternalCV : {}", id);
        externalCVRepository.delete(id);
        externalCVSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("externalCV", id.toString())).build();
    }

    /**
     * SEARCH  /_search/externalCVs/:query -> search for the externalCV corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/externalCVs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ExternalCV> searchExternalCVs(@PathVariable String query) {
        return StreamSupport
            .stream(externalCVSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
