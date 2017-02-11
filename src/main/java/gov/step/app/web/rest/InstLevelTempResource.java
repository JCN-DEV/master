package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLevelTemp;
import gov.step.app.repository.InstLevelTempRepository;
import gov.step.app.repository.search.InstLevelTempSearchRepository;
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
 * REST controller for managing InstLevelTemp.
 */
@RestController
@RequestMapping("/api")
public class InstLevelTempResource {

    private final Logger log = LoggerFactory.getLogger(InstLevelTempResource.class);

    @Inject
    private InstLevelTempRepository instLevelTempRepository;

    @Inject
    private InstLevelTempSearchRepository instLevelTempSearchRepository;

    /**
     * POST  /instLevelTemps -> Create a new instLevelTemp.
     */
    @RequestMapping(value = "/instLevelTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevelTemp> createInstLevelTemp(@RequestBody InstLevelTemp instLevelTemp) throws URISyntaxException {
        log.debug("REST request to save InstLevelTemp : {}", instLevelTemp);
        if (instLevelTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLevelTemp cannot already have an ID").body(null);
        }
        InstLevelTemp result = instLevelTempRepository.save(instLevelTemp);
        instLevelTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLevelTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLevelTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLevelTemps -> Updates an existing instLevelTemp.
     */
    @RequestMapping(value = "/instLevelTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevelTemp> updateInstLevelTemp(@RequestBody InstLevelTemp instLevelTemp) throws URISyntaxException {
        log.debug("REST request to update InstLevelTemp : {}", instLevelTemp);
        if (instLevelTemp.getId() == null) {
            return createInstLevelTemp(instLevelTemp);
        }
        InstLevelTemp result = instLevelTempRepository.save(instLevelTemp);
        instLevelTempSearchRepository.save(instLevelTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLevelTemp", instLevelTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLevelTemps -> get all the instLevelTemps.
     */
    @RequestMapping(value = "/instLevelTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLevelTemp>> getAllInstLevelTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstLevelTemp> page = instLevelTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLevelTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLevelTemps/:id -> get the "id" instLevelTemp.
     */
    @RequestMapping(value = "/instLevelTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLevelTemp> getInstLevelTemp(@PathVariable Long id) {
        log.debug("REST request to get InstLevelTemp : {}", id);
        return Optional.ofNullable(instLevelTempRepository.findOne(id))
            .map(instLevelTemp -> new ResponseEntity<>(
                instLevelTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instLevelTemps/:id -> delete the "id" instLevelTemp.
     */
    @RequestMapping(value = "/instLevelTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLevelTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstLevelTemp : {}", id);
        instLevelTempRepository.delete(id);
        instLevelTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLevelTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLevelTemps/:query -> search for the instLevelTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLevelTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLevelTemp> searchInstLevelTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instLevelTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
