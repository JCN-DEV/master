package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsLoanSchedule;
import gov.step.app.repository.PfmsLoanScheduleRepository;
import gov.step.app.repository.search.PfmsLoanScheduleSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing PfmsLoanSchedule.
 */
@RestController
@RequestMapping("/api")
public class PfmsLoanScheduleResource {

    private final Logger log = LoggerFactory.getLogger(PfmsLoanScheduleResource.class);

    @Inject
    private PfmsLoanScheduleRepository pfmsLoanScheduleRepository;

    @Inject
    private PfmsLoanScheduleSearchRepository pfmsLoanScheduleSearchRepository;

    /**
     * POST  /pfmsLoanSchedules -> Create a new pfmsLoanSchedule.
     */
    @RequestMapping(value = "/pfmsLoanSchedules",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanSchedule> createPfmsLoanSchedule(@Valid @RequestBody PfmsLoanSchedule pfmsLoanSchedule) throws URISyntaxException {
        log.debug("REST request to save PfmsLoanSchedule : {}", pfmsLoanSchedule);
        if (pfmsLoanSchedule.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsLoanSchedule cannot already have an ID").body(null);
        }
        PfmsLoanSchedule result = pfmsLoanScheduleRepository.save(pfmsLoanSchedule);
        pfmsLoanScheduleSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsLoanSchedules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsLoanSchedule", result.getId().toString()))
            .body(result);
    }

    /**
     * POST  /pfmsLoanSchedules -> Create a new pfmsLoanSchedule.
     */
    @RequestMapping(value = "/pfmsLoanSchedules/{isSaveAll}",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanSchedule> createAllPfmsLoanSchedule(@Valid @RequestBody List<PfmsLoanSchedule> pfmsLoanScheduleList) throws URISyntaxException {
        for(PfmsLoanSchedule pfmsLoanSchedule: pfmsLoanScheduleList){
            log.debug("REST request to save PfmsLoanSchedule : {}", pfmsLoanSchedule);
            if (pfmsLoanSchedule.getId() != null) {
                return ResponseEntity.badRequest().header("Failure", "A new pfmsLoanSchedule cannot already have an ID").body(null);
            }
            PfmsLoanSchedule result = pfmsLoanScheduleRepository.save(pfmsLoanSchedule);
            pfmsLoanScheduleSearchRepository.save(result);

        }
        return null;
    }

    /**
     * PUT  /pfmsLoanSchedules -> Updates an existing pfmsLoanSchedule.
     */
    @RequestMapping(value = "/pfmsLoanSchedules",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanSchedule> updatePfmsLoanSchedule(@Valid @RequestBody PfmsLoanSchedule pfmsLoanSchedule) throws URISyntaxException {
        log.debug("REST request to update PfmsLoanSchedule : {}", pfmsLoanSchedule);
        if (pfmsLoanSchedule.getId() == null) {
            return createPfmsLoanSchedule(pfmsLoanSchedule);
        }
        PfmsLoanSchedule result = pfmsLoanScheduleRepository.save(pfmsLoanSchedule);
        pfmsLoanScheduleSearchRepository.save(pfmsLoanSchedule);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsLoanSchedule", pfmsLoanSchedule.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsLoanSchedules -> get all the pfmsLoanSchedules.
     */
    @RequestMapping(value = "/pfmsLoanSchedules",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsLoanSchedule>> getAllPfmsLoanSchedules(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsLoanSchedule> page = pfmsLoanScheduleRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsLoanSchedules");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsLoanSchedules/:id -> get the "id" pfmsLoanSchedule.
     */
    @RequestMapping(value = "/pfmsLoanSchedules/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanSchedule> getPfmsLoanSchedule(@PathVariable Long id) {
        log.debug("REST request to get PfmsLoanSchedule : {}", id);
        return Optional.ofNullable(pfmsLoanScheduleRepository.findOne(id))
            .map(pfmsLoanSchedule -> new ResponseEntity<>(
                pfmsLoanSchedule,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsLoanSchedules/:id -> delete the "id" pfmsLoanSchedule.
     */
    @RequestMapping(value = "/pfmsLoanSchedules/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsLoanSchedule(@PathVariable Long id) {
        log.debug("REST request to delete PfmsLoanSchedule : {}", id);
        pfmsLoanScheduleRepository.delete(id);
        pfmsLoanScheduleSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsLoanSchedule", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsLoanSchedules/:query -> search for the pfmsLoanSchedule corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsLoanSchedules/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanSchedule> searchPfmsLoanSchedules(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsLoanScheduleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsLoanScheduleListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsLoanScheduleListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsLoanScheduleByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsLoanScheduleRepository.getPfmsLoanScheduleListByEmployee(employeeInfoId);

    }

    /**
     * GET  /pfmsLoanScheduleListByEmployeeAndAppByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsLoanScheduleListByEmployeeAndApp/{employeeInfoId}/{pfmsLoanAppId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmployeeAndApp(@PathVariable long employeeInfoId, @PathVariable long pfmsLoanAppId) throws Exception {
        log.debug("REST request to pfmsLoanScheduleByEmployee List : employeeInfo: {} ", employeeInfoId, pfmsLoanAppId);
        return pfmsLoanScheduleRepository.getPfmsLoanScheduleListByEmployeeAndApp(employeeInfoId, pfmsLoanAppId);

    }

    /**
     * GET  /pfmsLoanScheduleListByEmployeeAndAppByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsLoanScheduleListByEmpAppYearMonth/{employeeInfoId}/{pfmsLoanAppId}/{loanYear}/{loanMonth}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanSchedule> getPfmsLoanScheduleListByEmpAppYearMonth(@PathVariable long employeeInfoId, @PathVariable long pfmsLoanAppId, @PathVariable long loanYear, @PathVariable String loanMonth) throws Exception {
        log.debug("REST request to pfmsLoanScheduleByEmployee List : employeeInfo: {} ", employeeInfoId, pfmsLoanAppId, loanYear, loanMonth);
        return pfmsLoanScheduleRepository.getPfmsLoanScheduleListByEmpAppYearMonth(employeeInfoId, pfmsLoanAppId, loanYear, loanMonth);

    }
}
