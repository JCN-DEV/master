package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlAllowDeductInfo;
import gov.step.app.domain.payroll.PrlEconomicalCodeInfo;
import gov.step.app.repository.payroll.PrlAllowDeductInfoRepository;
import gov.step.app.repository.payroll.PrlEconomicalCodeInfoRepository;
import gov.step.app.repository.search.payroll.PrlAllowDeductInfoSearchRepository;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlAllowDeductInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlAllowDeductInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlAllowDeductInfoResource.class);

    @Inject
    private PrlAllowDeductInfoRepository prlAllowDeductInfoRepository;

    @Inject
    private PrlEconomicalCodeInfoRepository prlEconomicalCodeInfoRepository;

    @Inject
    private PrlAllowDeductInfoSearchRepository prlAllowDeductInfoSearchRepository;

    /**
     * POST  /prlAllowDeductInfos -> Create a new prlAllowDeductInfo.
     */
    @RequestMapping(value = "/prlAllowDeductInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlAllowDeductInfo> createPrlAllowDeductInfo(@Valid @RequestBody PrlAllowDeductInfo prlAllowDeductInfo) throws URISyntaxException {
        log.debug("REST request to save PrlAllowDeductInfo : {}", prlAllowDeductInfo);
        if (prlAllowDeductInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlAllowDeductInfo", "idexists", "A new prlAllowDeductInfo cannot already have an ID")).body(null);
        }
        PrlAllowDeductInfo result = prlAllowDeductInfoRepository.save(prlAllowDeductInfo);
        prlAllowDeductInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlAllowDeductInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlAllowDeductInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlAllowDeductInfos -> Updates an existing prlAllowDeductInfo.
     */
    @RequestMapping(value = "/prlAllowDeductInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlAllowDeductInfo> updatePrlAllowDeductInfo(@Valid @RequestBody PrlAllowDeductInfo prlAllowDeductInfo) throws URISyntaxException {
        log.debug("REST request to update PrlAllowDeductInfo : {}", prlAllowDeductInfo);
        if (prlAllowDeductInfo.getId() == null) {
            return createPrlAllowDeductInfo(prlAllowDeductInfo);
        }
        PrlAllowDeductInfo result = prlAllowDeductInfoRepository.save(prlAllowDeductInfo);
        prlAllowDeductInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlAllowDeductInfo", prlAllowDeductInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlAllowDeductInfos -> get all the prlAllowDeductInfos.
     */
    @RequestMapping(value = "/prlAllowDeductInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlAllowDeductInfo>> getAllPrlAllowDeductInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlAllowDeductInfos");
        Page<PrlAllowDeductInfo> page = prlAllowDeductInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlAllowDeductInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlAllowDeductInfos/:id -> get the "id" prlAllowDeductInfo.
     */
    @RequestMapping(value = "/prlAllowDeductInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlAllowDeductInfo> getPrlAllowDeductInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlAllowDeductInfo : {}", id);
        PrlAllowDeductInfo prlAllowDeductInfo = prlAllowDeductInfoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(prlAllowDeductInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlAllowDeductInfos/:id -> delete the "id" prlAllowDeductInfo.
     */
    @RequestMapping(value = "/prlAllowDeductInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlAllowDeductInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlAllowDeductInfo : {}", id);
        prlAllowDeductInfoRepository.delete(id);
        prlAllowDeductInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlAllowDeductInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlAllowDeductInfos/:query -> search for the prlAllowDeductInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlAllowDeductInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlAllowDeductInfo> searchPrlAllowDeductInfos(@PathVariable String query) {
        log.debug("REST request to search PrlAllowDeductInfos for query {}", query);
        return StreamSupport
            .stream(prlAllowDeductInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/prlAllowDeductInfosByTypeAndName/{type}/{name}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getAllowanceDeductionByTypeAndName(@PathVariable String type, @PathVariable String name)
    {
        Optional<PrlAllowDeductInfo> modelInfo = null;
        modelInfo = prlAllowDeductInfoRepository.findOneByTypeAndName(type, name);
        log.debug("prlAllowDeductInfosByTypeAndName by type: "+type+", name: "+name+", Stat: "+Optional.empty().equals(modelInfo));
        Map map =new HashMap();
        map.put("type", type);
        map.put("name", name);
        if(Optional.empty().equals(modelInfo))
        {
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }else{
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/prlAllowDeductInfosByType/{type}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlAllowDeductInfo> getAllowanceDeductionListByType(@PathVariable String type)
    {
        log.debug("prlAllowDeductInfosByType by type: "+type);
        List<PrlAllowDeductInfo> modelInfoList = prlAllowDeductInfoRepository.findAllByType(type);
        return modelInfoList;
    }

    @RequestMapping(value = "/prlAllowDeductInfoByCode/{code}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public PrlAllowDeductInfo getAllowanceDecuctionByCode(@PathVariable String code)
    {
        log.debug("prlAllowDeductInfoByCode by code: "+code);
        PrlAllowDeductInfo modelInfo = prlAllowDeductInfoRepository.findAllowanceDeductionByCode(code);
        return modelInfo;
    }

    @RequestMapping(value = "/prlAllowDeductInfoByEconCodes/{codes}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlAllowDeductInfo> getAllowanceListEconimicalCodes(@PathVariable String codes)
    {
        List<PrlAllowDeductInfo> allDedList = new ArrayList<PrlAllowDeductInfo>();
        List<String> codeList = new ArrayList<String>();
        for(String str: codes.split("#"))
        {
            codeList.add(str);
        }
        log.debug("prlAllowDeductInfoByEconCodes by codes: "+codes);
        List<PrlEconomicalCodeInfo> modelInfoList = prlEconomicalCodeInfoRepository.findAllowanceListByCodes(codeList);

        for(PrlEconomicalCodeInfo econCode: modelInfoList)
        {
            allDedList.add(econCode.getAllowanceDeductionInfo());
        }
        return allDedList;
    }

    @RequestMapping(value = "/prlAllowDeductInfosByCategory/{cat}/{alid}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getAllowanceDeductionByCategory(@PathVariable String cat, @PathVariable Long alid)
    {
        List<PrlAllowDeductInfo> modelList = null;

        if(alid>0)
        {
            modelList = prlAllowDeductInfoRepository.findAllowanceByCategoryAndID(cat, alid);
        }
        else
        {
            modelList = prlAllowDeductInfoRepository.findAllowanceByCategory(cat);
        }

        log.debug("prlAllowDeductInfosByCategory by cat: "+cat);
        Map map =new HashMap();
        map.put("category", cat);
        map.put("alid", alid);
        if(modelList!=null && modelList.size() > 0)
        {
            map.put("isExist",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
        else
        {
            map.put("isExist",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
