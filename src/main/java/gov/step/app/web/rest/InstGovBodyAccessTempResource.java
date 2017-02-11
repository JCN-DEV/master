package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstGovBodyAccessTemp;
import gov.step.app.repository.InstGovBodyAccessTempRepository;
import gov.step.app.repository.search.InstGovBodyAccessTempSearchRepository;
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
 * REST controller for managing InstGovBodyAccessTemp.
 */
@RestController
@RequestMapping("/api")
public class InstGovBodyAccessTempResource {

    private final Logger log = LoggerFactory.getLogger(InstGovBodyAccessTempResource.class);

    @Inject
    private InstGovBodyAccessTempRepository instGovBodyAccessTempRepository;

    @Inject
    private InstGovBodyAccessTempSearchRepository instGovBodyAccessTempSearchRepository;

    /**
     * POST  /instGovBodyAccessTemps -> Create a new instGovBodyAccessTemp.
     */
    @RequestMapping(value = "/instGovBodyAccessTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccessTemp> createInstGovBodyAccessTemp(@Valid @RequestBody InstGovBodyAccessTemp instGovBodyAccessTemp) throws URISyntaxException {
        log.debug("REST request to save InstGovBodyAccessTemp : {}", instGovBodyAccessTemp);
        if (instGovBodyAccessTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instGovBodyAccessTemp cannot already have an ID").body(null);
        }
        InstGovBodyAccessTemp result = instGovBodyAccessTempRepository.save(instGovBodyAccessTemp);
        instGovBodyAccessTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instGovBodyAccessTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instGovBodyAccessTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instGovBodyAccessTemps -> Updates an existing instGovBodyAccessTemp.
     */
    @RequestMapping(value = "/instGovBodyAccessTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccessTemp> updateInstGovBodyAccessTemp(@Valid @RequestBody InstGovBodyAccessTemp instGovBodyAccessTemp) throws URISyntaxException {
        log.debug("REST request to update InstGovBodyAccessTemp : {}", instGovBodyAccessTemp);
        if (instGovBodyAccessTemp.getId() == null) {
            return createInstGovBodyAccessTemp(instGovBodyAccessTemp);
        }
        InstGovBodyAccessTemp result = instGovBodyAccessTempRepository.save(instGovBodyAccessTemp);
        instGovBodyAccessTempSearchRepository.save(instGovBodyAccessTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGovBodyAccessTemp", instGovBodyAccessTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instGovBodyAccessTemps -> get all the instGovBodyAccessTemps.
     */
    @RequestMapping(value = "/instGovBodyAccessTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGovBodyAccessTemp>> getAllInstGovBodyAccessTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstGovBodyAccessTemp> page = instGovBodyAccessTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGovBodyAccessTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instGovBodyAccessTemps/:id -> get the "id" instGovBodyAccessTemp.
     */
    @RequestMapping(value = "/instGovBodyAccessTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccessTemp> getInstGovBodyAccessTemp(@PathVariable Long id) {
        log.debug("REST request to get InstGovBodyAccessTemp : {}", id);
        return Optional.ofNullable(instGovBodyAccessTempRepository.findOne(id))
            .map(instGovBodyAccessTemp -> new ResponseEntity<>(
                instGovBodyAccessTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instGovBodyAccessTemps/:id -> delete the "id" instGovBodyAccessTemp.
     */
    @RequestMapping(value = "/instGovBodyAccessTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstGovBodyAccessTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstGovBodyAccessTemp : {}", id);
        instGovBodyAccessTempRepository.delete(id);
        instGovBodyAccessTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instGovBodyAccessTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instGovBodyAccessTemps/:query -> search for the instGovBodyAccessTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instGovBodyAccessTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstGovBodyAccessTemp> searchInstGovBodyAccessTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instGovBodyAccessTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
