package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Fee;
import gov.step.app.repository.FeeRepository;
import gov.step.app.repository.search.FeeSearchRepository;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Fee.
 */
@RestController
@RequestMapping("/api")
public class FeeResource {

    private final Logger log = LoggerFactory.getLogger(FeeResource.class);

    @Inject
    private FeeRepository feeRepository;

    @Inject
    private FeeSearchRepository feeSearchRepository;

    /**
     * POST  /fees -> Create a new fee.
     */
    @RequestMapping(value = "/fees",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fee> createFee(@RequestBody Fee fee) throws URISyntaxException {
        log.debug("REST request to save Fee : {}", fee);
        if (fee.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new fee cannot already have an ID").body(null);
        }
        Fee result = feeRepository.save(fee);
        feeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/fees/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("fee", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /fees -> Updates an existing fee.
     */
    @RequestMapping(value = "/fees",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fee> updateFee(@RequestBody Fee fee) throws URISyntaxException {
        log.debug("REST request to update Fee : {}", fee);
        if (fee.getId() == null) {
            return createFee(fee);
        }
        Fee result = feeRepository.save(fee);
        feeSearchRepository.save(fee);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("fee", fee.getId().toString()))
            .body(result);
    }

    /**
     * GET  /fees -> get all the fees.
     */
    @RequestMapping(value = "/fees",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Fee>> getAllFees(Pageable pageable)
        throws URISyntaxException {
        Page<Fee> page = feeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/fees");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /fees/:id -> get the "id" fee.
     */
    @RequestMapping(value = "/fees/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Fee> getFee(@PathVariable Long id) {
        log.debug("REST request to get Fee : {}", id);
        return Optional.ofNullable(feeRepository.findOne(id))
            .map(fee -> new ResponseEntity<>(
                fee,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /fees/:id -> delete the "id" fee.
     */
    @RequestMapping(value = "/fees/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteFee(@PathVariable Long id) {
        log.debug("REST request to delete Fee : {}", id);
        feeRepository.delete(id);
        feeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("fee", id.toString())).build();
    }

    /**
     * SEARCH  /_search/fees/:query -> search for the fee corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/fees/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Fee> searchFees(@PathVariable String query) {
        return StreamSupport
            .stream(feeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
