package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDesignationHeadSetup;
import gov.step.app.domain.enumeration.designationType;
import gov.step.app.repository.HrDesignationHeadSetupRepository;
import gov.step.app.repository.search.HrDesignationHeadSetupSearchRepository;
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
 * REST controller for managing HrDesignationHeadSetup.
 */
@RestController
@RequestMapping("/api")
public class HrDesignationHeadSetupResource {

    private final Logger log = LoggerFactory.getLogger(HrDesignationHeadSetupResource.class);

    @Inject
    private HrDesignationHeadSetupRepository hrDesignationHeadSetupRepository;

    @Inject
    private HrDesignationHeadSetupSearchRepository hrDesignationHeadSetupSearchRepository;

    /**
     * POST  /hrDesignationHeadSetups -> Create a new hrDesignationHeadSetup.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationHeadSetup> createHrDesignationHeadSetup(@Valid @RequestBody HrDesignationHeadSetup hrDesignationHeadSetup) throws URISyntaxException {
        log.debug("REST request to save HrDesignationHeadSetup : {}", hrDesignationHeadSetup);
        if (hrDesignationHeadSetup.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrDesignationHeadSetup", "idexists", "A new hrDesignationHeadSetup cannot already have an ID")).body(null);
        }
        HrDesignationHeadSetup result = hrDesignationHeadSetupRepository.save(hrDesignationHeadSetup);
        hrDesignationHeadSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDesignationHeadSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDesignationHeadSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDesignationHeadSetups -> Updates an existing hrDesignationHeadSetup.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationHeadSetup> updateHrDesignationHeadSetup(@Valid @RequestBody HrDesignationHeadSetup hrDesignationHeadSetup) throws URISyntaxException {
        log.debug("REST request to update HrDesignationHeadSetup : {}", hrDesignationHeadSetup);
        if (hrDesignationHeadSetup.getId() == null) {
            return createHrDesignationHeadSetup(hrDesignationHeadSetup);
        }
        HrDesignationHeadSetup result = hrDesignationHeadSetupRepository.save(hrDesignationHeadSetup);
        hrDesignationHeadSetupSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDesignationHeadSetup", hrDesignationHeadSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDesignationHeadSetups -> get all the hrDesignationHeadSetups.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDesignationHeadSetup>> getAllHrDesignationHeadSetups(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrDesignationHeadSetups");
        Page<HrDesignationHeadSetup> page = hrDesignationHeadSetupRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDesignationHeadSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDesignationHeadSetups/:id -> get the "id" hrDesignationHeadSetup.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDesignationHeadSetup> getHrDesignationHeadSetup(@PathVariable Long id) {
        log.debug("REST request to get HrDesignationHeadSetup : {}", id);
        HrDesignationHeadSetup hrDesignationHeadSetup = hrDesignationHeadSetupRepository.findOne(id);
        return Optional.ofNullable(hrDesignationHeadSetup)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDesignationHeadSetups/:id -> delete the "id" hrDesignationHeadSetup.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDesignationHeadSetup(@PathVariable Long id) {
        log.debug("REST request to delete HrDesignationHeadSetup : {}", id);
        hrDesignationHeadSetupRepository.delete(id);
        hrDesignationHeadSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDesignationHeadSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDesignationHeadSetups/:query -> search for the hrDesignationHeadSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDesignationHeadSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationHeadSetup> searchHrDesignationHeadSetups(@PathVariable String query) {
        log.debug("REST request to search HrDesignationHeadSetups for query {}", query);
        return StreamSupport
            .stream(hrDesignationHeadSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrDesignationSetups -> get all the hrDesignationSetups.
     */
    @RequestMapping(value = "/hrDesignationHeadSetups/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDesignationHeadSetup>> getAllHrDesignationSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrDesignationHeadSetups by status");
        Page<HrDesignationHeadSetup> page = hrDesignationHeadSetupRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDesignationHeadSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDesignationHeadSetupsByStat/:stat -> get the all designation by active status.
     */
    @RequestMapping(value = "/hrDesignationHeadSetupsByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationHeadSetup> getAllDesignationByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all designation head by status : {}", stat);
        List<HrDesignationHeadSetup> designationHeadSetupList = hrDesignationHeadSetupRepository.findAllByActiveStatus(stat);
        return  designationHeadSetupList;
    }

    /**
     * GET  /hrDesignationHeadSetupsByStat/:stat -> get the all designation by active status.
     */
    @RequestMapping(value = "/hrDesignationHeadSetupsByType/{type}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDesignationHeadSetup> getAllDesignationByDesigType(@PathVariable String type)
        throws URISyntaxException
    {
        log.debug("REST all designation head by type : {}", type);
        designationType desigType = designationType.valueOf(type);
        List<HrDesignationHeadSetup> designationHeadSetupList = hrDesignationHeadSetupRepository.findAllByDesigType(desigType, true);
        return  designationHeadSetupList;
    }

    @RequestMapping(value = "/hrDesignationHeadSetups/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> designationByTypeCode(@RequestParam String value)
    {
        Optional<HrDesignationHeadSetup> modelInfo = hrDesignationHeadSetupRepository.findOneByDesignationCode(value.toLowerCase());
        log.debug("hrDesignationHeadSetups by code: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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

    @RequestMapping(value = "/hrDesignationHeadSetups/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> designationByName(@RequestParam String value)
    {
        Optional<HrDesignationHeadSetup> modelInfo = hrDesignationHeadSetupRepository.findOneByDesignationName(value.toLowerCase());
        log.debug("hrDesignationHeadSetups by name: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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
