package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Religion;
import gov.step.app.repository.ReligionRepository;
import gov.step.app.repository.search.ReligionSearchRepository;
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
 * REST controller for managing Religion.
 */
@RestController
@RequestMapping("/api")
public class ReligionResource {

    private final Logger log = LoggerFactory.getLogger(ReligionResource.class);

    @Inject
    private ReligionRepository religionRepository;

    @Inject
    private ReligionSearchRepository religionSearchRepository;

    /**
     * POST  /religions -> Create a new religion.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> createReligion(@Valid @RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to save Religion : {}", religion);
        if (religion.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new religion cannot already have an ID").body(null);
        }
        Religion result = religionRepository.save(religion);
        religionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/religions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("religion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /religions -> Updates an existing religion.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> updateReligion(@Valid @RequestBody Religion religion) throws URISyntaxException {
        log.debug("REST request to update Religion : {}", religion);
        if (religion.getId() == null) {
            return createReligion(religion);
        }
        Religion result = religionRepository.save(religion);
        religionSearchRepository.save(religion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("religion", religion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /religions -> get all the religions.
     */
    @RequestMapping(value = "/religions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Religion>> getAllReligions(Pageable pageable)
        throws URISyntaxException {
        Page<Religion> page = religionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/religions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /religions/:id -> get the "id" religion.
     */
    @RequestMapping(value = "/religions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Religion> getReligion(@PathVariable Long id) {
        log.debug("REST request to get Religion : {}", id);
        return Optional.ofNullable(religionRepository.findOne(id))
            .map(religion -> new ResponseEntity<>(
                religion,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /religions/:id -> delete the "id" religion.
     */
    @RequestMapping(value = "/religions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReligion(@PathVariable Long id) {
        log.debug("REST request to delete Religion : {}", id);
        religionRepository.delete(id);
        religionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("religion", id.toString())).build();
    }

    /**
     * SEARCH  /_search/religions/:query -> search for the religion corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/religions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Religion> searchReligions(@PathVariable String query) {
        return StreamSupport
            .stream(religionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
