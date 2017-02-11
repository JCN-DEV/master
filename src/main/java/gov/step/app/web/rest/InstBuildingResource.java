package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstBuilding;
import gov.step.app.repository.InstBuildingRepository;
import gov.step.app.repository.search.InstBuildingSearchRepository;
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
 * REST controller for managing InstBuilding.
 */
@RestController
@RequestMapping("/api")
public class InstBuildingResource {

    private final Logger log = LoggerFactory.getLogger(InstBuildingResource.class);

    @Inject
    private InstBuildingRepository instBuildingRepository;

    @Inject
    private InstBuildingSearchRepository instBuildingSearchRepository;

    /**
     * POST  /instBuildings -> Create a new instBuilding.
     */
    @RequestMapping(value = "/instBuildings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuilding> createInstBuilding(@Valid @RequestBody InstBuilding instBuilding) throws URISyntaxException {
        log.debug("REST request to save InstBuilding : {}", instBuilding);
        if (instBuilding.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instBuilding cannot already have an ID").body(null);
        }
        InstBuilding result = instBuildingRepository.save(instBuilding);
        instBuildingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instBuildings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instBuilding", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instBuildings -> Updates an existing instBuilding.
     */
    @RequestMapping(value = "/instBuildings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuilding> updateInstBuilding(@Valid @RequestBody InstBuilding instBuilding) throws URISyntaxException {
        log.debug("REST request to update InstBuilding : {}", instBuilding);
        if (instBuilding.getId() == null) {
            return createInstBuilding(instBuilding);
        }
        InstBuilding result = instBuildingRepository.save(instBuilding);
        instBuildingSearchRepository.save(instBuilding);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instBuilding", instBuilding.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instBuildings -> get all the instBuildings.
     */
    @RequestMapping(value = "/instBuildings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstBuilding>> getAllInstBuildings(Pageable pageable)
        throws URISyntaxException {
        Page<InstBuilding> page = instBuildingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instBuildings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instBuildings/:id -> get the "id" instBuilding.
     */
    @RequestMapping(value = "/instBuildings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstBuilding> getInstBuilding(@PathVariable Long id) {
        log.debug("REST request to get InstBuilding : {}", id);
        return Optional.ofNullable(instBuildingRepository.findOne(id))
            .map(instBuilding -> new ResponseEntity<>(
                instBuilding,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instBuildings/:id -> delete the "id" instBuilding.
     */
    @RequestMapping(value = "/instBuildings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstBuilding(@PathVariable Long id) {
        log.debug("REST request to delete InstBuilding : {}", id);
        instBuildingRepository.delete(id);
        instBuildingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instBuilding", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instBuildings/:query -> search for the instBuilding corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instBuildings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstBuilding> searchInstBuildings(@PathVariable String query) {
        return StreamSupport
            .stream(instBuildingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
