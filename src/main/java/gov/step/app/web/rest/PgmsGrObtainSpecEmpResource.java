package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsGrObtainSpecEmp;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.repository.PgmsGrObtainSpecEmpRepository;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.search.PgmsGrObtainSpecEmpSearchRepository;
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
 * REST controller for managing PgmsGrObtainSpecEmp.
 */
@RestController
@RequestMapping("/api")
public class PgmsGrObtainSpecEmpResource {

    private final Logger log = LoggerFactory.getLogger(PgmsGrObtainSpecEmpResource.class);

    @Inject
    private PgmsGrObtainSpecEmpRepository pgmsGrObtainSpecEmpRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private PgmsGrObtainSpecEmpSearchRepository pgmsGrObtainSpecEmpSearchRepository;

    /**
     * POST  /pgmsGrObtainSpecEmps -> Create a new pgmsGrObtainSpecEmp.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmps",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsGrObtainSpecEmp> createPgmsGrObtainSpecEmp(@Valid @RequestBody PgmsGrObtainSpecEmp pgmsGrObtainSpecEmp) throws URISyntaxException {
        log.debug("REST request to save PgmsGrObtainSpecEmp : {}", pgmsGrObtainSpecEmp);
        if (pgmsGrObtainSpecEmp.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsGrObtainSpecEmp cannot already have an ID").body(null);
        }
        PgmsGrObtainSpecEmp result = pgmsGrObtainSpecEmpRepository.save(pgmsGrObtainSpecEmp);
        pgmsGrObtainSpecEmpSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsGrObtainSpecEmps/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsGrObtainSpecEmp", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsGrObtainSpecEmps -> Updates an existing pgmsGrObtainSpecEmp.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmps",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsGrObtainSpecEmp> updatePgmsGrObtainSpecEmp(@Valid @RequestBody PgmsGrObtainSpecEmp pgmsGrObtainSpecEmp) throws URISyntaxException {
        log.debug("REST request to update PgmsGrObtainSpecEmp : {}", pgmsGrObtainSpecEmp);
        if (pgmsGrObtainSpecEmp.getId() == null) {
            return createPgmsGrObtainSpecEmp(pgmsGrObtainSpecEmp);
        }
        PgmsGrObtainSpecEmp result = pgmsGrObtainSpecEmpRepository.save(pgmsGrObtainSpecEmp);
        pgmsGrObtainSpecEmpSearchRepository.save(pgmsGrObtainSpecEmp);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsGrObtainSpecEmp", pgmsGrObtainSpecEmp.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsGrObtainSpecEmps -> get all the pgmsGrObtainSpecEmps.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmps",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsGrObtainSpecEmp>> getAllPgmsGrObtainSpecEmps(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsGrObtainSpecEmp> page = pgmsGrObtainSpecEmpRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsGrObtainSpecEmps");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsGrObtainSpecEmps/:id -> get the "id" pgmsGrObtainSpecEmp.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmps/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsGrObtainSpecEmp> getPgmsGrObtainSpecEmp(@PathVariable Long id) {
        log.debug("REST request to get PgmsGrObtainSpecEmp : {}", id);
        return Optional.ofNullable(pgmsGrObtainSpecEmpRepository.findOne(id))
            .map(pgmsGrObtainSpecEmp -> new ResponseEntity<>(
                pgmsGrObtainSpecEmp,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsGrObtainSpecEmps/:id -> delete the "id" pgmsGrObtainSpecEmp.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmps/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsGrObtainSpecEmp(@PathVariable Long id) {
        log.debug("REST request to delete PgmsGrObtainSpecEmp : {}", id);
        pgmsGrObtainSpecEmpRepository.delete(id);
        pgmsGrObtainSpecEmpSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsGrObtainSpecEmp", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsGrObtainSpecEmps/:query -> search for the pgmsGrObtainSpecEmp corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsGrObtainSpecEmps/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsGrObtainSpecEmp> searchPgmsGrObtainSpecEmps(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsGrObtainSpecEmpSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search Pension and Gratuity Rate according to year
     * to the query.
     */
    @RequestMapping(value = "/pgmsGrObtainSpecEmpsInfo/{empId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrEmployeeInfo getPgmsEmployeeinformationByemployeeId(@PathVariable String empId) {
        log.debug("REST request to get getPgmsEmployeeinformationByemployeeId : empId: {}", empId );
        HrEmployeeInfo pgmsEmpInfo = hrEmployeeInfoRepository.findByEmployeeId(empId);
        return pgmsEmpInfo;
    }
}
