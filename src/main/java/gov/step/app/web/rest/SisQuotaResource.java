package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SisQuota;
import gov.step.app.repository.SisQuotaRepository;
import gov.step.app.repository.search.SisQuotaSearchRepository;
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
 * REST controller for managing SisQuota.
 */
@RestController
@RequestMapping("/api")
public class SisQuotaResource {

    private final Logger log = LoggerFactory.getLogger(SisQuotaResource.class);

    @Inject
    private SisQuotaRepository sisQuotaRepository;

    @Inject
    private SisQuotaSearchRepository sisQuotaSearchRepository;

    /**
     * POST  /sisQuotas -> Create a new sisQuota.
     */
    @RequestMapping(value = "/sisQuotas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisQuota> createSisQuota(@Valid @RequestBody SisQuota sisQuota) throws URISyntaxException {
        log.debug("REST request to save SisQuota : {}", sisQuota);
        if (sisQuota.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sisQuota cannot already have an ID").body(null);
        }
        SisQuota result = sisQuotaRepository.save(sisQuota);
        sisQuotaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sisQuotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sisQuota", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sisQuotas -> Updates an existing sisQuota.
     */
    @RequestMapping(value = "/sisQuotas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisQuota> updateSisQuota(@Valid @RequestBody SisQuota sisQuota) throws URISyntaxException {
        log.debug("REST request to update SisQuota : {}", sisQuota);
        if (sisQuota.getId() == null) {
            return createSisQuota(sisQuota);
        }
        SisQuota result = sisQuotaRepository.save(sisQuota);
        sisQuotaSearchRepository.save(sisQuota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sisQuota", sisQuota.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sisQuotas -> get all the sisQuotas.
     */
    @RequestMapping(value = "/sisQuotas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SisQuota>> getAllSisQuotas(Pageable pageable)
        throws URISyntaxException {
        Page<SisQuota> page = sisQuotaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sisQuotas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sisQuotas/:id -> get the "id" sisQuota.
     */
    @RequestMapping(value = "/sisQuotas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisQuota> getSisQuota(@PathVariable Long id) {
        log.debug("REST request to get SisQuota : {}", id);
        return Optional.ofNullable(sisQuotaRepository.findOne(id))
            .map(sisQuota -> new ResponseEntity<>(
                sisQuota,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sisQuotas/:id -> delete the "id" sisQuota.
     */
    @RequestMapping(value = "/sisQuotas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSisQuota(@PathVariable Long id) {
        log.debug("REST request to delete SisQuota : {}", id);
        sisQuotaRepository.delete(id);
        sisQuotaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sisQuota", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sisQuotas/:query -> search for the sisQuota corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/sisQuotas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SisQuota> searchSisQuotas(@PathVariable String query) {
        return StreamSupport
            .stream(sisQuotaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
