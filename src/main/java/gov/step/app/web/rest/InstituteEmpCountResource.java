package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteEmpCount;
import gov.step.app.repository.InstituteEmpCountRepository;
import gov.step.app.repository.search.InstituteEmpCountSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstituteEmpCount.
 */
@RestController
@RequestMapping("/api")
public class InstituteEmpCountResource {

    private final Logger log = LoggerFactory.getLogger(InstituteEmpCountResource.class);

    @Inject
    private InstituteEmpCountRepository instituteEmpCountRepository;

    @Inject
    private InstituteEmpCountSearchRepository instituteEmpCountSearchRepository;

    /**
     * POST  /instituteEmpCounts -> Create a new instituteEmpCount.
     */
    @RequestMapping(value = "/instituteEmpCounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteEmpCount> createInstituteEmpCount(@RequestBody InstituteEmpCount instituteEmpCount) throws URISyntaxException {
        log.debug("REST request to save InstituteEmpCount : {}", instituteEmpCount);
        if (instituteEmpCount.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteEmpCount cannot already have an ID").body(null);
        }
        InstituteEmpCount result = instituteEmpCountRepository.save(instituteEmpCount);
        instituteEmpCountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteEmpCounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteEmpCount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteEmpCounts -> Updates an existing instituteEmpCount.
     */
    @RequestMapping(value = "/instituteEmpCounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteEmpCount> updateInstituteEmpCount(@RequestBody InstituteEmpCount instituteEmpCount) throws URISyntaxException {
        log.debug("REST request to update InstituteEmpCount : {}", instituteEmpCount);
        if (instituteEmpCount.getId() == null) {
            return createInstituteEmpCount(instituteEmpCount);
        }
        InstituteEmpCount result = instituteEmpCountRepository.save(instituteEmpCount);
        instituteEmpCountSearchRepository.save(instituteEmpCount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteEmpCount", instituteEmpCount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteEmpCounts -> get all the instituteEmpCounts.
     */
    @RequestMapping(value = "/instituteEmpCounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteEmpCount>> getAllInstituteEmpCounts(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteEmpCount> page = instituteEmpCountRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteEmpCounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteEmpCounts/:id -> get the "id" instituteEmpCount.
     */
    @RequestMapping(value = "/instituteEmpCounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteEmpCount> getInstituteEmpCount(@PathVariable Long id) {
        log.debug("REST request to get InstituteEmpCount : {}", id);
        return Optional.ofNullable(instituteEmpCountRepository.findOne(id))
            .map(instituteEmpCount -> new ResponseEntity<>(
                instituteEmpCount,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteEmpCounts/:id -> delete the "id" instituteEmpCount.
     */
    @RequestMapping(value = "/instituteEmpCounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteEmpCount(@PathVariable Long id) {
        log.debug("REST request to delete InstituteEmpCount : {}", id);
        instituteEmpCountRepository.delete(id);
        instituteEmpCountSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteEmpCount", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteEmpCounts/:query -> search for the instituteEmpCount corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteEmpCounts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteEmpCount> searchInstituteEmpCounts(@PathVariable String query) {
        return StreamSupport
            .stream(instituteEmpCountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    /**
     * GET  /instEmpCounts/:id -> get the "id" instEmpCount.
     */
    @RequestMapping(value = "/instituteEmpCountByInstituteId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteEmpCount> getInstEmpCountByInstituteId(@PathVariable Long id) {
        log.debug("REST request to get InstEmpCount : {}", id);
        return Optional.ofNullable(instituteEmpCountRepository.findOneByInstituteId(id))
            .map(instituteEmpCount -> new ResponseEntity<>(
                instituteEmpCount,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
