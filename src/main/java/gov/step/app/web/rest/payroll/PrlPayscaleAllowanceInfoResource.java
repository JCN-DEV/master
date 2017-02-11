package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlPayscaleAllowanceInfo;
import gov.step.app.repository.payroll.PrlPayscaleAllowanceInfoRepository;
import gov.step.app.repository.search.payroll.PrlPayscaleAllowanceInfoSearchRepository;
import gov.step.app.web.rest.jdbc.dao.PayrollJdbcDao;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlPayscaleAllowanceInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlPayscaleAllowanceInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlPayscaleAllowanceInfoResource.class);

    @Inject
    private PrlPayscaleAllowanceInfoRepository prlPayscaleAllowanceInfoRepository;

    @Inject
    private PrlPayscaleAllowanceInfoSearchRepository prlPayscaleAllowanceInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;

    /**
     * POST  /prlPayscaleAllowanceInfos -> Create a new prlPayscaleAllowanceInfo.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleAllowanceInfo> createPrlPayscaleAllowanceInfo(@Valid @RequestBody PrlPayscaleAllowanceInfo prlPayscaleAllowanceInfo) throws URISyntaxException {
        log.debug("REST request to save PrlPayscaleAllowanceInfo : {}", prlPayscaleAllowanceInfo);
        if (prlPayscaleAllowanceInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlPayscaleAllowanceInfo", "idexists", "A new prlPayscaleAllowanceInfo cannot already have an ID")).body(null);
        }
        PrlPayscaleAllowanceInfo result = prlPayscaleAllowanceInfoRepository.save(prlPayscaleAllowanceInfo);
        prlPayscaleAllowanceInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlPayscaleAllowanceInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlPayscaleAllowanceInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlPayscaleAllowanceInfos -> Updates an existing prlPayscaleAllowanceInfo.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleAllowanceInfo> updatePrlPayscaleAllowanceInfo(@Valid @RequestBody PrlPayscaleAllowanceInfo prlPayscaleAllowanceInfo) throws URISyntaxException {
        log.debug("REST request to update PrlPayscaleAllowanceInfo : {}", prlPayscaleAllowanceInfo);
        if (prlPayscaleAllowanceInfo.getId() == null) {
            return createPrlPayscaleAllowanceInfo(prlPayscaleAllowanceInfo);
        }
        PrlPayscaleAllowanceInfo result = prlPayscaleAllowanceInfoRepository.save(prlPayscaleAllowanceInfo);
        prlPayscaleAllowanceInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlPayscaleAllowanceInfo", prlPayscaleAllowanceInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlPayscaleAllowanceInfos -> get all the prlPayscaleAllowanceInfos.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlPayscaleAllowanceInfo>> getAllPrlPayscaleAllowanceInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlPayscaleAllowanceInfos");
        Page<PrlPayscaleAllowanceInfo> page = prlPayscaleAllowanceInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlPayscaleAllowanceInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlPayscaleAllowanceInfos/:id -> get the "id" prlPayscaleAllowanceInfo.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlPayscaleAllowanceInfo> getPrlPayscaleAllowanceInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlPayscaleAllowanceInfo : {}", id);
        PrlPayscaleAllowanceInfo prlPayscaleAllowanceInfo = prlPayscaleAllowanceInfoRepository.findOne(id);
        return Optional.ofNullable(prlPayscaleAllowanceInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlPayscaleAllowanceInfos/:id -> delete the "id" prlPayscaleAllowanceInfo.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlPayscaleAllowanceInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlPayscaleAllowanceInfo : {}", id);
        prlPayscaleAllowanceInfoRepository.delete(id);
        prlPayscaleAllowanceInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlPayscaleAllowanceInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlPayscaleAllowanceInfos/:query -> search for the prlPayscaleAllowanceInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlPayscaleAllowanceInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlPayscaleAllowanceInfo> searchPrlPayscaleAllowanceInfos(@PathVariable String query) {
        log.debug("REST request to search PrlPayscaleAllowanceInfos for query {}", query);
        return StreamSupport
            .stream(prlPayscaleAllowanceInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /prlPayscaleAllowanceInfosByPsGrd/:psid -> get allowance list by psid and grade id.
     */
    @RequestMapping(value = "/prlPayscaleAllowanceInfosByPsGrd/{psid}/{grdid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlPayscaleAllowanceInfo> getModelListByPayscaleIdAndGradeId(@PathVariable Long psid, @PathVariable Long grdid)
    {
        log.debug("REST request to load payscale allowance by payscale and grade List : PayScale: {}, Grade: {} ",psid, grdid);
        List<PrlPayscaleAllowanceInfo> modelList = payrollJdbcDao.findAllPayscaleAllowanceByPayScaleAndGrade(psid, grdid);
        return modelList;
    }
}
