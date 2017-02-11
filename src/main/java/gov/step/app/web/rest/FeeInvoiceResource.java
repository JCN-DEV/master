package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.FeeInvoice;
import gov.step.app.repository.FeeInvoiceRepository;
import gov.step.app.repository.search.FeeInvoiceSearchRepository;
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
 * REST controller for managing FeeInvoice.
 */
@RestController
@RequestMapping("/api")
public class FeeInvoiceResource {

    private final Logger log = LoggerFactory.getLogger(FeeInvoiceResource.class);

    @Inject
    private FeeInvoiceRepository feeInvoiceRepository;

    @Inject
    private FeeInvoiceSearchRepository feeInvoiceSearchRepository;

    /**
     * POST  /feeInvoices -> Create a new feeInvoice.
     */
    @RequestMapping(value = "/feeInvoices",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeeInvoice> createFeeInvoice(@Valid @RequestBody FeeInvoice feeInvoice) throws URISyntaxException {
        log.debug("REST request to save FeeInvoice : {}", feeInvoice);
        if (feeInvoice.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new feeInvoice cannot already have an ID").body(null);
        }
        FeeInvoice result = feeInvoiceRepository.save(feeInvoice);
        feeInvoiceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/feeInvoices/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("feeInvoice", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /feeInvoices -> Updates an existing feeInvoice.
     */
    @RequestMapping(value = "/feeInvoices",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeeInvoice> updateFeeInvoice(@Valid @RequestBody FeeInvoice feeInvoice) throws URISyntaxException {
        log.debug("REST request to update FeeInvoice : {}", feeInvoice);
        if (feeInvoice.getId() == null) {
            return createFeeInvoice(feeInvoice);
        }
        FeeInvoice result = feeInvoiceRepository.save(feeInvoice);
        feeInvoiceSearchRepository.save(feeInvoice);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("feeInvoice", feeInvoice.getId().toString()))
            .body(result);
    }

    /**
     * GET  /feeInvoices -> get all the feeInvoices.
     */
    @RequestMapping(value = "/feeInvoices",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<FeeInvoice>> getAllFeeInvoices(Pageable pageable)
        throws URISyntaxException {
        Page<FeeInvoice> page = feeInvoiceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/feeInvoices");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /feeInvoices/:id -> get the "id" feeInvoice.
     */
    @RequestMapping(value = "/feeInvoices/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<FeeInvoice> getFeeInvoice(@PathVariable Long id) {
        log.debug("REST request to get FeeInvoice : {}", id);
        return Optional.ofNullable(feeInvoiceRepository.findOne(id))
            .map(feeInvoice -> new ResponseEntity<>(
                feeInvoice,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /feeInvoices/:id -> delete the "id" feeInvoice.
     */
    @RequestMapping(value = "/feeInvoices/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFeeInvoice(@PathVariable Long id) {
        log.debug("REST request to delete FeeInvoice : {}", id);
        feeInvoiceRepository.delete(id);
        feeInvoiceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("feeInvoice", id.toString())).build();
    }

    /**
     * SEARCH  /_search/feeInvoices/:query -> search for the feeInvoice corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/feeInvoices/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<FeeInvoice> searchFeeInvoices(@PathVariable String query) {
        return StreamSupport
            .stream(feeInvoiceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
