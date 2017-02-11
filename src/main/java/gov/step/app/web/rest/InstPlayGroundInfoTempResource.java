package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstPlayGroundInfoTemp;
import gov.step.app.repository.InstPlayGroundInfoTempRepository;
import gov.step.app.repository.search.InstPlayGroundInfoTempSearchRepository;
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
 * REST controller for managing InstPlayGroundInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstPlayGroundInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstPlayGroundInfoTempResource.class);

    @Inject
    private InstPlayGroundInfoTempRepository instPlayGroundInfoTempRepository;

    @Inject
    private InstPlayGroundInfoTempSearchRepository instPlayGroundInfoTempSearchRepository;

    /**
     * POST  /instPlayGroundInfoTemps -> Create a new instPlayGroundInfoTemp.
     */
    @RequestMapping(value = "/instPlayGroundInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfoTemp> createInstPlayGroundInfoTemp(@RequestBody InstPlayGroundInfoTemp instPlayGroundInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstPlayGroundInfoTemp : {}", instPlayGroundInfoTemp);
        if (instPlayGroundInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instPlayGroundInfoTemp cannot already have an ID").body(null);
        }
        InstPlayGroundInfoTemp result = instPlayGroundInfoTempRepository.save(instPlayGroundInfoTemp);
        instPlayGroundInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instPlayGroundInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instPlayGroundInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instPlayGroundInfoTemps -> Updates an existing instPlayGroundInfoTemp.
     */
    @RequestMapping(value = "/instPlayGroundInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfoTemp> updateInstPlayGroundInfoTemp(@RequestBody InstPlayGroundInfoTemp instPlayGroundInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstPlayGroundInfoTemp : {}", instPlayGroundInfoTemp);
        if (instPlayGroundInfoTemp.getId() == null) {
            return createInstPlayGroundInfoTemp(instPlayGroundInfoTemp);
        }
        InstPlayGroundInfoTemp result = instPlayGroundInfoTempRepository.save(instPlayGroundInfoTemp);
        instPlayGroundInfoTempSearchRepository.save(instPlayGroundInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instPlayGroundInfoTemp", instPlayGroundInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instPlayGroundInfoTemps -> get all the instPlayGroundInfoTemps.
     */
    @RequestMapping(value = "/instPlayGroundInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstPlayGroundInfoTemp>> getAllInstPlayGroundInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstPlayGroundInfoTemp> page = instPlayGroundInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instPlayGroundInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instPlayGroundInfoTemps/:id -> get the "id" instPlayGroundInfoTemp.
     */
    @RequestMapping(value = "/instPlayGroundInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfoTemp> getInstPlayGroundInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstPlayGroundInfoTemp : {}", id);
        return Optional.ofNullable(instPlayGroundInfoTempRepository.findOne(id))
            .map(instPlayGroundInfoTemp -> new ResponseEntity<>(
                instPlayGroundInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instPlayGroundInfoTemps/:id -> delete the "id" instPlayGroundInfoTemp.
     */
    @RequestMapping(value = "/instPlayGroundInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstPlayGroundInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstPlayGroundInfoTemp : {}", id);
        instPlayGroundInfoTempRepository.delete(id);
        instPlayGroundInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instPlayGroundInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instPlayGroundInfoTemps/:query -> search for the instPlayGroundInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instPlayGroundInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstPlayGroundInfoTemp> searchInstPlayGroundInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instPlayGroundInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
