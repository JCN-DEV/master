package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsPenGrSetup;
import gov.step.app.domain.PgmsPenGrRate;
import gov.step.app.repository.PgmsPenGrSetupRepository;
import gov.step.app.repository.PgmsPenGrRateRepository;
import gov.step.app.repository.search.PgmsPenGrSetupSearchRepository;
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
 * REST controller for managing PgmsPenGrSetup.
 */
@RestController
@RequestMapping("/api")
public class PgmsPenGrSetupResource {

    private final Logger log = LoggerFactory.getLogger(PgmsPenGrSetupResource.class);

    @Inject
    private PgmsPenGrSetupRepository pgmsPenGrSetupRepository;

    @Inject
    private PgmsPenGrRateRepository pgmsPenGrRateRepository;

    @Inject
    private PgmsPenGrSetupSearchRepository pgmsPenGrSetupSearchRepository;

    /**
     * POST  /pgmsPenGrSetups -> Create a new pgmsPenGrSetup.
     */
    @RequestMapping(value = "/pgmsPenGrSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrSetup> createPgmsPenGrSetup(@Valid @RequestBody PgmsPenGrSetup pgmsPenGrSetup) throws URISyntaxException {
        log.debug("REST request to save PgmsPenGrSetup : {}", pgmsPenGrSetup);
        if (pgmsPenGrSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsPenGrSetup cannot already have an ID").body(null);
        }
        PgmsPenGrSetup result = pgmsPenGrSetupRepository.save(pgmsPenGrSetup);
        pgmsPenGrSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsPenGrSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsPenGrSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsPenGrSetups -> Updates an existing pgmsPenGrSetup.
     */
    @RequestMapping(value = "/pgmsPenGrSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrSetup> updatePgmsPenGrSetup(@Valid @RequestBody PgmsPenGrSetup pgmsPenGrSetup) throws URISyntaxException {
        log.debug("REST request to update PgmsPenGrSetup : {}", pgmsPenGrSetup);
        if (pgmsPenGrSetup.getId() == null) {
            return createPgmsPenGrSetup(pgmsPenGrSetup);
        }
        PgmsPenGrSetup result = pgmsPenGrSetupRepository.save(pgmsPenGrSetup);
        pgmsPenGrSetupSearchRepository.save(pgmsPenGrSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsPenGrSetup", pgmsPenGrSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsPenGrSetups -> get all the pgmsPenGrSetups.
     */
    @RequestMapping(value = "/pgmsPenGrSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsPenGrSetup>> getAllPgmsPenGrSetups(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsPenGrSetup> page = pgmsPenGrSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsPenGrSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsPenGrSetups/:id -> get the "id" pgmsPenGrSetup.
     */
    @RequestMapping(value = "/pgmsPenGrSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrSetup> getPgmsPenGrSetup(@PathVariable Long id) {
        log.debug("REST request to get PgmsPenGrSetup : {}", id);
        return Optional.ofNullable(pgmsPenGrSetupRepository.findOne(id))
            .map(pgmsPenGrSetup -> new ResponseEntity<>(
                pgmsPenGrSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsPenGrSetups/:id -> delete the "id" pgmsPenGrSetup.
     */
    @RequestMapping(value = "/pgmsPenGrSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsPenGrSetup(@PathVariable Long id) {
        log.debug("REST request to delete PgmsPenGrSetup : {}", id);
        pgmsPenGrSetupRepository.delete(id);
        pgmsPenGrSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsPenGrSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsPenGrSetups/:query -> search for the pgmsPenGrSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsPenGrSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrSetup> searchPgmsPenGrSetups(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsPenGrSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search Application No from Putup List
     * to the query.
     */
    @RequestMapping(value = "/pgmsPenGrRateLists/{penGrSetId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrRate> getPgmsPenGrRateByFilter(@PathVariable long penGrSetId) {
        log.debug("REST request to get iisPutupListsWithApplicant : penGrSetId: {}", penGrSetId);
        List<PgmsPenGrRate> pgmsPrRatelist = pgmsPenGrRateRepository.findAllByPenGrSetIdOrderByWorkingYearAsc(penGrSetId);
        return pgmsPrRatelist;
    }
}
