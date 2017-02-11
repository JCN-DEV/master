package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrGradeSetup;
import gov.step.app.repository.HrGradeSetupRepository;
import gov.step.app.repository.search.HrGradeSetupSearchRepository;
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
 * REST controller for managing HrGradeSetup.
 */
@RestController
@RequestMapping("/api")
public class HrGradeSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrGradeSetupResource.class);

    @Inject
    private HrGradeSetupRepository hrGradeSetupRepository;

    @Inject
    private HrGradeSetupSearchRepository hrGradeSetupSearchRepository;

    /**
     * POST  /hrGradeSetups -> Create a new hrGradeSetup.
     */
    @RequestMapping(value = "/hrGradeSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGradeSetup> createHrGradeSetup(@Valid @RequestBody HrGradeSetup hrGradeSetup) throws URISyntaxException {
        log.debug("REST request to save HrGradeSetup : {}", hrGradeSetup);
        if (hrGradeSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrGradeSetup", "idexists", "A new hrGradeSetup cannot already have an ID")).body(null);
        }
        HrGradeSetup result = hrGradeSetupRepository.save(hrGradeSetup);
        hrGradeSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrGradeSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrGradeSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrGradeSetups -> Updates an existing hrGradeSetup.
     */
    @RequestMapping(value = "/hrGradeSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGradeSetup> updateHrGradeSetup(@Valid @RequestBody HrGradeSetup hrGradeSetup) throws URISyntaxException {
        log.debug("REST request to update HrGradeSetup : {}", hrGradeSetup);
        if (hrGradeSetup.getId() == null) {
            return createHrGradeSetup(hrGradeSetup);
        }
        HrGradeSetup result = hrGradeSetupRepository.save(hrGradeSetup);
        hrGradeSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrGradeSetup", hrGradeSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrGradeSetups -> get all the hrGradeSetups.
     */
    @RequestMapping(value = "/hrGradeSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrGradeSetup>> getAllHrGradeSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrGradeSetups");
        Page<HrGradeSetup> page = hrGradeSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrGradeSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    @RequestMapping(value = "/hrGradeSetups/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeCode(@RequestParam String value)
    {
        Optional<HrGradeSetup> modelInfo = hrGradeSetupRepository.findOneByGradeCode(value.toLowerCase());
        log.debug("hrGradeSetups by code: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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
     * GET  /hrGradeSetups -> get all the hrGradeSetups.
     */
    @RequestMapping(value = "/hrGradeSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrGradeSetup>> getAllHrGradeSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrGradeSetups by status");
        Page<HrGradeSetup> page = hrGradeSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrGradeSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrGradeSetupsByStat/:stat -> get the all grade by active status.
     */
    @RequestMapping(value = "/hrGradeSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrGradeSetup> getAllDepartmentByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all grade by status : {}", stat);
        List<HrGradeSetup> gradeSetupList = hrGradeSetupRepository.findAllByActiveStatus(stat);
        return  gradeSetupList;
    }


    /**
     * GET  /hrGradeSetups/:id -> get the "id" hrGradeSetup.
     */
    @RequestMapping(value = "/hrGradeSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrGradeSetup> getHrGradeSetup(@PathVariable Long id) {
        log.debug("REST request to get HrGradeSetup : {}", id);
        HrGradeSetup hrGradeSetup = hrGradeSetupRepository.findOne(id);
        return Optional.ofNullable(hrGradeSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrGradeSetups/:id -> delete the "id" hrGradeSetup.
     */
    @RequestMapping(value = "/hrGradeSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrGradeSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrGradeSetup : {}", id);
        hrGradeSetupRepository.delete(id);
        hrGradeSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrGradeSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrGradeSetups/:query -> search for the hrGradeSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrGradeSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrGradeSetup> searchHrGradeSetups(@PathVariable String query) {
        log.debug("REST request to search HrGradeSetups for query {}", query);
        return StreamSupport
            .stream(hrGradeSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
