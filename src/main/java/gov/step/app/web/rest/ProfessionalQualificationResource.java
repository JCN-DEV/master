package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.ProfessionalQualification;
import gov.step.app.repository.ProfessionalQualificationRepository;
import gov.step.app.repository.search.ProfessionalQualificationSearchRepository;
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
 * REST controller for managing ProfessionalQualification.
 */
@RestController
@RequestMapping("/api")
public class ProfessionalQualificationResource {

    private final Logger log = LoggerFactory.getLogger(ProfessionalQualificationResource.class);

    @Inject
    private ProfessionalQualificationRepository professionalQualificationRepository;

    @Inject
    private ProfessionalQualificationSearchRepository professionalQualificationSearchRepository;

    /**
     * POST  /professionalQualifications -> Create a new professionalQualification.
     */
    @RequestMapping(value = "/professionalQualifications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessionalQualification> createProfessionalQualification(@Valid @RequestBody ProfessionalQualification professionalQualification) throws URISyntaxException {
        log.debug("REST request to save ProfessionalQualification : {}", professionalQualification);
        if (professionalQualification.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new professionalQualification cannot already have an ID").body(null);
        }
        ProfessionalQualification result = professionalQualificationRepository.save(professionalQualification);
        professionalQualificationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/professionalQualifications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("professionalQualification", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /professionalQualifications -> Updates an existing professionalQualification.
     */
    @RequestMapping(value = "/professionalQualifications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessionalQualification> updateProfessionalQualification(@Valid @RequestBody ProfessionalQualification professionalQualification) throws URISyntaxException {
        log.debug("REST request to update ProfessionalQualification : {}", professionalQualification);
        if (professionalQualification.getId() == null) {
            return createProfessionalQualification(professionalQualification);
        }
        ProfessionalQualification result = professionalQualificationRepository.save(professionalQualification);
        professionalQualificationSearchRepository.save(professionalQualification);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("professionalQualification", professionalQualification.getId().toString()))
            .body(result);
    }

    /**
     * GET  /professionalQualifications -> get all the professionalQualifications.
     */
    @RequestMapping(value = "/professionalQualifications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<ProfessionalQualification>> getAllProfessionalQualifications(Pageable pageable)
        throws URISyntaxException {
        Page<ProfessionalQualification> page = professionalQualificationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/professionalQualifications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /professionalQualifications/:id -> get the "id" professionalQualification.
     */
    @RequestMapping(value = "/professionalQualifications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProfessionalQualification> getProfessionalQualification(@PathVariable Long id) {
        log.debug("REST request to get ProfessionalQualification : {}", id);
        return Optional.ofNullable(professionalQualificationRepository.findOne(id))
            .map(professionalQualification -> new ResponseEntity<>(
                professionalQualification,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /professionalQualifications/:id -> delete the "id" professionalQualification.
     */
    @RequestMapping(value = "/professionalQualifications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProfessionalQualification(@PathVariable Long id) {
        log.debug("REST request to delete ProfessionalQualification : {}", id);
        professionalQualificationRepository.delete(id);
        professionalQualificationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("professionalQualification", id.toString())).build();
    }

    /**
     * SEARCH  /_search/professionalQualifications/:query -> search for the professionalQualification corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/professionalQualifications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProfessionalQualification> searchProfessionalQualifications(@PathVariable String query) {
        return StreamSupport
            .stream(professionalQualificationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
