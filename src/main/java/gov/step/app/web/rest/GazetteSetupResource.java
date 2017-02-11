package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.GazetteSetup;
import gov.step.app.repository.GazetteSetupRepository;
import gov.step.app.repository.search.GazetteSetupSearchRepository;
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
 * REST controller for managing GazetteSetup.
 */
@RestController
@RequestMapping("/api")
public class GazetteSetupResource {

    private final Logger log = LoggerFactory.getLogger(GazetteSetupResource.class);

    @Inject
    private GazetteSetupRepository gazetteSetupRepository;

    @Inject
    private GazetteSetupSearchRepository gazetteSetupSearchRepository;

    /**
     * POST  /gazetteSetups -> Create a new gazetteSetup.
     */
    @RequestMapping(value = "/gazetteSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GazetteSetup> createGazetteSetup(@Valid @RequestBody GazetteSetup gazetteSetup) throws URISyntaxException {
        log.debug("REST request to save GazetteSetup : {}", gazetteSetup);
        if (gazetteSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new gazetteSetup cannot already have an ID").body(null);
        }
        GazetteSetup result = gazetteSetupRepository.save(gazetteSetup);
        gazetteSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/gazetteSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("gazetteSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /gazetteSetups -> Updates an existing gazetteSetup.
     */
    @RequestMapping(value = "/gazetteSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GazetteSetup> updateGazetteSetup(@Valid @RequestBody GazetteSetup gazetteSetup) throws URISyntaxException {
        log.debug("REST request to update GazetteSetup : {}", gazetteSetup);
        if (gazetteSetup.getId() == null) {
            return createGazetteSetup(gazetteSetup);
        }
        GazetteSetup result = gazetteSetupRepository.save(gazetteSetup);
        gazetteSetupSearchRepository.save(gazetteSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("gazetteSetup", gazetteSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /gazetteSetups -> get all the gazetteSetups.
     */
    @RequestMapping(value = "/gazetteSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<GazetteSetup>> getAllGazetteSetups(Pageable pageable)
        throws URISyntaxException {
        Page<GazetteSetup> page = gazetteSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/gazetteSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /gazetteSetups/:id -> get the "id" gazetteSetup.
     */
    @RequestMapping(value = "/gazetteSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<GazetteSetup> getGazetteSetup(@PathVariable Long id) {
        log.debug("REST request to get GazetteSetup : {}", id);
        return Optional.ofNullable(gazetteSetupRepository.findOne(id))
            .map(gazetteSetup -> new ResponseEntity<>(
                gazetteSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /gazetteSetups/:id -> delete the "id" gazetteSetup.
     */
    @RequestMapping(value = "/gazetteSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteGazetteSetup(@PathVariable Long id) {
        log.debug("REST request to delete GazetteSetup : {}", id);
        gazetteSetupRepository.delete(id);
        gazetteSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("gazetteSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/gazetteSetups/:query -> search for the gazetteSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/gazetteSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<GazetteSetup> searchGazetteSetups(@PathVariable String query) {
        return StreamSupport
            .stream(gazetteSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
