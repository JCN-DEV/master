package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmpAddressTemp;
import gov.step.app.repository.InstEmpAddressTempRepository;
import gov.step.app.repository.search.InstEmpAddressTempSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing InstEmpAddressTemp.
 */
@RestController
@RequestMapping("/api")
public class InstEmpAddressTempResource {

    private final Logger log = LoggerFactory.getLogger(InstEmpAddressTempResource.class);

    @Inject
    private InstEmpAddressTempRepository instEmpAddressTempRepository;

    @Inject
    private InstEmpAddressTempSearchRepository instEmpAddressTempSearchRepository;

    /**
     * POST  /instEmpAddressTemps -> Create a new instEmpAddressTemp.
     */
    @RequestMapping(value = "/instEmpAddressTemps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddressTemp> createInstEmpAddressTemp(@RequestBody InstEmpAddressTemp instEmpAddressTemp) throws URISyntaxException {
        log.debug("REST request to save InstEmpAddressTemp : {}", instEmpAddressTemp);
        if (instEmpAddressTemp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmpAddressTemp cannot already have an ID").body(null);
        }
        InstEmpAddressTemp result = instEmpAddressTempRepository.save(instEmpAddressTemp);
        instEmpAddressTempSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmpAddressTemps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmpAddressTemp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmpAddressTemps -> Updates an existing instEmpAddressTemp.
     */
    @RequestMapping(value = "/instEmpAddressTemps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddressTemp> updateInstEmpAddressTemp(@RequestBody InstEmpAddressTemp instEmpAddressTemp) throws URISyntaxException {
        log.debug("REST request to update InstEmpAddressTemp : {}", instEmpAddressTemp);
        if (instEmpAddressTemp.getId() == null) {
            return createInstEmpAddressTemp(instEmpAddressTemp);
        }
        InstEmpAddressTemp result = instEmpAddressTempRepository.save(instEmpAddressTemp);
        instEmpAddressTempSearchRepository.save(instEmpAddressTemp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmpAddressTemp", instEmpAddressTemp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmpAddressTemps -> get all the instEmpAddressTemps.
     */
    @RequestMapping(value = "/instEmpAddressTemps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmpAddressTemp>> getAllInstEmpAddressTemps(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmpAddressTemp> page = instEmpAddressTempRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmpAddressTemps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmpAddressTemps/:id -> get the "id" instEmpAddressTemp.
     */
    @RequestMapping(value = "/instEmpAddressTemps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddressTemp> getInstEmpAddressTemp(@PathVariable Long id) {
        log.debug("REST request to get InstEmpAddressTemp : {}", id);
        return Optional.ofNullable(instEmpAddressTempRepository.findOne(id))
            .map(instEmpAddressTemp -> new ResponseEntity<>(
                instEmpAddressTemp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmpAddressTemps/:id -> delete the "id" instEmpAddressTemp.
     */
    @RequestMapping(value = "/instEmpAddressTemps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmpAddressTemp(@PathVariable Long id) {
        log.debug("REST request to delete InstEmpAddressTemp : {}", id);
        instEmpAddressTempRepository.delete(id);
        instEmpAddressTempSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmpAddressTemp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmpAddressTemps/:query -> search for the instEmpAddressTemp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmpAddressTemps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpAddressTemp> searchInstEmpAddressTemps(@PathVariable String query) {
        return StreamSupport
            .stream(instEmpAddressTempSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
