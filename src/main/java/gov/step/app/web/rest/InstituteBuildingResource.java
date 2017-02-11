package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteBuilding;
import gov.step.app.repository.InstituteBuildingRepository;
import gov.step.app.repository.search.InstituteBuildingSearchRepository;
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
 * REST controller for managing InstituteBuilding.
 */
@RestController
@RequestMapping("/api")
public class InstituteBuildingResource {

    private final Logger log = LoggerFactory.getLogger(InstituteBuildingResource.class);

    @Inject
    private InstituteBuildingRepository instituteBuildingRepository;

    @Inject
    private InstituteBuildingSearchRepository instituteBuildingSearchRepository;

    /**
     * POST  /instituteBuildings -> Create a new instituteBuilding.
     */
    @RequestMapping(value = "/instituteBuildings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteBuilding> createInstituteBuilding(@Valid @RequestBody InstituteBuilding instituteBuilding) throws URISyntaxException {
        log.debug("REST request to save InstituteBuilding : {}", instituteBuilding);
        if (instituteBuilding.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteBuilding cannot already have an ID").body(null);
        }
        InstituteBuilding result = instituteBuildingRepository.save(instituteBuilding);
        instituteBuildingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteBuildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteBuilding", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteBuildings -> Updates an existing instituteBuilding.
     */
    @RequestMapping(value = "/instituteBuildings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteBuilding> updateInstituteBuilding(@Valid @RequestBody InstituteBuilding instituteBuilding) throws URISyntaxException {
        log.debug("REST request to update InstituteBuilding : {}", instituteBuilding);
        if (instituteBuilding.getId() == null) {
            return createInstituteBuilding(instituteBuilding);
        }
        InstituteBuilding result = instituteBuildingRepository.save(instituteBuilding);
        instituteBuildingSearchRepository.save(instituteBuilding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteBuilding", instituteBuilding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteBuildings -> get all the instituteBuildings.
     */
    @RequestMapping(value = "/instituteBuildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteBuilding>> getAllInstituteBuildings(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteBuilding> page = instituteBuildingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteBuildings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteBuildings/:id -> get the "id" instituteBuilding.
     */
    @RequestMapping(value = "/instituteBuildings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteBuilding> getInstituteBuilding(@PathVariable Long id) {
        log.debug("REST request to get InstituteBuilding : {}", id);
        return Optional.ofNullable(instituteBuildingRepository.findOne(id))
            .map(instituteBuilding -> new ResponseEntity<>(
                instituteBuilding,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteBuildings/:id -> delete the "id" instituteBuilding.
     */
    @RequestMapping(value = "/instituteBuildings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteBuilding(@PathVariable Long id) {
        log.debug("REST request to delete InstituteBuilding : {}", id);
        instituteBuildingRepository.delete(id);
        instituteBuildingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteBuilding", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteBuildings/:query -> search for the instituteBuilding corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteBuildings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteBuilding> searchInstituteBuildings(@PathVariable String query) {
        return StreamSupport
            .stream(instituteBuildingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
