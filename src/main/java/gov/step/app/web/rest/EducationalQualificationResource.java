package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EducationalQualification;
import gov.step.app.repository.EducationalQualificationRepository;
import gov.step.app.repository.search.EducationalQualificationSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing EducationalQualification.
 */
@RestController
@RequestMapping("/api")
public class EducationalQualificationResource {

    private final Logger log = LoggerFactory.getLogger(EducationalQualificationResource.class);

    @Inject
    private EducationalQualificationRepository educationalQualificationRepository;

    @Inject
    private EducationalQualificationSearchRepository educationalQualificationSearchRepository;

    /**
     * POST  /educationalQualifications -> Create a new educationalQualification.
     */
    @RequestMapping(value = "/educationalQualifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationalQualification> createEducationalQualification(@Valid @RequestBody EducationalQualification educationalQualification) throws URISyntaxException {
        log.debug("REST request to save EducationalQualification : {}", educationalQualification);
        if (educationalQualification.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new educationalQualification cannot already have an ID").body(null);
        }
        EducationalQualification result = educationalQualificationRepository.save(educationalQualification);
        educationalQualificationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/educationalQualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("educationalQualification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /educationalQualifications -> Updates an existing educationalQualification.
     */
    @RequestMapping(value = "/educationalQualifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationalQualification> updateEducationalQualification(@Valid @RequestBody EducationalQualification educationalQualification) throws URISyntaxException {
        log.debug("REST request to update EducationalQualification : {}", educationalQualification);
        if (educationalQualification.getId() == null) {
            return createEducationalQualification(educationalQualification);
        }
        EducationalQualification result = educationalQualificationRepository.save(educationalQualification);
        educationalQualificationSearchRepository.save(educationalQualification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("educationalQualification", educationalQualification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /educationalQualifications -> get all the educationalQualifications.
     */
    @RequestMapping(value = "/educationalQualifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EducationalQualification>> getAllEducationalQualifications(Pageable pageable)
        throws URISyntaxException {
        Page<EducationalQualification> page = educationalQualificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/educationalQualifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /educationalQualifications/employee/:id -> get all educationalQualification by employee
     */
    @RequestMapping(value = "/educationalQualifications/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EducationalQualification> getAllEducationalQualificationByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<EducationalQualification> educationalQualifications = educationalQualificationRepository.findByEmployee(id);
        return  educationalQualifications;
    }

    /**
     * GET  /educationalQualifications/:id -> get the "id" educationalQualification.
     */
    @RequestMapping(value = "/educationalQualifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EducationalQualification> getEducationalQualification(@PathVariable Long id) {
        log.debug("REST request to get EducationalQualification : {}", id);
        return Optional.ofNullable(educationalQualificationRepository.findOne(id))
            .map(educationalQualification -> new ResponseEntity<>(
                educationalQualification,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /educationalQualifications/:id -> delete the "id" educationalQualification.
     */
    @RequestMapping(value = "/educationalQualifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEducationalQualification(@PathVariable Long id) {
        log.debug("REST request to delete EducationalQualification : {}", id);
        educationalQualificationRepository.delete(id);
        educationalQualificationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("educationalQualification", id.toString())).build();
    }

    /**
     * SEARCH  /_search/educationalQualifications/:query -> search for the educationalQualification corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/educationalQualifications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EducationalQualification> searchEducationalQualifications(@PathVariable String query) {
        return StreamSupport
            .stream(educationalQualificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
