package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstCategoryTemp;
import gov.step.app.repository.InstCategoryTempRepository;
import gov.step.app.repository.search.InstCategoryTempSearchRepository;
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
 * REST controller for managing InstCategoryTemp.
 */
@RestController
@RequestMapping("/api")
public class InstCategoryTempResource {

    private final Logger log = LoggerFactory.getLogger(InstCategoryTempResource.class);

    @Inject
    private InstCategoryTempRepository instCategoryTempRepository;

    @Inject
    private InstCategoryTempSearchRepository instCategoryTempSearchRepository;

    /**
     * POST  /instCategoryTemps -> Create a new instCategoryTemp.
     */
    @RequestMapping(value = "/instCategoryTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategoryTemp> createInstCategoryTemp(@RequestBody InstCategoryTemp instCategoryTemp) throws URISyntaxException {
        log.debug("REST request to save InstCategoryTemp : {}", instCategoryTemp);
        if (instCategoryTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instCategoryTemp cannot already have an ID").body(null);
        }
        InstCategoryTemp result = instCategoryTempRepository.save(instCategoryTemp);
        instCategoryTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instCategoryTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instCategoryTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instCategoryTemps -> Updates an existing instCategoryTemp.
     */
    @RequestMapping(value = "/instCategoryTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategoryTemp> updateInstCategoryTemp(@RequestBody InstCategoryTemp instCategoryTemp) throws URISyntaxException {
        log.debug("REST request to update InstCategoryTemp : {}", instCategoryTemp);
        if (instCategoryTemp.getId() == null) {
            return createInstCategoryTemp(instCategoryTemp);
        }
        InstCategoryTemp result = instCategoryTempRepository.save(instCategoryTemp);
        instCategoryTempSearchRepository.save(instCategoryTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instCategoryTemp", instCategoryTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instCategoryTemps -> get all the instCategoryTemps.
     */
    @RequestMapping(value = "/instCategoryTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstCategoryTemp>> getAllInstCategoryTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstCategoryTemp> page = instCategoryTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instCategoryTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instCategoryTemps/:id -> get the "id" instCategoryTemp.
     */
    @RequestMapping(value = "/instCategoryTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstCategoryTemp> getInstCategoryTemp(@PathVariable Long id) {
        log.debug("REST request to get InstCategoryTemp : {}", id);
        return Optional.ofNullable(instCategoryTempRepository.findOne(id))
            .map(instCategoryTemp -> new ResponseEntity<>(
                instCategoryTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instCategoryTemps/:id -> delete the "id" instCategoryTemp.
     */
    @RequestMapping(value = "/instCategoryTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstCategoryTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstCategoryTemp : {}", id);
        instCategoryTempRepository.delete(id);
        instCategoryTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instCategoryTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instCategoryTemps/:query -> search for the instCategoryTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instCategoryTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstCategoryTemp> searchInstCategoryTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instCategoryTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
