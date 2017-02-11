package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveTypeMap;
import gov.step.app.repository.AlmEmpLeaveTypeMapRepository;
import gov.step.app.repository.search.AlmEmpLeaveTypeMapSearchRepository;
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
 * REST controller for managing AlmEmpLeaveTypeMap.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveTypeMapResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveTypeMapResource.class);

    @Inject
    private AlmEmpLeaveTypeMapRepository almEmpLeaveTypeMapRepository;

    @Inject
    private AlmEmpLeaveTypeMapSearchRepository almEmpLeaveTypeMapSearchRepository;

    /**
     * POST  /almEmpLeaveTypeMaps -> Create a new almEmpLeaveTypeMap.
     */
    @RequestMapping(value = "/almEmpLeaveTypeMaps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveTypeMap> createAlmEmpLeaveTypeMap(@RequestBody AlmEmpLeaveTypeMap almEmpLeaveTypeMap) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveTypeMap : {}", almEmpLeaveTypeMap);
        if (almEmpLeaveTypeMap.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveTypeMap cannot already have an ID").body(null);
        }
        AlmEmpLeaveTypeMap result = almEmpLeaveTypeMapRepository.save(almEmpLeaveTypeMap);
        almEmpLeaveTypeMapSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveTypeMaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveTypeMap", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveTypeMaps -> Updates an existing almEmpLeaveTypeMap.
     */
    @RequestMapping(value = "/almEmpLeaveTypeMaps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveTypeMap> updateAlmEmpLeaveTypeMap(@RequestBody AlmEmpLeaveTypeMap almEmpLeaveTypeMap) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveTypeMap : {}", almEmpLeaveTypeMap);
        if (almEmpLeaveTypeMap.getId() == null) {
            return createAlmEmpLeaveTypeMap(almEmpLeaveTypeMap);
        }
        AlmEmpLeaveTypeMap result = almEmpLeaveTypeMapRepository.save(almEmpLeaveTypeMap);
        almEmpLeaveTypeMapSearchRepository.save(almEmpLeaveTypeMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveTypeMap", almEmpLeaveTypeMap.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveTypeMaps -> get all the almEmpLeaveTypeMaps.
     */
    @RequestMapping(value = "/almEmpLeaveTypeMaps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveTypeMap>> getAllAlmEmpLeaveTypeMaps(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveTypeMap> page = almEmpLeaveTypeMapRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveTypeMaps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveTypeMaps/:id -> get the "id" almEmpLeaveTypeMap.
     */
    @RequestMapping(value = "/almEmpLeaveTypeMaps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveTypeMap> getAlmEmpLeaveTypeMap(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveTypeMap : {}", id);
        return Optional.ofNullable(almEmpLeaveTypeMapRepository.findOne(id))
            .map(almEmpLeaveTypeMap -> new ResponseEntity<>(
                almEmpLeaveTypeMap,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveTypeMaps/:id -> delete the "id" almEmpLeaveTypeMap.
     */
    @RequestMapping(value = "/almEmpLeaveTypeMaps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveTypeMap(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveTypeMap : {}", id);
        almEmpLeaveTypeMapRepository.delete(id);
        almEmpLeaveTypeMapSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveTypeMap", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveTypeMaps/:query -> search for the almEmpLeaveTypeMap corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveTypeMaps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveTypeMap> searchAlmEmpLeaveTypeMaps(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveTypeMapSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
