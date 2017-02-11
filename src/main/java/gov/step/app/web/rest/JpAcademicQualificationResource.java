package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EducationalQualification;
import gov.step.app.domain.JpAcademicQualification;
import gov.step.app.repository.JpAcademicQualificationRepository;
import gov.step.app.repository.search.JpAcademicQualificationSearchRepository;
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
 * REST controller for managing JpAcademicQualification.
 */
@RestController
@RequestMapping("/api")
public class JpAcademicQualificationResource {

    private final Logger log = LoggerFactory.getLogger(JpAcademicQualificationResource.class);

    @Inject
    private JpAcademicQualificationRepository jpAcademicQualificationRepository;

    @Inject
    private JpAcademicQualificationSearchRepository jpAcademicQualificationSearchRepository;

    /**
     * POST  /jpAcademicQualifications -> Create a new jpAcademicQualification.
     */
    @RequestMapping(value = "/jpAcademicQualifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpAcademicQualification> createJpAcademicQualification(@Valid @RequestBody JpAcademicQualification jpAcademicQualification) throws URISyntaxException {
        log.debug("REST request to save JpAcademicQualification : {}", jpAcademicQualification);
        if (jpAcademicQualification.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpAcademicQualification cannot already have an ID").body(null);
        }
        JpAcademicQualification result = jpAcademicQualificationRepository.save(jpAcademicQualification);
        jpAcademicQualificationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpAcademicQualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpAcademicQualification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpAcademicQualifications -> Updates an existing jpAcademicQualification.
     */
    @RequestMapping(value = "/jpAcademicQualifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpAcademicQualification> updateJpAcademicQualification(@Valid @RequestBody JpAcademicQualification jpAcademicQualification) throws URISyntaxException {
        log.debug("REST request to update JpAcademicQualification : {}", jpAcademicQualification);
        if (jpAcademicQualification.getId() == null) {
            return createJpAcademicQualification(jpAcademicQualification);
        }
        JpAcademicQualification result = jpAcademicQualificationRepository.save(jpAcademicQualification);
        jpAcademicQualificationSearchRepository.save(jpAcademicQualification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpAcademicQualification", jpAcademicQualification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpAcademicQualifications -> get all the jpAcademicQualifications.
     */
    @RequestMapping(value = "/jpAcademicQualifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpAcademicQualification>> getAllJpAcademicQualifications(Pageable pageable)
        throws URISyntaxException {
        Page<JpAcademicQualification> page = jpAcademicQualificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpAcademicQualifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpAcademicQualifications/:id -> get the "id" jpAcademicQualification.
     */
    @RequestMapping(value = "/jpAcademicQualifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpAcademicQualification> getJpAcademicQualification(@PathVariable Long id) {
        log.debug("REST request to get JpAcademicQualification : {}", id);
        return Optional.ofNullable(jpAcademicQualificationRepository.findOne(id))
            .map(jpAcademicQualification -> new ResponseEntity<>(
                jpAcademicQualification,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /educationalQualifications/employee/:id -> get all academicQualification by jpEmployee
     */
    @RequestMapping(value = "/academicQualifications/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpAcademicQualification> getAllEducationalQualificationByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpAcademicQualification> academicQualifications = jpAcademicQualificationRepository.findByJpEmployee(id);
        return  academicQualifications;
    }

    /**
     * DELETE  /jpAcademicQualifications/:id -> delete the "id" jpAcademicQualification.
     */
    @RequestMapping(value = "/jpAcademicQualifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpAcademicQualification(@PathVariable Long id) {
        log.debug("REST request to delete JpAcademicQualification : {}", id);
        jpAcademicQualificationRepository.delete(id);
        jpAcademicQualificationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpAcademicQualification", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpAcademicQualifications/:query -> search for the jpAcademicQualification corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpAcademicQualifications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpAcademicQualification> searchJpAcademicQualifications(@PathVariable String query) {
        return StreamSupport
            .stream(jpAcademicQualificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
