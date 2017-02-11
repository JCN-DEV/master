package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteGovernBody;
import gov.step.app.repository.InstituteGovernBodyRepository;
import gov.step.app.repository.search.InstituteGovernBodySearchRepository;
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
 * REST controller for managing InstituteGovernBody.
 */
@RestController
@RequestMapping("/api")
public class InstituteGovernBodyResource {

    private final Logger log = LoggerFactory.getLogger(InstituteGovernBodyResource.class);

    @Inject
    private InstituteGovernBodyRepository instituteGovernBodyRepository;

    @Inject
    private InstituteGovernBodySearchRepository instituteGovernBodySearchRepository;

    /**
     * POST  /instituteGovernBodys -> Create a new instituteGovernBody.
     */
    @RequestMapping(value = "/instituteGovernBodys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteGovernBody> createInstituteGovernBody(@Valid @RequestBody InstituteGovernBody instituteGovernBody) throws URISyntaxException {
        log.debug("REST request to save InstituteGovernBody : {}", instituteGovernBody);
        if (instituteGovernBody.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteGovernBody cannot already have an ID").body(null);
        }
        InstituteGovernBody result = instituteGovernBodyRepository.save(instituteGovernBody);
        instituteGovernBodySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteGovernBodys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteGovernBody", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteGovernBodys -> Updates an existing instituteGovernBody.
     */
    @RequestMapping(value = "/instituteGovernBodys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteGovernBody> updateInstituteGovernBody(@Valid @RequestBody InstituteGovernBody instituteGovernBody) throws URISyntaxException {
        log.debug("REST request to update InstituteGovernBody : {}", instituteGovernBody);
        if (instituteGovernBody.getId() == null) {
            return createInstituteGovernBody(instituteGovernBody);
        }
        InstituteGovernBody result = instituteGovernBodyRepository.save(instituteGovernBody);
        instituteGovernBodySearchRepository.save(instituteGovernBody);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteGovernBody", instituteGovernBody.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteGovernBodys -> get all the instituteGovernBodys.
     */
    @RequestMapping(value = "/instituteGovernBodys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteGovernBody>> getAllInstituteGovernBodys(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteGovernBody> page = instituteGovernBodyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteGovernBodys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteGovernBodys/:id -> get the "id" instituteGovernBody.
     */
    @RequestMapping(value = "/instituteGovernBodys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteGovernBody> getInstituteGovernBody(@PathVariable Long id) {
        log.debug("REST request to get InstituteGovernBody : {}", id);
        return Optional.ofNullable(instituteGovernBodyRepository.findOne(id))
            .map(instituteGovernBody -> new ResponseEntity<>(
                instituteGovernBody,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteGovernBodys/:id -> delete the "id" instituteGovernBody.
     */
    @RequestMapping(value = "/instituteGovernBodys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteGovernBody(@PathVariable Long id) {
        log.debug("REST request to delete InstituteGovernBody : {}", id);
        instituteGovernBodyRepository.delete(id);
        instituteGovernBodySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteGovernBody", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteGovernBodys/:query -> search for the instituteGovernBody corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteGovernBodys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteGovernBody> searchInstituteGovernBodys(@PathVariable String query) {
        return StreamSupport
            .stream(instituteGovernBodySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
