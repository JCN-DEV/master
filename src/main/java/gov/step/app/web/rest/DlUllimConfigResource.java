package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlUllimConfig;
import gov.step.app.repository.DlUllimConfigRepository;
import gov.step.app.repository.search.DlUllimConfigSearchRepository;
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
 * REST controller for managing DlUllimConfig.
 */
@RestController
@RequestMapping("/api")
public class DlUllimConfigResource {

    private final Logger log = LoggerFactory.getLogger(DlUllimConfigResource.class);

    @Inject
    private DlUllimConfigRepository dlUllimConfigRepository;

    @Inject
    private DlUllimConfigSearchRepository dlUllimConfigSearchRepository;

    /**
     * POST  /dlUllimConfigs -> Create a new dlUllimConfig.
     */
    @RequestMapping(value = "/dlUllimConfigs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlUllimConfig> createDlUllimConfig(@Valid @RequestBody DlUllimConfig dlUllimConfig) throws URISyntaxException {
        log.debug("REST request to save DlUllimConfig : {}", dlUllimConfig);
        if (dlUllimConfig.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlUllimConfig cannot already have an ID").body(null);
        }
        DlUllimConfig result = dlUllimConfigRepository.save(dlUllimConfig);
        dlUllimConfigSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlUllimConfigs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlUllimConfig", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlUllimConfigs -> Updates an existing dlUllimConfig.
     */
    @RequestMapping(value = "/dlUllimConfigs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlUllimConfig> updateDlUllimConfig(@Valid @RequestBody DlUllimConfig dlUllimConfig) throws URISyntaxException {
        log.debug("REST request to update DlUllimConfig : {}", dlUllimConfig);
        if (dlUllimConfig.getId() == null) {
            return createDlUllimConfig(dlUllimConfig);
        }
        DlUllimConfig result = dlUllimConfigRepository.save(dlUllimConfig);
        dlUllimConfigSearchRepository.save(dlUllimConfig);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlUllimConfig", dlUllimConfig.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlUllimConfigs -> get all the dlUllimConfigs.
     */
    @RequestMapping(value = "/dlUllimConfigs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlUllimConfig>> getAllDlUllimConfigs(Pageable pageable)
        throws URISyntaxException {
        Page<DlUllimConfig> page = dlUllimConfigRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlUllimConfigs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlUllimConfigs/:id -> get the "id" dlUllimConfig.
     */
    @RequestMapping(value = "/dlUllimConfigs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlUllimConfig> getDlUllimConfig(@PathVariable Long id) {
        log.debug("REST request to get DlUllimConfig : {}", id);
        return Optional.ofNullable(dlUllimConfigRepository.findOne(id))
            .map(dlUllimConfig -> new ResponseEntity<>(
                dlUllimConfig,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlUllimConfigs/:id -> delete the "id" dlUllimConfig.
     */
    @RequestMapping(value = "/dlUllimConfigs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlUllimConfig(@PathVariable Long id) {
        log.debug("REST request to delete DlUllimConfig : {}", id);
        dlUllimConfigRepository.delete(id);
        dlUllimConfigSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlUllimConfig", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlUllimConfigs/:query -> search for the dlUllimConfig corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlUllimConfigs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlUllimConfig> searchDlUllimConfigs(@PathVariable String query) {
        return StreamSupport
            .stream(dlUllimConfigSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
