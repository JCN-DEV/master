package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.FeePaymentTypeSetup;
import gov.step.app.repository.FeePaymentTypeSetupRepository;
import gov.step.app.repository.search.FeePaymentTypeSetupSearchRepository;
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
 * REST controller for managing FeePaymentTypeSetup.
 */
@RestController
@RequestMapping("/api")
public class FeePaymentTypeSetupResource {

    private final Logger log = LoggerFactory.getLogger(FeePaymentTypeSetupResource.class);

    @Inject
    private FeePaymentTypeSetupRepository feePaymentTypeSetupRepository;

    @Inject
    private FeePaymentTypeSetupSearchRepository feePaymentTypeSetupSearchRepository;

    /**
     * POST  /feePaymentTypeSetups -> Create a new feePaymentTypeSetup.
     */
    @RequestMapping(value = "/feePaymentTypeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentTypeSetup> createFeePaymentTypeSetup(@Valid @RequestBody FeePaymentTypeSetup feePaymentTypeSetup) throws URISyntaxException {
        log.debug("REST request to save FeePaymentTypeSetup : {}", feePaymentTypeSetup);
        if (feePaymentTypeSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feePaymentTypeSetup cannot already have an ID").body(null);
        }
        FeePaymentTypeSetup result = feePaymentTypeSetupRepository.save(feePaymentTypeSetup);
        feePaymentTypeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feePaymentTypeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feePaymentTypeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feePaymentTypeSetups -> Updates an existing feePaymentTypeSetup.
     */
    @RequestMapping(value = "/feePaymentTypeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentTypeSetup> updateFeePaymentTypeSetup(@Valid @RequestBody FeePaymentTypeSetup feePaymentTypeSetup) throws URISyntaxException {
        log.debug("REST request to update FeePaymentTypeSetup : {}", feePaymentTypeSetup);
        if (feePaymentTypeSetup.getId() == null) {
            return createFeePaymentTypeSetup(feePaymentTypeSetup);
        }
        FeePaymentTypeSetup result = feePaymentTypeSetupRepository.save(feePaymentTypeSetup);
        feePaymentTypeSetupSearchRepository.save(feePaymentTypeSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feePaymentTypeSetup", feePaymentTypeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feePaymentTypeSetups -> get all the feePaymentTypeSetups.
     */
    @RequestMapping(value = "/feePaymentTypeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FeePaymentTypeSetup>> getAllFeePaymentTypeSetups(Pageable pageable)
        throws URISyntaxException {
        Page<FeePaymentTypeSetup> page = feePaymentTypeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feePaymentTypeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feePaymentTypeSetups/:id -> get the "id" feePaymentTypeSetup.
     */
    @RequestMapping(value = "/feePaymentTypeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentTypeSetup> getFeePaymentTypeSetup(@PathVariable Long id) {
        log.debug("REST request to get FeePaymentTypeSetup : {}", id);
        return Optional.ofNullable(feePaymentTypeSetupRepository.findOne(id))
            .map(feePaymentTypeSetup -> new ResponseEntity<>(
                feePaymentTypeSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feePaymentTypeSetups/:id -> delete the "id" feePaymentTypeSetup.
     */
    @RequestMapping(value = "/feePaymentTypeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeePaymentTypeSetup(@PathVariable Long id) {
        log.debug("REST request to delete FeePaymentTypeSetup : {}", id);
        feePaymentTypeSetupRepository.delete(id);
        feePaymentTypeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feePaymentTypeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feePaymentTypeSetups/:query -> search for the feePaymentTypeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/feePaymentTypeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FeePaymentTypeSetup> searchFeePaymentTypeSetups(@PathVariable String query) {
        return StreamSupport
            .stream(feePaymentTypeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
