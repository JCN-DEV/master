package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.GradeSetup;
import gov.step.app.repository.GradeSetupRepository;
import gov.step.app.repository.search.GradeSetupSearchRepository;
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
 * REST controller for managing GradeSetup.
 */
@RestController
@RequestMapping("/api")
public class GradeSetupResource {

    private final Logger log = LoggerFactory.getLogger(GradeSetupResource.class);

    @Inject
    private GradeSetupRepository gradeSetupRepository;

    @Inject
    private GradeSetupSearchRepository gradeSetupSearchRepository;

    /**
     * POST  /gradeSetups -> Create a new gradeSetup.
     */
    @RequestMapping(value = "/gradeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSetup> createGradeSetup(@Valid @RequestBody GradeSetup gradeSetup) throws URISyntaxException {
        log.debug("REST request to save GradeSetup : {}", gradeSetup);
        if (gradeSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new gradeSetup cannot already have an ID").body(null);
        }
        GradeSetup result = gradeSetupRepository.save(gradeSetup);
        gradeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gradeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gradeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gradeSetups -> Updates an existing gradeSetup.
     */
    @RequestMapping(value = "/gradeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSetup> updateGradeSetup(@Valid @RequestBody GradeSetup gradeSetup) throws URISyntaxException {
        log.debug("REST request to update GradeSetup : {}", gradeSetup);
        if (gradeSetup.getId() == null) {
            return createGradeSetup(gradeSetup);
        }
        GradeSetup result = gradeSetupRepository.save(gradeSetup);
        gradeSetupSearchRepository.save(gradeSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gradeSetup", gradeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gradeSetups -> get all the gradeSetups.
     */
    @RequestMapping(value = "/gradeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GradeSetup>> getAllGradeSetups(Pageable pageable)
        throws URISyntaxException {
        Page<GradeSetup> page = gradeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gradeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gradeSetups/:id -> get the "id" gradeSetup.
     */
    @RequestMapping(value = "/gradeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GradeSetup> getGradeSetup(@PathVariable Long id) {
        log.debug("REST request to get GradeSetup : {}", id);
        return Optional.ofNullable(gradeSetupRepository.findOne(id))
            .map(gradeSetup -> new ResponseEntity<>(
                gradeSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gradeSetups/:id -> delete the "id" gradeSetup.
     */
    @RequestMapping(value = "/gradeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGradeSetup(@PathVariable Long id) {
        log.debug("REST request to delete GradeSetup : {}", id);
        gradeSetupRepository.delete(id);
        gradeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gradeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/gradeSetups/:query -> search for the gradeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/gradeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GradeSetup> searchGradeSetups(@PathVariable String query) {
        return StreamSupport
            .stream(gradeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
