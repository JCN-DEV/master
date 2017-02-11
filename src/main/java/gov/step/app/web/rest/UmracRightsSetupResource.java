package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracRightsSetup;
import gov.step.app.repository.UmracRightsSetupRepository;
import gov.step.app.repository.search.UmracRightsSetupSearchRepository;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;

import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing UmracRightsSetup.
 */
@RestController
@RequestMapping("/api")
public class UmracRightsSetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracRightsSetupResource.class);

    @Inject
    private UmracRightsSetupRepository umracRightsSetupRepository;

    @Inject
    private UmracRightsSetupSearchRepository umracRightsSetupSearchRepository;

    /**
     * POST  /umracRightsSetups -> Create a new umracRightsSetup.
     */
    @RequestMapping(value = "/umracRightsSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsSetup> createUmracRightsSetup(@RequestBody UmracRightsSetup umracRightsSetup) throws URISyntaxException {
        log.debug("REST request to save UmracRightsSetup : {}", umracRightsSetup);
        if (umracRightsSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracRightsSetup cannot already have an ID").body(null);
        }

        umracRightsSetup.setCreateBy(Long.parseLong("1"));

        TransactionIdResource tir = new TransactionIdResource();
        umracRightsSetup.setRightId(tir.getGeneratedid("RIGHT"));
        DateResource dr = new DateResource();
        umracRightsSetup.setCreateDate(dr.getDateAsLocalDate());

        UmracRightsSetup result = umracRightsSetupRepository.save(umracRightsSetup);
        umracRightsSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracRightsSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracRightsSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracRightsSetups -> Updates an existing umracRightsSetup.
     */
    @RequestMapping(value = "/umracRightsSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsSetup> updateUmracRightsSetup(@RequestBody UmracRightsSetup umracRightsSetup) throws URISyntaxException {
        log.debug("REST request to update UmracRightsSetup : {}", umracRightsSetup);
        if (umracRightsSetup.getId() == null) {
            return createUmracRightsSetup(umracRightsSetup);
        }

        umracRightsSetup.setUpdatedBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracRightsSetup.setUpdatedTime(dr.getDateAsLocalDate());

        UmracRightsSetup result = umracRightsSetupRepository.save(umracRightsSetup);
        umracRightsSetupSearchRepository.save(umracRightsSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracRightsSetup", umracRightsSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracRightsSetups -> get all the umracRightsSetups.
     */
    @RequestMapping(value = "/umracRightsSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracRightsSetup>> getAllUmracRightsSetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracRightsSetup> page = umracRightsSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracRightsSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracRightsSetups/:id -> get the "id" umracRightsSetup.
     */
    @RequestMapping(value = "/umracRightsSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracRightsSetup> getUmracRightsSetup(@PathVariable Long id) {
        log.debug("REST request to get UmracRightsSetup : {}", id);
        return Optional.ofNullable(umracRightsSetupRepository.findOne(id))
            .map(umracRightsSetup -> new ResponseEntity<>(
                umracRightsSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracRightsSetups/:id -> delete the "id" umracRightsSetup.
     */
    @RequestMapping(value = "/umracRightsSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracRightsSetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracRightsSetup : {}", id);
        umracRightsSetupRepository.delete(id);
        umracRightsSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracRightsSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracRightsSetups/:query -> search for the umracRightsSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracRightsSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracRightsSetup> searchUmracRightsSetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracRightsSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
