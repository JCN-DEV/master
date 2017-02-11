package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlEmpGenSalDetailInfo;
import gov.step.app.domain.payroll.PrlEmpGeneratedSalInfo;
import gov.step.app.repository.payroll.PrlEmpGenSalDetailInfoRepository;
import gov.step.app.repository.payroll.PrlEmpGeneratedSalInfoRepository;
import gov.step.app.repository.search.payroll.PrlEmpGeneratedSalInfoSearchRepository;
import gov.step.app.web.rest.dto.PrlGeneratedSalaryDto;
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
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PrlEmpGeneratedSalInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlEmpGeneratedSalInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlEmpGeneratedSalInfoResource.class);

    @Inject
    private PrlEmpGeneratedSalInfoRepository prlEmpGeneratedSalInfoRepository;

    @Inject
    private PrlEmpGenSalDetailInfoRepository prlEmpGenSalDetailInfoRepository;

    @Inject
    private PrlEmpGeneratedSalInfoSearchRepository prlEmpGeneratedSalInfoSearchRepository;

    /**
     * POST  /prlEmpGeneratedSalInfos -> Create a new prlEmpGeneratedSalInfo.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGeneratedSalInfo> createPrlEmpGeneratedSalInfo(@RequestBody PrlEmpGeneratedSalInfo prlEmpGeneratedSalInfo) throws URISyntaxException {
        log.debug("REST request to save PrlEmpGeneratedSalInfo : {}", prlEmpGeneratedSalInfo);
        if (prlEmpGeneratedSalInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlEmpGeneratedSalInfo", "idexists", "A new prlEmpGeneratedSalInfo cannot already have an ID")).body(null);
        }
        PrlEmpGeneratedSalInfo result = prlEmpGeneratedSalInfoRepository.save(prlEmpGeneratedSalInfo);
        prlEmpGeneratedSalInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlEmpGeneratedSalInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlEmpGeneratedSalInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlEmpGeneratedSalInfos -> Updates an existing prlEmpGeneratedSalInfo.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGeneratedSalInfo> updatePrlEmpGeneratedSalInfo(@RequestBody PrlEmpGeneratedSalInfo prlEmpGeneratedSalInfo) throws URISyntaxException {
        log.debug("REST request to update PrlEmpGeneratedSalInfo : {}", prlEmpGeneratedSalInfo);
        if (prlEmpGeneratedSalInfo.getId() == null) {
            return createPrlEmpGeneratedSalInfo(prlEmpGeneratedSalInfo);
        }
        PrlEmpGeneratedSalInfo result = prlEmpGeneratedSalInfoRepository.save(prlEmpGeneratedSalInfo);
        prlEmpGeneratedSalInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlEmpGeneratedSalInfo", prlEmpGeneratedSalInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlEmpGeneratedSalInfos -> get all the prlEmpGeneratedSalInfos.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlEmpGeneratedSalInfo>> getAllPrlEmpGeneratedSalInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlEmpGeneratedSalInfos");
        Page<PrlEmpGeneratedSalInfo> page = prlEmpGeneratedSalInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlEmpGeneratedSalInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlEmpGeneratedSalInfos/:id -> get the "id" prlEmpGeneratedSalInfo.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGeneratedSalInfo> getPrlEmpGeneratedSalInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlEmpGeneratedSalInfo : {}", id);
        PrlEmpGeneratedSalInfo prlEmpGeneratedSalInfo = prlEmpGeneratedSalInfoRepository.findOne(id);
        return Optional.ofNullable(prlEmpGeneratedSalInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlEmpGeneratedSalInfos/:id -> delete the "id" prlEmpGeneratedSalInfo.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlEmpGeneratedSalInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlEmpGeneratedSalInfo : {}", id);
        prlEmpGeneratedSalInfoRepository.delete(id);
        prlEmpGeneratedSalInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlEmpGeneratedSalInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlEmpGeneratedSalInfos/:query -> search for the prlEmpGeneratedSalInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlEmpGeneratedSalInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEmpGeneratedSalInfo> searchPrlEmpGeneratedSalInfos(@PathVariable String query) {
        log.debug("REST request to search PrlEmpGeneratedSalInfos for query {}", query);
        return StreamSupport
            .stream(prlEmpGeneratedSalInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /prlEmpGeneratedSalInfosFilter/:year/:month/:empid -> get address list by psid.
     */
    @RequestMapping(value = "/prlEmpGeneratedSalInfosFilter/{year}/{month}/{empid}/{saltype}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public PrlGeneratedSalaryDto getGeneratedSalaryByFilter(@PathVariable Long year, @PathVariable String month, @PathVariable Long empid, @PathVariable String saltype)
    {
        log.debug("REST request to load generated salary by filters: year: {}, month: {}, empId: {}, salType: {} ",year, month, empid, saltype);

        PrlGeneratedSalaryDto generatedSalaryDto = null;
        List<PrlEmpGeneratedSalInfo> empGeneratedSalaryList = prlEmpGeneratedSalInfoRepository.findOneByMonthYearEmployee(month, year, empid, saltype);
        if(empGeneratedSalaryList!=null && empGeneratedSalaryList.size() > 0)
        {
            generatedSalaryDto = new PrlGeneratedSalaryDto();
            try
            {
                PrlEmpGeneratedSalInfo genSalInfo = empGeneratedSalaryList.get(0);
                log.debug("genertedSalary: "+genSalInfo.toString());
                generatedSalaryDto.setEmployeeSalaryInfo(genSalInfo);
                generatedSalaryDto.setSalaryInfo(genSalInfo.getSalaryInfo());

                List<PrlEmpGenSalDetailInfo> salaryDetailList = prlEmpGenSalDetailInfoRepository.findAllByEmpSalaryId(genSalInfo.getId());
                log.debug("AllowanceDeductionList: "+salaryDetailList.size());
                generatedSalaryDto.setSalaryDetailList(salaryDetailList);
            }
            catch(Exception ex)
            {
                log.error("EmployeeGeneratedSalary custom dto list: ",ex);
            }
        }
        return generatedSalaryDto;
    }
}
