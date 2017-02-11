package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JobType;
import gov.step.app.domain.JpSkill;
import gov.step.app.repository.JpSkillRepository;
import gov.step.app.repository.search.JpSkillSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JpSkill.
 */
@RestController
@RequestMapping("/api")
public class JpSkillResource {

    private final Logger log = LoggerFactory.getLogger(JpSkillResource.class);

    @Inject
    private JpSkillRepository jpSkillRepository;

    @Inject
    private JpSkillSearchRepository jpSkillSearchRepository;

    /**
     * POST  /jpSkills -> Create a new jpSkill.
     */
    @RequestMapping(value = "/jpSkills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkill> createJpSkill(@Valid @RequestBody JpSkill jpSkill) throws URISyntaxException {
        log.debug("REST request to save JpSkill : {}", jpSkill);
        if (jpSkill.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpSkill cannot already have an ID").body(null);
        }
        JpSkill result = jpSkillRepository.save(jpSkill);
        jpSkillSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpSkills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpSkill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpSkills -> Updates an existing jpSkill.
     */
    @RequestMapping(value = "/jpSkills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkill> updateJpSkill(@Valid @RequestBody JpSkill jpSkill) throws URISyntaxException {
        log.debug("REST request to update JpSkill : {}", jpSkill);
        if (jpSkill.getId() == null) {
            return createJpSkill(jpSkill);
        }
        JpSkill result = jpSkillRepository.save(jpSkill);
        jpSkillSearchRepository.save(jpSkill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpSkill", jpSkill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpSkills -> get all the jpSkills.
     */
    @RequestMapping(value = "/jpSkills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpSkill>> getAllJpSkills(Pageable pageable)
        throws URISyntaxException {
        Page<JpSkill> page = jpSkillRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpSkills");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    //to get all active jpSkills
    /**
     * GET  /jpSkills/active -> get all the jpSkills.
     */
    @RequestMapping(value = "/jpSkills/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpSkill>> getAllActiveJpSkills(Pageable pageable)
        throws URISyntaxException {
        Page<JpSkill> page = jpSkillRepository.findAllActive(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpSkills/active");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpSkills/:id -> get the "id" jpSkill.
     */
    @RequestMapping(value = "/jpSkills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkill> getJpSkill(@PathVariable Long id) {
        log.debug("REST request to get JpSkill : {}", id);
        return Optional.ofNullable(jpSkillRepository.findOne(id))
            .map(jpSkill -> new ResponseEntity<>(
                jpSkill,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /jpSkills/:id -> get the "id" jpSkill.
     */
    @RequestMapping(value = "/jpSkills/byName/{name}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkill> getJpSkill(@PathVariable String name) {
        log.debug("REST request to get JpSkill : {}", name);
        return Optional.ofNullable(jpSkillRepository.findOneByName(name.toLowerCase()))
            .map(jpSkill -> new ResponseEntity<>(
                jpSkill,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * DELETE  /jpSkills/:id -> delete the "id" jpSkill.
     */
    @RequestMapping(value = "/jpSkills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpSkill(@PathVariable Long id) {
        log.debug("REST request to delete JpSkill : {}", id);
        jpSkillRepository.delete(id);
        jpSkillSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpSkill", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpSkills/:query -> search for the jpSkill corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpSkills/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpSkill> searchJpSkills(@PathVariable String query) {
        return StreamSupport
            .stream(jpSkillSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /*check organization type exists by name*/

    @RequestMapping(value = "/jpSkill/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkJpSkillByName(@RequestParam String value) {

        JpSkill jpSkill = jpSkillRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(jpSkill == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
