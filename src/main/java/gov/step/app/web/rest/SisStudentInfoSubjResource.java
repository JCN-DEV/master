package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SisStudentInfoSubj;
import gov.step.app.repository.SisStudentInfoSubjRepository;
import gov.step.app.repository.search.SisStudentInfoSubjSearchRepository;
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
 * REST controller for managing SisStudentInfoSubj.
 */
@RestController
@RequestMapping("/api")
public class SisStudentInfoSubjResource {

    private final Logger log = LoggerFactory.getLogger(SisStudentInfoSubjResource.class);

    @Inject
    private SisStudentInfoSubjRepository sisStudentInfoSubjRepository;

    @Inject
    private SisStudentInfoSubjSearchRepository sisStudentInfoSubjSearchRepository;

    /**
     * POST  /sisStudentInfoSubjs -> Create a new sisStudentInfoSubj.
     */
    @RequestMapping(value = "/sisStudentInfoSubjs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfoSubj> createSisStudentInfoSubj(@RequestBody SisStudentInfoSubj sisStudentInfoSubj) throws URISyntaxException {
        log.debug("REST request to save SisStudentInfoSubj : {}", sisStudentInfoSubj);
        if (sisStudentInfoSubj.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sisStudentInfoSubj cannot already have an ID").body(null);
        }
        SisStudentInfoSubj result = sisStudentInfoSubjRepository.save(sisStudentInfoSubj);
        sisStudentInfoSubjSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sisStudentInfoSubjs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sisStudentInfoSubj", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sisStudentInfoSubjs -> Updates an existing sisStudentInfoSubj.
     */
    @RequestMapping(value = "/sisStudentInfoSubjs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfoSubj> updateSisStudentInfoSubj(@RequestBody SisStudentInfoSubj sisStudentInfoSubj) throws URISyntaxException {
        log.debug("REST request to update SisStudentInfoSubj : {}", sisStudentInfoSubj);
        if (sisStudentInfoSubj.getId() == null) {
            return createSisStudentInfoSubj(sisStudentInfoSubj);
        }
        SisStudentInfoSubj result = sisStudentInfoSubjRepository.save(sisStudentInfoSubj);
        sisStudentInfoSubjSearchRepository.save(sisStudentInfoSubj);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sisStudentInfoSubj", sisStudentInfoSubj.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sisStudentInfoSubjs -> get all the sisStudentInfoSubjs.
     */
    @RequestMapping(value = "/sisStudentInfoSubjs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SisStudentInfoSubj>> getAllSisStudentInfoSubjs(Pageable pageable)
        throws URISyntaxException {
        Page<SisStudentInfoSubj> page = sisStudentInfoSubjRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sisStudentInfoSubjs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sisStudentInfoSubjs/:id -> get the "id" sisStudentInfoSubj.
     */
    @RequestMapping(value = "/sisStudentInfoSubjs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisStudentInfoSubj> getSisStudentInfoSubj(@PathVariable Long id) {
        log.debug("REST request to get SisStudentInfoSubj : {}", id);
        return Optional.ofNullable(sisStudentInfoSubjRepository.findOne(id))
            .map(sisStudentInfoSubj -> new ResponseEntity<>(
                sisStudentInfoSubj,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sisStudentInfoSubjs/:id -> delete the "id" sisStudentInfoSubj.
     */
    @RequestMapping(value = "/sisStudentInfoSubjs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSisStudentInfoSubj(@PathVariable Long id) {
        log.debug("REST request to delete SisStudentInfoSubj : {}", id);
        sisStudentInfoSubjRepository.delete(id);
        sisStudentInfoSubjSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sisStudentInfoSubj", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sisStudentInfoSubjs/:query -> search for the sisStudentInfoSubj corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/sisStudentInfoSubjs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SisStudentInfoSubj> searchSisStudentInfoSubjs(@PathVariable String query) {
        return StreamSupport
            .stream(sisStudentInfoSubjSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
