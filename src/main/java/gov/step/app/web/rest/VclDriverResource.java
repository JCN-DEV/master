package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.VclDriver;
import gov.step.app.repository.VclDriverRepository;
import gov.step.app.repository.search.VclDriverSearchRepository;
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
 * REST controller for managing VclDriver.
 */
@RestController
@RequestMapping("/api")
public class VclDriverResource {

    private final Logger log = LoggerFactory.getLogger(VclDriverResource.class);
        
    @Inject
    private VclDriverRepository vclDriverRepository;
    
    @Inject
    private VclDriverSearchRepository vclDriverSearchRepository;
    
    /**
     * POST  /vclDrivers -> Create a new vclDriver.
     */
    @RequestMapping(value = "/vclDrivers",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclDriver> createVclDriver(@Valid @RequestBody VclDriver vclDriver) throws URISyntaxException {
        log.debug("REST request to save VclDriver : {}", vclDriver);
        if (vclDriver.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vclDriver", "idexists", "A new vclDriver cannot already have an ID")).body(null);
        }
        VclDriver result = vclDriverRepository.save(vclDriver);
        vclDriverSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vclDrivers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vclDriver", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vclDrivers -> Updates an existing vclDriver.
     */
    @RequestMapping(value = "/vclDrivers",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclDriver> updateVclDriver(@Valid @RequestBody VclDriver vclDriver) throws URISyntaxException {
        log.debug("REST request to update VclDriver : {}", vclDriver);
        if (vclDriver.getId() == null) {
            return createVclDriver(vclDriver);
        }
        VclDriver result = vclDriverRepository.save(vclDriver);
        vclDriverSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vclDriver", vclDriver.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vclDrivers -> get all the vclDrivers.
     */
    @RequestMapping(value = "/vclDrivers",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VclDriver>> getAllVclDrivers(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VclDrivers");
        Page<VclDriver> page = vclDriverRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vclDrivers");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vclDrivers/:id -> get the "id" vclDriver.
     */
    @RequestMapping(value = "/vclDrivers/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclDriver> getVclDriver(@PathVariable Long id) {
        log.debug("REST request to get VclDriver : {}", id);
        VclDriver vclDriver = vclDriverRepository.findOne(id);
        return Optional.ofNullable(vclDriver)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vclDrivers/:id -> delete the "id" vclDriver.
     */
    @RequestMapping(value = "/vclDrivers/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVclDriver(@PathVariable Long id) {
        log.debug("REST request to delete VclDriver : {}", id);
        vclDriverRepository.delete(id);
        vclDriverSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vclDriver", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vclDrivers/:query -> search for the vclDriver corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vclDrivers/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VclDriver> searchVclDrivers(@PathVariable String query) {
        log.debug("REST request to search VclDrivers for query {}", query);
        return StreamSupport
            .stream(vclDriverSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
