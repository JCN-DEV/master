package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstGovernBodyTemp;
import gov.step.app.repository.InstGovernBodyTempRepository;
import gov.step.app.repository.search.InstGovernBodyTempSearchRepository;
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
 * REST controller for managing InstGovernBodyTemp.
 */
@RestController
@RequestMapping("/api")
public class InstGovernBodyTempResource {

    private final Logger log = LoggerFactory.getLogger(InstGovernBodyTempResource.class);

    @Inject
    private InstGovernBodyTempRepository instGovernBodyTempRepository;

    @Inject
    private InstGovernBodyTempSearchRepository instGovernBodyTempSearchRepository;

    /**
     * POST  /instGovernBodyTemps -> Create a new instGovernBodyTemp.
     */
    @RequestMapping(value = "/instGovernBodyTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBodyTemp> createInstGovernBodyTemp(@Valid @RequestBody InstGovernBodyTemp instGovernBodyTemp) throws URISyntaxException {
        log.debug("REST request to save InstGovernBodyTemp : {}", instGovernBodyTemp);
        if (instGovernBodyTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instGovernBodyTemp cannot already have an ID").body(null);
        }
        InstGovernBodyTemp result = instGovernBodyTempRepository.save(instGovernBodyTemp);
        instGovernBodyTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instGovernBodyTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instGovernBodyTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instGovernBodyTemps -> Updates an existing instGovernBodyTemp.
     */
    @RequestMapping(value = "/instGovernBodyTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBodyTemp> updateInstGovernBodyTemp(@Valid @RequestBody InstGovernBodyTemp instGovernBodyTemp) throws URISyntaxException {
        log.debug("REST request to update InstGovernBodyTemp : {}", instGovernBodyTemp);
        if (instGovernBodyTemp.getId() == null) {
            return createInstGovernBodyTemp(instGovernBodyTemp);
        }
        InstGovernBodyTemp result = instGovernBodyTempRepository.save(instGovernBodyTemp);
        instGovernBodyTempSearchRepository.save(instGovernBodyTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGovernBodyTemp", instGovernBodyTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instGovernBodyTemps -> get all the instGovernBodyTemps.
     */
    @RequestMapping(value = "/instGovernBodyTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGovernBodyTemp>> getAllInstGovernBodyTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstGovernBodyTemp> page = instGovernBodyTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGovernBodyTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instGovernBodyTemps/:id -> get the "id" instGovernBodyTemp.
     */
    @RequestMapping(value = "/instGovernBodyTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovernBodyTemp> getInstGovernBodyTemp(@PathVariable Long id) {
        log.debug("REST request to get InstGovernBodyTemp : {}", id);
        return Optional.ofNullable(instGovernBodyTempRepository.findOne(id))
            .map(instGovernBodyTemp -> new ResponseEntity<>(
                instGovernBodyTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instGovernBodyTemps/:id -> delete the "id" instGovernBodyTemp.
     */
    @RequestMapping(value = "/instGovernBodyTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstGovernBodyTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstGovernBodyTemp : {}", id);
        instGovernBodyTempRepository.delete(id);
        instGovernBodyTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instGovernBodyTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instGovernBodyTemps/:query -> search for the instGovernBodyTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instGovernBodyTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstGovernBodyTemp> searchInstGovernBodyTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instGovernBodyTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
