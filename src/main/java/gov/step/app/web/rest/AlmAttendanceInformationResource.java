package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmAttendanceInformation;
import gov.step.app.repository.AlmAttendanceInformationRepository;
import gov.step.app.repository.search.AlmAttendanceInformationSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmAttendanceInformation.
 */
@RestController
@RequestMapping("/api")
public class AlmAttendanceInformationResource {

    private final Logger log = LoggerFactory.getLogger(AlmAttendanceInformationResource.class);

    @Inject
    private AlmAttendanceInformationRepository almAttendanceInformationRepository;

    @Inject
    private AlmAttendanceInformationSearchRepository almAttendanceInformationSearchRepository;

    /**
     * POST  /almAttendanceInformations -> Create a new almAttendanceInformation.
     */
    @RequestMapping(value = "/almAttendanceInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceInformation> createAlmAttendanceInformation(@Valid @RequestBody AlmAttendanceInformation almAttendanceInformation) throws URISyntaxException {
        log.debug("REST request to save AlmAttendanceInformation : {}", almAttendanceInformation);
        if (almAttendanceInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almAttendanceInformation cannot already have an ID").body(null);
        }
        AlmAttendanceInformation result = almAttendanceInformationRepository.save(almAttendanceInformation);
        almAttendanceInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almAttendanceInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almAttendanceInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almAttendanceInformations -> Updates an existing almAttendanceInformation.
     */
    @RequestMapping(value = "/almAttendanceInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceInformation> updateAlmAttendanceInformation(@Valid @RequestBody AlmAttendanceInformation almAttendanceInformation) throws URISyntaxException {
        log.debug("REST request to update AlmAttendanceInformation : {}", almAttendanceInformation);
        if (almAttendanceInformation.getId() == null) {
            return createAlmAttendanceInformation(almAttendanceInformation);
        }
        AlmAttendanceInformation result = almAttendanceInformationRepository.save(almAttendanceInformation);
        almAttendanceInformationSearchRepository.save(almAttendanceInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almAttendanceInformation", almAttendanceInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almAttendanceInformations -> get all the almAttendanceInformations.
     */
    @RequestMapping(value = "/almAttendanceInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmAttendanceInformation>> getAllAlmAttendanceInformations(Pageable pageable)
        throws URISyntaxException {
        Page<AlmAttendanceInformation> page = almAttendanceInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almAttendanceInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almAttendanceInformations/:id -> get the "id" almAttendanceInformation.
     */
    @RequestMapping(value = "/almAttendanceInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceInformation> getAlmAttendanceInformation(@PathVariable Long id) {
        log.debug("REST request to get AlmAttendanceInformation : {}", id);
        return Optional.ofNullable(almAttendanceInformationRepository.findOne(id))
            .map(almAttendanceInformation -> new ResponseEntity<>(
                almAttendanceInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almAttendanceInformations/:id -> delete the "id" almAttendanceInformation.
     */
    @RequestMapping(value = "/almAttendanceInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmAttendanceInformation(@PathVariable Long id) {
        log.debug("REST request to delete AlmAttendanceInformation : {}", id);
        almAttendanceInformationRepository.delete(id);
        almAttendanceInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almAttendanceInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almAttendanceInformations/:query -> search for the almAttendanceInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almAttendanceInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmAttendanceInformation> searchAlmAttendanceInformations(@PathVariable String query) {
        return StreamSupport
            .stream(almAttendanceInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
