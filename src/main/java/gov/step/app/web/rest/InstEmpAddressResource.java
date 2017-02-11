package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmpAddress;
import gov.step.app.domain.InstEmployee;
import gov.step.app.repository.InstEmpAddressRepository;
import gov.step.app.repository.search.InstEmpAddressSearchRepository;
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
 * REST controller for managing InstEmpAddress.
 */
@RestController
@RequestMapping("/api")
public class InstEmpAddressResource {

    private final Logger log = LoggerFactory.getLogger(InstEmpAddressResource.class);

    @Inject
    private InstEmpAddressRepository instEmpAddressRepository;

    @Inject
    private InstEmpAddressSearchRepository instEmpAddressSearchRepository;

    /**
     * POST  /instEmpAddresss -> Create a new instEmpAddress.
     */
    @RequestMapping(value = "/instEmpAddresss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddress> createInstEmpAddress(@RequestBody InstEmpAddress instEmpAddress) throws URISyntaxException {
        log.debug("REST request to save InstEmpAddress : {}", instEmpAddress);
        if (instEmpAddress.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmpAddress cannot already have an ID").body(null);
        }
        InstEmpAddress result = instEmpAddressRepository.save(instEmpAddress);
        instEmpAddressSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmpAddresss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmpAddress", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmpAddresss -> Updates an existing instEmpAddress.
     */
    @RequestMapping(value = "/instEmpAddresss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddress> updateInstEmpAddress(@RequestBody InstEmpAddress instEmpAddress) throws URISyntaxException {
        log.debug("REST request to update InstEmpAddress : {}", instEmpAddress);
        if (instEmpAddress.getId() == null) {
            return createInstEmpAddress(instEmpAddress);
        }
        InstEmpAddress result = instEmpAddressRepository.save(instEmpAddress);
        instEmpAddressSearchRepository.save(instEmpAddress);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmpAddress", instEmpAddress.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmpAddresss -> get all the instEmpAddresss.
     */
    @RequestMapping(value = "/instEmpAddresss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmpAddress>> getAllInstEmpAddresss(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmpAddress> page = instEmpAddressRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmpAddresss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmpAddresss/:id -> get the "id" instEmpAddress.
     */
    @RequestMapping(value = "/instEmpAddresss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddress> getInstEmpAddress(@PathVariable Long id) {
        log.debug("REST request to get InstEmpAddress : {}", id);
        return Optional.ofNullable(instEmpAddressRepository.findOne(id))
            .map(instEmpAddress -> new ResponseEntity<>(
                instEmpAddress,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmpAddresss/:id -> delete the "id" instEmpAddress.
     */
    @RequestMapping(value = "/instEmpAddresss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmpAddress(@PathVariable Long id) {
        log.debug("REST request to delete InstEmpAddress : {}", id);
        instEmpAddressRepository.delete(id);
        instEmpAddressSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmpAddress", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmpAddresss/:query -> search for the instEmpAddress corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmpAddresss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmpAddress> searchInstEmpAddresss(@PathVariable String query) {
        return StreamSupport
            .stream(instEmpAddressSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
    @RequestMapping(value = "/instEmpAddresss/instEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmpAddress> getInstEmpAddressByInstEmployeeId(@PathVariable Long id){

        return Optional.ofNullable(instEmpAddressRepository.findOneByInstEmployeeId(id))
            .map(instEmpAddress -> new ResponseEntity<>(
                instEmpAddress,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
