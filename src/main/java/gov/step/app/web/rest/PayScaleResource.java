package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PayScale;
import gov.step.app.repository.PayScaleRepository;
import gov.step.app.repository.search.PayScaleSearchRepository;
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
 * REST controller for managing PayScale.
 */
@RestController
@RequestMapping("/api")
public class PayScaleResource {

    private final Logger log = LoggerFactory.getLogger(PayScaleResource.class);

    @Inject
    private PayScaleRepository payScaleRepository;

    @Inject
    private PayScaleSearchRepository payScaleSearchRepository;

    /**
     * POST  /payScales -> Create a new payScale.
     */
    @RequestMapping(value = "/payScales",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayScale> createPayScale(@Valid @RequestBody PayScale payScale) throws URISyntaxException {
        log.debug("REST request to save PayScale : {}", payScale);
        if (payScale.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new payScale cannot already have an ID").body(null);
        }
        PayScale result = payScaleRepository.save(payScale);
        payScaleSearchRepository.save(result);

        return ResponseEntity.created(new URI("/api/payScales/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("payScale", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /payScales -> Updates an existing payScale.
     */
    @RequestMapping(value = "/payScales",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayScale> updatePayScale(@Valid @RequestBody PayScale payScale) throws URISyntaxException {
        log.debug("REST request to update PayScale : {}", payScale);
        if (payScale.getId() == null) {
            return createPayScale(payScale);
        }
        PayScale result = payScaleRepository.save(payScale);
        payScaleSearchRepository.save(payScale);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("payScale", payScale.getId().toString()))
            .body(result);
    }

    /**
     * GET  /payScales -> get all the payScales.
     */
    @RequestMapping(value = "/payScales",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PayScale>> getAllPayScales(Pageable pageable)
        throws URISyntaxException {
        Page<PayScale> page = payScaleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payScales");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payScales -> get all the payScales.
     */
    @RequestMapping(value = "/payScales/activeGazette",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PayScale>> getAllPayScalesByActiveGazette(Pageable pageable)
        throws URISyntaxException {
        Page<PayScale> page = payScaleRepository.findAllByActiveGazette(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/payScales/activeGazette");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /payScales/:id -> get the "id" payScale.
     */
    @RequestMapping(value = "/payScales/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PayScale> getPayScale(@PathVariable Long id) {
        log.debug("REST request to get PayScale : {}", id);
        return Optional.ofNullable(payScaleRepository.findOne(id))
            .map(payScale -> new ResponseEntity<>(
                payScale,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /payScales/:id -> delete the "id" payScale.
     */
    @RequestMapping(value = "/payScales/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePayScale(@PathVariable Long id) {
        log.debug("REST request to delete PayScale : {}", id);
        payScaleRepository.delete(id);
        payScaleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("payScale", id.toString())).build();
    }

    /**
     * SEARCH  /_search/payScales/:query -> search for the payScale corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/payScales/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PayScale> searchPayScales(@PathVariable String query) {
        return StreamSupport
            .stream(payScaleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
