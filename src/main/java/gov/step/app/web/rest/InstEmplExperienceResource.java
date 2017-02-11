package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplExperience;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmplExperienceRepository;
import gov.step.app.repository.InstEmployeeRepository;
import gov.step.app.repository.search.InstEmplExperienceSearchRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmplExperience.
 */
@RestController
@RequestMapping("/api")
public class InstEmplExperienceResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplExperienceResource.class);

    @Inject
    private InstEmplExperienceRepository instEmplExperienceRepository;

    @Inject
    private InstEmplExperienceSearchRepository instEmplExperienceSearchRepository;

    @Inject
    private InstEmployeeRepository instEmployeeRepository;

    /**
     * POST  /instEmplExperiences -> Create a new instEmplExperience.
     */
    @RequestMapping(value = "/instEmplExperiences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplExperience> createInstEmplExperience(@Valid @RequestBody InstEmplExperience instEmplExperience) throws URISyntaxException {
        log.debug("REST request to save InstEmplExperience : {}", instEmplExperience);
        if (instEmplExperience.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplExperience cannot already have an ID").body(null);
        }
        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        instEmplExperience.setInstEmployee(instEmployeeresult);
        InstEmplExperience result = instEmplExperienceRepository.save(instEmplExperience);
        instEmplExperienceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplExperiences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplExperience", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplExperiences -> Updates an existing instEmplExperience.
     */
    @RequestMapping(value = "/instEmplExperiences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplExperience> updateInstEmplExperience(@Valid @RequestBody InstEmplExperience instEmplExperience) throws URISyntaxException {
        log.debug("REST request to update InstEmplExperience : {}", instEmplExperience);
        if (instEmplExperience.getId() == null) {
            return createInstEmplExperience(instEmplExperience);
        }
        InstEmplExperience result = instEmplExperienceRepository.save(instEmplExperience);
        instEmplExperienceSearchRepository.save(instEmplExperience);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplExperience", instEmplExperience.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplExperiences -> get all the instEmplExperiences.
     */
    @RequestMapping(value = "/instEmplExperiences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplExperience>> getAllInstEmplExperiences(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplExperience> page = instEmplExperienceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplExperiences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplExperiences/:id -> get the "id" instEmplExperience.
     */
    @RequestMapping(value = "/instEmplExperiences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplExperience> getInstEmplExperience(@PathVariable Long id) {
        log.debug("REST request to get InstEmplExperience : {}", id);
        return Optional.ofNullable(instEmplExperienceRepository.findOne(id))
            .map(instEmplExperience -> new ResponseEntity<>(
                instEmplExperience,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    /**
     * GET  /instEmplExperiencesCurrent -> get the "id" instEmplExperience.
     */
    @RequestMapping(value = "/instEmplExperiencesCurrent",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplExperience> getInstEmplExperienceCurrent() {
        log.debug("REST request to get InstEmplExperience : {}");


        String userName = SecurityUtils.getCurrentUserLogin();
        InstEmployee instEmployeeresult=instEmployeeRepository.findOneByEmployeeCode(userName);
        List<Map<String, Object>> instEmplExperiences=null;
        return instEmplExperienceRepository.findByInstEmployeeId(instEmployeeresult.getId());
    }

    /**
     * DELETE  /instEmplExperiences/:id -> delete the "id" instEmplExperience.
     */
    @RequestMapping(value = "/instEmplExperiences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplExperience(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplExperience : {}", id);
        instEmplExperienceRepository.delete(id);
        instEmplExperienceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplExperience", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplExperiences/:query -> search for the instEmplExperience corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplExperiences/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplExperience> searchInstEmplExperiences(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplExperienceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
