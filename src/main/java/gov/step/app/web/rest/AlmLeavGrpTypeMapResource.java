package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmLeavGrpTypeMap;
import gov.step.app.repository.AlmLeavGrpTypeMapRepository;
import gov.step.app.repository.search.AlmLeavGrpTypeMapSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmLeavGrpTypeMap.
 */
@RestController
@RequestMapping("/api")
public class AlmLeavGrpTypeMapResource {

    private final Logger log = LoggerFactory.getLogger(AlmLeavGrpTypeMapResource.class);

    @Inject
    private AlmLeavGrpTypeMapRepository almLeavGrpTypeMapRepository;

    @Inject
    private AlmLeavGrpTypeMapSearchRepository almLeavGrpTypeMapSearchRepository;

    /**
     * POST  /almLeavGrpTypeMaps -> Create a new almLeavGrpTypeMap.
     */
    @RequestMapping(value = "/almLeavGrpTypeMaps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeavGrpTypeMap> createAlmLeavGrpTypeMap(@RequestBody AlmLeavGrpTypeMap almLeavGrpTypeMap) throws URISyntaxException {
        log.debug("REST request to save AlmLeavGrpTypeMap : {}", almLeavGrpTypeMap);
        if (almLeavGrpTypeMap.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almLeavGrpTypeMap cannot already have an ID").body(null);
        }
        AlmLeavGrpTypeMap result = almLeavGrpTypeMapRepository.save(almLeavGrpTypeMap);
        almLeavGrpTypeMapSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almLeavGrpTypeMaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almLeavGrpTypeMap", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almLeavGrpTypeMaps -> Updates an existing almLeavGrpTypeMap.
     */
    @RequestMapping(value = "/almLeavGrpTypeMaps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeavGrpTypeMap> updateAlmLeavGrpTypeMap(@RequestBody AlmLeavGrpTypeMap almLeavGrpTypeMap) throws URISyntaxException {
        log.debug("REST request to update AlmLeavGrpTypeMap : {}", almLeavGrpTypeMap);
        if (almLeavGrpTypeMap.getId() == null) {
            return createAlmLeavGrpTypeMap(almLeavGrpTypeMap);
        }
        AlmLeavGrpTypeMap result = almLeavGrpTypeMapRepository.save(almLeavGrpTypeMap);
        almLeavGrpTypeMapSearchRepository.save(almLeavGrpTypeMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almLeavGrpTypeMap", almLeavGrpTypeMap.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almLeavGrpTypeMaps -> get all the almLeavGrpTypeMaps.
     */
    @RequestMapping(value = "/almLeavGrpTypeMaps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmLeavGrpTypeMap>> getAllAlmLeavGrpTypeMaps(Pageable pageable)
        throws URISyntaxException {
        Page<AlmLeavGrpTypeMap> page = almLeavGrpTypeMapRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almLeavGrpTypeMaps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almLeavGrpTypeMaps/:id -> get the "id" almLeavGrpTypeMap.
     */
    @RequestMapping(value = "/almLeavGrpTypeMaps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmLeavGrpTypeMap> getAlmLeavGrpTypeMap(@PathVariable Long id) {
        log.debug("REST request to get AlmLeavGrpTypeMap : {}", id);
        return Optional.ofNullable(almLeavGrpTypeMapRepository.findOne(id))
            .map(almLeavGrpTypeMap -> new ResponseEntity<>(
                almLeavGrpTypeMap,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almLeavGrpTypeMaps/:id -> delete the "id" almLeavGrpTypeMap.
     */
    @RequestMapping(value = "/almLeavGrpTypeMaps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmLeavGrpTypeMap(@PathVariable Long id) {
        log.debug("REST request to delete AlmLeavGrpTypeMap : {}", id);
        almLeavGrpTypeMapRepository.delete(id);
        almLeavGrpTypeMapSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almLeavGrpTypeMap", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almLeavGrpTypeMaps/:query -> search for the almLeavGrpTypeMap corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almLeavGrpTypeMaps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmLeavGrpTypeMap> searchAlmLeavGrpTypeMaps(@PathVariable String query) {
        return StreamSupport
            .stream(almLeavGrpTypeMapSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
