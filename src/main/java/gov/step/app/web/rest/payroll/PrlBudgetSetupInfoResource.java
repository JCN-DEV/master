package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlBudgetSetupInfo;
import gov.step.app.repository.payroll.PrlBudgetSetupInfoRepository;
import gov.step.app.repository.search.payroll.PrlBudgetSetupInfoSearchRepository;
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
 * REST controller for managing PrlBudgetSetupInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlBudgetSetupInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlBudgetSetupInfoResource.class);

    @Inject
    private PrlBudgetSetupInfoRepository prlBudgetSetupInfoRepository;

    @Inject
    private PrlBudgetSetupInfoSearchRepository prlBudgetSetupInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;


    /**
     * POST  /prlBudgetSetupInfos -> Create a new prlBudgetSetupInfo.
     */
    @RequestMapping(value = "/prlBudgetSetupInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlBudgetSetupInfo> createPrlBudgetSetupInfo(@Valid @RequestBody PrlBudgetSetupInfo prlBudgetSetupInfo) throws URISyntaxException {
        log.debug("REST request to save PrlBudgetSetupInfo : {}", prlBudgetSetupInfo);
        if (prlBudgetSetupInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlBudgetSetupInfo", "idexists", "A new prlBudgetSetupInfo cannot already have an ID")).body(null);
        }
        PrlBudgetSetupInfo result = prlBudgetSetupInfoRepository.save(prlBudgetSetupInfo);
        prlBudgetSetupInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlBudgetSetupInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlBudgetSetupInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlBudgetSetupInfos -> Updates an existing prlBudgetSetupInfo.
     */
    @RequestMapping(value = "/prlBudgetSetupInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlBudgetSetupInfo> updatePrlBudgetSetupInfo(@Valid @RequestBody PrlBudgetSetupInfo prlBudgetSetupInfo) throws URISyntaxException {
        log.debug("REST request to update PrlBudgetSetupInfo : {}", prlBudgetSetupInfo);
        if (prlBudgetSetupInfo.getId() == null) {
            return createPrlBudgetSetupInfo(prlBudgetSetupInfo);
        }
        PrlBudgetSetupInfo result = prlBudgetSetupInfoRepository.save(prlBudgetSetupInfo);
        prlBudgetSetupInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlBudgetSetupInfo", prlBudgetSetupInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlBudgetSetupInfos -> get all the prlBudgetSetupInfos.
     */
    @RequestMapping(value = "/prlBudgetSetupInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlBudgetSetupInfo>> getAllPrlBudgetSetupInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlBudgetSetupInfos");
        Page<PrlBudgetSetupInfo> page = prlBudgetSetupInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlBudgetSetupInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlBudgetSetupInfos/:id -> get the "id" prlBudgetSetupInfo.
     */
    @RequestMapping(value = "/prlBudgetSetupInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlBudgetSetupInfo> getPrlBudgetSetupInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlBudgetSetupInfo : {}", id);
        PrlBudgetSetupInfo prlBudgetSetupInfo = prlBudgetSetupInfoRepository.findOne(id);
        return Optional.ofNullable(prlBudgetSetupInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlBudgetSetupInfos/:id -> delete the "id" prlBudgetSetupInfo.
     */
    @RequestMapping(value = "/prlBudgetSetupInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlBudgetSetupInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlBudgetSetupInfo : {}", id);
        prlBudgetSetupInfoRepository.delete(id);
        prlBudgetSetupInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlBudgetSetupInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlBudgetSetupInfos/:query -> search for the prlBudgetSetupInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlBudgetSetupInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlBudgetSetupInfo> searchPrlBudgetSetupInfos(@PathVariable String query) {
        log.debug("REST request to search PrlBudgetSetupInfos for query {}", query);
        return StreamSupport
            .stream(prlBudgetSetupInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlBudgetSetupInfosByFilter/:type/:year/ -> get all budget setup list by type.
     */
    @RequestMapping(value = "/prlBudgetSetupInfosByFilter/{type}/{year}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlBudgetSetupInfo> getModelListBudgetSetupAll(@PathVariable String type,@PathVariable Long year)
    {
        log.debug("REST request to load budget setup type: {}, year: {}",type, year);
        List<PrlBudgetSetupInfo> modelList = payrollJdbcDao.getBudgetListWithAllowanceDeductionCode(year, type);
        return modelList;
    }
}
