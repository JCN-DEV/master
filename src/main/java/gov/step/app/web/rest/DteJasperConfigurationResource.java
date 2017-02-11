package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DteJasperConfiguration;
import gov.step.app.repository.DteJasperConfigurationRepository;
import gov.step.app.repository.search.DteJasperConfigurationSearchRepository;
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
 * REST controller for managing DteJasperConfiguration.
 */
@RestController
@RequestMapping("/api")
public class DteJasperConfigurationResource {

    private final Logger log = LoggerFactory.getLogger(DteJasperConfigurationResource.class);

    @Inject
    private DteJasperConfigurationRepository dteJasperConfigurationRepository;

    @Inject
    private DteJasperConfigurationSearchRepository dteJasperConfigurationSearchRepository;

    /**
     * POST  /dteJasperConfigurations -> Create a new dteJasperConfiguration.
     */
    @RequestMapping(value = "/dteJasperConfigurations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DteJasperConfiguration> createDteJasperConfiguration(@Valid @RequestBody DteJasperConfiguration dteJasperConfiguration) throws URISyntaxException {
        log.debug("REST request to save DteJasperConfiguration : {}", dteJasperConfiguration);
        if (dteJasperConfiguration.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dteJasperConfiguration cannot already have an ID").body(null);
        }
        DteJasperConfiguration result = dteJasperConfigurationRepository.save(dteJasperConfiguration);
        dteJasperConfigurationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dteJasperConfigurations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dteJasperConfiguration", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dteJasperConfigurations -> Updates an existing dteJasperConfiguration.
     */
    @RequestMapping(value = "/dteJasperConfigurations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DteJasperConfiguration> updateDteJasperConfiguration(@Valid @RequestBody DteJasperConfiguration dteJasperConfiguration) throws URISyntaxException {
        log.debug("REST request to update DteJasperConfiguration : {}", dteJasperConfiguration);
        if (dteJasperConfiguration.getId() == null) {
            return createDteJasperConfiguration(dteJasperConfiguration);
        }
        DteJasperConfiguration result = dteJasperConfigurationRepository.save(dteJasperConfiguration);
        dteJasperConfigurationSearchRepository.save(dteJasperConfiguration);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dteJasperConfiguration", dteJasperConfiguration.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dteJasperConfigurations -> get all the dteJasperConfigurations.
     */
    @RequestMapping(value = "/dteJasperConfigurations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DteJasperConfiguration>> getAllDteJasperConfigurations(Pageable pageable)
        throws URISyntaxException {
        Page<DteJasperConfiguration> page = dteJasperConfigurationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dteJasperConfigurations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dteJasperConfigurations/:id -> get the "id" dteJasperConfiguration.
     */
    @RequestMapping(value = "/dteJasperConfigurations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DteJasperConfiguration> getDteJasperConfiguration(@PathVariable Long id) {
        log.debug("REST request to get DteJasperConfiguration : {}", id);
        return Optional.ofNullable(dteJasperConfigurationRepository.findOne(id))
            .map(dteJasperConfiguration -> new ResponseEntity<>(
                dteJasperConfiguration,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dteJasperConfigurations/:id -> delete the "id" dteJasperConfiguration.
     */
    @RequestMapping(value = "/dteJasperConfigurations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDteJasperConfiguration(@PathVariable Long id) {
        log.debug("REST request to delete DteJasperConfiguration : {}", id);
        dteJasperConfigurationRepository.delete(id);
        dteJasperConfigurationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dteJasperConfiguration", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dteJasperConfigurations/:query -> search for the dteJasperConfiguration corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dteJasperConfigurations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DteJasperConfiguration> searchDteJasperConfigurations(@PathVariable String query) {
        return StreamSupport
            .stream(dteJasperConfigurationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
