package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.VclVehicleDriverAssign;
import gov.step.app.repository.VclVehicleDriverAssignRepository;
import gov.step.app.repository.search.VclVehicleDriverAssignSearchRepository;
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
 * REST controller for managing VclVehicleDriverAssign.
 */
@RestController
@RequestMapping("/api")
public class VclVehicleDriverAssignResource {

    private final Logger log = LoggerFactory.getLogger(VclVehicleDriverAssignResource.class);
        
    @Inject
    private VclVehicleDriverAssignRepository vclVehicleDriverAssignRepository;
    
    @Inject
    private VclVehicleDriverAssignSearchRepository vclVehicleDriverAssignSearchRepository;
    
    /**
     * POST  /vclVehicleDriverAssigns -> Create a new vclVehicleDriverAssign.
     */
    @RequestMapping(value = "/vclVehicleDriverAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicleDriverAssign> createVclVehicleDriverAssign(@RequestBody VclVehicleDriverAssign vclVehicleDriverAssign) throws URISyntaxException {
        log.debug("REST request to save VclVehicleDriverAssign : {}", vclVehicleDriverAssign);
        if (vclVehicleDriverAssign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vclVehicleDriverAssign", "idexists", "A new vclVehicleDriverAssign cannot already have an ID")).body(null);
        }
        VclVehicleDriverAssign result = vclVehicleDriverAssignRepository.save(vclVehicleDriverAssign);
        vclVehicleDriverAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vclVehicleDriverAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vclVehicleDriverAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vclVehicleDriverAssigns -> Updates an existing vclVehicleDriverAssign.
     */
    @RequestMapping(value = "/vclVehicleDriverAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicleDriverAssign> updateVclVehicleDriverAssign(@RequestBody VclVehicleDriverAssign vclVehicleDriverAssign) throws URISyntaxException {
        log.debug("REST request to update VclVehicleDriverAssign : {}", vclVehicleDriverAssign);
        if (vclVehicleDriverAssign.getId() == null) {
            return createVclVehicleDriverAssign(vclVehicleDriverAssign);
        }
        VclVehicleDriverAssign result = vclVehicleDriverAssignRepository.save(vclVehicleDriverAssign);
        vclVehicleDriverAssignSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vclVehicleDriverAssign", vclVehicleDriverAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vclVehicleDriverAssigns -> get all the vclVehicleDriverAssigns.
     */
    @RequestMapping(value = "/vclVehicleDriverAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VclVehicleDriverAssign>> getAllVclVehicleDriverAssigns(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VclVehicleDriverAssigns");
        Page<VclVehicleDriverAssign> page = vclVehicleDriverAssignRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vclVehicleDriverAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vclVehicleDriverAssigns/:id -> get the "id" vclVehicleDriverAssign.
     */
    @RequestMapping(value = "/vclVehicleDriverAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclVehicleDriverAssign> getVclVehicleDriverAssign(@PathVariable Long id) {
        log.debug("REST request to get VclVehicleDriverAssign : {}", id);
        VclVehicleDriverAssign vclVehicleDriverAssign = vclVehicleDriverAssignRepository.findOne(id);
        return Optional.ofNullable(vclVehicleDriverAssign)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vclVehicleDriverAssigns/:id -> delete the "id" vclVehicleDriverAssign.
     */
    @RequestMapping(value = "/vclVehicleDriverAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVclVehicleDriverAssign(@PathVariable Long id) {
        log.debug("REST request to delete VclVehicleDriverAssign : {}", id);
        vclVehicleDriverAssignRepository.delete(id);
        vclVehicleDriverAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vclVehicleDriverAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vclVehicleDriverAssigns/:query -> search for the vclVehicleDriverAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vclVehicleDriverAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VclVehicleDriverAssign> searchVclVehicleDriverAssigns(@PathVariable String query) {
        log.debug("REST request to search VclVehicleDriverAssigns for query {}", query);
        return StreamSupport
            .stream(vclVehicleDriverAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
