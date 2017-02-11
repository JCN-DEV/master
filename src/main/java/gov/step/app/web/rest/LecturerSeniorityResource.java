package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.LecturerSeniority;
import gov.step.app.repository.LecturerSeniorityRepository;
import gov.step.app.repository.search.LecturerSenioritySearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing LecturerSeniority.
 */
@RestController
@RequestMapping("/api")
public class LecturerSeniorityResource {

    private final Logger log = LoggerFactory.getLogger(LecturerSeniorityResource.class);

    @Inject
    private LecturerSeniorityRepository lecturerSeniorityRepository;

    @Inject
    private LecturerSenioritySearchRepository lecturerSenioritySearchRepository;

    /**
     * POST  /lecturerSenioritys -> Create a new lecturerSeniority.
     */
    @RequestMapping(value = "/lecturerSenioritys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LecturerSeniority> createLecturerSeniority(@Valid @RequestBody LecturerSeniority lecturerSeniority) throws URISyntaxException {
        log.debug("REST request to save LecturerSeniority : {}", lecturerSeniority);
        if (lecturerSeniority.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new lecturerSeniority cannot already have an ID").body(null);
        }
        LecturerSeniority result = lecturerSeniorityRepository.save(lecturerSeniority);
        lecturerSenioritySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/lecturerSenioritys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("lecturerSeniority", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lecturerSenioritys -> Updates an existing lecturerSeniority.
     */
    @RequestMapping(value = "/lecturerSenioritys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LecturerSeniority> updateLecturerSeniority(@Valid @RequestBody LecturerSeniority lecturerSeniority) throws URISyntaxException {
        log.debug("REST request to update LecturerSeniority : {}", lecturerSeniority);
        if (lecturerSeniority.getId() == null) {
            return createLecturerSeniority(lecturerSeniority);
        }
        LecturerSeniority result = lecturerSeniorityRepository.save(lecturerSeniority);
        lecturerSenioritySearchRepository.save(lecturerSeniority);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("lecturerSeniority", lecturerSeniority.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lecturerSenioritys -> get all the lecturerSenioritys.
     */
    @RequestMapping(value = "/lecturerSenioritys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<LecturerSeniority>> getAllLecturerSenioritys(Pageable pageable)
        throws URISyntaxException {
        Page<LecturerSeniority> page = lecturerSeniorityRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/lecturerSenioritys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /lecturerSenioritys/:id -> get the "id" lecturerSeniority.
     */
    @RequestMapping(value = "/lecturerSenioritys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<LecturerSeniority> getLecturerSeniority(@PathVariable Long id) {
        log.debug("REST request to get LecturerSeniority : {}", id);
        return Optional.ofNullable(lecturerSeniorityRepository.findOne(id))
            .map(lecturerSeniority -> new ResponseEntity<>(
                lecturerSeniority,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lecturerSenioritys/:id -> delete the "id" lecturerSeniority.
     */
    @RequestMapping(value = "/lecturerSenioritys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLecturerSeniority(@PathVariable Long id) {
        log.debug("REST request to delete LecturerSeniority : {}", id);
        lecturerSeniorityRepository.delete(id);
        lecturerSenioritySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("lecturerSeniority", id.toString())).build();
    }

    /**
     * SEARCH  /_search/lecturerSenioritys/:query -> search for the lecturerSeniority corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/lecturerSenioritys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<LecturerSeniority> searchLecturerSenioritys(@PathVariable String query) {
        return StreamSupport
            .stream(lecturerSenioritySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
