package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrGazetteSetup;
import gov.step.app.repository.HrGazetteSetupRepository;
import gov.step.app.repository.search.HrGazetteSetupSearchRepository;
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
 * REST controller for managing HrGazetteSetup.
 */
@RestController
@RequestMapping("/api")
public class HrGazetteSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrGazetteSetupResource.class);

    @Inject
    private HrGazetteSetupRepository hrGazetteSetupRepository;

    @Inject
    private HrGazetteSetupSearchRepository hrGazetteSetupSearchRepository;

    /**
     * POST  /hrGazetteSetups -> Create a new hrGazetteSetup.
     */
    @RequestMapping(value = "/hrGazetteSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGazetteSetup> createHrGazetteSetup(@Valid @RequestBody HrGazetteSetup hrGazetteSetup) throws URISyntaxException {
        log.debug("REST request to save HrGazetteSetup : {}", hrGazetteSetup);
        if (hrGazetteSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrGazetteSetup", "idexists", "A new hrGazetteSetup cannot already have an ID")).body(null);
        }
        HrGazetteSetup result = hrGazetteSetupRepository.save(hrGazetteSetup);
        hrGazetteSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrGazetteSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrGazetteSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrGazetteSetups -> Updates an existing hrGazetteSetup.
     */
    @RequestMapping(value = "/hrGazetteSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGazetteSetup> updateHrGazetteSetup(@Valid @RequestBody HrGazetteSetup hrGazetteSetup) throws URISyntaxException {
        log.debug("REST request to update HrGazetteSetup : {}", hrGazetteSetup);
        if (hrGazetteSetup.getId() == null) {
            return createHrGazetteSetup(hrGazetteSetup);
        }
        HrGazetteSetup result = hrGazetteSetupRepository.save(hrGazetteSetup);
        hrGazetteSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrGazetteSetup", hrGazetteSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrGazetteSetups -> get all the hrGazetteSetups.
     */
    @RequestMapping(value = "/hrGazetteSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrGazetteSetup>> getAllHrGazetteSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrGazetteSetups");
        Page<HrGazetteSetup> page = hrGazetteSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrGazetteSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrGazetteSetups/:id -> get the "id" hrGazetteSetup.
     */
    @RequestMapping(value = "/hrGazetteSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGazetteSetup> getHrGazetteSetup(@PathVariable Long id) {
        log.debug("REST request to get HrGazetteSetup : {}", id);
        HrGazetteSetup hrGazetteSetup = hrGazetteSetupRepository.findOne(id);
        return Optional.ofNullable(hrGazetteSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/hrGazetteSetups/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeCode(@RequestParam String value)
    {
        Optional<HrGazetteSetup> modelInfo = hrGazetteSetupRepository.findOneByGazetteCode(value.toLowerCase());
        log.debug("hrGazetteSetups by code: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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
     * DELETE  /hrGazetteSetups/:id -> delete the "id" hrGazetteSetup.
     */
    @RequestMapping(value = "/hrGazetteSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrGazetteSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrGazetteSetup : {}", id);
        hrGazetteSetupRepository.delete(id);
        hrGazetteSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrGazetteSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrGazetteSetups/:query -> search for the hrGazetteSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrGazetteSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrGazetteSetup> searchHrGazetteSetups(@PathVariable String query) {
        log.debug("REST request to search HrGazetteSetups for query {}", query);
        return StreamSupport
            .stream(hrGazetteSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrGradeSetupsByStat/:stat -> get the all grade by active status.
     */
    @RequestMapping(value = "/hrGazetteSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrGazetteSetup> getAllGazetteSetupListByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all gazette by status : {}", stat);
        List<HrGazetteSetup> gazetteSetupList = hrGazetteSetupRepository.findAllByActiveStatus(stat);
        return  gazetteSetupList;
    }
}
