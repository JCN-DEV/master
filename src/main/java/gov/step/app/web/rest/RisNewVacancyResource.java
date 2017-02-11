package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.RisNewVacancy;
import gov.step.app.repository.RisNewVacancyRepository;
import gov.step.app.repository.search.RisNewVacancySearchRepository;
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
 * REST controller for managing RisNewVacancy.
 */
@RestController
@RequestMapping("/api")
public class RisNewVacancyResource {

    private final Logger log = LoggerFactory.getLogger(RisNewVacancyResource.class);

    @Inject
    private RisNewVacancyRepository risNewVacancyRepository;

    @Inject
    private RisNewVacancySearchRepository risNewVacancySearchRepository;

    /**
     * POST  /risNewVacancys -> Create a new risNewVacancy.
     */
    @RequestMapping(value = "/risNewVacancys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewVacancy> createRisNewVacancy(@RequestBody RisNewVacancy risNewVacancy) throws URISyntaxException {
        log.debug("REST request to save RisNewVacancy : {}", risNewVacancy);
        if (risNewVacancy.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new risNewVacancy cannot already have an ID").body(null);
        }
        RisNewVacancy result = risNewVacancyRepository.save(risNewVacancy);
        risNewVacancySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/risNewVacancys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("risNewVacancy", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /risNewVacancys -> Updates an existing risNewVacancy.
     */
    @RequestMapping(value = "/risNewVacancys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewVacancy> updateRisNewVacancy(@RequestBody RisNewVacancy risNewVacancy) throws URISyntaxException {
        log.debug("REST request to update RisNewVacancy : {}", risNewVacancy);
        if (risNewVacancy.getId() == null) {
            return createRisNewVacancy(risNewVacancy);
        }
        RisNewVacancy result = risNewVacancyRepository.save(risNewVacancy);
        risNewVacancySearchRepository.save(risNewVacancy);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("risNewVacancy", risNewVacancy.getId().toString()))
            .body(result);
    }

    /**
     * GET  /risNewVacancys -> get all the risNewVacancys.
     */
    @RequestMapping(value = "/risNewVacancys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<RisNewVacancy>> getAllRisNewVacancys(Pageable pageable)
        throws URISyntaxException {
        Page<RisNewVacancy> page = risNewVacancyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/risNewVacancys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /risNewVacancys/:id -> get the "id" risNewVacancy.
     */
    @RequestMapping(value = "/risNewVacancys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<RisNewVacancy> getRisNewVacancy(@PathVariable Long id) {
        log.debug("REST request to get RisNewVacancy : {}", id);
        return Optional.ofNullable(risNewVacancyRepository.findOne(id))
            .map(risNewVacancy -> new ResponseEntity<>(
                risNewVacancy,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /risNewVacancys/:id -> delete the "id" risNewVacancy.
     */
    @RequestMapping(value = "/risNewVacancys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRisNewVacancy(@PathVariable Long id) {
        log.debug("REST request to delete RisNewVacancy : {}", id);
        risNewVacancyRepository.delete(id);
        risNewVacancySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("risNewVacancy", id.toString())).build();
    }

    /**
     * SEARCH  /_search/risNewVacancys/:query -> search for the risNewVacancy corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/risNewVacancys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<RisNewVacancy> searchRisNewVacancys(@PathVariable String query) {
        return StreamSupport
            .stream(risNewVacancySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
