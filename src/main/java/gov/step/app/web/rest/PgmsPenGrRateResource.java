package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsPenGrRate;
import gov.step.app.repository.PgmsPenGrRateRepository;
import gov.step.app.repository.search.PgmsPenGrRateSearchRepository;
import gov.step.app.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing PgmsPenGrRate.
 */
@RestController
@RequestMapping("/api")
public class PgmsPenGrRateResource {

    private final Logger log = LoggerFactory.getLogger(PgmsPenGrRateResource.class);

    @Inject
    private PgmsPenGrRateRepository pgmsPenGrRateRepository;

    @Inject
    private PgmsPenGrRateSearchRepository pgmsPenGrRateSearchRepository;

    /**
     * POST  /pgmsPenGrRates -> Create a new pgmsPenGrRate.
     */
    @RequestMapping(value = "/pgmsPenGrRates",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrRate> createPgmsPenGrRate(@Valid @RequestBody PgmsPenGrRate pgmsPenGrRate) throws URISyntaxException {
        log.debug("REST request to save PgmsPenGrRate : {}", pgmsPenGrRate);
        if (pgmsPenGrRate.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsPenGrRate cannot already have an ID").body(null);
        }
        PgmsPenGrRate result = pgmsPenGrRateRepository.save(pgmsPenGrRate);
        pgmsPenGrRateSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsPenGrRates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsPenGrRate", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsPenGrRates -> Updates an existing pgmsPenGrRate.
     */
    @RequestMapping(value = "/pgmsPenGrRates",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrRate> updatePgmsPenGrRate(@Valid @RequestBody PgmsPenGrRate pgmsPenGrRate) throws URISyntaxException {
        log.debug("REST request to update PgmsPenGrRate : {}", pgmsPenGrRate);
        if (pgmsPenGrRate.getId() == null) {
            return createPgmsPenGrRate(pgmsPenGrRate);
        }
        PgmsPenGrRate result = pgmsPenGrRateRepository.save(pgmsPenGrRate);
        pgmsPenGrRateSearchRepository.save(pgmsPenGrRate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsPenGrRate", pgmsPenGrRate.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsPenGrRates -> get all the pgmsPenGrRates.
     */
    @RequestMapping(value = "/pgmsPenGrRates",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrRate> getAllPgmsPenGrRates() {
        log.debug("REST request to get all PgmsPenGrRates");
        return pgmsPenGrRateRepository.findAll();
    }

    /**
     * GET  /pgmsPenGrRates/:id -> get the "id" pgmsPenGrRate.
     */
    @RequestMapping(value = "/pgmsPenGrRates/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrRate> getPgmsPenGrRate(@PathVariable Long id) {
        log.debug("REST request to get PgmsPenGrRate : {}", id);
        return Optional.ofNullable(pgmsPenGrRateRepository.findOne(id))
            .map(pgmsPenGrRate -> new ResponseEntity<>(
                pgmsPenGrRate,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsPenGrRates/:id -> delete the "id" pgmsPenGrRate.
     */
    @RequestMapping(value = "/pgmsPenGrRates/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsPenGrRate(@PathVariable Long id) {
        log.debug("REST request to delete PgmsPenGrRate : {}", id);
        pgmsPenGrRateRepository.delete(id);
        pgmsPenGrRateSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsPenGrRate", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsPenGrRates/:query -> search for the pgmsPenGrRate corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsPenGrRates/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrRate> searchPgmsPenGrRates(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsPenGrRateSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
