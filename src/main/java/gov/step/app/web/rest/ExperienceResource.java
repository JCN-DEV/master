package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Experience;
import gov.step.app.domain.Skill;
import gov.step.app.repository.ExperienceRepository;
import gov.step.app.repository.search.ExperienceSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing Experience.
 */
@RestController
@RequestMapping("/api")
public class ExperienceResource {

    private final Logger log = LoggerFactory.getLogger(ExperienceResource.class);

    @Inject
    private ExperienceRepository experienceRepository;

    @Inject
    private ExperienceSearchRepository experienceSearchRepository;

    /**
     * POST /experiences -> Create a new experience.
     */
    @RequestMapping(value = "/experiences", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Experience> createExperience(@Valid @RequestBody Experience experience)
        throws URISyntaxException {
        log.debug("REST request to save Experience : {}", experience);
        if (experience.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new experience cannot already have an ID")
                .body(null);
        }
        Experience result = experienceRepository.save(experience);
        experienceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/experiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("experience", result.getId().toString())).body(result);
    }

    /**
     * PUT /experiences -> Updates an existing experience.
     */
    @RequestMapping(value = "/experiences", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Experience> updateExperience(@Valid @RequestBody Experience experience)
        throws URISyntaxException {
        log.debug("REST request to update Experience : {}", experience);
        if (experience.getId() == null) {
            return createExperience(experience);
        }
        Experience result = experienceRepository.save(experience);
        experienceSearchRepository.save(experience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("experience", experience.getId().toString())).body(result);
    }

    /**
     * GET /experiences -> get all the experiences.
     */
    @RequestMapping(value = "/experiences", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Experience>> getAllExperiences(Pageable pageable) throws URISyntaxException {

        Page<Experience> page = experienceRepository.findByManagerIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            page = experienceRepository.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/experiences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /experiences/employee/:id -> get all experience by employee
     */
    @RequestMapping(value = "/experiences/employee/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Experience> getAllExperienceByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<Experience> experiences = experienceRepository.findByEmployee(id);
        return experiences;

    }

    /**
     * GET /experiences/:id -> get the "id" experience.
     */
    @RequestMapping(value = "/experiences/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Experience> getExperience(@PathVariable Long id) {
        log.debug("REST request to get Experience : {}", id);
        return Optional.ofNullable(experienceRepository.findOne(id))
            .map(experience -> new ResponseEntity<>(experience, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /experiences/:id -> delete the "id" experience.
     */
    @RequestMapping(value = "/experiences/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteExperience(@PathVariable Long id) {
        log.debug("REST request to delete Experience : {}", id);
        experienceRepository.delete(id);
        experienceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("experience", id.toString())).build();
    }

    /**
     * SEARCH /_search/experiences/:query -> search for the experience
     * corresponding to the query.
     */
    @RequestMapping(value = "/_search/experiences/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Experience> searchExperiences(@PathVariable String query) {
        return StreamSupport.stream(experienceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
