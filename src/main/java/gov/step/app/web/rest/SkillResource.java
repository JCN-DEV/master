package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JobType;
import gov.step.app.domain.Skill;
import gov.step.app.repository.SkillRepository;
import gov.step.app.repository.search.SkillSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Skill.
 */
@RestController
@RequestMapping("/api")
public class SkillResource {

    private final Logger log = LoggerFactory.getLogger(SkillResource.class);

    @Inject
    private SkillRepository skillRepository;

    @Inject
    private SkillSearchRepository skillSearchRepository;

    /**
     * POST  /skills -> Create a new skill.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> createSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to save Skill : {}", skill);
        if (skill.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new skill cannot already have an ID").body(null);
        }
        Skill result = skillRepository.save(skill);
        skillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/skills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("skill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /skills -> Updates an existing skill.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> updateSkill(@Valid @RequestBody Skill skill) throws URISyntaxException {
        log.debug("REST request to update Skill : {}", skill);
        if (skill.getId() == null) {
            return createSkill(skill);
        }
        Skill result = skillRepository.save(skill);
        skillSearchRepository.save(skill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("skill", skill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /skills -> get all the skills.
     */
    @RequestMapping(value = "/skills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Skill>> getAllSkills(Pageable pageable)
        throws URISyntaxException {
        Page<Skill> page = skillRepository.findByEmployeeUserIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER))
            page = skillRepository.findByManagerIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            page = skillRepository.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/skills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /skills/employee/:id -> get all skill by employee
     */
    @RequestMapping(value = "/skills/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Skill> getAllSkillByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<Skill> skills = skillRepository.findByEmployee(id);
        return skills;

    }

    /**
     * GET  /skills/:id -> get the "id" skill.
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Skill> getSkill(@PathVariable Long id) {
        log.debug("REST request to get Skill : {}", id);
        return Optional.ofNullable(skillRepository.findOne(id))
            .map(skill -> new ResponseEntity<>(
                skill,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /skills/:id -> delete the "id" skill.
     */
    @RequestMapping(value = "/skills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSkill(@PathVariable Long id) {
        log.debug("REST request to delete Skill : {}", id);
        skillRepository.delete(id);
        skillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("skill", id.toString())).build();
    }

    /**
     * SEARCH  /_search/skills/:query -> search for the skill corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/skills/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Skill> searchSkills(@PathVariable String query) {
        return StreamSupport
            .stream(skillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }




}
