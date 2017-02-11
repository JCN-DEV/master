package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmplTypeInfo;
import gov.step.app.repository.HrEmplTypeInfoRepository;
import gov.step.app.repository.search.HrEmplTypeInfoSearchRepository;
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
 * REST controller for managing HrEmplTypeInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmplTypeInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmplTypeInfoResource.class);

    @Inject
    private HrEmplTypeInfoRepository hrEmplTypeInfoRepository;

    @Inject
    private HrEmplTypeInfoSearchRepository hrEmplTypeInfoSearchRepository;

    /**
     * POST  /hrEmplTypeInfos -> Create a new hrEmplTypeInfo.
     */
    @RequestMapping(value = "/hrEmplTypeInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmplTypeInfo> createHrEmplTypeInfo(@Valid @RequestBody HrEmplTypeInfo hrEmplTypeInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmplTypeInfo : {}", hrEmplTypeInfo);
        if (hrEmplTypeInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmplTypeInfo", "idexists", "A new hrEmplTypeInfo cannot already have an ID")).body(null);
        }
        HrEmplTypeInfo result = hrEmplTypeInfoRepository.save(hrEmplTypeInfo);
        hrEmplTypeInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmplTypeInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmplTypeInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmplTypeInfos -> Updates an existing hrEmplTypeInfo.
     */
    @RequestMapping(value = "/hrEmplTypeInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmplTypeInfo> updateHrEmplTypeInfo(@Valid @RequestBody HrEmplTypeInfo hrEmplTypeInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmplTypeInfo : {}", hrEmplTypeInfo);
        if (hrEmplTypeInfo.getId() == null) {
            return createHrEmplTypeInfo(hrEmplTypeInfo);
        }
        HrEmplTypeInfo result = hrEmplTypeInfoRepository.save(hrEmplTypeInfo);
        hrEmplTypeInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmplTypeInfo", hrEmplTypeInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmplTypeInfos -> get all the hrEmplTypeInfos.
     */
    @RequestMapping(value = "/hrEmplTypeInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmplTypeInfo>> getAllHrEmplTypeInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmplTypeInfos");
        Page<HrEmplTypeInfo> page = hrEmplTypeInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmplTypeInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmplTypeInfos/:id -> get the "id" hrEmplTypeInfo.
     */
    @RequestMapping(value = "/hrEmplTypeInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmplTypeInfo> getHrEmplTypeInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmplTypeInfo : {}", id);
        HrEmplTypeInfo hrEmplTypeInfo = hrEmplTypeInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmplTypeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmplTypeInfos/:id -> delete the "id" hrEmplTypeInfo.
     */
    @RequestMapping(value = "/hrEmplTypeInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmplTypeInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmplTypeInfo : {}", id);
        hrEmplTypeInfoRepository.delete(id);
        hrEmplTypeInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmplTypeInfo", id.toString())).build();
    }


    /**
     * GET  /hrEmplTypeInfos -> get all the hrEmplTypeInfos.
     */
    @RequestMapping(value = "/hrEmplTypeInfos/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmplTypeInfo>> getAllHrGradeSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrEmplTypeInfos by status");
        Page<HrEmplTypeInfo> page = hrEmplTypeInfoRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrGradeSetups/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmplTypeInfosByStat/:stat -> get the all empl type by active status.
     */
    @RequestMapping(value = "/hrEmplTypeInfosByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmplTypeInfo> getAllEmplTypeByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all empl type by status : {}", stat);
        List<HrEmplTypeInfo> emplTypeInfoList = hrEmplTypeInfoRepository.findAllByActiveStatus(stat);
        return  emplTypeInfoList;
    }

    @RequestMapping(value = "/hrEmplTypeInfos/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkByTypeCode(@RequestParam String value)
    {
        Optional<HrEmplTypeInfo> empType = hrEmplTypeInfoRepository.findOneByTypeCode(value.toLowerCase());
        log.debug("EmployeeType by code: "+value+", Stat: "+Optional.empty().equals(empType));
        Map map =new HashMap();
        map.put("value", value);
        if(Optional.empty().equals(empType))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/hrEmplTypeInfos/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> checkByTypeName(@RequestParam String value)
    {
        Optional<HrEmplTypeInfo> modelInfo = hrEmplTypeInfoRepository.findOneByTypeName(value.toLowerCase());
        log.debug("hrEmplTypeInfos by name: "+value+", Stat: "+Optional.empty().equals(modelInfo));
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
     * SEARCH  /_search/hrEmplTypeInfos/:query -> search for the hrEmplTypeInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmplTypeInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmplTypeInfo> searchHrEmplTypeInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmplTypeInfos for query {}", query);
        return StreamSupport
            .stream(hrEmplTypeInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
