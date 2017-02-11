package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstAcaInfoTemp;
import gov.step.app.repository.InstAcaInfoTempRepository;
import gov.step.app.repository.search.InstAcaInfoTempSearchRepository;
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
 * REST controller for managing InstAcaInfoTemp.
 */
@RestController
@RequestMapping("/api")
public class InstAcaInfoTempResource {

    private final Logger log = LoggerFactory.getLogger(InstAcaInfoTempResource.class);

    @Inject
    private InstAcaInfoTempRepository instAcaInfoTempRepository;

    @Inject
    private InstAcaInfoTempSearchRepository instAcaInfoTempSearchRepository;

    /**
     * POST  /instAcaInfoTemps -> Create a new instAcaInfoTemp.
     */
    @RequestMapping(value = "/instAcaInfoTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfoTemp> createInstAcaInfoTemp(@RequestBody InstAcaInfoTemp instAcaInfoTemp) throws URISyntaxException {
        log.debug("REST request to save InstAcaInfoTemp : {}", instAcaInfoTemp);
        if (instAcaInfoTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instAcaInfoTemp cannot already have an ID").body(null);
        }
        InstAcaInfoTemp result = instAcaInfoTempRepository.save(instAcaInfoTemp);
        instAcaInfoTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instAcaInfoTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instAcaInfoTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instAcaInfoTemps -> Updates an existing instAcaInfoTemp.
     */
    @RequestMapping(value = "/instAcaInfoTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfoTemp> updateInstAcaInfoTemp(@RequestBody InstAcaInfoTemp instAcaInfoTemp) throws URISyntaxException {
        log.debug("REST request to update InstAcaInfoTemp : {}", instAcaInfoTemp);
        if (instAcaInfoTemp.getId() == null) {
            return createInstAcaInfoTemp(instAcaInfoTemp);
        }
        InstAcaInfoTemp result = instAcaInfoTempRepository.save(instAcaInfoTemp);
        instAcaInfoTempSearchRepository.save(instAcaInfoTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instAcaInfoTemp", instAcaInfoTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instAcaInfoTemps -> get all the instAcaInfoTemps.
     */
    @RequestMapping(value = "/instAcaInfoTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstAcaInfoTemp>> getAllInstAcaInfoTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstAcaInfoTemp> page = instAcaInfoTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instAcaInfoTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instAcaInfoTemps/:id -> get the "id" instAcaInfoTemp.
     */
    @RequestMapping(value = "/instAcaInfoTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstAcaInfoTemp> getInstAcaInfoTemp(@PathVariable Long id) {
        log.debug("REST request to get InstAcaInfoTemp : {}", id);
        return Optional.ofNullable(instAcaInfoTempRepository.findOne(id))
            .map(instAcaInfoTemp -> new ResponseEntity<>(
                instAcaInfoTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instAcaInfoTemps/:id -> delete the "id" instAcaInfoTemp.
     */
    @RequestMapping(value = "/instAcaInfoTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstAcaInfoTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstAcaInfoTemp : {}", id);
        instAcaInfoTempRepository.delete(id);
        instAcaInfoTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instAcaInfoTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instAcaInfoTemps/:query -> search for the instAcaInfoTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instAcaInfoTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstAcaInfoTemp> searchInstAcaInfoTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instAcaInfoTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
