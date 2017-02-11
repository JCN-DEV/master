package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpAcademicQualification;
import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.repository.JpEmployeeExperienceRepository;
import gov.step.app.repository.search.JpEmployeeExperienceSearchRepository;
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
 * REST controller for managing JpEmployeeExperience.
 */
@RestController
@RequestMapping("/api")
public class JpEmployeeExperienceResource {

    private final Logger log = LoggerFactory.getLogger(JpEmployeeExperienceResource.class);

    @Inject
    private JpEmployeeExperienceRepository jpEmployeeExperienceRepository;

    @Inject
    private JpEmployeeExperienceSearchRepository jpEmployeeExperienceSearchRepository;

    /**
     * POST  /jpEmployeeExperiences -> Create a new jpEmployeeExperience.
     */
    @RequestMapping(value = "/jpEmployeeExperiences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeExperience> createJpEmployeeExperience(@Valid @RequestBody JpEmployeeExperience jpEmployeeExperience) throws URISyntaxException {
        log.debug("REST request to save JpEmployeeExperience : {}", jpEmployeeExperience);
        if (jpEmployeeExperience.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpEmployeeExperience cannot already have an ID").body(null);
        }
        JpEmployeeExperience result = jpEmployeeExperienceRepository.save(jpEmployeeExperience);
        jpEmployeeExperienceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpEmployeeExperiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpEmployeeExperience", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpEmployeeExperiences -> Updates an existing jpEmployeeExperience.
     */
    @RequestMapping(value = "/jpEmployeeExperiences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeExperience> updateJpEmployeeExperience(@Valid @RequestBody JpEmployeeExperience jpEmployeeExperience) throws URISyntaxException {
        log.debug("REST request to update JpEmployeeExperience : {}", jpEmployeeExperience);
        if (jpEmployeeExperience.getId() == null) {
            return createJpEmployeeExperience(jpEmployeeExperience);
        }
        JpEmployeeExperience result = jpEmployeeExperienceRepository.save(jpEmployeeExperience);
        jpEmployeeExperienceSearchRepository.save(jpEmployeeExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpEmployeeExperience", jpEmployeeExperience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpEmployeeExperiences -> get all the jpEmployeeExperiences.
     */
    @RequestMapping(value = "/jpEmployeeExperiences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpEmployeeExperience>> getAllJpEmployeeExperiences(Pageable pageable)
        throws URISyntaxException {
        Page<JpEmployeeExperience> page = jpEmployeeExperienceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpEmployeeExperiences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpEmployeeExperiences/:id -> get the "id" jpEmployeeExperience.
     */
    @RequestMapping(value = "/jpEmployeeExperiences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeExperience> getJpEmployeeExperience(@PathVariable Long id) {
        log.debug("REST request to get JpEmployeeExperience : {}", id);
        return Optional.ofNullable(jpEmployeeExperienceRepository.findOne(id))
            .map(jpEmployeeExperience -> new ResponseEntity<>(
                jpEmployeeExperience,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpEmployeeExperiences/:id -> delete the "id" jpEmployeeExperience.
     */
    @RequestMapping(value = "/jpEmployeeExperiences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpEmployeeExperience(@PathVariable Long id) {
        log.debug("REST request to delete JpEmployeeExperience : {}", id);
        jpEmployeeExperienceRepository.delete(id);
        jpEmployeeExperienceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpEmployeeExperience", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpEmployeeExperiences/:query -> search for the jpEmployeeExperience corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpEmployeeExperiences/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeExperience> searchJpEmployeeExperiences(@PathVariable String query) {
        return StreamSupport
            .stream(jpEmployeeExperienceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/jpEmployeeExperiences/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeExperience> getAllEmployeeExperienceByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpEmployeeExperience> experiences = jpEmployeeExperienceRepository.findByJpEmployee(id);
        return  experiences;
    }
}
