package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsUtmostGpfApp;
import gov.step.app.repository.PfmsUtmostGpfAppRepository;
import gov.step.app.repository.search.PfmsUtmostGpfAppSearchRepository;
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
 * REST controller for managing PfmsUtmostGpfApp.
 */
@RestController
@RequestMapping("/api")
public class PfmsUtmostGpfAppResource {

    private final Logger log = LoggerFactory.getLogger(PfmsUtmostGpfAppResource.class);

    @Inject
    private PfmsUtmostGpfAppRepository pfmsUtmostGpfAppRepository;

    @Inject
    private PfmsUtmostGpfAppSearchRepository pfmsUtmostGpfAppSearchRepository;

    /**
     * POST  /pfmsUtmostGpfApps -> Create a new pfmsUtmostGpfApp.
     */
    @RequestMapping(value = "/pfmsUtmostGpfApps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsUtmostGpfApp> createPfmsUtmostGpfApp(@Valid @RequestBody PfmsUtmostGpfApp pfmsUtmostGpfApp) throws URISyntaxException {
        log.debug("REST request to save PfmsUtmostGpfApp : {}", pfmsUtmostGpfApp);
        if (pfmsUtmostGpfApp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsUtmostGpfApp cannot already have an ID").body(null);
        }
        PfmsUtmostGpfApp result = pfmsUtmostGpfAppRepository.save(pfmsUtmostGpfApp);
        pfmsUtmostGpfAppSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsUtmostGpfApps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsUtmostGpfApp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsUtmostGpfApps -> Updates an existing pfmsUtmostGpfApp.
     */
    @RequestMapping(value = "/pfmsUtmostGpfApps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsUtmostGpfApp> updatePfmsUtmostGpfApp(@Valid @RequestBody PfmsUtmostGpfApp pfmsUtmostGpfApp) throws URISyntaxException {
        log.debug("REST request to update PfmsUtmostGpfApp : {}", pfmsUtmostGpfApp);
        if (pfmsUtmostGpfApp.getId() == null) {
            return createPfmsUtmostGpfApp(pfmsUtmostGpfApp);
        }
        PfmsUtmostGpfApp result = pfmsUtmostGpfAppRepository.save(pfmsUtmostGpfApp);
        pfmsUtmostGpfAppSearchRepository.save(pfmsUtmostGpfApp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsUtmostGpfApp", pfmsUtmostGpfApp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsUtmostGpfApps -> get all the pfmsUtmostGpfApps.
     */
    @RequestMapping(value = "/pfmsUtmostGpfApps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsUtmostGpfApp>> getAllPfmsUtmostGpfApps(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsUtmostGpfApp> page = pfmsUtmostGpfAppRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsUtmostGpfApps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsUtmostGpfApps/:id -> get the "id" pfmsUtmostGpfApp.
     */
    @RequestMapping(value = "/pfmsUtmostGpfApps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsUtmostGpfApp> getPfmsUtmostGpfApp(@PathVariable Long id) {
        log.debug("REST request to get PfmsUtmostGpfApp : {}", id);
        return Optional.ofNullable(pfmsUtmostGpfAppRepository.findOne(id))
            .map(pfmsUtmostGpfApp -> new ResponseEntity<>(
                pfmsUtmostGpfApp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsUtmostGpfApps/:id -> delete the "id" pfmsUtmostGpfApp.
     */
    @RequestMapping(value = "/pfmsUtmostGpfApps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsUtmostGpfApp(@PathVariable Long id) {
        log.debug("REST request to delete PfmsUtmostGpfApp : {}", id);
        pfmsUtmostGpfAppRepository.delete(id);
        pfmsUtmostGpfAppSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsUtmostGpfApp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsUtmostGpfApps/:query -> search for the pfmsUtmostGpfApp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsUtmostGpfApps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsUtmostGpfApp> searchPfmsUtmostGpfApps(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsUtmostGpfAppSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsUtmostGpfAppListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsUtmostGpfAppListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsUtmostGpfApp> getPfmsUtmostGpfAppListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsUtmostGpfAppByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsUtmostGpfAppRepository.getPfmsUtmostGpfAppListByEmployee(employeeInfoId);

    }
}
