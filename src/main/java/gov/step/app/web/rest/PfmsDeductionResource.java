package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsDeduction;
import gov.step.app.domain.PfmsLoanSchedule;
import gov.step.app.repository.PfmsDeductionRepository;
import gov.step.app.repository.PfmsLoanScheduleRepository;
import gov.step.app.repository.search.PfmsDeductionSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing PfmsDeduction.
 */
@RestController
@RequestMapping("/api")
public class PfmsDeductionResource {

    private final Logger log = LoggerFactory.getLogger(PfmsDeductionResource.class);

    @Inject
    private PfmsDeductionRepository pfmsDeductionRepository;

    @Inject
    private PfmsLoanScheduleRepository pfmsLoanScheduleRepository;

    @Inject
    private PfmsDeductionSearchRepository pfmsDeductionSearchRepository;

    /**
     * POST  /pfmsDeductions -> Create a new pfmsDeduction.
     */
    @RequestMapping(value = "/pfmsDeductions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeduction> createPfmsDeduction(@Valid @RequestBody PfmsDeduction pfmsDeduction) throws URISyntaxException {
        log.debug("REST request to save PfmsDeduction : {}", pfmsDeduction);
        if (pfmsDeduction.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsDeduction cannot already have an ID").body(null);
        }


        ArrayList<String> months = new ArrayList<String>() ;
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        int monthNo = 0;
        long installmentNo = 0;
        int adjsYear= 0;
        monthNo = months.indexOf(pfmsDeduction.getDeductionMonth());
        adjsYear = (int) (long)pfmsDeduction.getDeductionYear();
        int insNo = (int) (long) pfmsDeduction.getInstallmentNo();
        double deductAmt = pfmsDeduction.getDeductionAmount() / pfmsDeduction.getInstallmentNo();
        for(int i = 0; i < insNo; i++){

            installmentNo += 1;
            PfmsLoanSchedule pfmsLoanSchedule = new PfmsLoanSchedule();
            pfmsLoanSchedule.setLoanYear((long) adjsYear);
            pfmsLoanSchedule.setLoanMonth(months.get(monthNo));
            pfmsLoanSchedule.setDeductedAmount(deductAmt);
            pfmsLoanSchedule.setInstallmentNo(installmentNo);
            pfmsLoanSchedule.setTotInstallNo(pfmsDeduction.getInstallmentNo());
            pfmsLoanSchedule.setTotLoanAmount(pfmsDeduction.getDeductionAmount());
            pfmsLoanSchedule.setIsRepay(false);
            pfmsLoanSchedule.setReason("");
            pfmsLoanSchedule.setActiveStatus(true);
            pfmsLoanSchedule.setPfmsLoanApp(pfmsDeduction.getPfmsLoanApp());
            pfmsLoanSchedule.setEmployeeInfo(pfmsDeduction.getEmployeeInfo());
            pfmsLoanSchedule.setUpdateBy(pfmsDeduction.getUpdateBy());
            pfmsLoanSchedule.setUpdateDate(pfmsDeduction.getUpdateDate());
            pfmsLoanSchedule.setCreateBy(pfmsDeduction.getCreateBy());
            pfmsLoanSchedule.setCreateDate(pfmsDeduction.getCreateDate());
            pfmsLoanScheduleRepository.save(pfmsLoanSchedule);

            monthNo = monthNo + 1;
            if(monthNo > 11){
                monthNo = 0;
                adjsYear += 1;
            }
        }

        PfmsDeduction result = pfmsDeductionRepository.save(pfmsDeduction);
        pfmsDeductionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsDeductions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsDeduction", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsDeductions -> Updates an existing pfmsDeduction.
     */
    @RequestMapping(value = "/pfmsDeductions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeduction> updatePfmsDeduction(@Valid @RequestBody PfmsDeduction pfmsDeduction) throws URISyntaxException {
        log.debug("REST request to update PfmsDeduction : {}", pfmsDeduction);
        if (pfmsDeduction.getId() == null) {
            return createPfmsDeduction(pfmsDeduction);
        }

        PfmsDeduction result = pfmsDeductionRepository.save(pfmsDeduction);
        pfmsDeductionSearchRepository.save(pfmsDeduction);

        List<PfmsLoanSchedule> pfmsLoanSchedules = pfmsLoanScheduleRepository.getPfmsLoanScheduleListByEmployeeAndApp(pfmsDeduction.getEmployeeInfo().getId(), pfmsDeduction.getPfmsLoanApp().getId());
        for(PfmsLoanSchedule pfmsLoanSchedule: pfmsLoanSchedules){
            pfmsLoanScheduleRepository.delete(pfmsLoanSchedule.getId());
        }

        ArrayList<String> months = new ArrayList<String>() ;
        months.add("January");
        months.add("February");
        months.add("March");
        months.add("April");
        months.add("May");
        months.add("June");
        months.add("July");
        months.add("August");
        months.add("September");
        months.add("October");
        months.add("November");
        months.add("December");
        int monthNo = 0;
        long installmentNo = 0;
        int adjsYear= 0;
        monthNo = months.indexOf(pfmsDeduction.getDeductionMonth());
        adjsYear = (int) (long)pfmsDeduction.getDeductionYear();
        int insNo = (int) (long) pfmsDeduction.getInstallmentNo();
        double deductAmt = pfmsDeduction.getDeductionAmount() / pfmsDeduction.getInstallmentNo();
        for(int i = 0; i < insNo; i++){

            installmentNo += 1;
            PfmsLoanSchedule pfmsLoanSchedule = new PfmsLoanSchedule();
            pfmsLoanSchedule.setLoanYear((long) adjsYear);
            pfmsLoanSchedule.setLoanMonth(months.get(monthNo));
            pfmsLoanSchedule.setDeductedAmount(deductAmt);
            pfmsLoanSchedule.setInstallmentNo(installmentNo);
            pfmsLoanSchedule.setTotInstallNo(pfmsDeduction.getInstallmentNo());
            pfmsLoanSchedule.setTotLoanAmount(pfmsDeduction.getDeductionAmount());
            pfmsLoanSchedule.setIsRepay(false);
            pfmsLoanSchedule.setReason("");
            pfmsLoanSchedule.setActiveStatus(true);
            pfmsLoanSchedule.setPfmsLoanApp(pfmsDeduction.getPfmsLoanApp());
            pfmsLoanSchedule.setEmployeeInfo(pfmsDeduction.getEmployeeInfo());
            pfmsLoanSchedule.setUpdateBy(pfmsDeduction.getUpdateBy());
            pfmsLoanSchedule.setUpdateDate(pfmsDeduction.getUpdateDate());
            pfmsLoanSchedule.setCreateBy(pfmsDeduction.getCreateBy());
            pfmsLoanSchedule.setCreateDate(pfmsDeduction.getCreateDate());
            pfmsLoanScheduleRepository.save(pfmsLoanSchedule);

            monthNo = monthNo + 1;
            if(monthNo > 11){
                monthNo = 0;
                adjsYear += 1;
            }
        }
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsDeduction", pfmsDeduction.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsDeductions -> get all the pfmsDeductions.
     */
    @RequestMapping(value = "/pfmsDeductions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsDeduction>> getAllPfmsDeductions(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsDeduction> page = pfmsDeductionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsDeductions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsDeductions/:id -> get the "id" pfmsDeduction.
     */
    @RequestMapping(value = "/pfmsDeductions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsDeduction> getPfmsDeduction(@PathVariable Long id) {
        log.debug("REST request to get PfmsDeduction : {}", id);
        return Optional.ofNullable(pfmsDeductionRepository.findOne(id))
            .map(pfmsDeduction -> new ResponseEntity<>(
                pfmsDeduction,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsDeductions/:id -> delete the "id" pfmsDeduction.
     */
    @RequestMapping(value = "/pfmsDeductions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsDeduction(@PathVariable Long id) {
        log.debug("REST request to delete PfmsDeduction : {}", id);
        PfmsDeduction pfmsDeduction = pfmsDeductionRepository.findOne(id);
        List<PfmsLoanSchedule> pfmsLoanSchedules = pfmsLoanScheduleRepository.getPfmsLoanScheduleListByEmployeeAndApp(pfmsDeduction.getEmployeeInfo().getId(), pfmsDeduction.getPfmsLoanApp().getId());
        for(PfmsLoanSchedule pfmsLoanSchedule: pfmsLoanSchedules){
            pfmsLoanScheduleRepository.delete(pfmsLoanSchedule.getId());
        }
        pfmsDeductionRepository.delete(id);
        pfmsDeductionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsDeduction", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsDeductions/:query -> search for the pfmsDeduction corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsDeductions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsDeduction> searchPfmsDeductions(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsDeductionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsDeductionListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsDeductionListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsDeduction> getPfmsDeductionListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsDeductionByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsDeductionRepository.getPfmsDeductionListByEmployee(employeeInfoId);

    }
}
