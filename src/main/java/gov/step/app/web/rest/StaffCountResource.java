package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.StaffCount;
import gov.step.app.repository.StaffCountRepository;
import gov.step.app.repository.search.StaffCountSearchRepository;
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
 * REST controller for managing StaffCount.
 */
@RestController
@RequestMapping("/api")
public class StaffCountResource {

    private final Logger log = LoggerFactory.getLogger(StaffCountResource.class);

    @Inject
    private StaffCountRepository staffCountRepository;

    @Inject
    private StaffCountSearchRepository staffCountSearchRepository;

    /**
     * POST  /staffCounts -> Create a new staffCount.
     */
    @RequestMapping(value = "/staffCounts",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffCount> createStaffCount(@RequestBody StaffCount staffCount) throws URISyntaxException {
        log.debug("REST request to save StaffCount : {}", staffCount);
        if (staffCount.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new staffCount cannot already have an ID").body(null);
        }
        StaffCount result = staffCountRepository.save(staffCount);
        staffCountSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/staffCounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("staffCount", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /staffCounts -> Updates an existing staffCount.
     */
    @RequestMapping(value = "/staffCounts",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffCount> updateStaffCount(@RequestBody StaffCount staffCount) throws URISyntaxException {
        log.debug("REST request to update StaffCount : {}", staffCount);
        if (staffCount.getId() == null) {
            return createStaffCount(staffCount);
        }
        StaffCount result = staffCountRepository.save(staffCount);
        staffCountSearchRepository.save(staffCount);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("staffCount", staffCount.getId().toString()))
            .body(result);
    }

    /**
     * GET  /staffCounts -> get all the staffCounts.
     */
    @RequestMapping(value = "/staffCounts",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<StaffCount>> getAllStaffCounts(Pageable pageable)
        throws URISyntaxException {
        Page<StaffCount> page = staffCountRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staffCounts");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /staffCounts/:id -> get the "id" staffCount.
     */
    @RequestMapping(value = "/staffCounts/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<StaffCount> getStaffCount(@PathVariable Long id) {
        log.debug("REST request to get StaffCount : {}", id);
        return Optional.ofNullable(staffCountRepository.findOne(id))
            .map(staffCount -> new ResponseEntity<>(
                staffCount,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /staffCounts/:id -> delete the "id" staffCount.
     */
    @RequestMapping(value = "/staffCounts/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteStaffCount(@PathVariable Long id) {
        log.debug("REST request to delete StaffCount : {}", id);
        staffCountRepository.delete(id);
        staffCountSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("staffCount", id.toString())).build();
    }

    /**
     * SEARCH  /_search/staffCounts/:query -> search for the staffCount corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/staffCounts/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<StaffCount> searchStaffCounts(@PathVariable String query) {
        return StreamSupport
            .stream(staffCountSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
