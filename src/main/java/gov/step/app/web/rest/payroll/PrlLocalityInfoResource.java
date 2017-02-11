package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlLocalityInfo;
import gov.step.app.repository.payroll.PrlLocalityInfoRepository;
import gov.step.app.repository.search.payroll.PrlLocalityInfoSearchRepository;
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
 * REST controller for managing PrlLocalityInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlLocalityInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlLocalityInfoResource.class);

    @Inject
    private PrlLocalityInfoRepository prlLocalityInfoRepository;

    @Inject
    private PrlLocalityInfoSearchRepository prlLocalityInfoSearchRepository;

    /**
     * POST  /prlLocalityInfos -> Create a new prlLocalityInfo.
     */
    @RequestMapping(value = "/prlLocalityInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalityInfo> createPrlLocalityInfo(@Valid @RequestBody PrlLocalityInfo prlLocalityInfo) throws URISyntaxException {
        log.debug("REST request to save PrlLocalityInfo : {}", prlLocalityInfo);
        if (prlLocalityInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlLocalityInfo", "idexists", "A new prlLocalityInfo cannot already have an ID")).body(null);
        }
        PrlLocalityInfo result = prlLocalityInfoRepository.save(prlLocalityInfo);
        prlLocalityInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlLocalityInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlLocalityInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlLocalityInfos -> Updates an existing prlLocalityInfo.
     */
    @RequestMapping(value = "/prlLocalityInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalityInfo> updatePrlLocalityInfo(@Valid @RequestBody PrlLocalityInfo prlLocalityInfo) throws URISyntaxException {
        log.debug("REST request to update PrlLocalityInfo : {}", prlLocalityInfo);
        if (prlLocalityInfo.getId() == null) {
            return createPrlLocalityInfo(prlLocalityInfo);
        }
        PrlLocalityInfo result = prlLocalityInfoRepository.save(prlLocalityInfo);
        prlLocalityInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlLocalityInfo", prlLocalityInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlLocalityInfos -> get all the prlLocalityInfos.
     */
    @RequestMapping(value = "/prlLocalityInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlLocalityInfo>> getAllPrlLocalityInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlLocalityInfos");
        Page<PrlLocalityInfo> page = prlLocalityInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlLocalityInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlLocalityInfos/:id -> get the "id" prlLocalityInfo.
     */
    @RequestMapping(value = "/prlLocalityInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalityInfo> getPrlLocalityInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlLocalityInfo : {}", id);
        PrlLocalityInfo prlLocalityInfo = prlLocalityInfoRepository.findOne(id);
        return Optional.ofNullable(prlLocalityInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlLocalityInfos/:id -> delete the "id" prlLocalityInfo.
     */
    @RequestMapping(value = "/prlLocalityInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlLocalityInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlLocalityInfo : {}", id);
        prlLocalityInfoRepository.delete(id);
        prlLocalityInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlLocalityInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlLocalityInfos/:query -> search for the prlLocalityInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlLocalityInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlLocalityInfo> searchPrlLocalityInfos(@PathVariable String query) {
        log.debug("REST request to search PrlLocalityInfos for query {}", query);
        return StreamSupport
            .stream(prlLocalityInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/prlLocalityInfosByDistAndName/{distid}/{name}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getLocalityNameByDistrictAndName(@PathVariable Long distid, @PathVariable String name)
    {
        Optional<PrlLocalityInfo> modelInfo = null;
        modelInfo = prlLocalityInfoRepository.findOneByDistrictAndName(distid, name);
        log.debug("prlLocalityInfosByDistAndName by distId: "+distid+", name: "+name+", Stat: "+Optional.empty().equals(modelInfo));
        Map map =new HashMap();
        map.put("distid", distid);
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
}
