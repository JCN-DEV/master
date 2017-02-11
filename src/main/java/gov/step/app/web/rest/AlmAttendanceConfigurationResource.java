package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmAttendanceConfiguration;
import gov.step.app.repository.AlmAttendanceConfigurationRepository;
import gov.step.app.repository.search.AlmAttendanceConfigurationSearchRepository;
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
 * REST controller for managing AlmAttendanceConfiguration.
 */
@RestController
@RequestMapping("/api")
public class AlmAttendanceConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AlmAttendanceConfigurationResource.class);

    @Inject
    private AlmAttendanceConfigurationRepository almAttendanceConfigurationRepository;

    @Inject
    private AlmAttendanceConfigurationSearchRepository almAttendanceConfigurationSearchRepository;

    /**
     * POST  /almAttendanceConfigurations -> Create a new almAttendanceConfiguration.
     */
    @RequestMapping(value = "/almAttendanceConfigurations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceConfiguration> createAlmAttendanceConfiguration(@Valid @RequestBody AlmAttendanceConfiguration almAttendanceConfiguration) throws URISyntaxException {
        log.debug("REST request to save AlmAttendanceConfiguration : {}", almAttendanceConfiguration);
        if (almAttendanceConfiguration.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almAttendanceConfiguration cannot already have an ID").body(null);
        }
        AlmAttendanceConfiguration result = almAttendanceConfigurationRepository.save(almAttendanceConfiguration);
        almAttendanceConfigurationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almAttendanceConfigurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almAttendanceConfiguration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almAttendanceConfigurations -> Updates an existing almAttendanceConfiguration.
     */
    @RequestMapping(value = "/almAttendanceConfigurations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceConfiguration> updateAlmAttendanceConfiguration(@Valid @RequestBody AlmAttendanceConfiguration almAttendanceConfiguration) throws URISyntaxException {
        log.debug("REST request to update AlmAttendanceConfiguration : {}", almAttendanceConfiguration);
        if (almAttendanceConfiguration.getId() == null) {
            return createAlmAttendanceConfiguration(almAttendanceConfiguration);
        }
        AlmAttendanceConfiguration result = almAttendanceConfigurationRepository.save(almAttendanceConfiguration);
        almAttendanceConfigurationSearchRepository.save(almAttendanceConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almAttendanceConfiguration", almAttendanceConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almAttendanceConfigurations -> get all the almAttendanceConfigurations.
     */
    @RequestMapping(value = "/almAttendanceConfigurations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmAttendanceConfiguration>> getAllAlmAttendanceConfigurations(Pageable pageable)
        throws URISyntaxException {
        Page<AlmAttendanceConfiguration> page = almAttendanceConfigurationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almAttendanceConfigurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almAttendanceConfigurations/:id -> get the "id" almAttendanceConfiguration.
     */
    @RequestMapping(value = "/almAttendanceConfigurations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmAttendanceConfiguration> getAlmAttendanceConfiguration(@PathVariable Long id) {
        log.debug("REST request to get AlmAttendanceConfiguration : {}", id);
        return Optional.ofNullable(almAttendanceConfigurationRepository.findOne(id))
            .map(almAttendanceConfiguration -> new ResponseEntity<>(
                almAttendanceConfiguration,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almAttendanceConfigurations/:id -> delete the "id" almAttendanceConfiguration.
     */
    @RequestMapping(value = "/almAttendanceConfigurations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmAttendanceConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete AlmAttendanceConfiguration : {}", id);
        almAttendanceConfigurationRepository.delete(id);
        almAttendanceConfigurationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almAttendanceConfiguration", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almAttendanceConfigurations/:query -> search for the almAttendanceConfiguration corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almAttendanceConfigurations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmAttendanceConfiguration> searchAlmAttendanceConfigurations(@PathVariable String query) {
        return StreamSupport
            .stream(almAttendanceConfigurationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
