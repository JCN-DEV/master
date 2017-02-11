package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDepartmentSetup;
import gov.step.app.repository.HrDepartmentSetupRepository;
import gov.step.app.repository.search.HrDepartmentSetupSearchRepository;
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
 * REST controller for managing HrDepartmentSetup.
 */
@RestController
@RequestMapping("/api")
public class HrDepartmentSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrDepartmentSetupResource.class);

    @Inject
    private HrDepartmentSetupRepository hrDepartmentSetupRepository;

    @Inject
    private HrDepartmentSetupSearchRepository hrDepartmentSetupSearchRepository;

    /**
     * POST  /hrDepartmentSetups -> Create a new hrDepartmentSetup.
     */
    @RequestMapping(value = "/hrDepartmentSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentSetup> createHrDepartmentSetup(@Valid @RequestBody HrDepartmentSetup hrDepartmentSetup) throws URISyntaxException {
        log.debug("REST request to save HrDepartmentSetup : {}", hrDepartmentSetup);
        if (hrDepartmentSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrDepartmentSetup", "idexists", "A new hrDepartmentSetup cannot already have an ID")).body(null);
        }
        HrDepartmentSetup result = hrDepartmentSetupRepository.save(hrDepartmentSetup);
        hrDepartmentSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDepartmentSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDepartmentSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDepartmentSetups -> Updates an existing hrDepartmentSetup.
     */
    @RequestMapping(value = "/hrDepartmentSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentSetup> updateHrDepartmentSetup(@Valid @RequestBody HrDepartmentSetup hrDepartmentSetup) throws URISyntaxException {
        log.debug("REST request to update HrDepartmentSetup : {}", hrDepartmentSetup);
        if (hrDepartmentSetup.getId() == null) {
            return createHrDepartmentSetup(hrDepartmentSetup);
        }
        HrDepartmentSetup result = hrDepartmentSetupRepository.save(hrDepartmentSetup);
        hrDepartmentSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDepartmentSetup", hrDepartmentSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDepartmentSetups -> get all the hrDepartmentSetups.
     */
    @RequestMapping(value = "/hrDepartmentSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentSetup>> getAllHrDepartmentSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrDepartmentSetups");
        Page<HrDepartmentSetup> page = hrDepartmentSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentSetups -> get all the hrDepartmentSetups.
     */
    @RequestMapping(value = "/hrDepartmentSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentSetup>> getAllHrDepartmentSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrDepartmentSetups by status");
        Page<HrDepartmentSetup> page = hrDepartmentSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentSetupsByStat/:stat -> get the all department by active status.
     */
    @RequestMapping(value = "/hrDepartmentSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentSetup> getAllDepartmentByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all department by status : {}", stat);
        List<HrDepartmentSetup> departmentList = hrDepartmentSetupRepository.findAllByActiveStatus(stat);
        return  departmentList;
    }

    /**
     * GET  /hrDepartmentSetups/:id -> get the "id" hrDepartmentSetup.
     */
    @RequestMapping(value = "/hrDepartmentSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentSetup> getHrDepartmentSetup(@PathVariable Long id) {
        log.debug("REST request to get HrDepartmentSetup : {}", id);
        HrDepartmentSetup hrDepartmentSetup = hrDepartmentSetupRepository.findOne(id);
        return Optional.ofNullable(hrDepartmentSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDepartmentSetups/:id -> delete the "id" hrDepartmentSetup.
     */
    @RequestMapping(value = "/hrDepartmentSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDepartmentSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrDepartmentSetup : {}", id);
        hrDepartmentSetupRepository.delete(id);
        hrDepartmentSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDepartmentSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDepartmentSetups/:query -> search for the hrDepartmentSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDepartmentSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentSetup> searchHrDepartmentSetups(@PathVariable String query) {
        log.debug("REST request to search HrDepartmentSetups for query {}", query);
        return StreamSupport
            .stream(hrDepartmentSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/hrDepartmentSetupsByOrgTypeAndFilters/{orgtype}/{deptid}/{refid}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getDepartmentByDepartmentHeadAndWorkArea(@PathVariable String orgtype, @PathVariable Long deptid, @PathVariable Long refid)
    {
        List<HrDepartmentSetup> modelInfoList = null;
        if(orgtype.equalsIgnoreCase("Institute"))
        {
            modelInfoList = hrDepartmentSetupRepository.findOneByDepartmentHeadAndInstitute(refid, deptid);
        }
        else
        {
            modelInfoList = hrDepartmentSetupRepository.findOneByDepartmentHeadAndWorkArea(refid,deptid);
        }
        log.debug("hrDepartmentSetupsByOrgTypeAndFilters by refid: "+refid+", deptid: "+deptid);
        Map map =new HashMap();
        map.put("orgtype", orgtype);
        map.put("refid", refid);
        map.put("deptid", deptid);
        if(modelInfoList!=null) map.put("total", modelInfoList.size());
        if(modelInfoList!=null && modelInfoList.size()>0)
        {
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
