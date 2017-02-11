package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDepartmentalProceeding;
import gov.step.app.repository.HrDepartmentalProceedingRepository;
import gov.step.app.repository.search.HrDepartmentalProceedingSearchRepository;
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
 * REST controller for managing HrDepartmentalProceeding.
 */
@RestController
@RequestMapping("/api")
public class HrDepartmentalProceedingResource {

    private final Logger log = LoggerFactory.getLogger(HrDepartmentalProceedingResource.class);
        
    @Inject
    private HrDepartmentalProceedingRepository hrDepartmentalProceedingRepository;
    
    @Inject
    private HrDepartmentalProceedingSearchRepository hrDepartmentalProceedingSearchRepository;
    
    /**
     * POST  /hrDepartmentalProceedings -> Create a new hrDepartmentalProceeding.
     */
    @RequestMapping(value = "/hrDepartmentalProceedings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentalProceeding> createHrDepartmentalProceeding(@Valid @RequestBody HrDepartmentalProceeding hrDepartmentalProceeding) throws URISyntaxException {
        log.debug("REST request to save HrDepartmentalProceeding : {}", hrDepartmentalProceeding);
        if (hrDepartmentalProceeding.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrDepartmentalProceeding", "idexists", "A new hrDepartmentalProceeding cannot already have an ID")).body(null);
        }
        HrDepartmentalProceeding result = hrDepartmentalProceedingRepository.save(hrDepartmentalProceeding);
        hrDepartmentalProceedingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDepartmentalProceedings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDepartmentalProceeding", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDepartmentalProceedings -> Updates an existing hrDepartmentalProceeding.
     */
    @RequestMapping(value = "/hrDepartmentalProceedings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentalProceeding> updateHrDepartmentalProceeding(@Valid @RequestBody HrDepartmentalProceeding hrDepartmentalProceeding) throws URISyntaxException {
        log.debug("REST request to update HrDepartmentalProceeding : {}", hrDepartmentalProceeding);
        if (hrDepartmentalProceeding.getId() == null) {
            return createHrDepartmentalProceeding(hrDepartmentalProceeding);
        }
        HrDepartmentalProceeding result = hrDepartmentalProceedingRepository.save(hrDepartmentalProceeding);
        hrDepartmentalProceedingSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDepartmentalProceeding", hrDepartmentalProceeding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDepartmentalProceedings -> get all the hrDepartmentalProceedings.
     */
    @RequestMapping(value = "/hrDepartmentalProceedings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentalProceeding>> getAllHrDepartmentalProceedings(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrDepartmentalProceedings");
        Page<HrDepartmentalProceeding> page = hrDepartmentalProceedingRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentalProceedings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentalProceedings/:id -> get the "id" hrDepartmentalProceeding.
     */
    @RequestMapping(value = "/hrDepartmentalProceedings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentalProceeding> getHrDepartmentalProceeding(@PathVariable Long id) {
        log.debug("REST request to get HrDepartmentalProceeding : {}", id);
        HrDepartmentalProceeding hrDepartmentalProceeding = hrDepartmentalProceedingRepository.findOne(id);
        return Optional.ofNullable(hrDepartmentalProceeding)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDepartmentalProceedings/:id -> delete the "id" hrDepartmentalProceeding.
     */
    @RequestMapping(value = "/hrDepartmentalProceedings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDepartmentalProceeding(@PathVariable Long id) {
        log.debug("REST request to delete HrDepartmentalProceeding : {}", id);
        hrDepartmentalProceedingRepository.delete(id);
        hrDepartmentalProceedingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDepartmentalProceeding", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDepartmentalProceedings/:query -> search for the hrDepartmentalProceeding corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDepartmentalProceedings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentalProceeding> searchHrDepartmentalProceedings(@PathVariable String query) {
        log.debug("REST request to search HrDepartmentalProceedings for query {}", query);
        return StreamSupport
            .stream(hrDepartmentalProceedingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
