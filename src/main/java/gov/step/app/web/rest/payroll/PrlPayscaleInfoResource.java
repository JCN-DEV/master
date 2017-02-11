package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlPayscaleInfo;
import gov.step.app.repository.payroll.PrlPayscaleInfoRepository;
import gov.step.app.repository.search.payroll.PrlPayscaleInfoSearchRepository;
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
 * REST controller for managing PrlPayscaleInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlPayscaleInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlPayscaleInfoResource.class);

    @Inject
    private PrlPayscaleInfoRepository prlPayscaleInfoRepository;

    @Inject
    private PrlPayscaleInfoSearchRepository prlPayscaleInfoSearchRepository;

    /**
     * POST  /prlPayscaleInfos -> Create a new prlPayscaleInfo.
     */
    @RequestMapping(value = "/prlPayscaleInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleInfo> createPrlPayscaleInfo(@Valid @RequestBody PrlPayscaleInfo prlPayscaleInfo) throws URISyntaxException {
        log.debug("REST request to save PrlPayscaleInfo : {}", prlPayscaleInfo);
        if (prlPayscaleInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlPayscaleInfo", "idexists", "A new prlPayscaleInfo cannot already have an ID")).body(null);
        }
        PrlPayscaleInfo result = prlPayscaleInfoRepository.save(prlPayscaleInfo);
        prlPayscaleInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlPayscaleInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlPayscaleInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlPayscaleInfos -> Updates an existing prlPayscaleInfo.
     */
    @RequestMapping(value = "/prlPayscaleInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleInfo> updatePrlPayscaleInfo(@Valid @RequestBody PrlPayscaleInfo prlPayscaleInfo) throws URISyntaxException {
        log.debug("REST request to update PrlPayscaleInfo : {}", prlPayscaleInfo);
        if (prlPayscaleInfo.getId() == null) {
            return createPrlPayscaleInfo(prlPayscaleInfo);
        }
        PrlPayscaleInfo result = prlPayscaleInfoRepository.save(prlPayscaleInfo);
        prlPayscaleInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlPayscaleInfo", prlPayscaleInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlPayscaleInfos -> get all the prlPayscaleInfos.
     */
    @RequestMapping(value = "/prlPayscaleInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlPayscaleInfo>> getAllPrlPayscaleInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlPayscaleInfos");
        Page<PrlPayscaleInfo> page = prlPayscaleInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlPayscaleInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlPayscaleInfos/:id -> get the "id" prlPayscaleInfo.
     */
    @RequestMapping(value = "/prlPayscaleInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleInfo> getPrlPayscaleInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlPayscaleInfo : {}", id);
        PrlPayscaleInfo prlPayscaleInfo = prlPayscaleInfoRepository.findOne(id);
        return Optional.ofNullable(prlPayscaleInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlPayscaleInfos/:id -> delete the "id" prlPayscaleInfo.
     */
    @RequestMapping(value = "/prlPayscaleInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlPayscaleInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlPayscaleInfo : {}", id);
        prlPayscaleInfoRepository.delete(id);
        prlPayscaleInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlPayscaleInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlPayscaleInfos/:query -> search for the prlPayscaleInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlPayscaleInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlPayscaleInfo> searchPrlPayscaleInfos(@PathVariable String query) {
        log.debug("REST request to search PrlPayscaleInfos for query {}", query);
        return StreamSupport
            .stream(prlPayscaleInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlPayscaleInfosByGrdGzzt/:gztid/:grdid -> get payscale info by gztid and grdid.
     */
    @RequestMapping(value = "/prlPayscaleInfosByGrdGzzt/{gztid}/{grdid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public PrlPayscaleInfo getModelInfoByPayscaleId(@PathVariable Long grdid, @PathVariable Long gztid)
    {
        log.debug("REST request to load payscale by gazzete: {}, grade: {} ",gztid, grdid);
        PrlPayscaleInfo payscaleInfo = prlPayscaleInfoRepository.findByGazzeteAndGradeInfo(gztid, grdid);
        return payscaleInfo;
    }

    /**
     * GET  /prlPayscaleInfCheckGrdGzztNotSelf/:gztid/:grdid/:{psid} -> get payscale info by gztid and grdid.
     */
    @RequestMapping(value = "/prlPayscaleInfCheckGrdGzztNotSelf/{gztid}/{grdid}/{psid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getModelInfoByPayscaleIdNotSelf(@PathVariable Long grdid, @PathVariable Long gztid, @PathVariable Long psid)
    {
        log.debug("REST load payscale check by gazzete: {}, grade: {}, payscale: {} ",gztid, grdid, psid);
        PrlPayscaleInfo payscaleInfo = null;
        if(psid > 0)
        {
            payscaleInfo = prlPayscaleInfoRepository.findByGazzeteAndGradeExceptSelf(gztid, grdid, psid);
        }
        else
        {
            payscaleInfo = prlPayscaleInfoRepository.findByGazzeteAndGradeInfo(gztid, grdid);
        }

        Map map =new HashMap();

        if(payscaleInfo!=null)
        {
            map.put("total", 1);
            map.put("payscaleid", payscaleInfo.getId());
            map.put("payscalename", payscaleInfo.getName());
            map.put("isValid",false);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
        else {
            map.put("total", 0);
            map.put("payscaleid", -1);
            map.put("payscalename", "");
            map.put("isValid",true);
            return new ResponseEntity<Map>(map,HttpStatus.OK);
        }
    }
}
