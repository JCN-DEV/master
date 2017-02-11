package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlSalaryStructureInfo;
import gov.step.app.repository.payroll.PrlSalaryStructureInfoRepository;
import gov.step.app.repository.search.payroll.PrlSalaryStructureInfoSearchRepository;
import gov.step.app.web.rest.dto.PrlSalaryStructureDto;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlSalaryStructureInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlSalaryStructureInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlSalaryStructureInfoResource.class);

    @Inject
    private PrlSalaryStructureInfoRepository prlSalaryStructureInfoRepository;

    @Inject
    private PrlSalaryStructureInfoSearchRepository prlSalaryStructureInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;

    /**
     * POST  /prlSalaryStructureInfos -> Create a new prlSalaryStructureInfo.
     */
    @RequestMapping(value = "/prlSalaryStructureInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryStructureInfo> createPrlSalaryStructureInfo(@Valid @RequestBody PrlSalaryStructureInfo prlSalaryStructureInfo) throws URISyntaxException {
        log.debug("REST request to save PrlSalaryStructureInfo : {}", prlSalaryStructureInfo);
        if (prlSalaryStructureInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlSalaryStructureInfo", "idexists", "A new prlSalaryStructureInfo cannot already have an ID")).body(null);
        }
        PrlSalaryStructureInfo result = prlSalaryStructureInfoRepository.save(prlSalaryStructureInfo);
        prlSalaryStructureInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlSalaryStructureInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlSalaryStructureInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlSalaryStructureInfos -> Updates an existing prlSalaryStructureInfo.
     */
    @RequestMapping(value = "/prlSalaryStructureInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryStructureInfo> updatePrlSalaryStructureInfo(@Valid @RequestBody PrlSalaryStructureInfo prlSalaryStructureInfo) throws URISyntaxException {
        log.debug("REST request to update PrlSalaryStructureInfo : {}", prlSalaryStructureInfo);
        if (prlSalaryStructureInfo.getId() == null) {
            return createPrlSalaryStructureInfo(prlSalaryStructureInfo);
        }
        PrlSalaryStructureInfo result = prlSalaryStructureInfoRepository.save(prlSalaryStructureInfo);
        prlSalaryStructureInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlSalaryStructureInfo", prlSalaryStructureInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlSalaryStructureInfos -> get all the prlSalaryStructureInfos.
     */
    @RequestMapping(value = "/prlSalaryStructureInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlSalaryStructureInfo>> getAllPrlSalaryStructureInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlSalaryStructureInfos");
        Page<PrlSalaryStructureInfo> page = prlSalaryStructureInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlSalaryStructureInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlSalaryStructureInfos/:id -> get the "id" prlSalaryStructureInfo.
     */
    @RequestMapping(value = "/prlSalaryStructureInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryStructureInfo> getPrlSalaryStructureInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlSalaryStructureInfo : {}", id);
        PrlSalaryStructureInfo prlSalaryStructureInfo = prlSalaryStructureInfoRepository.findOne(id);
        return Optional.ofNullable(prlSalaryStructureInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlSalaryStructureInfos/:id -> delete the "id" prlSalaryStructureInfo.
     */
    @RequestMapping(value = "/prlSalaryStructureInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlSalaryStructureInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlSalaryStructureInfo : {}", id);
        prlSalaryStructureInfoRepository.delete(id);
        prlSalaryStructureInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlSalaryStructureInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlSalaryStructureInfos/:query -> search for the prlSalaryStructureInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlSalaryStructureInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlSalaryStructureInfo> searchPrlSalaryStructureInfos(@PathVariable String query) {
        log.debug("REST request to search PrlSalaryStructureInfos for query {}", query);
        return StreamSupport
            .stream(prlSalaryStructureInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlSalaryStructureInfosByFilter/:psid/:grdid/:empid -> get address list by psid.
     */
    @RequestMapping(value = "/prlSalaryStructureInfosByFilter/{psid}/{grdid}/{empid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlSalaryStructureDto> getModelListByPayscaleId(@PathVariable Long psid, @PathVariable Long grdid, @PathVariable Long empid)
    {
        log.debug("REST request to load salary structure payscaleId: {}, gradeId: {}, empId: {} ",psid, grdid, empid);
        List<PrlSalaryStructureDto> modelList = payrollJdbcDao.findAllSalaryStructureAllowanceByFilter(psid, grdid, empid);
        return modelList;
    }

    /**
     * GET  /prlSalaryStructureInfosByEmp/:salid/:empid -> get salary structure list by emp and id.
     */
    @RequestMapping(value = "/prlSalaryStructureInfosByEmp/{salid}/{empid}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> getStructureByEmployee(@PathVariable Long salid, @PathVariable Long empid)
    {
        log.debug("REST load salary structure by empl: {}, current: {} ",empid, salid);

        List<PrlSalaryStructureInfo> salaryStructureInfoList = prlSalaryStructureInfoRepository.findByEmployee(empid);

        PrlSalaryStructureInfo salaryStructureInfo = null;
        Map map =new HashMap();
        if(salaryStructureInfoList!=null )
        {
            log.debug("list size: {} ",salaryStructureInfoList.size());
            if(salaryStructureInfoList.size()>1)
            {
                map.put("total", salaryStructureInfoList.size());
                map.put("empid", empid);
                map.put("salid", salid);
                map.put("isValid",false);
            }
            else if(salaryStructureInfoList.size()==1)
            {
                salaryStructureInfo = salaryStructureInfoList.get(0);
                if(salid>0 && salaryStructureInfo.getId()==salid)
                {
                    map.put("total", 1);
                    map.put("empid", empid);
                    map.put("salid", salid);
                    map.put("isValid",true);
                }
                else
                {
                    map.put("total", salaryStructureInfoList.size());
                    map.put("empid", empid);
                    map.put("salid", salid);
                    map.put("isValid",false);
                }
            }
            else
            {
                map.put("total", 0);
                map.put("empid", empid);
                map.put("salid", salid);
                map.put("isValid",true);
            }
        }
        else
        {
            log.debug("list is null ");
            map.put("total", 0);
            map.put("empid", empid);
            map.put("salid", salid);
            map.put("isValid",true);
        }
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }
}
