package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlLocalitySetInfo;
import gov.step.app.repository.payroll.PrlLocalitySetInfoRepository;
import gov.step.app.repository.search.payroll.PrlLocalitySetInfoSearchRepository;
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
 * REST controller for managing PrlLocalitySetInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlLocalitySetInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlLocalitySetInfoResource.class);

    @Inject
    private PrlLocalitySetInfoRepository prlLocalitySetInfoRepository;

    @Inject
    private PrlLocalitySetInfoSearchRepository prlLocalitySetInfoSearchRepository;

    /**
     * POST  /prlLocalitySetInfos -> Create a new prlLocalitySetInfo.
     */
    @RequestMapping(value = "/prlLocalitySetInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalitySetInfo> createPrlLocalitySetInfo(@Valid @RequestBody PrlLocalitySetInfo prlLocalitySetInfo) throws URISyntaxException {
        log.debug("REST request to save PrlLocalitySetInfo : {}", prlLocalitySetInfo);
        if (prlLocalitySetInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlLocalitySetInfo", "idexists", "A new prlLocalitySetInfo cannot already have an ID")).body(null);
        }
        PrlLocalitySetInfo result = prlLocalitySetInfoRepository.save(prlLocalitySetInfo);
        prlLocalitySetInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlLocalitySetInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlLocalitySetInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlLocalitySetInfos -> Updates an existing prlLocalitySetInfo.
     */
    @RequestMapping(value = "/prlLocalitySetInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalitySetInfo> updatePrlLocalitySetInfo(@Valid @RequestBody PrlLocalitySetInfo prlLocalitySetInfo) throws URISyntaxException {
        log.debug("REST request to update PrlLocalitySetInfo : {}", prlLocalitySetInfo);
        if (prlLocalitySetInfo.getId() == null) {
            return createPrlLocalitySetInfo(prlLocalitySetInfo);
        }
        PrlLocalitySetInfo result = prlLocalitySetInfoRepository.save(prlLocalitySetInfo);
        prlLocalitySetInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlLocalitySetInfo", prlLocalitySetInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlLocalitySetInfos -> get all the prlLocalitySetInfos.
     */
    @RequestMapping(value = "/prlLocalitySetInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlLocalitySetInfo>> getAllPrlLocalitySetInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlLocalitySetInfos");
        Page<PrlLocalitySetInfo> page = prlLocalitySetInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlLocalitySetInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlLocalitySetInfos/:id -> get the "id" prlLocalitySetInfo.
     */
    @RequestMapping(value = "/prlLocalitySetInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlLocalitySetInfo> getPrlLocalitySetInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlLocalitySetInfo : {}", id);
        PrlLocalitySetInfo prlLocalitySetInfo = prlLocalitySetInfoRepository.findOneWithEagerRelationships(id);
        return Optional.ofNullable(prlLocalitySetInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlLocalitySetInfos/:id -> delete the "id" prlLocalitySetInfo.
     */
    @RequestMapping(value = "/prlLocalitySetInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlLocalitySetInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlLocalitySetInfo : {}", id);
        prlLocalitySetInfoRepository.delete(id);
        prlLocalitySetInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlLocalitySetInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlLocalitySetInfos/:query -> search for the prlLocalitySetInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlLocalitySetInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlLocalitySetInfo> searchPrlLocalitySetInfos(@PathVariable String query) {
        log.debug("REST request to search PrlLocalitySetInfos for query {}", query);
        return StreamSupport
            .stream(prlLocalitySetInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/prlLocalitySetInfosByName/{name}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getUniqueLocalitySetName(@PathVariable String name)
    {
        Optional<PrlLocalitySetInfo> modelInfo = null;
        modelInfo = prlLocalitySetInfoRepository.findOneByName(name);
        log.debug("prlLocalitySetInfosByName by  name: "+name+", Stat: "+Optional.empty().equals(modelInfo));
        Map map =new HashMap();
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
