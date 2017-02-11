package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstMemShipTemp;
import gov.step.app.repository.InstMemShipTempRepository;
import gov.step.app.repository.search.InstMemShipTempSearchRepository;
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
 * REST controller for managing InstMemShipTemp.
 */
@RestController
@RequestMapping("/api")
public class InstMemShipTempResource {

    private final Logger log = LoggerFactory.getLogger(InstMemShipTempResource.class);

    @Inject
    private InstMemShipTempRepository instMemShipTempRepository;

    @Inject
    private InstMemShipTempSearchRepository instMemShipTempSearchRepository;

    /**
     * POST  /instMemShipTemps -> Create a new instMemShipTemp.
     */
    @RequestMapping(value = "/instMemShipTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShipTemp> createInstMemShipTemp(@RequestBody InstMemShipTemp instMemShipTemp) throws URISyntaxException {
        log.debug("REST request to save InstMemShipTemp : {}", instMemShipTemp);
        if (instMemShipTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instMemShipTemp cannot already have an ID").body(null);
        }
        InstMemShipTemp result = instMemShipTempRepository.save(instMemShipTemp);
        instMemShipTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instMemShipTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instMemShipTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instMemShipTemps -> Updates an existing instMemShipTemp.
     */
    @RequestMapping(value = "/instMemShipTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShipTemp> updateInstMemShipTemp(@RequestBody InstMemShipTemp instMemShipTemp) throws URISyntaxException {
        log.debug("REST request to update InstMemShipTemp : {}", instMemShipTemp);
        if (instMemShipTemp.getId() == null) {
            return createInstMemShipTemp(instMemShipTemp);
        }
        InstMemShipTemp result = instMemShipTempRepository.save(instMemShipTemp);
        instMemShipTempSearchRepository.save(instMemShipTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instMemShipTemp", instMemShipTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instMemShipTemps -> get all the instMemShipTemps.
     */
    @RequestMapping(value = "/instMemShipTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstMemShipTemp>> getAllInstMemShipTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstMemShipTemp> page = instMemShipTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instMemShipTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instMemShipTemps/:id -> get the "id" instMemShipTemp.
     */
    @RequestMapping(value = "/instMemShipTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstMemShipTemp> getInstMemShipTemp(@PathVariable Long id) {
        log.debug("REST request to get InstMemShipTemp : {}", id);
        return Optional.ofNullable(instMemShipTempRepository.findOne(id))
            .map(instMemShipTemp -> new ResponseEntity<>(
                instMemShipTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instMemShipTemps/:id -> delete the "id" instMemShipTemp.
     */
    @RequestMapping(value = "/instMemShipTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstMemShipTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstMemShipTemp : {}", id);
        instMemShipTempRepository.delete(id);
        instMemShipTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instMemShipTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instMemShipTemps/:query -> search for the instMemShipTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instMemShipTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstMemShipTemp> searchInstMemShipTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instMemShipTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
