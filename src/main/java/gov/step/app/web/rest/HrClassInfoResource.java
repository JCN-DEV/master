package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrClassInfo;
import gov.step.app.repository.HrClassInfoRepository;
import gov.step.app.repository.search.HrClassInfoSearchRepository;
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
 * REST controller for managing HrClassInfo.
 */
@RestController
@RequestMapping("/api")
public class HrClassInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrClassInfoResource.class);

    @Inject
    private HrClassInfoRepository hrClassInfoRepository;

    @Inject
    private HrClassInfoSearchRepository hrClassInfoSearchRepository;

    /**
     * POST  /hrClassInfos -> Create a new hrClassInfo.
     */
    @RequestMapping(value = "/hrClassInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrClassInfo> createHrClassInfo(@Valid @RequestBody HrClassInfo hrClassInfo) throws URISyntaxException {
        log.debug("REST request to save HrClassInfo : {}", hrClassInfo);
        if (hrClassInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrClassInfo", "idexists", "A new hrClassInfo cannot already have an ID")).body(null);
        }
        HrClassInfo result = hrClassInfoRepository.save(hrClassInfo);
        hrClassInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrClassInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrClassInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrClassInfos -> Updates an existing hrClassInfo.
     */
    @RequestMapping(value = "/hrClassInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrClassInfo> updateHrClassInfo(@Valid @RequestBody HrClassInfo hrClassInfo) throws URISyntaxException {
        log.debug("REST request to update HrClassInfo : {}", hrClassInfo);
        if (hrClassInfo.getId() == null) {
            return createHrClassInfo(hrClassInfo);
        }
        HrClassInfo result = hrClassInfoRepository.save(hrClassInfo);
        hrClassInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrClassInfo", hrClassInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrClassInfos -> get all the hrClassInfos.
     */
    @RequestMapping(value = "/hrClassInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrClassInfo>> getAllHrClassInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrClassInfos");
        Page<HrClassInfo> page = hrClassInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrClassInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrClassInfos -> get all the hrClassInfos.
     */
    @RequestMapping(value = "/hrClassInfos/bystat",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrClassInfo>> getAllHrClassSetupByStatus(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of hrClassInfos by status");
        Page<HrClassInfo> page = hrClassInfoRepository.findAllByActiveStatus(pageable, true);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrClassInfos/bystat");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrClassInfos/:id -> get the "id" hrClassInfo.
     */
    @RequestMapping(value = "/hrClassInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrClassInfo> getHrClassInfo(@PathVariable Long id) {
        log.debug("REST request to get HrClassInfo : {}", id);
        HrClassInfo hrClassInfo = hrClassInfoRepository.findOne(id);
        return Optional.ofNullable(hrClassInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrClassInfos/:id -> delete the "id" hrClassInfo.
     */
    @RequestMapping(value = "/hrClassInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrClassInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrClassInfo : {}", id);
        hrClassInfoRepository.delete(id);
        hrClassInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrClassInfo", id.toString())).build();
    }

    @RequestMapping(value = "/hrClassInfos/checkcode/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeCode(@RequestParam String value)
    {
        List<HrClassInfo> classInfoList = hrClassInfoRepository.findOneByClassCode(value);
        log.debug("hrClassInfos by code: "+value);
        Map map =new HashMap();
        map.put("classcode", value);
        if(classInfoList!=null) map.put("total", classInfoList.size());
        if(classInfoList!=null && classInfoList.size()>0)
        {
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/hrClassInfos/checkname/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> classByTypeName(@RequestParam String value)
    {
        List<HrClassInfo> classInfoList = hrClassInfoRepository.findOneByClassName(value);
        log.debug("hrClassInfos by name: "+value);

        Map map =new HashMap();
        map.put("classname", value);
        if(classInfoList!=null) map.put("total", classInfoList.size());
        if(classInfoList!=null && classInfoList.size()>0)
        {
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    /**
     * SEARCH  /_search/hrClassInfos/:query -> search for the hrClassInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrClassInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrClassInfo> searchHrClassInfos(@PathVariable String query) {
        log.debug("REST request to search HrClassInfos for query {}", query);
        return StreamSupport
            .stream(hrClassInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
