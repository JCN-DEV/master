package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlGeneratedSalaryInfo;
import gov.step.app.repository.payroll.PrlGeneratedSalaryInfoRepository;
import gov.step.app.repository.search.payroll.PrlGeneratedSalaryInfoSearchRepository;
import gov.step.app.web.rest.dto.PrlGeneratedSalaryDto;
import gov.step.app.web.rest.dto.PrlSalaryGenerationDto;
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
 * REST controller for managing PrlGeneratedSalaryInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlGeneratedSalaryInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlGeneratedSalaryInfoResource.class);

    @Inject
    private PrlGeneratedSalaryInfoRepository prlGeneratedSalaryInfoRepository;

    @Inject
    private PrlGeneratedSalaryInfoSearchRepository prlGeneratedSalaryInfoSearchRepository;

    @Inject
    private PayrollJdbcDao payrollJdbcDao;

    /**
     * POST  /prlGeneratedSalaryInfos -> Create a new prlGeneratedSalaryInfo.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> createPrlGeneratedSalaryInfo(@Valid @RequestBody PrlGeneratedSalaryInfo prlGeneratedSalaryInfo) throws URISyntaxException {
        log.debug("REST request to save PrlGeneratedSalaryInfo : {}", prlGeneratedSalaryInfo);
        if (prlGeneratedSalaryInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlGeneratedSalaryInfo", "idexists", "A new prlGeneratedSalaryInfo cannot already have an ID")).body(null);
        }
        Map map =new HashMap();
        try
        {
            map.put("type", prlGeneratedSalaryInfo.getSalaryType());
            map.put("month", prlGeneratedSalaryInfo.getMonthName());
            map.put("year", String.valueOf(prlGeneratedSalaryInfo.getYearName()));
            log.debug("generateSalaryByStoredProc new req: Type: "+prlGeneratedSalaryInfo.getSalaryType()+", Month: "+prlGeneratedSalaryInfo.getMonthName()+", Year: "+prlGeneratedSalaryInfo.getYearName());

            payrollJdbcDao.generateEmployeeSalaryByStoredProc(prlGeneratedSalaryInfo);
            map.put("status", "SUCCESS");
            map.put("isValid",true);
        }
        catch(Exception ex)
        {
            map.put("isValid",false);
            map.put("status", "FAILED");
            map.put("msg", ex.getMessage());
        }
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }

    /**
     * PUT  /prlGeneratedSalaryInfos -> Updates an existing prlGeneratedSalaryInfo.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> updatePrlGeneratedSalaryInfo(@Valid @RequestBody PrlGeneratedSalaryInfo prlGeneratedSalaryInfo) throws URISyntaxException {
        log.debug("REST request to update PrlGeneratedSalaryInfo : {}", prlGeneratedSalaryInfo);
        if (prlGeneratedSalaryInfo.getId() == null) {
            return createPrlGeneratedSalaryInfo(prlGeneratedSalaryInfo);
        }
        Map map =new HashMap();
        try
        {
            map.put("type", prlGeneratedSalaryInfo.getSalaryType());
            map.put("month", prlGeneratedSalaryInfo.getMonthName());
            map.put("year", String.valueOf(prlGeneratedSalaryInfo.getYearName()));
            log.debug("generateSalaryByStoredProc by Type: "+prlGeneratedSalaryInfo.getSalaryType()+", Month: "+prlGeneratedSalaryInfo.getMonthName()+", Year: "+prlGeneratedSalaryInfo.getYearName());

            payrollJdbcDao.generateEmployeeSalaryByStoredProc(prlGeneratedSalaryInfo);
            map.put("status", "SUCCESS");
            map.put("isValid",true);
        }
        catch(Exception ex)
        {
            map.put("isValid",false);
            map.put("status", "FAILED");
            map.put("msg", ex.getMessage());
        }
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }

    /**
     * GET  /prlGeneratedSalaryInfos -> get all the prlGeneratedSalaryInfos.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlGeneratedSalaryInfo>> getAllPrlGeneratedSalaryInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlGeneratedSalaryInfos");
        Page<PrlGeneratedSalaryInfo> page = prlGeneratedSalaryInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlGeneratedSalaryInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlGeneratedSalaryInfos/:id -> get the "id" prlGeneratedSalaryInfo.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlGeneratedSalaryInfo> getPrlGeneratedSalaryInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlGeneratedSalaryInfo : {}", id);
        PrlGeneratedSalaryInfo prlGeneratedSalaryInfo = prlGeneratedSalaryInfoRepository.findOne(id);
        return Optional.ofNullable(prlGeneratedSalaryInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlGeneratedSalaryInfos/:id -> delete the "id" prlGeneratedSalaryInfo.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlGeneratedSalaryInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlGeneratedSalaryInfo : {}", id);
        prlGeneratedSalaryInfoRepository.delete(id);
        prlGeneratedSalaryInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlGeneratedSalaryInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlGeneratedSalaryInfos/:query -> search for the prlGeneratedSalaryInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlGeneratedSalaryInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlGeneratedSalaryInfo> searchPrlGeneratedSalaryInfos(@PathVariable String query) {
        log.debug("REST request to search PrlGeneratedSalaryInfos for query {}", query);
        return StreamSupport
            .stream(prlGeneratedSalaryInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    @RequestMapping(value = "/prlGenerateSalaryByStoredProc",
        method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> generateSalaryByStoredProc(@Valid @RequestBody PrlSalaryGenerationDto salGenDto)
    {
        Map map =new HashMap();
        try
        {
            map.put("type", salGenDto.getSalaryType());
            map.put("month", salGenDto.getMonthName());
            map.put("year", String.valueOf(salGenDto.getYear()));
            log.debug("generateSalaryByStoredProc by Type: "+salGenDto.getSalaryType()+", Month: "+salGenDto.getMonthName()+", Year: "+salGenDto.getYear());

            payrollJdbcDao.generateEmployeeSalaryByStoredProc(salGenDto);
            map.put("status", "SUCCESS");
            map.put("isValid",true);
        }
        catch(Exception ex)
        {
            map.put("isValid",false);
            map.put("status", "FAILED");
            map.put("msg", ex.getMessage());
        }
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }

    @RequestMapping(value = "/prlDisburseSalaryByStoredProc",
        method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> disburseSalaryByStoredProc(@Valid @RequestBody PrlGeneratedSalaryInfo prlGeneratedSalaryInfo)
    {
        Map map =new HashMap();
        try
        {
            map.put("type", prlGeneratedSalaryInfo.getSalaryType());
            map.put("month", prlGeneratedSalaryInfo.getMonthName());
            map.put("year", String.valueOf(prlGeneratedSalaryInfo.getYearName()));
            log.debug("disburseSalaryByStoredProc by Type: "+prlGeneratedSalaryInfo.getSalaryType()+", Month: "+prlGeneratedSalaryInfo.getMonthName()+", Year: "+prlGeneratedSalaryInfo.getYearName());

            payrollJdbcDao.updateDisburseStatusByStoredProc(prlGeneratedSalaryInfo);
            map.put("status", "SUCCESS");
            map.put("isValid",true);
        }
        catch(Exception ex)
        {
            map.put("isValid",false);
            map.put("status", "FAILED");
            map.put("msg", ex.getMessage());
        }
        return new ResponseEntity<Map>(map,HttpStatus.OK);
    }

    /**
     * GET  /prlGeneratedSalaryInfosFilter/:year/:month/:saltype -> get address list by year,month, saltype.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfosFilter/{year}/{month}/{saltype}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlGeneratedSalaryInfo> getGeneratedSalaryByFilter(@PathVariable Long year, @PathVariable String month, @PathVariable String saltype)
    {
        log.debug("REST request to load generated salary by filters: year: {}, month: {}, salType: {} ",year, month, saltype);

        List<PrlGeneratedSalaryInfo> empGeneratedSalaryList = prlGeneratedSalaryInfoRepository.findOneByMonthYearType(month, year, saltype);

        return empGeneratedSalaryList;
    }

    /**
     * GET  /prlGeneratedSalaryInfosFilter/:year/:month/:saltype -> get address list by year,month, saltype.
     */
    @RequestMapping(value = "/prlGeneratedSalaryInfosHistory",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public PrlGeneratedSalaryInfo lastInsertedDataHistory()
    {
        log.debug("REST request to last inserted data ");
        PrlGeneratedSalaryInfo genSalInfo = null;
        List<PrlGeneratedSalaryInfo> empGeneratedSalaryList = prlGeneratedSalaryInfoRepository.getLastInsertedData();
        if(empGeneratedSalaryList!=null && empGeneratedSalaryList.size()>0)
        {
            genSalInfo = empGeneratedSalaryList.get(0);
        }
        return genSalInfo;
    }
}
