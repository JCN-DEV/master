package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.domain.JpEmployeeReference;
import gov.step.app.repository.JpEmployeeReferenceRepository;
import gov.step.app.repository.search.JpEmployeeReferenceSearchRepository;
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
 * REST controller for managing JpEmployeeReference.
 */
@RestController
@RequestMapping("/api")
public class JpEmployeeReferenceResource {

    private final Logger log = LoggerFactory.getLogger(JpEmployeeReferenceResource.class);

    @Inject
    private JpEmployeeReferenceRepository jpEmployeeReferenceRepository;

    @Inject
    private JpEmployeeReferenceSearchRepository jpEmployeeReferenceSearchRepository;

    /**
     * POST  /jpEmployeeReferences -> Create a new jpEmployeeReference.
     */
    @RequestMapping(value = "/jpEmployeeReferences",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeReference> createJpEmployeeReference(@Valid @RequestBody JpEmployeeReference jpEmployeeReference) throws URISyntaxException {
        log.debug("REST request to save JpEmployeeReference : {}", jpEmployeeReference);
        if (jpEmployeeReference.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpEmployeeReference cannot already have an ID").body(null);
        }
        JpEmployeeReference result = jpEmployeeReferenceRepository.save(jpEmployeeReference);
        jpEmployeeReferenceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpEmployeeReferences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpEmployeeReference", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpEmployeeReferences -> Updates an existing jpEmployeeReference.
     */
    @RequestMapping(value = "/jpEmployeeReferences",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeReference> updateJpEmployeeReference(@Valid @RequestBody JpEmployeeReference jpEmployeeReference) throws URISyntaxException {
        log.debug("REST request to update JpEmployeeReference : {}", jpEmployeeReference);
        if (jpEmployeeReference.getId() == null) {
            return createJpEmployeeReference(jpEmployeeReference);
        }
        JpEmployeeReference result = jpEmployeeReferenceRepository.save(jpEmployeeReference);
        jpEmployeeReferenceSearchRepository.save(jpEmployeeReference);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpEmployeeReference", jpEmployeeReference.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpEmployeeReferences -> get all the jpEmployeeReferences.
     */
    @RequestMapping(value = "/jpEmployeeReferences",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpEmployeeReference>> getAllJpEmployeeReferences(Pageable pageable)
        throws URISyntaxException {
        Page<JpEmployeeReference> page = jpEmployeeReferenceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpEmployeeReferences");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpEmployeeReferences/:id -> get the "id" jpEmployeeReference.
     */
    @RequestMapping(value = "/jpEmployeeReferences/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeReference> getJpEmployeeReference(@PathVariable Long id) {
        log.debug("REST request to get JpEmployeeReference : {}", id);
        return Optional.ofNullable(jpEmployeeReferenceRepository.findOne(id))
            .map(jpEmployeeReference -> new ResponseEntity<>(
                jpEmployeeReference,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpEmployeeReferences/:id -> delete the "id" jpEmployeeReference.
     */
    @RequestMapping(value = "/jpEmployeeReferences/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpEmployeeReference(@PathVariable Long id) {
        log.debug("REST request to delete JpEmployeeReference : {}", id);
        jpEmployeeReferenceRepository.delete(id);
        jpEmployeeReferenceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpEmployeeReference", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpEmployeeReferences/:query -> search for the jpEmployeeReference corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpEmployeeReferences/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeReference> searchJpEmployeeReferences(@PathVariable String query) {
        return StreamSupport
            .stream(jpEmployeeReferenceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/references/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeReference> getAllEmployeeReferencesByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpEmployeeReference> experiences = jpEmployeeReferenceRepository.findByJpEmployee(id);
        return  experiences;
    }
}
