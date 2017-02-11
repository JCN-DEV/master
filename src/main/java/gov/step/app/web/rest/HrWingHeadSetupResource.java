package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrWingHeadSetup;
import gov.step.app.repository.HrWingHeadSetupRepository;
import gov.step.app.repository.search.HrWingHeadSetupSearchRepository;
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
 * REST controller for managing HrWingHeadSetup.
 */
@RestController
@RequestMapping("/api")
public class HrWingHeadSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrWingHeadSetupResource.class);

    @Inject
    private HrWingHeadSetupRepository hrWingHeadSetupRepository;

    @Inject
    private HrWingHeadSetupSearchRepository hrWingHeadSetupSearchRepository;

    /**
     * POST  /hrWingHeadSetups -> Create a new hrWingHeadSetup.
     */
    @RequestMapping(value = "/hrWingHeadSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingHeadSetup> createHrWingHeadSetup(@Valid @RequestBody HrWingHeadSetup hrWingHeadSetup) throws URISyntaxException {
        log.debug("REST request to save HrWingHeadSetup : {}", hrWingHeadSetup);
        if (hrWingHeadSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new hrWingHeadSetup cannot already have an ID").body(null);
        }

        if(hrWingHeadSetup.getActiveHead())
        {
            hrWingHeadSetupRepository.updateAllWingHeadActiveStatus(hrWingHeadSetup.getWingInfo().getId(), false);
        }

        HrWingHeadSetup result = hrWingHeadSetupRepository.save(hrWingHeadSetup);
        hrWingHeadSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrWingHeadSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrWingHeadSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrWingHeadSetups -> Updates an existing hrWingHeadSetup.
     */
    @RequestMapping(value = "/hrWingHeadSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingHeadSetup> updateHrWingHeadSetup(@Valid @RequestBody HrWingHeadSetup hrWingHeadSetup) throws URISyntaxException {
        log.debug("REST request to update HrWingHeadSetup : {}", hrWingHeadSetup);
        if (hrWingHeadSetup.getId() == null) {
            return createHrWingHeadSetup(hrWingHeadSetup);
        }

        if(hrWingHeadSetup.getActiveHead())
        {
            hrWingHeadSetupRepository.updateAllWingHeadActiveStatus(hrWingHeadSetup.getWingInfo().getId(), false);
        }
        HrWingHeadSetup result = hrWingHeadSetupRepository.save(hrWingHeadSetup);
        hrWingHeadSetupSearchRepository.save(hrWingHeadSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrWingHeadSetup", hrWingHeadSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrWingHeadSetups -> get all the hrWingHeadSetups.
     */
    @RequestMapping(value = "/hrWingHeadSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrWingHeadSetup>> getAllHrWingHeadSetups(Pageable pageable)
        throws URISyntaxException {
        Page<HrWingHeadSetup> page = hrWingHeadSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrWingHeadSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrWingHeadSetups/:id -> get the "id" hrWingHeadSetup.
     */
    @RequestMapping(value = "/hrWingHeadSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrWingHeadSetup> getHrWingHeadSetup(@PathVariable Long id) {
        log.debug("REST request to get HrWingHeadSetup : {}", id);
        return Optional.ofNullable(hrWingHeadSetupRepository.findOne(id))
            .map(hrWingHeadSetup -> new ResponseEntity<>(
                hrWingHeadSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrWingHeadSetups/:id -> delete the "id" hrWingHeadSetup.
     */
    @RequestMapping(value = "/hrWingHeadSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrWingHeadSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrWingHeadSetup : {}", id);
        hrWingHeadSetupRepository.delete(id);
        hrWingHeadSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrWingHeadSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrWingHeadSetups/:query -> search for the hrWingHeadSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrWingHeadSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrWingHeadSetup> searchHrWingHeadSetups(@PathVariable String query) {
        return StreamSupport
            .stream(hrWingHeadSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrWingHeadSetupsByWing/:wingId -> get wing head list by wing id.
     */
    @RequestMapping(value = "/hrWingHeadSetupsByWing/{wingId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrWingHeadSetup> getWingListByWing(@PathVariable Long wingId) {
        log.debug("REST hrWingHeadSetupsByWing List : wingId: {} ",wingId);
        List<HrWingHeadSetup> modelList = hrWingHeadSetupRepository.findAllByWing(wingId);

        return modelList;
    }
}
