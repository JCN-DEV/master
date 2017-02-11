package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracModuleSetup;
import gov.step.app.repository.UmracModuleSetupRepository;
import gov.step.app.repository.search.UmracModuleSetupSearchRepository;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;

import gov.step.app.web.rest.util.TransactionIdResource;
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
 * REST controller for managing UmracModuleSetup.
 */
@RestController
@RequestMapping("/api")
public class UmracModuleSetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracModuleSetupResource.class);

    @Inject
    private UmracModuleSetupRepository umracModuleSetupRepository;

    @Inject
    private UmracModuleSetupSearchRepository umracModuleSetupSearchRepository;

    /**
     * POST  /umracModuleSetups -> Create a new umracModuleSetup.
     */
    @RequestMapping(value = "/umracModuleSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracModuleSetup> createUmracModuleSetup(@Valid @RequestBody UmracModuleSetup umracModuleSetup) throws URISyntaxException {
        log.debug("REST request to save UmracModuleSetup : {}", umracModuleSetup);
        if (umracModuleSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracModuleSetup cannot already have an ID").body(null);
        }
        umracModuleSetup.setCreateBy(Long.parseLong("1"));
        TransactionIdResource tir = new TransactionIdResource();
        umracModuleSetup.setModuleId(tir.getGeneratedid("MID"));
        DateResource dr = new DateResource();
        umracModuleSetup.setCreateDate(dr.getDateAsLocalDate());
        UmracModuleSetup result = umracModuleSetupRepository.save(umracModuleSetup);
        umracModuleSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracModuleSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracModuleSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracModuleSetups -> Updates an existing umracModuleSetup.
     */
    @RequestMapping(value = "/umracModuleSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracModuleSetup> updateUmracModuleSetup(@Valid @RequestBody UmracModuleSetup umracModuleSetup) throws URISyntaxException {
        log.debug("REST request to update UmracModuleSetup : {}", umracModuleSetup);
        if (umracModuleSetup.getId() == null) {
            return createUmracModuleSetup(umracModuleSetup);
        }
        umracModuleSetup.setUpdatedBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracModuleSetup.setUpdatedTime(dr.getDateAsLocalDate());
        UmracModuleSetup result = umracModuleSetupRepository.save(umracModuleSetup);
        umracModuleSetupSearchRepository.save(umracModuleSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracModuleSetup", umracModuleSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracModuleSetups -> get all the umracModuleSetups.
     */
    @RequestMapping(value = "/umracModuleSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracModuleSetup>> getAllUmracModuleSetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracModuleSetup> page = umracModuleSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracModuleSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracModuleSetups/:id -> get the "id" umracModuleSetup.
     */
    @RequestMapping(value = "/umracModuleSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracModuleSetup> getUmracModuleSetup(@PathVariable Long id) {
        log.debug("REST request to get UmracModuleSetup : {}", id);
        return Optional.ofNullable(umracModuleSetupRepository.findOne(id))
            .map(umracModuleSetup -> new ResponseEntity<>(
                umracModuleSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracModuleSetups/:id -> delete the "id" umracModuleSetup.
     */
    @RequestMapping(value = "/umracModuleSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracModuleSetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracModuleSetup : {}", id);
        umracModuleSetupRepository.delete(id);
        umracModuleSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracModuleSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracModuleSetups/:query -> search for the umracModuleSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracModuleSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracModuleSetup> searchUmracModuleSetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracModuleSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/umracModuleSetups/moduleName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getModuleByNames(@RequestParam String value) {

        log.debug("REST request to get umracModuleSetups by name : {}", value);

        Optional<UmracModuleSetup> umracModuleSetup = umracModuleSetupRepository.findOneByModuleName(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/
        //generateAllRightsJson();
        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracModuleSetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }
}
