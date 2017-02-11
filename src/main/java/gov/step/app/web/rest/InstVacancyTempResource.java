package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstVacancyTemp;
import gov.step.app.repository.InstVacancyTempRepository;
import gov.step.app.repository.search.InstVacancyTempSearchRepository;
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
 * REST controller for managing InstVacancyTemp.
 */
@RestController
@RequestMapping("/api")
public class InstVacancyTempResource {

    private final Logger log = LoggerFactory.getLogger(InstVacancyTempResource.class);

    @Inject
    private InstVacancyTempRepository instVacancyTempRepository;

    @Inject
    private InstVacancyTempSearchRepository instVacancyTempSearchRepository;

    /**
     * POST  /instVacancyTemps -> Create a new instVacancyTemp.
     */
    @RequestMapping(value = "/instVacancyTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancyTemp> createInstVacancyTemp(@RequestBody InstVacancyTemp instVacancyTemp) throws URISyntaxException {
        log.debug("REST request to save InstVacancyTemp : {}", instVacancyTemp);
        if (instVacancyTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instVacancyTemp cannot already have an ID").body(null);
        }
        InstVacancyTemp result = instVacancyTempRepository.save(instVacancyTemp);
        instVacancyTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instVacancyTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instVacancyTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instVacancyTemps -> Updates an existing instVacancyTemp.
     */
    @RequestMapping(value = "/instVacancyTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancyTemp> updateInstVacancyTemp(@RequestBody InstVacancyTemp instVacancyTemp) throws URISyntaxException {
        log.debug("REST request to update InstVacancyTemp : {}", instVacancyTemp);
        if (instVacancyTemp.getId() == null) {
            return createInstVacancyTemp(instVacancyTemp);
        }
        InstVacancyTemp result = instVacancyTempRepository.save(instVacancyTemp);
        instVacancyTempSearchRepository.save(instVacancyTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instVacancyTemp", instVacancyTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instVacancyTemps -> get all the instVacancyTemps.
     */
    @RequestMapping(value = "/instVacancyTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstVacancyTemp>> getAllInstVacancyTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstVacancyTemp> page = instVacancyTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instVacancyTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instVacancyTemps/:id -> get the "id" instVacancyTemp.
     */
    @RequestMapping(value = "/instVacancyTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstVacancyTemp> getInstVacancyTemp(@PathVariable Long id) {
        log.debug("REST request to get InstVacancyTemp : {}", id);
        return Optional.ofNullable(instVacancyTempRepository.findOne(id))
            .map(instVacancyTemp -> new ResponseEntity<>(
                instVacancyTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instVacancyTemps/:id -> delete the "id" instVacancyTemp.
     */
    @RequestMapping(value = "/instVacancyTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstVacancyTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstVacancyTemp : {}", id);
        instVacancyTempRepository.delete(id);
        instVacancyTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instVacancyTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instVacancyTemps/:query -> search for the instVacancyTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instVacancyTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstVacancyTemp> searchInstVacancyTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instVacancyTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
