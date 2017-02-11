package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.CmsCurriculumRegCfg;
import gov.step.app.repository.CmsCurriculumRegCfgRepository;
import gov.step.app.repository.search.CmsCurriculumRegCfgSearchRepository;
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
 * REST controller for managing CmsCurriculumRegCfg.
 */
@RestController
@RequestMapping("/api")
public class CmsCurriculumRegCfgResource {

    private final Logger log = LoggerFactory.getLogger(CmsCurriculumRegCfgResource.class);

    @Inject
    private CmsCurriculumRegCfgRepository cmsCurriculumRegCfgRepository;

    @Inject
    private CmsCurriculumRegCfgSearchRepository cmsCurriculumRegCfgSearchRepository;

    /**
     * POST  /cmsCurriculumRegCfgs -> Create a new cmsCurriculumRegCfg.
     */
    @RequestMapping(value = "/cmsCurriculumRegCfgs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculumRegCfg> createCmsCurriculumRegCfg(@Valid @RequestBody CmsCurriculumRegCfg cmsCurriculumRegCfg) throws URISyntaxException {
        log.debug("REST request to save CmsCurriculumRegCfg : {}", cmsCurriculumRegCfg);
        if (cmsCurriculumRegCfg.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new cmsCurriculumRegCfg cannot already have an ID").body(null);
        }
        CmsCurriculumRegCfg result = cmsCurriculumRegCfgRepository.save(cmsCurriculumRegCfg);
        cmsCurriculumRegCfgSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/cmsCurriculumRegCfgs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("cmsCurriculumRegCfg", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cmsCurriculumRegCfgs -> Updates an existing cmsCurriculumRegCfg.
     */
    @RequestMapping(value = "/cmsCurriculumRegCfgs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculumRegCfg> updateCmsCurriculumRegCfg(@Valid @RequestBody CmsCurriculumRegCfg cmsCurriculumRegCfg) throws URISyntaxException {
        log.debug("REST request to update CmsCurriculumRegCfg : {}", cmsCurriculumRegCfg);
        if (cmsCurriculumRegCfg.getId() == null) {
            return createCmsCurriculumRegCfg(cmsCurriculumRegCfg);
        }
        CmsCurriculumRegCfg result = cmsCurriculumRegCfgRepository.save(cmsCurriculumRegCfg);
        cmsCurriculumRegCfgSearchRepository.save(cmsCurriculumRegCfg);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("cmsCurriculumRegCfg", cmsCurriculumRegCfg.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cmsCurriculumRegCfgs -> get all the cmsCurriculumRegCfgs.
     */
    @RequestMapping(value = "/cmsCurriculumRegCfgs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<CmsCurriculumRegCfg>> getAllCmsCurriculumRegCfgs(Pageable pageable)
        throws URISyntaxException {
        Page<CmsCurriculumRegCfg> page = cmsCurriculumRegCfgRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/cmsCurriculumRegCfgs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /cmsCurriculumRegCfgs/:id -> get the "id" cmsCurriculumRegCfg.
     */
    @RequestMapping(value = "/cmsCurriculumRegCfgs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<CmsCurriculumRegCfg> getCmsCurriculumRegCfg(@PathVariable Long id) {
        log.debug("REST request to get CmsCurriculumRegCfg : {}", id);
        return Optional.ofNullable(cmsCurriculumRegCfgRepository.findOne(id))
            .map(cmsCurriculumRegCfg -> new ResponseEntity<>(
                cmsCurriculumRegCfg,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /cmsCurriculumRegCfgs/:id -> delete the "id" cmsCurriculumRegCfg.
     */
    @RequestMapping(value = "/cmsCurriculumRegCfgs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteCmsCurriculumRegCfg(@PathVariable Long id) {
        log.debug("REST request to delete CmsCurriculumRegCfg : {}", id);
        cmsCurriculumRegCfgRepository.delete(id);
        cmsCurriculumRegCfgSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("cmsCurriculumRegCfg", id.toString())).build();
    }

    /**
     * SEARCH  /_search/cmsCurriculumRegCfgs/:query -> search for the cmsCurriculumRegCfg corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/cmsCurriculumRegCfgs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<CmsCurriculumRegCfg> searchCmsCurriculumRegCfgs(@PathVariable String query) {
        return StreamSupport
            .stream(cmsCurriculumRegCfgSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
