package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlPayscaleBasicInfo;
import gov.step.app.repository.payroll.PrlPayscaleBasicInfoRepository;
import gov.step.app.repository.search.payroll.PrlPayscaleBasicInfoSearchRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlPayscaleBasicInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlPayscaleBasicInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlPayscaleBasicInfoResource.class);

    @Inject
    private PrlPayscaleBasicInfoRepository prlPayscaleBasicInfoRepository;

    @Inject
    private PrlPayscaleBasicInfoSearchRepository prlPayscaleBasicInfoSearchRepository;

    /**
     * POST  /prlPayscaleBasicInfos -> Create a new prlPayscaleBasicInfo.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleBasicInfo> createPrlPayscaleBasicInfo(@Valid @RequestBody PrlPayscaleBasicInfo prlPayscaleBasicInfo) throws URISyntaxException {
        log.debug("REST request to save PrlPayscaleBasicInfo : {}", prlPayscaleBasicInfo);
        if (prlPayscaleBasicInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlPayscaleBasicInfo", "idexists", "A new prlPayscaleBasicInfo cannot already have an ID")).body(null);
        }
        PrlPayscaleBasicInfo result = prlPayscaleBasicInfoRepository.save(prlPayscaleBasicInfo);
        prlPayscaleBasicInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlPayscaleBasicInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlPayscaleBasicInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlPayscaleBasicInfos -> Updates an existing prlPayscaleBasicInfo.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleBasicInfo> updatePrlPayscaleBasicInfo(@Valid @RequestBody PrlPayscaleBasicInfo prlPayscaleBasicInfo) throws URISyntaxException {
        log.debug("REST request to update PrlPayscaleBasicInfo : {}", prlPayscaleBasicInfo);
        if (prlPayscaleBasicInfo.getId() == null) {
            return createPrlPayscaleBasicInfo(prlPayscaleBasicInfo);
        }
        PrlPayscaleBasicInfo result = prlPayscaleBasicInfoRepository.save(prlPayscaleBasicInfo);
        prlPayscaleBasicInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlPayscaleBasicInfo", prlPayscaleBasicInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlPayscaleBasicInfos -> get all the prlPayscaleBasicInfos.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlPayscaleBasicInfo>> getAllPrlPayscaleBasicInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlPayscaleBasicInfos");
        Page<PrlPayscaleBasicInfo> page = prlPayscaleBasicInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlPayscaleBasicInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlPayscaleBasicInfos/:id -> get the "id" prlPayscaleBasicInfo.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleBasicInfo> getPrlPayscaleBasicInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlPayscaleBasicInfo : {}", id);
        PrlPayscaleBasicInfo prlPayscaleBasicInfo = prlPayscaleBasicInfoRepository.findOne(id);
        return Optional.ofNullable(prlPayscaleBasicInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlPayscaleBasicInfos/:id -> delete the "id" prlPayscaleBasicInfo.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlPayscaleBasicInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlPayscaleBasicInfo : {}", id);
        prlPayscaleBasicInfoRepository.delete(id);
        prlPayscaleBasicInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlPayscaleBasicInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlPayscaleBasicInfos/:query -> search for the prlPayscaleBasicInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlPayscaleBasicInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlPayscaleBasicInfo> searchPrlPayscaleBasicInfos(@PathVariable String query) {
        log.debug("REST request to search PrlPayscaleBasicInfos for query {}", query);
        return StreamSupport
            .stream(prlPayscaleBasicInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlPayscaleBasicInfosByPayscale/:psid -> get address list by psid.
     */
    @RequestMapping(value = "/prlPayscaleBasicInfosByPayscale/{psid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlPayscaleBasicInfo> getModelListByPayscaleId(@PathVariable Long psid)
    {
        log.debug("REST request to load payscale basic by payscale List : logStatus: {} ",psid);
        List<PrlPayscaleBasicInfo> modelList = prlPayscaleBasicInfoRepository.findAllByPayScaleId(psid);
        return modelList;
    }
}
