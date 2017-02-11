package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Division;
import gov.step.app.repository.DivisionRepository;
import gov.step.app.repository.search.DivisionSearchRepository;
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
 * REST controller for managing Division.
 */
@RestController
@RequestMapping("/api")
public class DivisionResource {

    private final Logger log = LoggerFactory.getLogger(DivisionResource.class);

    @Inject
    private DivisionRepository divisionRepository;

    @Inject
    private DivisionSearchRepository divisionSearchRepository;

    /**
     * POST  /divisions -> Create a new division.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> createDivision(@Valid @RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to save Division : {}", division);
        if (division.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new division cannot already have an ID").body(null);
        }
        Division result = divisionRepository.save(division);
        divisionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/divisions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("division", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /divisions -> Updates an existing division.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> updateDivision(@Valid @RequestBody Division division) throws URISyntaxException {
        log.debug("REST request to update Division : {}", division);
        if (division.getId() == null) {
            return createDivision(division);
        }
        Division result = divisionRepository.save(division);
        divisionSearchRepository.save(division);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("division", division.getId().toString()))
            .body(result);
    }

    /**
     * GET  /divisions -> get all the divisions.
     */
    @RequestMapping(value = "/divisions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Division>> getAllDivisions(Pageable pageable)
        throws URISyntaxException {
        Page<Division> page = divisionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/divisions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /divisions/country/:id -> get all division by country
     */
    @RequestMapping(value = "/divisions/country/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Division> getDivisionByCountry(@PathVariable Long id)
        throws URISyntaxException {
        List<Division> divisions = divisionRepository.findByCountry(id);
            return divisions;
    }


    /**
     * GET  /divisions/:id -> get the "id" division.
     */
    @RequestMapping(value = "/divisions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Division> getDivision(@PathVariable Long id) {
        log.debug("REST request to get Division : {}", id);
        return Optional.ofNullable(divisionRepository.findOne(id))
            .map(division -> new ResponseEntity<>(
                division,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /divisions/:id -> delete the "id" division.
     */
    @RequestMapping(value = "/divisions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDivision(@PathVariable Long id) {
        log.debug("REST request to delete Division : {}", id);
        divisionRepository.delete(id);
        divisionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("division", id.toString())).build();
    }

    /**
     * SEARCH  /_search/divisions/:query -> search for the division corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/divisions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Division> searchDivisions(@PathVariable String query) {
        return StreamSupport
            .stream(divisionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
