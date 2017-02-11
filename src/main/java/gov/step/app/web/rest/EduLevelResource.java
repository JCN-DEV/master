package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.EduBoard;
import gov.step.app.domain.EduLevel;
import gov.step.app.repository.EduLevelRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.EduLevelSearchRepository;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EduLevel.
 */
@RestController
@RequestMapping("/api")
public class EduLevelResource {

    private final Logger log = LoggerFactory.getLogger(EduLevelResource.class);

    @Inject
    private EduLevelRepository eduLevelRepository;

    @Inject
    private EduLevelSearchRepository eduLevelSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /eduLevels -> Create a new eduLevel.
     */
    @RequestMapping(value = "/eduLevels",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduLevel> createEduLevel(@Valid @RequestBody EduLevel eduLevel) throws URISyntaxException {
        log.debug("REST request to save EduLevel : {}", eduLevel);
        if (eduLevel.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new eduLevel cannot already have an ID").body(null);
        }
        eduLevel.setCreateDate(LocalDate.now());
        eduLevel.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        EduLevel result = eduLevelRepository.save(eduLevel);
        eduLevelSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/eduLevels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("eduLevel", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /eduLevels -> Updates an existing eduLevel.
     */
    @RequestMapping(value = "/eduLevels",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduLevel> updateEduLevel(@Valid @RequestBody EduLevel eduLevel) throws URISyntaxException {
        log.debug("REST request to update EduLevel : {}", eduLevel);
        if (eduLevel.getId() == null) {
            return createEduLevel(eduLevel);
        }
        eduLevel.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        eduLevel.setUpdateDate(LocalDate.now());
        EduLevel result = eduLevelRepository.save(eduLevel);
        eduLevelSearchRepository.save(eduLevel);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("eduLevel", eduLevel.getId().toString()))
            .body(result);
    }

    /**
     * GET  /eduLevels -> get all the eduLevels.
     */
    @RequestMapping(value = "/eduLevels",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EduLevel>> getAllEduLevels(Pageable pageable)
        throws URISyntaxException {
        Page<EduLevel> page = eduLevelRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eduLevels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }


    /**
     * GET  /eduLevels/active -> get all active eduLevels.
     */
    @RequestMapping(value = "/eduLevels/active",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EduLevel>> getAllActiveEduLevels(Pageable pageable)
        throws URISyntaxException {
        Page<EduLevel> page = eduLevelRepository.findAllActiveEduLevels(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/eduLevels");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /eduLevels/:id -> get the "id" eduLevel.
     */
    @RequestMapping(value = "/eduLevels/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EduLevel> getEduLevel(@PathVariable Long id) {
        log.debug("REST request to get EduLevel : {}", id);
        return Optional.ofNullable(eduLevelRepository.findOne(id))
            .map(eduLevel -> new ResponseEntity<>(
                eduLevel,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /eduLevels/:id -> delete the "id" eduLevel.
     */
    @RequestMapping(value = "/eduLevels/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEduLevel(@PathVariable Long id) {
        log.debug("REST request to delete EduLevel : {}", id);
        eduLevelRepository.delete(id);
        eduLevelSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("eduLevel", id.toString())).build();
    }

    /**
     * SEARCH  /_search/eduLevels/:query -> search for the eduLevel corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/eduLevels/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EduLevel> searchEduLevels(@PathVariable String query) {
        return StreamSupport
            .stream(eduLevelSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /*check Education Board exists by name*/
    @RequestMapping(value = "/eduLevel/checkByName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkEduLevelByName(@RequestParam String value) {

        EduLevel eduLevel = eduLevelRepository.findOneByName(value.toLowerCase());

        Map map =new HashMap();

        map.put("value", value);

        if(eduLevel == null){
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);

        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }

    }
}
