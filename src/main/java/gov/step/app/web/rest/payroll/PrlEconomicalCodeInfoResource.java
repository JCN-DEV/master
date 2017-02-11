package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlEconomicalCodeInfo;
import gov.step.app.repository.payroll.PrlEconomicalCodeInfoRepository;
import gov.step.app.repository.search.payroll.PrlEconomicalCodeInfoSearchRepository;
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
 * REST controller for managing PrlEconomicalCodeInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlEconomicalCodeInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlEconomicalCodeInfoResource.class);

    @Inject
    private PrlEconomicalCodeInfoRepository prlEconomicalCodeInfoRepository;

    @Inject
    private PrlEconomicalCodeInfoSearchRepository prlEconomicalCodeInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;

    /**
     * POST  /prlEconomicalCodeInfos -> Create a new prlEconomicalCodeInfo.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEconomicalCodeInfo> createPrlEconomicalCodeInfo(@Valid @RequestBody PrlEconomicalCodeInfo prlEconomicalCodeInfo) throws URISyntaxException {
        log.debug("REST request to save PrlEconomicalCodeInfo : {}", prlEconomicalCodeInfo);
        if (prlEconomicalCodeInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlEconomicalCodeInfo", "idexists", "A new prlEconomicalCodeInfo cannot already have an ID")).body(null);
        }
        PrlEconomicalCodeInfo result = prlEconomicalCodeInfoRepository.save(prlEconomicalCodeInfo);
        prlEconomicalCodeInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlEconomicalCodeInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlEconomicalCodeInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlEconomicalCodeInfos -> Updates an existing prlEconomicalCodeInfo.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEconomicalCodeInfo> updatePrlEconomicalCodeInfo(@Valid @RequestBody PrlEconomicalCodeInfo prlEconomicalCodeInfo) throws URISyntaxException {
        log.debug("REST request to update PrlEconomicalCodeInfo : {}", prlEconomicalCodeInfo);
        if (prlEconomicalCodeInfo.getId() == null) {
            return createPrlEconomicalCodeInfo(prlEconomicalCodeInfo);
        }
        PrlEconomicalCodeInfo result = prlEconomicalCodeInfoRepository.save(prlEconomicalCodeInfo);
        prlEconomicalCodeInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlEconomicalCodeInfo", prlEconomicalCodeInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlEconomicalCodeInfos -> get all the prlEconomicalCodeInfos.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlEconomicalCodeInfo>> getAllPrlEconomicalCodeInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlEconomicalCodeInfos");
        Page<PrlEconomicalCodeInfo> page = prlEconomicalCodeInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlEconomicalCodeInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlEconomicalCodeInfos/:id -> get the "id" prlEconomicalCodeInfo.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEconomicalCodeInfo> getPrlEconomicalCodeInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlEconomicalCodeInfo : {}", id);
        PrlEconomicalCodeInfo prlEconomicalCodeInfo = prlEconomicalCodeInfoRepository.findOne(id);
        return Optional.ofNullable(prlEconomicalCodeInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlEconomicalCodeInfos/:id -> delete the "id" prlEconomicalCodeInfo.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlEconomicalCodeInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlEconomicalCodeInfo : {}", id);
        prlEconomicalCodeInfoRepository.delete(id);
        prlEconomicalCodeInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlEconomicalCodeInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlEconomicalCodeInfos/:query -> search for the prlEconomicalCodeInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlEconomicalCodeInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEconomicalCodeInfo> searchPrlEconomicalCodeInfos(@PathVariable String query) {
        log.debug("REST request to search PrlEconomicalCodeInfos for query {}", query);
        return StreamSupport
            .stream(prlEconomicalCodeInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlEconomicalCodeInfosAll -> get economical code list by psid.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfosAll/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEconomicalCodeInfo> getModelListByPayscaleId()
    {
        log.debug("REST request to load all economical code");
        List<PrlEconomicalCodeInfo> modelList = payrollJdbcDao.getEconomicalCodeListByAllAllowanceDeduction();
        return modelList;
    }

    /**
     * GET  /prlSalaryStructureInfosByFilter/:psid/:grdid/:empid -> get address list by psid.
     */
    @RequestMapping(value = "/prlEconomicalCodeInfosByFilter/{type}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEconomicalCodeInfo> getModelListByPayscaleId(@PathVariable String type)
    {
        log.debug("REST request to load economical code type: {}",type);
        List<PrlEconomicalCodeInfo> modelList = null;
        if(type.equalsIgnoreCase("all"))
        {
            modelList = payrollJdbcDao.getEconomicalCodeListByAllAllowanceDeduction();
        }
        else if(type.equalsIgnoreCase("saved"))
        {
            modelList = prlEconomicalCodeInfoRepository.findAllCode();
        }
        else if(type.equalsIgnoreCase("filtered"))
        {
            modelList = prlEconomicalCodeInfoRepository.findAllowanceAndSalaryCode();
        }

        return modelList;
    }
}
