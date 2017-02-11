package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracIdentitySetup;
import gov.step.app.repository.UmracIdentitySetupRepository;
import gov.step.app.repository.search.UmracIdentitySetupSearchRepository;
import gov.step.app.web.rest.util.DateResource;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing UmracIdentitySetup.
 */
@RestController
@RequestMapping("/api")
public class UmracIdentitySetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracIdentitySetupResource.class);

    @Inject
    private UmracIdentitySetupRepository umracIdentitySetupRepository;

    @Inject
    private UmracIdentitySetupSearchRepository umracIdentitySetupSearchRepository;

    /**
     * POST  /umracIdentitySetups -> Create a new umracIdentitySetup.
     */
    @RequestMapping(value = "/umracIdentitySetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracIdentitySetup> createUmracIdentitySetup(@Valid @RequestBody UmracIdentitySetup umracIdentitySetup) throws URISyntaxException {
        log.debug("REST request to save UmracIdentitySetup : {}", umracIdentitySetup);
        if (umracIdentitySetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracIdentitySetup cannot already have an ID").body(null);
        }
        umracIdentitySetup.setCreateBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracIdentitySetup.setCreateDate(dr.getDateAsLocalDate());
        UmracIdentitySetup result = umracIdentitySetupRepository.save(umracIdentitySetup);
        umracIdentitySetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracIdentitySetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracIdentitySetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracIdentitySetups -> Updates an existing umracIdentitySetup.
     */
    @RequestMapping(value = "/umracIdentitySetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracIdentitySetup> updateUmracIdentitySetup(@Valid @RequestBody UmracIdentitySetup umracIdentitySetup) throws URISyntaxException {
        log.debug("REST request to update UmracIdentitySetup : {}", umracIdentitySetup);
        if (umracIdentitySetup.getId() == null) {
            return createUmracIdentitySetup(umracIdentitySetup);
        }
        umracIdentitySetup.setUpdatedBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracIdentitySetup.setUpdatedTime(dr.getDateAsLocalDate());

        UmracIdentitySetup result = umracIdentitySetupRepository.save(umracIdentitySetup);
        umracIdentitySetupSearchRepository.save(umracIdentitySetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracIdentitySetup", umracIdentitySetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracIdentitySetups -> get all the umracIdentitySetups.
     */
    @RequestMapping(value = "/umracIdentitySetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracIdentitySetup>> getAllUmracIdentitySetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracIdentitySetup> page = umracIdentitySetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracIdentitySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracIdentitySetups/:id -> get the "id" umracIdentitySetup.
     */
    @RequestMapping(value = "/umracIdentitySetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracIdentitySetup> getUmracIdentitySetup(@PathVariable Long id) {
        log.debug("REST request to get UmracIdentitySetup : {}", id);
        return Optional.ofNullable(umracIdentitySetupRepository.findOne(id))
            .map(umracIdentitySetup -> new ResponseEntity<>(
                umracIdentitySetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracIdentitySetups/:id -> delete the "id" umracIdentitySetup.
     */
    @RequestMapping(value = "/umracIdentitySetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracIdentitySetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracIdentitySetup : {}", id);
        umracIdentitySetupRepository.delete(id);
        umracIdentitySetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracIdentitySetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracIdentitySetups/:query -> search for the umracIdentitySetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracIdentitySetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracIdentitySetup> searchUmracIdentitySetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracIdentitySetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/umracIdentitySetups/employeeId/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getEmployeeId(@RequestParam String value) {

        log.debug("REST request to get smsSenderSetups by getEmployeeId : {}", value);

        Optional<UmracIdentitySetup> umracIdentitySetup = umracIdentitySetupRepository.findOneByEmployeeId(value);

        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracIdentitySetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/umracIdentitySetups/userName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getUserName(@RequestParam String value) {

        log.debug("REST request to get smsSenderSetups by getUserName : {}", value);

        Optional<UmracIdentitySetup> umracIdentitySetup = umracIdentitySetupRepository.findOneByEmployeeName(value);

        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracIdentitySetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/umracIdentitySetups/userEmail/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getUserEmail(@RequestParam String value) {

        log.debug("REST request to get smsSenderSetups by userEmail : {}", value);

        Optional<UmracIdentitySetup> umracIdentitySetup = umracIdentitySetupRepository.findOneByEmployeeEmail(value);

        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracIdentitySetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }

}
