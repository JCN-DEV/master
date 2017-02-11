package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracActionSetup;
import gov.step.app.repository.UmracActionSetupRepository;
import gov.step.app.repository.search.UmracActionSetupSearchRepository;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing UmracActionSetup.
 */
@RestController
@RequestMapping("/api")
public class UmracActionSetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracActionSetupResource.class);

    @Inject
    private UmracActionSetupRepository umracActionSetupRepository;

    @Inject
    private UmracActionSetupSearchRepository umracActionSetupSearchRepository;

    /**
     * POST  /umracActionSetups -> Create a new umracActionSetup.
     */
    @RequestMapping(value = "/umracActionSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracActionSetup> createUmracActionSetup(@Valid @RequestBody UmracActionSetup umracActionSetup) throws URISyntaxException {
        log.debug("REST request to save UmracActionSetup : {}", umracActionSetup);
        if (umracActionSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracActionSetup cannot already have an ID").body(null);
        }
        umracActionSetup.setCreateBy(Long.parseLong("1"));
        //TransactionIdResource tir = new TransactionIdResource();
        //umracActionSetup.setActionId(tir.getGeneratedid("AID"));
        DateResource dr = new DateResource();
        umracActionSetup.setCreateDate(dr.getDateAsLocalDate());
        UmracActionSetup result = umracActionSetupRepository.save(umracActionSetup);
        umracActionSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracActionSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracActionSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracActionSetups -> Updates an existing umracActionSetup.
     */
    @RequestMapping(value = "/umracActionSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracActionSetup> updateUmracActionSetup(@Valid @RequestBody UmracActionSetup umracActionSetup) throws URISyntaxException {
        log.debug("REST request to update UmracActionSetup : {}", umracActionSetup);
        if (umracActionSetup.getId() == null) {
            return createUmracActionSetup(umracActionSetup);
        }
        umracActionSetup.setCreateBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracActionSetup.setCreateDate(dr.getDateAsLocalDate());

        UmracActionSetup result = umracActionSetupRepository.save(umracActionSetup);
        umracActionSetupSearchRepository.save(umracActionSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracActionSetup", umracActionSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracActionSetups -> get all the umracActionSetups.
     */
    @RequestMapping(value = "/umracActionSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracActionSetup>> getAllUmracActionSetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracActionSetup> page = umracActionSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracActionSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracActionSetups/:id -> get the "id" umracActionSetup.
     */
    @RequestMapping(value = "/umracActionSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracActionSetup> getUmracActionSetup(@PathVariable Long id) {
        log.debug("REST request to get UmracActionSetup : {}", id);
        return Optional.ofNullable(umracActionSetupRepository.findOne(id))
            .map(umracActionSetup -> new ResponseEntity<>(
                umracActionSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracActionSetups/:id -> delete the "id" umracActionSetup.
     */
    @RequestMapping(value = "/umracActionSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracActionSetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracActionSetup : {}", id);
        umracActionSetupRepository.delete(id);
        umracActionSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracActionSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracActionSetups/:query -> search for the umracActionSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracActionSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracActionSetup> searchUmracActionSetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracActionSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
