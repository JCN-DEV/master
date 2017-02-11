package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrWingSetup;
import gov.step.app.repository.HrWingSetupRepository;
import gov.step.app.repository.search.HrWingSetupSearchRepository;
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
 * REST controller for managing HrWingSetup.
 */
@RestController
@RequestMapping("/api")
public class HrWingSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrWingSetupResource.class);

    @Inject
    private HrWingSetupRepository hrWingSetupRepository;

    @Inject
    private HrWingSetupSearchRepository hrWingSetupSearchRepository;

    /**
     * POST  /hrWingSetups -> Create a new hrWingSetup.
     */
    @RequestMapping(value = "/hrWingSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingSetup> createHrWingSetup(@Valid @RequestBody HrWingSetup hrWingSetup) throws URISyntaxException {
        log.debug("REST request to save HrWingSetup : {}", hrWingSetup);
        if (hrWingSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new hrWingSetup cannot already have an ID").body(null);
        }
        HrWingSetup result = hrWingSetupRepository.save(hrWingSetup);
        hrWingSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrWingSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrWingSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrWingSetups -> Updates an existing hrWingSetup.
     */
    @RequestMapping(value = "/hrWingSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingSetup> updateHrWingSetup(@Valid @RequestBody HrWingSetup hrWingSetup) throws URISyntaxException {
        log.debug("REST request to update HrWingSetup : {}", hrWingSetup);
        if (hrWingSetup.getId() == null) {
            return createHrWingSetup(hrWingSetup);
        }
        HrWingSetup result = hrWingSetupRepository.save(hrWingSetup);
        hrWingSetupSearchRepository.save(hrWingSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrWingSetup", hrWingSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrWingSetups -> get all the hrWingSetups.
     */
    @RequestMapping(value = "/hrWingSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrWingSetup>> getAllHrWingSetups(Pageable pageable)
        throws URISyntaxException {
        Page<HrWingSetup> page = hrWingSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrWingSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrWingSetups/:id -> get the "id" hrWingSetup.
     */
    @RequestMapping(value = "/hrWingSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingSetup> getHrWingSetup(@PathVariable Long id) {
        log.debug("REST request to get HrWingSetup : {}", id);
        return Optional.ofNullable(hrWingSetupRepository.findOne(id))
            .map(hrWingSetup -> new ResponseEntity<>(
                hrWingSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrWingSetups/:id -> delete the "id" hrWingSetup.
     */
    @RequestMapping(value = "/hrWingSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrWingSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrWingSetup : {}", id);
        hrWingSetupRepository.delete(id);
        hrWingSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrWingSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrWingSetups/:query -> search for the hrWingSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrWingSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrWingSetup> searchHrWingSetups(@PathVariable String query) {
        return StreamSupport
            .stream(hrWingSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrWingSetupsByStat/:stat -> get the all wing by active status.
     */
    @RequestMapping(value = "/hrWingSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrWingSetup> getAllGazetteSetupListByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all wing by status : {}", stat);
        List<HrWingSetup> wingList = hrWingSetupRepository.findAllByActiveStatus(stat);
        return  wingList;
    }
}
