package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.FeePaymentCollection;
import gov.step.app.repository.FeePaymentCollectionRepository;
import gov.step.app.repository.search.FeePaymentCollectionSearchRepository;
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
 * REST controller for managing FeePaymentCollection.
 */
@RestController
@RequestMapping("/api")
public class FeePaymentCollectionResource {

    private final Logger log = LoggerFactory.getLogger(FeePaymentCollectionResource.class);

    @Inject
    private FeePaymentCollectionRepository feePaymentCollectionRepository;

    @Inject
    private FeePaymentCollectionSearchRepository feePaymentCollectionSearchRepository;

    /**
     * POST  /feePaymentCollections -> Create a new feePaymentCollection.
     */
    @RequestMapping(value = "/feePaymentCollections",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCollection> createFeePaymentCollection(@Valid @RequestBody FeePaymentCollection feePaymentCollection) throws URISyntaxException {
        log.debug("REST request to save FeePaymentCollection : {}", feePaymentCollection);
        if (feePaymentCollection.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feePaymentCollection cannot already have an ID").body(null);
        }
        FeePaymentCollection result = feePaymentCollectionRepository.save(feePaymentCollection);
        feePaymentCollectionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feePaymentCollections/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feePaymentCollection", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feePaymentCollections -> Updates an existing feePaymentCollection.
     */
    @RequestMapping(value = "/feePaymentCollections",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCollection> updateFeePaymentCollection(@Valid @RequestBody FeePaymentCollection feePaymentCollection) throws URISyntaxException {
        log.debug("REST request to update FeePaymentCollection : {}", feePaymentCollection);
        if (feePaymentCollection.getId() == null) {
            return createFeePaymentCollection(feePaymentCollection);
        }
        FeePaymentCollection result = feePaymentCollectionRepository.save(feePaymentCollection);
        feePaymentCollectionSearchRepository.save(feePaymentCollection);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feePaymentCollection", feePaymentCollection.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feePaymentCollections -> get all the feePaymentCollections.
     */
    @RequestMapping(value = "/feePaymentCollections",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FeePaymentCollection>> getAllFeePaymentCollections(Pageable pageable)
        throws URISyntaxException {
        Page<FeePaymentCollection> page = feePaymentCollectionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feePaymentCollections");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feePaymentCollections/:id -> get the "id" feePaymentCollection.
     */
    @RequestMapping(value = "/feePaymentCollections/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeePaymentCollection> getFeePaymentCollection(@PathVariable Long id) {
        log.debug("REST request to get FeePaymentCollection : {}", id);
        return Optional.ofNullable(feePaymentCollectionRepository.findOne(id))
            .map(feePaymentCollection -> new ResponseEntity<>(
                feePaymentCollection,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feePaymentCollections/:id -> delete the "id" feePaymentCollection.
     */
    @RequestMapping(value = "/feePaymentCollections/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeePaymentCollection(@PathVariable Long id) {
        log.debug("REST request to delete FeePaymentCollection : {}", id);
        feePaymentCollectionRepository.delete(id);
        feePaymentCollectionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feePaymentCollection", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feePaymentCollections/:query -> search for the feePaymentCollection corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/feePaymentCollections/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FeePaymentCollection> searchFeePaymentCollections(@PathVariable String query) {
        return StreamSupport
            .stream(feePaymentCollectionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
