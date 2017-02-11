package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmWeekendConfiguration;
import gov.step.app.repository.AlmWeekendConfigurationRepository;
import gov.step.app.repository.search.AlmWeekendConfigurationSearchRepository;
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
 * REST controller for managing AlmWeekendConfiguration.
 */
@RestController
@RequestMapping("/api")
public class AlmWeekendConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(AlmWeekendConfigurationResource.class);

    @Inject
    private AlmWeekendConfigurationRepository almWeekendConfigurationRepository;

    @Inject
    private AlmWeekendConfigurationSearchRepository almWeekendConfigurationSearchRepository;

    /**
     * POST  /almWeekendConfigurations -> Create a new almWeekendConfiguration.
     */
    @RequestMapping(value = "/almWeekendConfigurations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWeekendConfiguration> createAlmWeekendConfiguration(@Valid @RequestBody AlmWeekendConfiguration almWeekendConfiguration) throws URISyntaxException {
        log.debug("REST request to save AlmWeekendConfiguration : {}", almWeekendConfiguration);
        if (almWeekendConfiguration.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almWeekendConfiguration cannot already have an ID").body(null);
        }
        AlmWeekendConfiguration result = almWeekendConfigurationRepository.save(almWeekendConfiguration);
        almWeekendConfigurationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almWeekendConfigurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almWeekendConfiguration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almWeekendConfigurations -> Updates an existing almWeekendConfiguration.
     */
    @RequestMapping(value = "/almWeekendConfigurations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWeekendConfiguration> updateAlmWeekendConfiguration(@Valid @RequestBody AlmWeekendConfiguration almWeekendConfiguration) throws URISyntaxException {
        log.debug("REST request to update AlmWeekendConfiguration : {}", almWeekendConfiguration);
        if (almWeekendConfiguration.getId() == null) {
            return createAlmWeekendConfiguration(almWeekendConfiguration);
        }
        AlmWeekendConfiguration result = almWeekendConfigurationRepository.save(almWeekendConfiguration);
        almWeekendConfigurationSearchRepository.save(almWeekendConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almWeekendConfiguration", almWeekendConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almWeekendConfigurations -> get all the almWeekendConfigurations.
     */
    @RequestMapping(value = "/almWeekendConfigurations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmWeekendConfiguration>> getAllAlmWeekendConfigurations(Pageable pageable)
        throws URISyntaxException {
        Page<AlmWeekendConfiguration> page = almWeekendConfigurationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almWeekendConfigurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almWeekendConfigurations/:id -> get the "id" almWeekendConfiguration.
     */
    @RequestMapping(value = "/almWeekendConfigurations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmWeekendConfiguration> getAlmWeekendConfiguration(@PathVariable Long id) {
        log.debug("REST request to get AlmWeekendConfiguration : {}", id);
        return Optional.ofNullable(almWeekendConfigurationRepository.findOne(id))
            .map(almWeekendConfiguration -> new ResponseEntity<>(
                almWeekendConfiguration,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almWeekendConfigurations/:id -> delete the "id" almWeekendConfiguration.
     */
    @RequestMapping(value = "/almWeekendConfigurations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmWeekendConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete AlmWeekendConfiguration : {}", id);
        almWeekendConfigurationRepository.delete(id);
        almWeekendConfigurationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almWeekendConfiguration", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almWeekendConfigurations/:query -> search for the almWeekendConfiguration corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almWeekendConfigurations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmWeekendConfiguration> searchAlmWeekendConfigurations(@PathVariable String query) {
        return StreamSupport
            .stream(almWeekendConfigurationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
