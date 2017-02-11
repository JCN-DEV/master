package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.VclRequisition;
import gov.step.app.repository.VclRequisitionRepository;
import gov.step.app.repository.search.VclRequisitionSearchRepository;
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
 * REST controller for managing VclRequisition.
 */
@RestController
@RequestMapping("/api")
public class VclRequisitionResource {

    private final Logger log = LoggerFactory.getLogger(VclRequisitionResource.class);
        
    @Inject
    private VclRequisitionRepository vclRequisitionRepository;
    
    @Inject
    private VclRequisitionSearchRepository vclRequisitionSearchRepository;
    
    /**
     * POST  /vclRequisitions -> Create a new vclRequisition.
     */
    @RequestMapping(value = "/vclRequisitions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclRequisition> createVclRequisition(@Valid @RequestBody VclRequisition vclRequisition) throws URISyntaxException {
        log.debug("REST request to save VclRequisition : {}", vclRequisition);
        if (vclRequisition.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("vclRequisition", "idexists", "A new vclRequisition cannot already have an ID")).body(null);
        }
        VclRequisition result = vclRequisitionRepository.save(vclRequisition);
        vclRequisitionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/vclRequisitions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("vclRequisition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /vclRequisitions -> Updates an existing vclRequisition.
     */
    @RequestMapping(value = "/vclRequisitions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclRequisition> updateVclRequisition(@Valid @RequestBody VclRequisition vclRequisition) throws URISyntaxException {
        log.debug("REST request to update VclRequisition : {}", vclRequisition);
        if (vclRequisition.getId() == null) {
            return createVclRequisition(vclRequisition);
        }
        VclRequisition result = vclRequisitionRepository.save(vclRequisition);
        vclRequisitionSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("vclRequisition", vclRequisition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /vclRequisitions -> get all the vclRequisitions.
     */
    @RequestMapping(value = "/vclRequisitions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<VclRequisition>> getAllVclRequisitions(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of VclRequisitions");
        Page<VclRequisition> page = vclRequisitionRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/vclRequisitions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /vclRequisitions/:id -> get the "id" vclRequisition.
     */
    @RequestMapping(value = "/vclRequisitions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VclRequisition> getVclRequisition(@PathVariable Long id) {
        log.debug("REST request to get VclRequisition : {}", id);
        VclRequisition vclRequisition = vclRequisitionRepository.findOne(id);
        return Optional.ofNullable(vclRequisition)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /vclRequisitions/:id -> delete the "id" vclRequisition.
     */
    @RequestMapping(value = "/vclRequisitions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVclRequisition(@PathVariable Long id) {
        log.debug("REST request to delete VclRequisition : {}", id);
        vclRequisitionRepository.delete(id);
        vclRequisitionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("vclRequisition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/vclRequisitions/:query -> search for the vclRequisition corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/vclRequisitions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VclRequisition> searchVclRequisitions(@PathVariable String query) {
        log.debug("REST request to search VclRequisitions for query {}", query);
        return StreamSupport
            .stream(vclRequisitionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
