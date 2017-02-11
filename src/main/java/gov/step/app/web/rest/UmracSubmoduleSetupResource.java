package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.UmracSubmoduleSetup;
import gov.step.app.repository.UmracSubmoduleSetupRepository;
import gov.step.app.repository.search.UmracSubmoduleSetupSearchRepository;
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
 * REST controller for managing UmracSubmoduleSetup.
 */
@RestController
@RequestMapping("/api")
public class UmracSubmoduleSetupResource {

    private final Logger log = LoggerFactory.getLogger(UmracSubmoduleSetupResource.class);

    @Inject
    private UmracSubmoduleSetupRepository umracSubmoduleSetupRepository;

    @Inject
    private UmracSubmoduleSetupSearchRepository umracSubmoduleSetupSearchRepository;

    /**
     * POST  /umracSubmoduleSetups -> Create a new umracSubmoduleSetup.
     */
    @RequestMapping(value = "/umracSubmoduleSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracSubmoduleSetup> createUmracSubmoduleSetup(@Valid @RequestBody UmracSubmoduleSetup umracSubmoduleSetup) throws URISyntaxException {
        log.debug("REST request to save UmracSubmoduleSetup : {}", umracSubmoduleSetup);
        if (umracSubmoduleSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new umracSubmoduleSetup cannot already have an ID").body(null);
        }

        umracSubmoduleSetup.setCreateBy(Long.parseLong("1"));
        TransactionIdResource tir = new TransactionIdResource();
        umracSubmoduleSetup.setSubModuleId(tir.getGeneratedid("SMID"));
        DateResource dr = new DateResource();
        umracSubmoduleSetup.setCreateDate(dr.getDateAsLocalDate());

        UmracSubmoduleSetup result = umracSubmoduleSetupRepository.save(umracSubmoduleSetup);
        umracSubmoduleSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/umracSubmoduleSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("umracSubmoduleSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /umracSubmoduleSetups -> Updates an existing umracSubmoduleSetup.
     */
    @RequestMapping(value = "/umracSubmoduleSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracSubmoduleSetup> updateUmracSubmoduleSetup(@Valid @RequestBody UmracSubmoduleSetup umracSubmoduleSetup) throws URISyntaxException {
        log.debug("REST request to update UmracSubmoduleSetup : {}", umracSubmoduleSetup);
        if (umracSubmoduleSetup.getId() == null) {
            return createUmracSubmoduleSetup(umracSubmoduleSetup);
        }

        umracSubmoduleSetup.setUpdatedBy(Long.parseLong("1"));
        DateResource dr = new DateResource();
        umracSubmoduleSetup.setUpdatedTime(dr.getDateAsLocalDate());

        UmracSubmoduleSetup result = umracSubmoduleSetupRepository.save(umracSubmoduleSetup);
        umracSubmoduleSetupSearchRepository.save(umracSubmoduleSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("umracSubmoduleSetup", umracSubmoduleSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /umracSubmoduleSetups -> get all the umracSubmoduleSetups.
     */
    @RequestMapping(value = "/umracSubmoduleSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<UmracSubmoduleSetup>> getAllUmracSubmoduleSetups(Pageable pageable)
        throws URISyntaxException {
        Page<UmracSubmoduleSetup> page = umracSubmoduleSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/umracSubmoduleSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /umracSubmoduleSetups/:id -> get the "id" umracSubmoduleSetup.
     */
    @RequestMapping(value = "/umracSubmoduleSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<UmracSubmoduleSetup> getUmracSubmoduleSetup(@PathVariable Long id) {
        log.debug("REST request to get UmracSubmoduleSetup : {}", id);
        return Optional.ofNullable(umracSubmoduleSetupRepository.findOne(id))
            .map(umracSubmoduleSetup -> new ResponseEntity<>(
                umracSubmoduleSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /umracSubmoduleSetups/:id -> delete the "id" umracSubmoduleSetup.
     */
    @RequestMapping(value = "/umracSubmoduleSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteUmracSubmoduleSetup(@PathVariable Long id) {
        log.debug("REST request to delete UmracSubmoduleSetup : {}", id);
        umracSubmoduleSetupRepository.delete(id);
        umracSubmoduleSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("umracSubmoduleSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/umracSubmoduleSetups/:query -> search for the umracSubmoduleSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/umracSubmoduleSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<UmracSubmoduleSetup> searchUmracSubmoduleSetups(@PathVariable String query) {
        return StreamSupport
            .stream(umracSubmoduleSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/umracSubmoduleSetups/subModuleName/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getSubModuleByName(@RequestParam String value) {

        log.debug("REST request to get umracModuleSetups by name : {}", value);

        Optional<UmracSubmoduleSetup> umracSubModuleSetup = umracSubmoduleSetupRepository.findOneBySubModuleName(value);

       /* log.debug("user on check for----"+value);
        log.debug("cmsCurriculum on check code----"+Optional.empty().equals(cmsCurriculum));*/
        //generateAllRightsJson();
        Map map = new HashMap();

        map.put("value", value);

        if (Optional.empty().equals(umracSubModuleSetup)) {
            map.put("isValid", true);
            return new ResponseEntity<Map>(map, HttpStatus.OK);

        } else {
            map.put("isValid", false);
            return new ResponseEntity<Map>(map, HttpStatus.OK);
        }
    }
}
