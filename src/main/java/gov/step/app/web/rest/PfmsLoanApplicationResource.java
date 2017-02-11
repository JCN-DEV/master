package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsLoanApplication;
import gov.step.app.repository.PfmsLoanApplicationRepository;
import gov.step.app.repository.search.PfmsLoanApplicationSearchRepository;
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
 * REST controller for managing PfmsLoanApplication.
 */
@RestController
@RequestMapping("/api")
public class PfmsLoanApplicationResource {

    private final Logger log = LoggerFactory.getLogger(PfmsLoanApplicationResource.class);

    @Inject
    private PfmsLoanApplicationRepository pfmsLoanApplicationRepository;

    @Inject
    private PfmsLoanApplicationSearchRepository pfmsLoanApplicationSearchRepository;

    /**
     * POST  /pfmsLoanApplications -> Create a new pfmsLoanApplication.
     */
    @RequestMapping(value = "/pfmsLoanApplications",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanApplication> createPfmsLoanApplication(@Valid @RequestBody PfmsLoanApplication pfmsLoanApplication) throws URISyntaxException {
        log.debug("REST request to save PfmsLoanApplication : {}", pfmsLoanApplication);
        if (pfmsLoanApplication.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsLoanApplication cannot already have an ID").body(null);
        }
        PfmsLoanApplication result = pfmsLoanApplicationRepository.save(pfmsLoanApplication);
        pfmsLoanApplicationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsLoanApplications/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsLoanApplication", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsLoanApplications -> Updates an existing pfmsLoanApplication.
     */
    @RequestMapping(value = "/pfmsLoanApplications",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanApplication> updatePfmsLoanApplication(@Valid @RequestBody PfmsLoanApplication pfmsLoanApplication) throws URISyntaxException {
        log.debug("REST request to update PfmsLoanApplication : {}", pfmsLoanApplication);
        if (pfmsLoanApplication.getId() == null) {
            return createPfmsLoanApplication(pfmsLoanApplication);
        }
        PfmsLoanApplication result = pfmsLoanApplicationRepository.save(pfmsLoanApplication);
        pfmsLoanApplicationSearchRepository.save(pfmsLoanApplication);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsLoanApplication", pfmsLoanApplication.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsLoanApplications -> get all the pfmsLoanApplications.
     */
    @RequestMapping(value = "/pfmsLoanApplications",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsLoanApplication>> getAllPfmsLoanApplications(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsLoanApplication> page = pfmsLoanApplicationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsLoanApplications");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsLoanApplications/:id -> get the "id" pfmsLoanApplication.
     */
    @RequestMapping(value = "/pfmsLoanApplications/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsLoanApplication> getPfmsLoanApplication(@PathVariable Long id) {
        log.debug("REST request to get PfmsLoanApplication : {}", id);
        return Optional.ofNullable(pfmsLoanApplicationRepository.findOne(id))
            .map(pfmsLoanApplication -> new ResponseEntity<>(
                pfmsLoanApplication,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsLoanApplications/:id -> delete the "id" pfmsLoanApplication.
     */
    @RequestMapping(value = "/pfmsLoanApplications/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsLoanApplication(@PathVariable Long id) {
        log.debug("REST request to delete PfmsLoanApplication : {}", id);
        pfmsLoanApplicationRepository.delete(id);
        pfmsLoanApplicationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsLoanApplication", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsLoanApplications/:query -> search for the pfmsLoanApplication corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsLoanApplications/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanApplication> searchPfmsLoanApplications(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsLoanApplicationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /pfmsLoanApplicationsByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsLoanApplicationByEmployee/{employeeId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsLoanApplication> pfmsLoanApplicationByEmployee(@PathVariable long employeeId) throws Exception {
        log.debug("REST request to pfmsLoanApplicationByEmployee List : employeeInfo: {} ", employeeId);
        return pfmsLoanApplicationRepository.pfmsLoanApplicationByEmployee(employeeId);

    }
}
