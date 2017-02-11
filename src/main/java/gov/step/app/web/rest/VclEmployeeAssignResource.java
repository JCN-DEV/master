package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.VclEmployeeAssign;
import gov.step.app.repository.VclEmployeeAssignRepository;
import gov.step.app.repository.search.VclEmployeeAssignSearchRepository;
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
 * REST controller for managing VclEmployeeAssign.
 */
@RestController
@RequestMapping("/api")
public class VclEmployeeAssignResource {

    private final Logger log = LoggerFactory.getLogger(VclEmployeeAssignResource.class);
        
    @Inject
    private VclEmployeeAssignRepository vclEmployeeAssignRepository;
    
    @Inject
    private VclEmployeeAssignSearchRepository vclEmployeeAssignSearchRepository;
    
    /**
     * POST  /vclEmployeeAssigns -> Create a new vclEmployeeAssign.
     */
    @RequestMapping(value = "/vclEmployeeAssigns",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclEmployeeAssign> createVclEmployeeAssign(@RequestBody VclEmployeeAssign vclEmployeeAssign) throws URISyntaxException {
        log.debug("REST request to save VclEmployeeAssign : {}", vclEmployeeAssign);
        if (vclEmployeeAssign.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vclEmployeeAssign", "idexists", "A new vclEmployeeAssign cannot already have an ID")).body(null);
        }
        VclEmployeeAssign result = vclEmployeeAssignRepository.save(vclEmployeeAssign);
        vclEmployeeAssignSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vclEmployeeAssigns/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vclEmployeeAssign", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vclEmployeeAssigns -> Updates an existing vclEmployeeAssign.
     */
    @RequestMapping(value = "/vclEmployeeAssigns",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclEmployeeAssign> updateVclEmployeeAssign(@RequestBody VclEmployeeAssign vclEmployeeAssign) throws URISyntaxException {
        log.debug("REST request to update VclEmployeeAssign : {}", vclEmployeeAssign);
        if (vclEmployeeAssign.getId() == null) {
            return createVclEmployeeAssign(vclEmployeeAssign);
        }
        VclEmployeeAssign result = vclEmployeeAssignRepository.save(vclEmployeeAssign);
        vclEmployeeAssignSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vclEmployeeAssign", vclEmployeeAssign.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vclEmployeeAssigns -> get all the vclEmployeeAssigns.
     */
    @RequestMapping(value = "/vclEmployeeAssigns",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VclEmployeeAssign>> getAllVclEmployeeAssigns(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VclEmployeeAssigns");
        Page<VclEmployeeAssign> page = vclEmployeeAssignRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vclEmployeeAssigns");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vclEmployeeAssigns/:id -> get the "id" vclEmployeeAssign.
     */
    @RequestMapping(value = "/vclEmployeeAssigns/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclEmployeeAssign> getVclEmployeeAssign(@PathVariable Long id) {
        log.debug("REST request to get VclEmployeeAssign : {}", id);
        VclEmployeeAssign vclEmployeeAssign = vclEmployeeAssignRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(vclEmployeeAssign)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vclEmployeeAssigns/:id -> delete the "id" vclEmployeeAssign.
     */
    @RequestMapping(value = "/vclEmployeeAssigns/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVclEmployeeAssign(@PathVariable Long id) {
        log.debug("REST request to delete VclEmployeeAssign : {}", id);
        vclEmployeeAssignRepository.delete(id);
        vclEmployeeAssignSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vclEmployeeAssign", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vclEmployeeAssigns/:query -> search for the vclEmployeeAssign corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vclEmployeeAssigns/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VclEmployeeAssign> searchVclEmployeeAssigns(@PathVariable String query) {
        log.debug("REST request to search VclEmployeeAssigns for query {}", query);
        return StreamSupport
            .stream(vclEmployeeAssignSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
