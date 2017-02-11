package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrPayScaleSetup;
import gov.step.app.repository.HrPayScaleSetupRepository;
import gov.step.app.repository.search.HrPayScaleSetupSearchRepository;
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
 * REST controller for managing HrPayScaleSetup.
 */
@RestController
@RequestMapping("/api")
public class HrPayScaleSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrPayScaleSetupResource.class);

    @Inject
    private HrPayScaleSetupRepository hrPayScaleSetupRepository;

    @Inject
    private HrPayScaleSetupSearchRepository hrPayScaleSetupSearchRepository;

    /**
     * POST  /hrPayScaleSetups -> Create a new hrPayScaleSetup.
     */
    @RequestMapping(value = "/hrPayScaleSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrPayScaleSetup> createHrPayScaleSetup(@Valid @RequestBody HrPayScaleSetup hrPayScaleSetup) throws URISyntaxException {
        log.debug("REST request to save HrPayScaleSetup : {}", hrPayScaleSetup);
        if (hrPayScaleSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrPayScaleSetup", "idexists", "A new hrPayScaleSetup cannot already have an ID")).body(null);
        }
        HrPayScaleSetup result = hrPayScaleSetupRepository.save(hrPayScaleSetup);
        hrPayScaleSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrPayScaleSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrPayScaleSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrPayScaleSetups -> Updates an existing hrPayScaleSetup.
     */
    @RequestMapping(value = "/hrPayScaleSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrPayScaleSetup> updateHrPayScaleSetup(@Valid @RequestBody HrPayScaleSetup hrPayScaleSetup) throws URISyntaxException {
        log.debug("REST request to update HrPayScaleSetup : {}", hrPayScaleSetup);
        if (hrPayScaleSetup.getId() == null) {
            return createHrPayScaleSetup(hrPayScaleSetup);
        }
        HrPayScaleSetup result = hrPayScaleSetupRepository.save(hrPayScaleSetup);
        hrPayScaleSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrPayScaleSetup", hrPayScaleSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrPayScaleSetups -> get all the hrPayScaleSetups.
     */
    @RequestMapping(value = "/hrPayScaleSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrPayScaleSetup>> getAllHrPayScaleSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrPayScaleSetups");
        Page<HrPayScaleSetup> page = hrPayScaleSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrPayScaleSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/hrPayScaleSetups/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> payscaleByTypeCode(@RequestParam String value)
    {
        Optional<HrPayScaleSetup> modelInfo = hrPayScaleSetupRepository.findOneByPayScaleCode(value.toLowerCase());
        log.debug("hrPayScaleSetups by code: "+value+", Stat: "+Optional.empty().equals(modelInfo));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(modelInfo))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * GET  /hrPayScaleSetups -> get all the hrPayScaleSetups.
     */
    @RequestMapping(value = "/hrPayScaleSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrPayScaleSetup>> getAllHrDesignationSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrPayScaleSetups by status");
        Page<HrPayScaleSetup> page = hrPayScaleSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrPayScaleSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrPayScaleSetups/:id -> get the "id" hrPayScaleSetup.
     */
    @RequestMapping(value = "/hrPayScaleSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrPayScaleSetup> getHrPayScaleSetup(@PathVariable Long id) {
        log.debug("REST request to get HrPayScaleSetup : {}", id);
        HrPayScaleSetup hrPayScaleSetup = hrPayScaleSetupRepository.findOne(id);
        return Optional.ofNullable(hrPayScaleSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrPayScaleSetups/:id -> delete the "id" hrPayScaleSetup.
     */
    @RequestMapping(value = "/hrPayScaleSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrPayScaleSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrPayScaleSetup : {}", id);
        hrPayScaleSetupRepository.delete(id);
        hrPayScaleSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrPayScaleSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrPayScaleSetups/:query -> search for the hrPayScaleSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrPayScaleSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrPayScaleSetup> searchHrPayScaleSetups(@PathVariable String query) {
        log.debug("REST request to search HrPayScaleSetups for query {}", query);
        return StreamSupport
            .stream(hrPayScaleSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
