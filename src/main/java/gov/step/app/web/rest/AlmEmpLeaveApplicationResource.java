package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveApplication;
import gov.step.app.domain.AlmEmpLeaveBalance;
import gov.step.app.repository.AlmEmpLeaveApplicationRepository;
import gov.step.app.repository.AlmEmpLeaveBalanceRepository;
import gov.step.app.repository.search.AlmEmpLeaveApplicationSearchRepository;
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
 * REST controller for managing AlmEmpLeaveApplication.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveApplicationResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveApplicationResource.class);

    @Inject
    private AlmEmpLeaveApplicationRepository almEmpLeaveApplicationRepository;

    @Inject
    private AlmEmpLeaveApplicationSearchRepository almEmpLeaveApplicationSearchRepository;

    @Inject
    private AlmEmpLeaveBalanceRepository almEmpLeaveBalanceRepository;

    /**
     * POST  /almEmpLeaveApplications -> Create a new almEmpLeaveApplication.
     */
    @RequestMapping(value = "/almEmpLeaveApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveApplication> createAlmEmpLeaveApplication(@Valid @RequestBody AlmEmpLeaveApplication almEmpLeaveApplication) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveApplication : {}", almEmpLeaveApplication);
        if (almEmpLeaveApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveApplication cannot already have an ID").body(null);
        }
        AlmEmpLeaveApplication result = almEmpLeaveApplicationRepository.save(almEmpLeaveApplication);
        almEmpLeaveApplicationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveApplications -> Updates an existing almEmpLeaveApplication.
     */
    @RequestMapping(value = "/almEmpLeaveApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveApplication> updateAlmEmpLeaveApplication(@Valid @RequestBody AlmEmpLeaveApplication almEmpLeaveApplication) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveApplication : {}", almEmpLeaveApplication);
        if (almEmpLeaveApplication.getId() == null) {
            return createAlmEmpLeaveApplication(almEmpLeaveApplication);
        }
        AlmEmpLeaveApplication result = almEmpLeaveApplicationRepository.save(almEmpLeaveApplication);
        almEmpLeaveApplicationSearchRepository.save(almEmpLeaveApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveApplication", almEmpLeaveApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveApplications -> get all the almEmpLeaveApplications.
     */
    @RequestMapping(value = "/almEmpLeaveApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveApplication>> getAllAlmEmpLeaveApplications(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveApplication> page = almEmpLeaveApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveApplications/:id -> get the "id" almEmpLeaveApplication.
     */
    @RequestMapping(value = "/almEmpLeaveApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveApplication> getAlmEmpLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveApplication : {}", id);
        return Optional.ofNullable(almEmpLeaveApplicationRepository.findOne(id))
            .map(almEmpLeaveApplication -> new ResponseEntity<>(
                almEmpLeaveApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveApplications/:id -> delete the "id" almEmpLeaveApplication.
     */
    @RequestMapping(value = "/almEmpLeaveApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveApplication(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveApplication : {}", id);
        almEmpLeaveApplicationRepository.delete(id);
        almEmpLeaveApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveApplications/:query -> search for the almEmpLeaveApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveApplication> searchAlmEmpLeaveApplications(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/almEmpLeaveBalByEmpInfoAndLeaveType/",
        method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveBalance> classByEmpIdAndLeaveTypeId(@RequestParam Long employeeId, @RequestParam Long leaveTypeId)
    {
        AlmEmpLeaveBalance almEmpLeaveBalance = almEmpLeaveBalanceRepository.findOneByAlmEmployeeInfoAndAlmLeaveType(employeeId, leaveTypeId);
        return Optional.ofNullable(almEmpLeaveBalance)
            .map(almEmpLeaveApplication -> new ResponseEntity<>(
                almEmpLeaveApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }
}
