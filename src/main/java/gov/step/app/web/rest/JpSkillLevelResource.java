package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpSkill;
import gov.step.app.domain.JpSkillLevel;
import gov.step.app.repository.JpSkillLevelRepository;
import gov.step.app.repository.search.JpSkillLevelSearchRepository;
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
 * REST controller for managing JpSkillLevel.
 */
@RestController
@RequestMapping("/api")
public class JpSkillLevelResource {

    private final Logger log = LoggerFactory.getLogger(JpSkillLevelResource.class);

    @Inject
    private JpSkillLevelRepository jpSkillLevelRepository;

    @Inject
    private JpSkillLevelSearchRepository jpSkillLevelSearchRepository;

    /**
     * POST  /jpSkillLevels -> Create a new jpSkillLevel.
     */
    @RequestMapping(value = "/jpSkillLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkillLevel> createJpSkillLevel(@Valid @RequestBody JpSkillLevel jpSkillLevel) throws URISyntaxException {
        log.debug("REST request to save JpSkillLevel : {}", jpSkillLevel);
        if (jpSkillLevel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpSkillLevel cannot already have an ID").body(null);
        }
        JpSkillLevel result = jpSkillLevelRepository.save(jpSkillLevel);
        jpSkillLevelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpSkillLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpSkillLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpSkillLevels -> Updates an existing jpSkillLevel.
     */
    @RequestMapping(value = "/jpSkillLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkillLevel> updateJpSkillLevel(@Valid @RequestBody JpSkillLevel jpSkillLevel) throws URISyntaxException {
        log.debug("REST request to update JpSkillLevel : {}", jpSkillLevel);
        if (jpSkillLevel.getId() == null) {
            return createJpSkillLevel(jpSkillLevel);
        }
        JpSkillLevel result = jpSkillLevelRepository.save(jpSkillLevel);
        jpSkillLevelSearchRepository.save(jpSkillLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpSkillLevel", jpSkillLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpSkillLevels -> get all the jpSkillLevels.
     */
    @RequestMapping(value = "/jpSkillLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpSkillLevel>> getAllJpSkillLevels(Pageable pageable)
        throws URISyntaxException {
        Page<JpSkillLevel> page = jpSkillLevelRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpSkillLevels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpSkillLevels/active -> get all the active jpSkillLevels.
     */
    @RequestMapping(value = "/jpSkillLevels/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpSkillLevel>> getAllActiveJpSkillLevels(Pageable pageable)
        throws URISyntaxException {
        Page<JpSkillLevel> page = jpSkillLevelRepository.findAllActive(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpSkillLevels/active");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpSkillLevels/:id -> get the "id" jpSkillLevel.
     */
    @RequestMapping(value = "/jpSkillLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpSkillLevel> getJpSkillLevel(@PathVariable Long id) {
        log.debug("REST request to get JpSkillLevel : {}", id);
        return Optional.ofNullable(jpSkillLevelRepository.findOne(id))
            .map(jpSkillLevel -> new ResponseEntity<>(
                jpSkillLevel,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpSkillLevels/:id -> delete the "id" jpSkillLevel.
     */
    @RequestMapping(value = "/jpSkillLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpSkillLevel(@PathVariable Long id) {
        log.debug("REST request to delete JpSkillLevel : {}", id);
        jpSkillLevelRepository.delete(id);
        jpSkillLevelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpSkillLevel", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpSkillLevels/:query -> search for the jpSkillLevel corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpSkillLevels/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpSkillLevel> searchJpSkillLevels(@PathVariable String query) {
        return StreamSupport
            .stream(jpSkillLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /*check organization type exists by name*/

    @RequestMapping(value = "/jpSkillLevel/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkJpSkillLevelByName(@RequestParam String value) {

        JpSkillLevel jpSkillLevel = jpSkillLevelRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(jpSkillLevel == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
