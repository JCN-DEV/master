package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlHouseRentAllowInfo;
import gov.step.app.repository.payroll.PrlHouseRentAllowInfoRepository;
import gov.step.app.repository.search.payroll.PrlHouseRentAllowInfoSearchRepository;
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
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlHouseRentAllowInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlHouseRentAllowInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlHouseRentAllowInfoResource.class);

    @Inject
    private PrlHouseRentAllowInfoRepository prlHouseRentAllowInfoRepository;

    @Inject
    private PrlHouseRentAllowInfoSearchRepository prlHouseRentAllowInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;

    /**
     * POST  /prlHouseRentAllowInfos -> Create a new prlHouseRentAllowInfo.
     */
    @RequestMapping(value = "/prlHouseRentAllowInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlHouseRentAllowInfo> createPrlHouseRentAllowInfo(@Valid @RequestBody PrlHouseRentAllowInfo prlHouseRentAllowInfo) throws URISyntaxException {
        log.debug("REST request to save PrlHouseRentAllowInfo : {}", prlHouseRentAllowInfo);
        if (prlHouseRentAllowInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlHouseRentAllowInfo", "idexists", "A new prlHouseRentAllowInfo cannot already have an ID")).body(null);
        }
        PrlHouseRentAllowInfo result = prlHouseRentAllowInfoRepository.save(prlHouseRentAllowInfo);
        prlHouseRentAllowInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlHouseRentAllowInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlHouseRentAllowInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlHouseRentAllowInfos -> Updates an existing prlHouseRentAllowInfo.
     */
    @RequestMapping(value = "/prlHouseRentAllowInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlHouseRentAllowInfo> updatePrlHouseRentAllowInfo(@Valid @RequestBody PrlHouseRentAllowInfo prlHouseRentAllowInfo) throws URISyntaxException {
        log.debug("REST request to update PrlHouseRentAllowInfo : {}", prlHouseRentAllowInfo);
        if (prlHouseRentAllowInfo.getId() == null) {
            return createPrlHouseRentAllowInfo(prlHouseRentAllowInfo);
        }
        PrlHouseRentAllowInfo result = prlHouseRentAllowInfoRepository.save(prlHouseRentAllowInfo);
        prlHouseRentAllowInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlHouseRentAllowInfo", prlHouseRentAllowInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlHouseRentAllowInfos -> get all the prlHouseRentAllowInfos.
     */
    @RequestMapping(value = "/prlHouseRentAllowInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlHouseRentAllowInfo>> getAllPrlHouseRentAllowInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlHouseRentAllowInfos");
        Page<PrlHouseRentAllowInfo> page = prlHouseRentAllowInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlHouseRentAllowInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlHouseRentAllowInfos/:id -> get the "id" prlHouseRentAllowInfo.
     */
    @RequestMapping(value = "/prlHouseRentAllowInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlHouseRentAllowInfo> getPrlHouseRentAllowInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlHouseRentAllowInfo : {}", id);
        PrlHouseRentAllowInfo prlHouseRentAllowInfo = prlHouseRentAllowInfoRepository.findOne(id);
        return Optional.ofNullable(prlHouseRentAllowInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlHouseRentAllowInfos/:id -> delete the "id" prlHouseRentAllowInfo.
     */
    @RequestMapping(value = "/prlHouseRentAllowInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlHouseRentAllowInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlHouseRentAllowInfo : {}", id);
        prlHouseRentAllowInfoRepository.delete(id);
        prlHouseRentAllowInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlHouseRentAllowInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlHouseRentAllowInfos/:query -> search for the prlHouseRentAllowInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlHouseRentAllowInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlHouseRentAllowInfo> searchPrlHouseRentAllowInfos(@PathVariable String query) {
        log.debug("REST request to search PrlHouseRentAllowInfos for query {}", query);
        return StreamSupport
            .stream(prlHouseRentAllowInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/prlHouseRentAllowInfosMinMaxByLocality/{gztid}/{lclstid}",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getDesignationByDesigHeadAndWorkArea(@PathVariable Long gztid, @PathVariable Long lclstid)
    {
        Map map = payrollJdbcDao.findHouseRentMinMaxByLocalityAndGezzete(gztid, lclstid);
        log.debug("prlHouseRentAllowInfosMinMaxByLocality by gezzeteId: "+gztid+", localitySetId: "+lclstid);

        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }
}
