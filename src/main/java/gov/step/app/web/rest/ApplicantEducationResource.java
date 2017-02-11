package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.ApplicantEducation;
import gov.step.app.repository.ApplicantEducationRepository;
import gov.step.app.repository.search.ApplicantEducationSearchRepository;
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
 * REST controller for managing ApplicantEducation.
 */
@RestController
@RequestMapping("/api")
public class ApplicantEducationResource {

    private final Logger log = LoggerFactory.getLogger(ApplicantEducationResource.class);

    @Inject
    private ApplicantEducationRepository applicantEducationRepository;

    @Inject
    private ApplicantEducationSearchRepository applicantEducationSearchRepository;

    /**
     * POST  /applicantEducations -> Create a new applicantEducation.
     */
    @RequestMapping(value = "/applicantEducations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantEducation> createApplicantEducation(@Valid @RequestBody ApplicantEducation applicantEducation) throws URISyntaxException {
        log.debug("REST request to save ApplicantEducation : {}", applicantEducation);
        if (applicantEducation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new applicantEducation cannot already have an ID").body(null);
        }
        ApplicantEducation result = applicantEducationRepository.save(applicantEducation);
        applicantEducationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/applicantEducations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("applicantEducation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /applicantEducations -> Updates an existing applicantEducation.
     */
    @RequestMapping(value = "/applicantEducations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantEducation> updateApplicantEducation(@Valid @RequestBody ApplicantEducation applicantEducation) throws URISyntaxException {
        log.debug("REST request to update ApplicantEducation : {}", applicantEducation);
        if (applicantEducation.getId() == null) {
            return createApplicantEducation(applicantEducation);
        }
        ApplicantEducation result = applicantEducationRepository.save(applicantEducation);
        applicantEducationSearchRepository.save(applicantEducation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("applicantEducation", applicantEducation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /applicantEducations -> get all the applicantEducations.
     */
    @RequestMapping(value = "/applicantEducations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ApplicantEducation>> getAllApplicantEducations(Pageable pageable)
        throws URISyntaxException {
        Page<ApplicantEducation> page = applicantEducationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/applicantEducations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /applicantEducations/employee/:id -> get all the applicantEducations for employee id.
     */
    @RequestMapping(value = "/applicantEducations/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ApplicantEducation> getAllApplicantEducationsByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<ApplicantEducation> applicantEducations = applicantEducationRepository.findByEmployee(id);
        log.debug("applicant educations are: {}", applicantEducations);
        return applicantEducations;
    }

    /**
     * GET  /applicantEducations/:id -> get the "id" applicantEducation.
     */
    @RequestMapping(value = "/applicantEducations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ApplicantEducation> getApplicantEducation(@PathVariable Long id) {
        log.debug("REST request to get ApplicantEducation : {}", id);
        return Optional.ofNullable(applicantEducationRepository.findOne(id))
            .map(applicantEducation -> new ResponseEntity<>(
                applicantEducation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /applicantEducations/:id -> delete the "id" applicantEducation.
     */
    @RequestMapping(value = "/applicantEducations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteApplicantEducation(@PathVariable Long id) {
        log.debug("REST request to delete ApplicantEducation : {}", id);
        applicantEducationRepository.delete(id);
        applicantEducationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("applicantEducation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/applicantEducations/:query -> search for the applicantEducation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/applicantEducations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ApplicantEducation> searchApplicantEducations(@PathVariable String query) {
        return StreamSupport
            .stream(applicantEducationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
