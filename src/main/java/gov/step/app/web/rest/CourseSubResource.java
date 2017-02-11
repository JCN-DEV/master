package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CourseSub;
import gov.step.app.repository.CourseSubRepository;
import gov.step.app.repository.search.CourseSubSearchRepository;
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
 * REST controller for managing CourseSub.
 */
@RestController
@RequestMapping("/api")
public class CourseSubResource {

    private final Logger log = LoggerFactory.getLogger(CourseSubResource.class);

    @Inject
    private CourseSubRepository courseSubRepository;

    @Inject
    private CourseSubSearchRepository courseSubSearchRepository;

    /**
     * POST  /courseSubs -> Create a new courseSub.
     */
    @RequestMapping(value = "/courseSubs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSub> createCourseSub(@Valid @RequestBody CourseSub courseSub) throws URISyntaxException {
        log.debug("REST request to save CourseSub : {}", courseSub);
        if (courseSub.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courseSub cannot already have an ID").body(null);
        }
        CourseSub result = courseSubRepository.save(courseSub);
        courseSubSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/courseSubs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseSub", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courseSubs -> Updates an existing courseSub.
     */
    @RequestMapping(value = "/courseSubs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSub> updateCourseSub(@Valid @RequestBody CourseSub courseSub) throws URISyntaxException {
        log.debug("REST request to update CourseSub : {}", courseSub);
        if (courseSub.getId() == null) {
            return createCourseSub(courseSub);
        }
        CourseSub result = courseSubRepository.save(courseSub);
        courseSubSearchRepository.save(courseSub);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseSub", courseSub.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courseSubs -> get all the courseSubs.
     */
    @RequestMapping(value = "/courseSubs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CourseSub>> getAllCourseSubs(Pageable pageable)
        throws URISyntaxException {
        Page<CourseSub> page = courseSubRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courseSubs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courseSubs/:id -> get the "id" courseSub.
     */
    @RequestMapping(value = "/courseSubs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSub> getCourseSub(@PathVariable Long id) {
        log.debug("REST request to get CourseSub : {}", id);
        return Optional.ofNullable(courseSubRepository.findOne(id))
            .map(courseSub -> new ResponseEntity<>(
                courseSub,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courseSubs/:id -> delete the "id" courseSub.
     */
    @RequestMapping(value = "/courseSubs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourseSub(@PathVariable Long id) {
        log.debug("REST request to delete CourseSub : {}", id);
        courseSubRepository.delete(id);
        courseSubSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseSub", id.toString())).build();
    }

    /**
     * SEARCH  /_search/courseSubs/:query -> search for the courseSub corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/courseSubs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CourseSub> searchCourseSubs(@PathVariable String query) {
        return StreamSupport
            .stream(courseSubSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
