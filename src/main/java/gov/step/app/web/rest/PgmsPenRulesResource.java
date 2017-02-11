package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsPenRules;
import gov.step.app.repository.PgmsPenRulesRepository;
import gov.step.app.repository.search.PgmsPenRulesSearchRepository;
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
 * REST controller for managing PgmsPenRules.
 */
@RestController
@RequestMapping("/api")
public class PgmsPenRulesResource {

    private final Logger log = LoggerFactory.getLogger(PgmsPenRulesResource.class);

    @Inject
    private PgmsPenRulesRepository pgmsPenRulesRepository;

    @Inject
    private PgmsPenRulesSearchRepository pgmsPenRulesSearchRepository;

    /**
     * POST  /pgmsPenRuless -> Create a new pgmsPenRules.
     */
    @RequestMapping(value = "/pgmsPenRuless",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenRules> createPgmsPenRules(@Valid @RequestBody PgmsPenRules pgmsPenRules) throws URISyntaxException {
        log.debug("REST request to save PgmsPenRules : {}", pgmsPenRules);
        if (pgmsPenRules.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsPenRules cannot already have an ID").body(null);
        }
        PgmsPenRules result = pgmsPenRulesRepository.save(pgmsPenRules);
        pgmsPenRulesSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsPenRuless/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsPenRules", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsPenRuless -> Updates an existing pgmsPenRules.
     */
    @RequestMapping(value = "/pgmsPenRuless",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenRules> updatePgmsPenRules(@Valid @RequestBody PgmsPenRules pgmsPenRules) throws URISyntaxException {
        log.debug("REST request to update PgmsPenRules : {}", pgmsPenRules);
        if (pgmsPenRules.getId() == null) {
            return createPgmsPenRules(pgmsPenRules);
        }
        PgmsPenRules result = pgmsPenRulesRepository.save(pgmsPenRules);
        pgmsPenRulesSearchRepository.save(pgmsPenRules);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsPenRules", pgmsPenRules.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsPenRuless -> get all the pgmsPenRuless.
     */
    @RequestMapping(value = "/pgmsPenRuless",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsPenRules>> getAllPgmsPenRuless(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsPenRules> page = pgmsPenRulesRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsPenRuless");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsPenRuless/:id -> get the "id" pgmsPenRules.
     */
    @RequestMapping(value = "/pgmsPenRuless/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenRules> getPgmsPenRules(@PathVariable Long id) {
        log.debug("REST request to get PgmsPenRules : {}", id);
        return Optional.ofNullable(pgmsPenRulesRepository.findOne(id))
            .map(pgmsPenRules -> new ResponseEntity<>(
                pgmsPenRules,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsPenRuless/:id -> delete the "id" pgmsPenRules.
     */
    @RequestMapping(value = "/pgmsPenRuless/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsPenRules(@PathVariable Long id) {
        log.debug("REST request to delete PgmsPenRules : {}", id);
        pgmsPenRulesRepository.delete(id);
        pgmsPenRulesSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsPenRules", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsPenRuless/:query -> search for the pgmsPenRules corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsPenRuless/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenRules> searchPgmsPenRuless(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsPenRulesSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
