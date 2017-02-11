package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PfmsEmpMonthlyAdj;
import gov.step.app.repository.PfmsEmpMonthlyAdjRepository;
import gov.step.app.repository.search.PfmsEmpMonthlyAdjSearchRepository;
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
 * REST controller for managing PfmsEmpMonthlyAdj.
 */
@RestController
@RequestMapping("/api")
public class PfmsEmpMonthlyAdjResource {

    private final Logger log = LoggerFactory.getLogger(PfmsEmpMonthlyAdjResource.class);

    @Inject
    private PfmsEmpMonthlyAdjRepository pfmsEmpMonthlyAdjRepository;

    @Inject
    private PfmsEmpMonthlyAdjSearchRepository pfmsEmpMonthlyAdjSearchRepository;

    /**
     * POST  /pfmsEmpMonthlyAdjs -> Create a new pfmsEmpMonthlyAdj.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMonthlyAdj> createPfmsEmpMonthlyAdj(@Valid @RequestBody PfmsEmpMonthlyAdj pfmsEmpMonthlyAdj) throws URISyntaxException {
        log.debug("REST request to save PfmsEmpMonthlyAdj : {}", pfmsEmpMonthlyAdj);
        if (pfmsEmpMonthlyAdj.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pfmsEmpMonthlyAdj cannot already have an ID").body(null);
        }
        PfmsEmpMonthlyAdj result = pfmsEmpMonthlyAdjRepository.save(pfmsEmpMonthlyAdj);
        pfmsEmpMonthlyAdjSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pfmsEmpMonthlyAdjs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pfmsEmpMonthlyAdj", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pfmsEmpMonthlyAdjs -> Updates an existing pfmsEmpMonthlyAdj.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMonthlyAdj> updatePfmsEmpMonthlyAdj(@Valid @RequestBody PfmsEmpMonthlyAdj pfmsEmpMonthlyAdj) throws URISyntaxException {
        log.debug("REST request to update PfmsEmpMonthlyAdj : {}", pfmsEmpMonthlyAdj);
        if (pfmsEmpMonthlyAdj.getId() == null) {
            return createPfmsEmpMonthlyAdj(pfmsEmpMonthlyAdj);
        }
        PfmsEmpMonthlyAdj result = pfmsEmpMonthlyAdjRepository.save(pfmsEmpMonthlyAdj);
        pfmsEmpMonthlyAdjSearchRepository.save(pfmsEmpMonthlyAdj);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pfmsEmpMonthlyAdj", pfmsEmpMonthlyAdj.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pfmsEmpMonthlyAdjs -> get all the pfmsEmpMonthlyAdjs.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PfmsEmpMonthlyAdj>> getAllPfmsEmpMonthlyAdjs(Pageable pageable)
        throws URISyntaxException {
        Page<PfmsEmpMonthlyAdj> page = pfmsEmpMonthlyAdjRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pfmsEmpMonthlyAdjs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pfmsEmpMonthlyAdjs/:id -> get the "id" pfmsEmpMonthlyAdj.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PfmsEmpMonthlyAdj> getPfmsEmpMonthlyAdj(@PathVariable Long id) {
        log.debug("REST request to get PfmsEmpMonthlyAdj : {}", id);
        return Optional.ofNullable(pfmsEmpMonthlyAdjRepository.findOne(id))
            .map(pfmsEmpMonthlyAdj -> new ResponseEntity<>(
                pfmsEmpMonthlyAdj,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pfmsEmpMonthlyAdjs/:id -> delete the "id" pfmsEmpMonthlyAdj.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePfmsEmpMonthlyAdj(@PathVariable Long id) {
        log.debug("REST request to delete PfmsEmpMonthlyAdj : {}", id);
        pfmsEmpMonthlyAdjRepository.delete(id);
        pfmsEmpMonthlyAdjSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pfmsEmpMonthlyAdj", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pfmsEmpMonthlyAdjs/:query -> search for the pfmsEmpMonthlyAdj corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pfmsEmpMonthlyAdjs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMonthlyAdj> searchPfmsEmpMonthlyAdjs(@PathVariable String query) {
        return StreamSupport
            .stream(pfmsEmpMonthlyAdjSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

//    /**
//     * GET  /hrEmployeeInfosByFilter/:fieldName/:fieldValue -> get employee by filter.
//     */
//    @RequestMapping(value = "/findByMaxDate/{areaid}",
//            method = RequestMethod.GET,
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public List<PfmsEmpMonthlyAdj> findByMaxDate(@PathVariable HrEmployeeInfo employeeId) {
//        log.debug("REST request to hrEmployeeInfosListByWorkArea List : WorkArea: {} ", employeeId);
//
//        List<PfmsEmpMonthlyAdj> modelList = pfmsEmpMonthlyAdjRepository.findByMaxDate(employeeId);
//        log.debug("List Len:  ", modelList.size());
//        return modelList;
//    }

    /**
     * GET  /pfmsEmpMonthlyAdjListByEmployeeByFilter/:fieldName/:fieldValue -> get employee by filter.
     */
    @RequestMapping(value = "/pfmsEmpMonthlyAdjListByEmployee/{employeeInfoId}",
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PfmsEmpMonthlyAdj> getPfmsEmpMonthlyAdjListByEmployee(@PathVariable long employeeInfoId) throws Exception {
        log.debug("REST request to pfmsEmpMonthlyAdjByEmployee List : employeeInfo: {} ", employeeInfoId);
        return pfmsEmpMonthlyAdjRepository.getPfmsEmpMonthlyAdjListByEmployee(employeeInfoId);

    }
}
