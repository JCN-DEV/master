package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.VclVehicle;
import gov.step.app.repository.VclVehicleRepository;
import gov.step.app.repository.search.VclVehicleSearchRepository;
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
 * REST controller for managing VclVehicle.
 */
@RestController
@RequestMapping("/api")
public class VclVehicleResource {

    private final Logger log = LoggerFactory.getLogger(VclVehicleResource.class);
        
    @Inject
    private VclVehicleRepository vclVehicleRepository;
    
    @Inject
    private VclVehicleSearchRepository vclVehicleSearchRepository;
    
    /**
     * POST  /vclVehicles -> Create a new vclVehicle.
     */
    @RequestMapping(value = "/vclVehicles",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicle> createVclVehicle(@Valid @RequestBody VclVehicle vclVehicle) throws URISyntaxException {
        log.debug("REST request to save VclVehicle : {}", vclVehicle);
        if (vclVehicle.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vclVehicle", "idexists", "A new vclVehicle cannot already have an ID")).body(null);
        }
        VclVehicle result = vclVehicleRepository.save(vclVehicle);
        vclVehicleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vclVehicles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vclVehicle", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vclVehicles -> Updates an existing vclVehicle.
     */
    @RequestMapping(value = "/vclVehicles",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicle> updateVclVehicle(@Valid @RequestBody VclVehicle vclVehicle) throws URISyntaxException {
        log.debug("REST request to update VclVehicle : {}", vclVehicle);
        if (vclVehicle.getId() == null) {
            return createVclVehicle(vclVehicle);
        }
        VclVehicle result = vclVehicleRepository.save(vclVehicle);
        vclVehicleSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vclVehicle", vclVehicle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vclVehicles -> get all the vclVehicles.
     */
    @RequestMapping(value = "/vclVehicles",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VclVehicle>> getAllVclVehicles(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VclVehicles");
        Page<VclVehicle> page = vclVehicleRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vclVehicles");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vclVehicles/:id -> get the "id" vclVehicle.
     */
    @RequestMapping(value = "/vclVehicles/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicle> getVclVehicle(@PathVariable Long id) {
        log.debug("REST request to get VclVehicle : {}", id);
        VclVehicle vclVehicle = vclVehicleRepository.findOne(id);
        return Optional.ofNullable(vclVehicle)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vclVehicles/:id -> delete the "id" vclVehicle.
     */
    @RequestMapping(value = "/vclVehicles/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVclVehicle(@PathVariable Long id) {
        log.debug("REST request to delete VclVehicle : {}", id);
        vclVehicleRepository.delete(id);
        vclVehicleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vclVehicle", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vclVehicles/:query -> search for the vclVehicle corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vclVehicles/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VclVehicle> searchVclVehicles(@PathVariable String query) {
        log.debug("REST request to search VclVehicles for query {}", query);
        return StreamSupport
            .stream(vclVehicleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
