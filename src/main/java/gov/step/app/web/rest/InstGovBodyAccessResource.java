package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstGovBodyAccess;
import gov.step.app.repository.InstGovBodyAccessRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.search.InstGovBodyAccessSearchRepository;
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
 * REST controller for managing InstGovBodyAccess.
 */
@RestController
@RequestMapping("/api")
public class InstGovBodyAccessResource {

    private final Logger log = LoggerFactory.getLogger(InstGovBodyAccessResource.class);

    @Inject
    private InstGovBodyAccessRepository instGovBodyAccessRepository;

    @Inject
    private InstGovBodyAccessSearchRepository instGovBodyAccessSearchRepository;

    @Inject
    private InstituteRepository instituteRepository;

    /**
     * POST  /instGovBodyAccesss -> Create a new instGovBodyAccess.
     */
    @RequestMapping(value = "/instGovBodyAccesss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccess> createInstGovBodyAccess(@Valid @RequestBody InstGovBodyAccess instGovBodyAccess) throws URISyntaxException {
        log.debug("REST request to save InstGovBodyAccess : {}", instGovBodyAccess);
        if (instGovBodyAccess.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instGovBodyAccess cannot already have an ID").body(null);
        }
        instGovBodyAccess.setInstitute(instituteRepository.findOneByUserIsCurrentUser());
        instGovBodyAccess.setStatus(0);
        instGovBodyAccess.setDateCreated(LocalDate.now());
        instGovBodyAccess.setDateModified(LocalDate.now());
        InstGovBodyAccess result = instGovBodyAccessRepository.save(instGovBodyAccess);
        instGovBodyAccessSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instGovBodyAccesss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instGovBodyAccess", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instGovBodyAccesss -> Updates an existing instGovBodyAccess.
     */
    @RequestMapping(value = "/instGovBodyAccesss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccess> updateInstGovBodyAccess(@Valid @RequestBody InstGovBodyAccess instGovBodyAccess) throws URISyntaxException {
        log.debug("REST request to update InstGovBodyAccess : {}", instGovBodyAccess);
        if (instGovBodyAccess.getId() == null) {
            return createInstGovBodyAccess(instGovBodyAccess);
        }
        InstGovBodyAccess result = instGovBodyAccessRepository.save(instGovBodyAccess);
        instGovBodyAccessSearchRepository.save(instGovBodyAccess);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instGovBodyAccess", instGovBodyAccess.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instGovBodyAccesss -> get all the instGovBodyAccesss.
     */
    @RequestMapping(value = "/instGovBodyAccesss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstGovBodyAccess>> getAllInstGovBodyAccesss(Pageable pageable)
        throws URISyntaxException {
        Page<InstGovBodyAccess> page = instGovBodyAccessRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instGovBodyAccesss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instGovBodyAccesss/:id -> get the "id" instGovBodyAccess.
     */
    @RequestMapping(value = "/instGovBodyAccesss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccess> getInstGovBodyAccess(@PathVariable Long id) {
        log.debug("REST request to get InstGovBodyAccess : {}", id);
        return Optional.ofNullable(instGovBodyAccessRepository.findOne(id))
            .map(instGovBodyAccess -> new ResponseEntity<>(
                instGovBodyAccess,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /instGovBodyAccesss/currentInstitute
     */
    @RequestMapping(value = "/instGovBodyAccesss/currentInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstGovBodyAccess> getInstGovBodyAccessOfCurrentInstitute() {
        log.debug("REST request to get InstGovBodyAccess : {}");
        return Optional.ofNullable(instGovBodyAccessRepository.findOneByCurrentInstitute())
            .map(instGovBodyAccess -> new ResponseEntity<>(
                instGovBodyAccess,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instGovBodyAccesss/:id -> delete the "id" instGovBodyAccess.
     */
    @RequestMapping(value = "/instGovBodyAccesss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstGovBodyAccess(@PathVariable Long id) {
        log.debug("REST request to delete InstGovBodyAccess : {}", id);
        instGovBodyAccessRepository.delete(id);
        instGovBodyAccessSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instGovBodyAccess", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instGovBodyAccesss/:query -> search for the instGovBodyAccess corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instGovBodyAccesss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstGovBodyAccess> searchInstGovBodyAccesss(@PathVariable String query) {
        return StreamSupport
            .stream(instGovBodyAccessSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
