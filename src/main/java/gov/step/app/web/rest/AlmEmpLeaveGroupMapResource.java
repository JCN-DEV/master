package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveGroupMap;
import gov.step.app.repository.AlmEmpLeaveGroupMapRepository;
import gov.step.app.repository.search.AlmEmpLeaveGroupMapSearchRepository;
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
 * REST controller for managing AlmEmpLeaveGroupMap.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveGroupMapResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveGroupMapResource.class);

    @Inject
    private AlmEmpLeaveGroupMapRepository almEmpLeaveGroupMapRepository;

    @Inject
    private AlmEmpLeaveGroupMapSearchRepository almEmpLeaveGroupMapSearchRepository;

    /**
     * POST  /almEmpLeaveGroupMaps -> Create a new almEmpLeaveGroupMap.
     */
    @RequestMapping(value = "/almEmpLeaveGroupMaps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveGroupMap> createAlmEmpLeaveGroupMap(@RequestBody AlmEmpLeaveGroupMap almEmpLeaveGroupMap) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveGroupMap : {}", almEmpLeaveGroupMap);
        if (almEmpLeaveGroupMap.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveGroupMap cannot already have an ID").body(null);
        }
        AlmEmpLeaveGroupMap result = almEmpLeaveGroupMapRepository.save(almEmpLeaveGroupMap);
        almEmpLeaveGroupMapSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveGroupMaps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveGroupMap", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveGroupMaps -> Updates an existing almEmpLeaveGroupMap.
     */
    @RequestMapping(value = "/almEmpLeaveGroupMaps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveGroupMap> updateAlmEmpLeaveGroupMap(@RequestBody AlmEmpLeaveGroupMap almEmpLeaveGroupMap) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveGroupMap : {}", almEmpLeaveGroupMap);
        if (almEmpLeaveGroupMap.getId() == null) {
            return createAlmEmpLeaveGroupMap(almEmpLeaveGroupMap);
        }
        AlmEmpLeaveGroupMap result = almEmpLeaveGroupMapRepository.save(almEmpLeaveGroupMap);
        almEmpLeaveGroupMapSearchRepository.save(almEmpLeaveGroupMap);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveGroupMap", almEmpLeaveGroupMap.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveGroupMaps -> get all the almEmpLeaveGroupMaps.
     */
    @RequestMapping(value = "/almEmpLeaveGroupMaps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveGroupMap>> getAllAlmEmpLeaveGroupMaps(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveGroupMap> page = almEmpLeaveGroupMapRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveGroupMaps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveGroupMaps/:id -> get the "id" almEmpLeaveGroupMap.
     */
    @RequestMapping(value = "/almEmpLeaveGroupMaps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveGroupMap> getAlmEmpLeaveGroupMap(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveGroupMap : {}", id);
        return Optional.ofNullable(almEmpLeaveGroupMapRepository.findOne(id))
            .map(almEmpLeaveGroupMap -> new ResponseEntity<>(
                almEmpLeaveGroupMap,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveGroupMaps/:id -> delete the "id" almEmpLeaveGroupMap.
     */
    @RequestMapping(value = "/almEmpLeaveGroupMaps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveGroupMap(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveGroupMap : {}", id);
        almEmpLeaveGroupMapRepository.delete(id);
        almEmpLeaveGroupMapSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveGroupMap", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveGroupMaps/:query -> search for the almEmpLeaveGroupMap corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveGroupMaps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveGroupMap> searchAlmEmpLeaveGroupMaps(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveGroupMapSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
