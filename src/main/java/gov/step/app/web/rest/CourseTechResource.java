package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CourseTech;
import gov.step.app.repository.CourseTechRepository;
import gov.step.app.repository.search.CourseTechSearchRepository;
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
 * REST controller for managing CourseTech.
 */
@RestController
@RequestMapping("/api")
public class CourseTechResource {

    private final Logger log = LoggerFactory.getLogger(CourseTechResource.class);

    @Inject
    private CourseTechRepository courseTechRepository;

    @Inject
    private CourseTechSearchRepository courseTechSearchRepository;

    /**
     * POST  /courseTechs -> Create a new courseTech.
     */
    @RequestMapping(value = "/courseTechs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseTech> createCourseTech(@Valid @RequestBody CourseTech courseTech) throws URISyntaxException {
        log.debug("REST request to save CourseTech : {}", courseTech);
        if (courseTech.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courseTech cannot already have an ID").body(null);
        }
        CourseTech result = courseTechRepository.save(courseTech);
        courseTechSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/courseTechs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseTech", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courseTechs -> Updates an existing courseTech.
     */
    @RequestMapping(value = "/courseTechs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseTech> updateCourseTech(@Valid @RequestBody CourseTech courseTech) throws URISyntaxException {
        log.debug("REST request to update CourseTech : {}", courseTech);
        if (courseTech.getId() == null) {
            return createCourseTech(courseTech);
        }
        CourseTech result = courseTechRepository.save(courseTech);
        courseTechSearchRepository.save(courseTech);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseTech", courseTech.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courseTechs -> get all the courseTechs.
     */
    @RequestMapping(value = "/courseTechs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CourseTech>> getAllCourseTechs(Pageable pageable)
        throws URISyntaxException {
        Page<CourseTech> page = courseTechRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courseTechs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courseTechs/:id -> get the "id" courseTech.
     */
    @RequestMapping(value = "/courseTechs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseTech> getCourseTech(@PathVariable Long id) {
        log.debug("REST request to get CourseTech : {}", id);
        return Optional.ofNullable(courseTechRepository.findOne(id))
            .map(courseTech -> new ResponseEntity<>(
                courseTech,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courseTechs/:id -> delete the "id" courseTech.
     */
    @RequestMapping(value = "/courseTechs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourseTech(@PathVariable Long id) {
        log.debug("REST request to delete CourseTech : {}", id);
        courseTechRepository.delete(id);
        courseTechSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseTech", id.toString())).build();
    }

    /**
     * SEARCH  /_search/courseTechs/:query -> search for the courseTech corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/courseTechs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CourseTech> searchCourseTechs(@PathVariable String query) {
        return StreamSupport
            .stream(courseTechSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
