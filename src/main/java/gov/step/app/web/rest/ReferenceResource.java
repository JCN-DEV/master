package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Reference;
import gov.step.app.repository.ReferenceRepository;
import gov.step.app.repository.search.ReferenceSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
import org.w3c.dom.ls.LSInput;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Reference.
 */
@RestController
@RequestMapping("/api")
public class ReferenceResource {

    private final Logger log = LoggerFactory.getLogger(ReferenceResource.class);

    @Inject
    private ReferenceRepository referenceRepository;

    @Inject
    private ReferenceSearchRepository referenceSearchRepository;

    /**
     * POST  /references -> Create a new reference.
     */
    @RequestMapping(value = "/references",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reference> createReference(@Valid @RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to save Reference : {}", reference);
        if (reference.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new reference cannot already have an ID").body(null);
        }
        Reference result = referenceRepository.save(reference);
        referenceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/references/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("reference", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /references -> Updates an existing reference.
     */
    @RequestMapping(value = "/references",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reference> updateReference(@Valid @RequestBody Reference reference) throws URISyntaxException {
        log.debug("REST request to update Reference : {}", reference);
        if (reference.getId() == null) {
            return createReference(reference);
        }
        Reference result = referenceRepository.save(reference);
        referenceSearchRepository.save(reference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("reference", reference.getId().toString()))
            .body(result);
    }

    /**
     * GET  /references -> get all the references.
     */
    @RequestMapping(value = "/references",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Reference>> getAllReferences(Pageable pageable)
        throws URISyntaxException {

        Page<Reference> page = referenceRepository.findByEmployeeUserIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER))
            page = referenceRepository.findByManagerIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            page = referenceRepository.findAll(pageable);


        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/references");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /references/employee/:id -> get all reference by employee
     */
    @RequestMapping(value = "/references/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reference> getAllReferenceByemployee(@PathVariable Long id)
        throws URISyntaxException {
        List<Reference> references = referenceRepository.findByEmployee(id);
        return references;
    }

    /**
     * GET  /references/:id -> get the "id" reference.
     */
    @RequestMapping(value = "/references/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Reference> getReference(@PathVariable Long id) {
        log.debug("REST request to get Reference : {}", id);
        return Optional.ofNullable(referenceRepository.findOne(id))
            .map(reference -> new ResponseEntity<>(
                reference,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /references/:id -> delete the "id" reference.
     */
    @RequestMapping(value = "/references/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteReference(@PathVariable Long id) {
        log.debug("REST request to delete Reference : {}", id);
        referenceRepository.delete(id);
        referenceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("reference", id.toString())).build();
    }

    /**
     * SEARCH  /_search/references/:query -> search for the reference corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/references/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Reference> searchReferences(@PathVariable String query) {
        return StreamSupport
            .stream(referenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
