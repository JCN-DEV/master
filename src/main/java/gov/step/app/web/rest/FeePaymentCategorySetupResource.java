package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.FeePaymentCategorySetup;
import gov.step.app.repository.FeePaymentCategorySetupRepository;
import gov.step.app.repository.search.FeePaymentCategorySetupSearchRepository;
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
 * REST controller for managing FeePaymentCategorySetup.
 */
@RestController
@RequestMapping("/api")
public class FeePaymentCategorySetupResource {

    private final Logger log = LoggerFactory.getLogger(FeePaymentCategorySetupResource.class);

    @Inject
    private FeePaymentCategorySetupRepository feePaymentCategorySetupRepository;

    @Inject
    private FeePaymentCategorySetupSearchRepository feePaymentCategorySetupSearchRepository;

    /**
     * POST  /feePaymentCategorySetups -> Create a new feePaymentCategorySetup.
     */
    @RequestMapping(value = "/feePaymentCategorySetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCategorySetup> createFeePaymentCategorySetup(@Valid @RequestBody FeePaymentCategorySetup feePaymentCategorySetup) throws URISyntaxException {
        log.debug("REST request to save FeePaymentCategorySetup : {}", feePaymentCategorySetup);
        if (feePaymentCategorySetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feePaymentCategorySetup cannot already have an ID").body(null);
        }
        FeePaymentCategorySetup result = feePaymentCategorySetupRepository.save(feePaymentCategorySetup);
        feePaymentCategorySetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feePaymentCategorySetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feePaymentCategorySetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feePaymentCategorySetups -> Updates an existing feePaymentCategorySetup.
     */
    @RequestMapping(value = "/feePaymentCategorySetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCategorySetup> updateFeePaymentCategorySetup(@Valid @RequestBody FeePaymentCategorySetup feePaymentCategorySetup) throws URISyntaxException {
        log.debug("REST request to update FeePaymentCategorySetup : {}", feePaymentCategorySetup);
        if (feePaymentCategorySetup.getId() == null) {
            return createFeePaymentCategorySetup(feePaymentCategorySetup);
        }
        FeePaymentCategorySetup result = feePaymentCategorySetupRepository.save(feePaymentCategorySetup);
        feePaymentCategorySetupSearchRepository.save(feePaymentCategorySetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feePaymentCategorySetup", feePaymentCategorySetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feePaymentCategorySetups -> get all the feePaymentCategorySetups.
     */
    @RequestMapping(value = "/feePaymentCategorySetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FeePaymentCategorySetup>> getAllFeePaymentCategorySetups(Pageable pageable)
        throws URISyntaxException {
        Page<FeePaymentCategorySetup> page = feePaymentCategorySetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feePaymentCategorySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feePaymentCategorySetups/:id -> get the "id" feePaymentCategorySetup.
     */
    @RequestMapping(value = "/feePaymentCategorySetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCategorySetup> getFeePaymentCategorySetup(@PathVariable Long id) {
        log.debug("REST request to get FeePaymentCategorySetup : {}", id);
        return Optional.ofNullable(feePaymentCategorySetupRepository.findOne(id))
            .map(feePaymentCategorySetup -> new ResponseEntity<>(
                feePaymentCategorySetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feePaymentCategorySetups/:id -> delete the "id" feePaymentCategorySetup.
     */
    @RequestMapping(value = "/feePaymentCategorySetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeePaymentCategorySetup(@PathVariable Long id) {
        log.debug("REST request to delete FeePaymentCategorySetup : {}", id);
        feePaymentCategorySetupRepository.delete(id);
        feePaymentCategorySetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feePaymentCategorySetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feePaymentCategorySetups/:query -> search for the feePaymentCategorySetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/feePaymentCategorySetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FeePaymentCategorySetup> searchFeePaymentCategorySetups(@PathVariable String query) {
        return StreamSupport
            .stream(feePaymentCategorySetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
