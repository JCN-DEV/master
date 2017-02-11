package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDesignationSetup;
import gov.step.app.domain.enumeration.designationType;
import gov.step.app.repository.HrDesignationSetupRepository;
import gov.step.app.repository.search.HrDesignationSetupSearchRepository;
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
 * REST controller for managing HrDesignationSetup.
 */
@RestController
@RequestMapping("/api")
public class HrDesignationSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrDesignationSetupResource.class);

    @Inject
    private HrDesignationSetupRepository hrDesignationSetupRepository;

    @Inject
    private HrDesignationSetupSearchRepository hrDesignationSetupSearchRepository;

    /**
     * POST  /hrDesignationSetups -> Create a new hrDesignationSetup.
     */
    @RequestMapping(value = "/hrDesignationSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationSetup> createHrDesignationSetup(@Valid @RequestBody HrDesignationSetup hrDesignationSetup) throws URISyntaxException {
        log.debug("REST request to save HrDesignationSetup : {}", hrDesignationSetup);
        if (hrDesignationSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrDesignationSetup", "idexists", "A new hrDesignationSetup cannot already have an ID")).body(null);
        }
        HrDesignationSetup result = hrDesignationSetupRepository.save(hrDesignationSetup);
        hrDesignationSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDesignationSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDesignationSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDesignationSetups -> Updates an existing hrDesignationSetup.
     */
    @RequestMapping(value = "/hrDesignationSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationSetup> updateHrDesignationSetup(@Valid @RequestBody HrDesignationSetup hrDesignationSetup) throws URISyntaxException {
        log.debug("REST request to update HrDesignationSetup : {}", hrDesignationSetup);
        if (hrDesignationSetup.getId() == null) {
            return createHrDesignationSetup(hrDesignationSetup);
        }
        HrDesignationSetup result = hrDesignationSetupRepository.save(hrDesignationSetup);
        hrDesignationSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDesignationSetup", hrDesignationSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDesignationSetups -> get all the hrDesignationSetups.
     */
    @RequestMapping(value = "/hrDesignationSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDesignationSetup>> getAllHrDesignationSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrDesignationSetups");
        Page<HrDesignationSetup> page = hrDesignationSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDesignationSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDesignationSetups -> get all the hrDesignationSetups.
     */
    @RequestMapping(value = "/hrDesignationSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDesignationSetup>> getAllHrDesignationSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrDesignationSetups by status");
        Page<HrDesignationSetup> page = hrDesignationSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDesignationSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentSetupsByStat/:stat -> get the all department by active status.
     */
    @RequestMapping(value = "/hrDesignationSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationSetup> getAllDesignationByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all designation by status : {}", stat);
        List<HrDesignationSetup> designationList = hrDesignationSetupRepository.findAllByActiveStatus(stat);
        return  designationList;
    }

    /**
     * GET  /hrDesignationSetups/:id -> get the "id" hrDesignationSetup.
     */
    @RequestMapping(value = "/hrDesignationSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationSetup> getHrDesignationSetup(@PathVariable Long id) {
        log.debug("REST request to get HrDesignationSetup : {}", id);
        HrDesignationSetup hrDesignationSetup = hrDesignationSetupRepository.findOne(id);
        return Optional.ofNullable(hrDesignationSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDesignationSetups/:id -> delete the "id" hrDesignationSetup.
     */
    @RequestMapping(value = "/hrDesignationSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDesignationSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrDesignationSetup : {}", id);
        hrDesignationSetupRepository.delete(id);
        hrDesignationSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDesignationSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDesignationSetups/:query -> search for the hrDesignationSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDesignationSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationSetup> searchHrDesignationSetups(@PathVariable String query) {
        log.debug("REST request to search HrDesignationSetups for query {}", query);
        return StreamSupport
            .stream(hrDesignationSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/hrDesignationSetupsByOrgTypeAndFilters/{orgtype}/{desigid}/{refid}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getDesignationByOrgCategoryAndFilters(@PathVariable String orgtype, @PathVariable Long desigid, @PathVariable Long refid)
    {
        List<HrDesignationSetup> modelInfoList = null;
        if(orgtype.equalsIgnoreCase("Institute"))
        {
            modelInfoList = hrDesignationSetupRepository.findOneByDesignationHeadAndInstitute(refid, desigid);
        }
        else
        {
            modelInfoList = hrDesignationSetupRepository.findOneByDesignationHeadAndWorkArea(refid, desigid);
        }
        log.debug("hrDesignationSetupsByOrgTypeAndFilters by refid: "+refid+", desigid: "+desigid+", orgtype: "+orgtype);
        Map map =new HashMap();
        map.put("orgtype", orgtype);
        map.put("refid", refid);
        map.put("desigid", desigid);
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

    @RequestMapping(value = "/hrDesignationSetupsByTypeLevelCat/{dtype}/{lvlid}/{catId}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> hrDesignationSetupsByTypeLevelCat(@PathVariable String dtype, @PathVariable Long lvlid, @PathVariable Long catId)
    {
        designationType desigType = designationType.valueOf(dtype);
        List<HrDesignationSetup> modelInfoList = hrDesignationSetupRepository.findOneByInstLevelAndInstCatAndDesigLevel(desigType, lvlid, catId);

        log.debug("hrDesignationSetupsByTypeLevelCat by desigType: "+dtype+", levelId: "+lvlid+", catid: "+catId);
        Map map =new HashMap();
        map.put("desigType", dtype);
        map.put("levelId", lvlid);
        map.put("catid", catId);
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

    /**
     * GET  /hrDesignationSetupsByType/:stat -> get the all designation by type.
     */
    @RequestMapping(value = "/hrDesignationSetupsByType/{type}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationSetup> getAllDesignationByActiveStatus(@PathVariable String type)
        throws URISyntaxException
    {
        log.debug("REST all designation by type : {}", type);
        designationType desigType = designationType.valueOf(type);
        List<HrDesignationSetup> designationSetupList = hrDesignationSetupRepository.findAllByDesigType(desigType, true);
        return  designationSetupList;
    }
}
