package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MiscConfigSetup;
import gov.step.app.repository.MiscConfigSetupRepository;
import gov.step.app.repository.search.MiscConfigSetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MiscConfigSetup.
 */
@RestController
@RequestMapping("/api")
public class MiscConfigSetupResource {

    private final Logger log = LoggerFactory.getLogger(MiscConfigSetupResource.class);

    @Inject
    private MiscConfigSetupRepository miscConfigSetupRepository;

    @Inject
    private MiscConfigSetupSearchRepository miscConfigSetupSearchRepository;

    /**
     * POST  /miscConfigSetups -> Create a new miscConfigSetup.
     */
    @RequestMapping(value = "/miscConfigSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscConfigSetup> createMiscConfigSetup(@Valid @RequestBody MiscConfigSetup miscConfigSetup) throws URISyntaxException {
        log.debug("REST request to save MiscConfigSetup : {}", miscConfigSetup);
        if (miscConfigSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new miscConfigSetup cannot already have an ID").body(null);
        }
        MiscConfigSetup result = miscConfigSetupRepository.save(miscConfigSetup);
        miscConfigSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/miscConfigSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("miscConfigSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /miscConfigSetups -> Updates an existing miscConfigSetup.
     */
    @RequestMapping(value = "/miscConfigSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscConfigSetup> updateMiscConfigSetup(@Valid @RequestBody MiscConfigSetup miscConfigSetup) throws URISyntaxException {
        log.debug("REST request to update MiscConfigSetup : {}", miscConfigSetup);
        if (miscConfigSetup.getId() == null) {
            return createMiscConfigSetup(miscConfigSetup);
        }
        MiscConfigSetup result = miscConfigSetupRepository.save(miscConfigSetup);
        miscConfigSetupSearchRepository.save(miscConfigSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("miscConfigSetup", miscConfigSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /miscConfigSetups -> get all the miscConfigSetups.
     */
    @RequestMapping(value = "/miscConfigSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MiscConfigSetup>> getAllMiscConfigSetups(Pageable pageable)
        throws URISyntaxException {
        Page<MiscConfigSetup> page = miscConfigSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/miscConfigSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /miscConfigSetups/:id -> get the "id" miscConfigSetup.
     */
    @RequestMapping(value = "/miscConfigSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscConfigSetup> getMiscConfigSetup(@PathVariable Long id) {
        log.debug("REST request to get MiscConfigSetup : {}", id);
        return Optional.ofNullable(miscConfigSetupRepository.findOne(id))
            .map(miscConfigSetup -> new ResponseEntity<>(
                miscConfigSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /miscConfigSetups/:id -> delete the "id" miscConfigSetup.
     */
    @RequestMapping(value = "/miscConfigSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMiscConfigSetup(@PathVariable Long id) {
        log.debug("REST request to delete MiscConfigSetup : {}", id);
        miscConfigSetupRepository.delete(id);
        miscConfigSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("miscConfigSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/miscConfigSetups/:query -> search for the miscConfigSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/miscConfigSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MiscConfigSetup> searchMiscConfigSetups(@PathVariable String query) {
        return StreamSupport
            .stream(miscConfigSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /miscConfigSetupsByStat -> get all the miscConfigSetupsByStat by Active Status
     */
    @RequestMapping(value = "/miscConfigSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MiscConfigSetup>> getAllMiscTypeSetupsByCategoryAndStatus(@PathVariable boolean stat , Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of MiscTypeSetups by stat:{}: ", stat);

        Page<MiscConfigSetup> page = miscConfigSetupRepository.findAllByActiveStatus(stat, pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/miscConfigSetupsByStat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/miscConfigSetupsByName/{name}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MiscConfigSetup> getMiscConfigByPropName(@PathVariable String name)
    {
        MiscConfigSetup miscConfigSetup = miscConfigSetupRepository.findOneByPropertyName(name);
        return Optional.ofNullable(miscConfigSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
