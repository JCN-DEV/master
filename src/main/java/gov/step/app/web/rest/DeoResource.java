package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Deo;
import gov.step.app.repository.DeoRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.DeoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.UserService;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing Deo.
 */
@RestController
@RequestMapping("/api")
public class DeoResource {

    private final Logger log = LoggerFactory.getLogger(DeoResource.class);

    @Inject
    private DeoRepository deoRepository;

    @Inject
    private DeoSearchRepository deoSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /deos -> Create a new deo.
     */
    @RequestMapping(value = "/deos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deo> createDeo(@Valid @RequestBody Deo deo) throws URISyntaxException {
        log.debug("REST request to save Deo : {}", deo);
        if (deo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new deo cannot already have an ID").body(null);
        }
        deo.setCreatedBy(userRepository.findOneByLogin(SecurityUtils.getCurrentUser().getUsername()).get());
        deo.setStatus(0);
        deo.setActivated(true);
        deo.setDateCrated(LocalDate.now());
        deo.setDateModified(LocalDate.now());
        Deo result = deoRepository.save(deo);
        deoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/deos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deos -> Updates an existing deo.
     */
    @RequestMapping(value = "/deos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deo> updateDeo(@Valid @RequestBody Deo deo) throws URISyntaxException {
        log.debug("REST request to update Deo : {}", deo);
        if (deo.getId() == null) {
            return createDeo(deo);
        }
        deo.setDateModified(LocalDate.now());
        deo.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        Deo result = deoRepository.save(deo);
        deoSearchRepository.save(deo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deo", deo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deos -> get all the deos.
     */
    @RequestMapping(value = "/deos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Deo>> getAllDeos(Pageable pageable)
        throws URISyntaxException {
        Page<Deo> page = deoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /deos/:id -> get the "id" deo.
     */
    @RequestMapping(value = "/deos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Deo> getDeo(@PathVariable Long id) {
        log.debug("REST request to get Deo : {}", id);
        return Optional.ofNullable(deoRepository.findOne(id))
            .map(deo -> new ResponseEntity<>(
                deo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deos/:id -> delete the "id" deo.
     */
    @RequestMapping(value = "/deos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeo(@PathVariable Long id) {
        log.debug("REST request to delete Deo : {}", id);
        deoRepository.delete(id);
        deoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/deos/:query -> search for the deo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/deos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Deo> searchDeos(@PathVariable String query) {
        return StreamSupport
            .stream(deoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
