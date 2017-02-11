package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDepartmentHeadSetup;
import gov.step.app.repository.HrDepartmentHeadSetupRepository;
import gov.step.app.repository.search.HrDepartmentHeadSetupSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing HrDepartmentHeadSetup.
 */
@RestController
@RequestMapping("/api")
public class HrDepartmentHeadSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrDepartmentHeadSetupResource.class);

    @Inject
    private HrDepartmentHeadSetupRepository hrDepartmentHeadSetupRepository;

    @Inject
    private HrDepartmentHeadSetupSearchRepository hrDepartmentHeadSetupSearchRepository;

    /**
     * POST  /hrDepartmentHeadSetups -> Create a new hrDepartmentHeadSetup.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadSetup> createHrDepartmentHeadSetup(@Valid @RequestBody HrDepartmentHeadSetup hrDepartmentHeadSetup) throws URISyntaxException {
        log.debug("REST request to save HrDepartmentHeadSetup : {}", hrDepartmentHeadSetup);
        if (hrDepartmentHeadSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrDepartmentHeadSetup", "idexists", "A new hrDepartmentHeadSetup cannot already have an ID")).body(null);
        }
        HrDepartmentHeadSetup result = hrDepartmentHeadSetupRepository.save(hrDepartmentHeadSetup);
        hrDepartmentHeadSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDepartmentHeadSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDepartmentHeadSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDepartmentHeadSetups -> Updates an existing hrDepartmentHeadSetup.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadSetup> updateHrDepartmentHeadSetup(@Valid @RequestBody HrDepartmentHeadSetup hrDepartmentHeadSetup) throws URISyntaxException {
        log.debug("REST request to update HrDepartmentHeadSetup : {}", hrDepartmentHeadSetup);
        if (hrDepartmentHeadSetup.getId() == null) {
            return createHrDepartmentHeadSetup(hrDepartmentHeadSetup);
        }
        HrDepartmentHeadSetup result = hrDepartmentHeadSetupRepository.save(hrDepartmentHeadSetup);
        hrDepartmentHeadSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDepartmentHeadSetup", hrDepartmentHeadSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDepartmentHeadSetups -> get all the hrDepartmentHeadSetups.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentHeadSetup>> getAllHrDepartmentHeadSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrDepartmentHeadSetups");
        Page<HrDepartmentHeadSetup> page = hrDepartmentHeadSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentHeadSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentHeadSetups/:id -> get the "id" hrDepartmentHeadSetup.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadSetup> getHrDepartmentHeadSetup(@PathVariable Long id) {
        log.debug("REST request to get HrDepartmentHeadSetup : {}", id);
        HrDepartmentHeadSetup hrDepartmentHeadSetup = hrDepartmentHeadSetupRepository.findOne(id);
        return Optional.ofNullable(hrDepartmentHeadSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDepartmentHeadSetups/:id -> delete the "id" hrDepartmentHeadSetup.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDepartmentHeadSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrDepartmentHeadSetup : {}", id);
        hrDepartmentHeadSetupRepository.delete(id);
        hrDepartmentHeadSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDepartmentHeadSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDepartmentHeadSetups/:query -> search for the hrDepartmentHeadSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDepartmentHeadSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentHeadSetup> searchHrDepartmentHeadSetups(@PathVariable String query) {
        log.debug("REST request to search HrDepartmentHeadSetups for query {}", query);
        return StreamSupport
            .stream(hrDepartmentHeadSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrDepartmentHeadSetups -> get all the hrDepartmentSetups.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentHeadSetup>> getAllHrDepartmentSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrDepartmentHeadSetups by status");
        Page<HrDepartmentHeadSetup> page = hrDepartmentHeadSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentHeadSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentHeadSetupsByStat/:stat -> get the all department by active status.
     */
    @RequestMapping(value = "/hrDepartmentHeadSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentHeadSetup> getAllDepartmentByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all department head by status : {}", stat);
        List<HrDepartmentHeadSetup> departmentList = hrDepartmentHeadSetupRepository.findAllByActiveStatus(stat);
        return  departmentList;
    }

    @RequestMapping(value = "/hrDepartmentHeadSetups/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> departmentByTypeCode(@RequestParam String value)
    {
        Optional<HrDepartmentHeadSetup> modelInfo = hrDepartmentHeadSetupRepository.findOneByDepartmentCode(value.toLowerCase());
        log.debug("hrDepartmentHeadSetups by code: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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

    @RequestMapping(value = "/hrDepartmentHeadSetups/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> departmentByDeptName(@RequestParam String value)
    {
        Optional<HrDepartmentHeadSetup> modelInfo = hrDepartmentHeadSetupRepository.findOneByDepartmentName(value.toLowerCase());
        log.debug("hrDepartmentHeadSetups by name: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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
}
