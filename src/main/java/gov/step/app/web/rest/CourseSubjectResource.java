package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CourseSubject;
import gov.step.app.repository.CourseSubjectRepository;
import gov.step.app.repository.search.CourseSubjectSearchRepository;
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
 * REST controller for managing CourseSubject.
 */
@RestController
@RequestMapping("/api")
public class CourseSubjectResource {

    private final Logger log = LoggerFactory.getLogger(CourseSubjectResource.class);

    @Inject
    private CourseSubjectRepository courseSubjectRepository;

    @Inject
    private CourseSubjectSearchRepository courseSubjectSearchRepository;

    /**
     * POST  /courseSubjects -> Create a new courseSubject.
     */
    @RequestMapping(value = "/courseSubjects",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSubject> createCourseSubject(@Valid @RequestBody CourseSubject courseSubject) throws URISyntaxException {
        log.debug("REST request to save CourseSubject : {}", courseSubject);
        if (courseSubject.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new courseSubject cannot already have an ID").body(null);
        }
        CourseSubject result = courseSubjectRepository.save(courseSubject);
        courseSubjectSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/courseSubjects/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("courseSubject", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /courseSubjects -> Updates an existing courseSubject.
     */
    @RequestMapping(value = "/courseSubjects",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSubject> updateCourseSubject(@Valid @RequestBody CourseSubject courseSubject) throws URISyntaxException {
        log.debug("REST request to update CourseSubject : {}", courseSubject);
        if (courseSubject.getId() == null) {
            return createCourseSubject(courseSubject);
        }
        CourseSubject result = courseSubjectRepository.save(courseSubject);
        courseSubjectSearchRepository.save(courseSubject);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("courseSubject", courseSubject.getId().toString()))
            .body(result);
    }

    /**
     * GET  /courseSubjects -> get all the courseSubjects.
     */
    @RequestMapping(value = "/courseSubjects",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CourseSubject>> getAllCourseSubjects(Pageable pageable)
        throws URISyntaxException {
        Page<CourseSubject> page = courseSubjectRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/courseSubjects");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /courseSubjects/:id -> get the "id" courseSubject.
     */
    @RequestMapping(value = "/courseSubjects/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CourseSubject> getCourseSubject(@PathVariable Long id) {
        log.debug("REST request to get CourseSubject : {}", id);
        return Optional.ofNullable(courseSubjectRepository.findOne(id))
            .map(courseSubject -> new ResponseEntity<>(
                courseSubject,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /courseSubjects/:id -> delete the "id" courseSubject.
     */
    @RequestMapping(value = "/courseSubjects/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCourseSubject(@PathVariable Long id) {
        log.debug("REST request to delete CourseSubject : {}", id);
        courseSubjectRepository.delete(id);
        courseSubjectSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("courseSubject", id.toString())).build();
    }

    /**
     * SEARCH  /_search/courseSubjects/:query -> search for the courseSubject corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/courseSubjects/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CourseSubject> searchCourseSubjects(@PathVariable String query) {
        return StreamSupport
            .stream(courseSubjectSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
