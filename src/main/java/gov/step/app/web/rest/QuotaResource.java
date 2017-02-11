package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Quota;
import gov.step.app.repository.QuotaRepository;
import gov.step.app.repository.search.QuotaSearchRepository;
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
 * REST controller for managing Quota.
 */
@RestController
@RequestMapping("/api")
public class QuotaResource {

    private final Logger log = LoggerFactory.getLogger(QuotaResource.class);

    @Inject
    private QuotaRepository quotaRepository;

    @Inject
    private QuotaSearchRepository quotaSearchRepository;

    /**
     * POST  /quotas -> Create a new quota.
     */
    @RequestMapping(value = "/quotas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quota> createQuota(@RequestBody Quota quota) throws URISyntaxException {
        log.debug("REST request to save Quota : {}", quota);
        if (quota.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new quota cannot already have an ID").body(null);
        }
        Quota result = quotaRepository.save(quota);
        quotaSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/quotas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("quota", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /quotas -> Updates an existing quota.
     */
    @RequestMapping(value = "/quotas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quota> updateQuota(@RequestBody Quota quota) throws URISyntaxException {
        log.debug("REST request to update Quota : {}", quota);
        if (quota.getId() == null) {
            return createQuota(quota);
        }
        Quota result = quotaRepository.save(quota);
        quotaSearchRepository.save(quota);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("quota", quota.getId().toString()))
            .body(result);
    }

    /**
     * GET  /quotas -> get all the quotas.
     */
    @RequestMapping(value = "/quotas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Quota>> getAllQuotas(Pageable pageable)
        throws URISyntaxException {
        Page<Quota> page = quotaRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/quotas");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /quotas/:id -> get the "id" quota.
     */
    @RequestMapping(value = "/quotas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Quota> getQuota(@PathVariable Long id) {
        log.debug("REST request to get Quota : {}", id);
        return Optional.ofNullable(quotaRepository.findOne(id))
            .map(quota -> new ResponseEntity<>(
                quota,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /quotas/:id -> delete the "id" quota.
     */
    @RequestMapping(value = "/quotas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteQuota(@PathVariable Long id) {
        log.debug("REST request to delete Quota : {}", id);
        quotaRepository.delete(id);
        quotaSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("quota", id.toString())).build();
    }

    /**
     * SEARCH  /_search/quotas/:query -> search for the quota corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/quotas/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Quota> searchQuotas(@PathVariable String query) {
        return StreamSupport
            .stream(quotaSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
