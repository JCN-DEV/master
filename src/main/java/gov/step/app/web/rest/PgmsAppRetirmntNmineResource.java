package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrNomineeInfo;
import gov.step.app.domain.PgmsAppRetirmntNmine;
import gov.step.app.repository.PgmsAppRetirmntNmineRepository;
import gov.step.app.repository.HrNomineeInfoRepository;
import gov.step.app.repository.search.PgmsAppRetirmntNmineSearchRepository;
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
 * REST controller for managing PgmsAppRetirmntNmine.
 */
@RestController
@RequestMapping("/api")
public class PgmsAppRetirmntNmineResource {

    private final Logger log = LoggerFactory.getLogger(PgmsAppRetirmntNmineResource.class);

    @Inject
    private PgmsAppRetirmntNmineRepository pgmsAppRetirmntNmineRepository;

    @Inject
    private HrNomineeInfoRepository hrNomineeInfoRepository;

    @Inject
    private PgmsAppRetirmntNmineSearchRepository pgmsAppRetirmntNmineSearchRepository;

    /**
     * POST  /pgmsAppRetirmntNmines -> Create a new pgmsAppRetirmntNmine.
     */
    @RequestMapping(value = "/pgmsAppRetirmntNmines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntNmine> createPgmsAppRetirmntNmine(@Valid @RequestBody PgmsAppRetirmntNmine pgmsAppRetirmntNmine) throws URISyntaxException {
        log.debug("REST request to save PgmsAppRetirmntNmine : {}", pgmsAppRetirmntNmine);
        if (pgmsAppRetirmntNmine.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsAppRetirmntNmine cannot already have an ID").body(null);
        }
        PgmsAppRetirmntNmine result = pgmsAppRetirmntNmineRepository.save(pgmsAppRetirmntNmine);
        pgmsAppRetirmntNmineSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsAppRetirmntNmines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsAppRetirmntNmine", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsAppRetirmntNmines -> Updates an existing pgmsAppRetirmntNmine.
     */
    @RequestMapping(value = "/pgmsAppRetirmntNmines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntNmine> updatePgmsAppRetirmntNmine(@Valid @RequestBody PgmsAppRetirmntNmine pgmsAppRetirmntNmine) throws URISyntaxException {
        log.debug("REST request to update PgmsAppRetirmntNmine : {}", pgmsAppRetirmntNmine);
        if (pgmsAppRetirmntNmine.getId() == null) {
            return createPgmsAppRetirmntNmine(pgmsAppRetirmntNmine);
        }
        PgmsAppRetirmntNmine result = pgmsAppRetirmntNmineRepository.save(pgmsAppRetirmntNmine);
        pgmsAppRetirmntNmineSearchRepository.save(pgmsAppRetirmntNmine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsAppRetirmntNmine", pgmsAppRetirmntNmine.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsAppRetirmntNmines -> get all the pgmsAppRetirmntNmines.
     */
    @RequestMapping(value = "/pgmsAppRetirmntNmines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsAppRetirmntNmine>> getAllPgmsAppRetirmntNmines(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsAppRetirmntNmine> page = pgmsAppRetirmntNmineRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsAppRetirmntNmines");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsAppRetirmntNmines/:id -> get the "id" pgmsAppRetirmntNmine.
     */
    @RequestMapping(value = "/pgmsAppRetirmntNmines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsAppRetirmntNmine> getPgmsAppRetirmntNmine(@PathVariable Long id) {
        log.debug("REST request to get PgmsAppRetirmntNmine : {}", id);
        return Optional.ofNullable(pgmsAppRetirmntNmineRepository.findOne(id))
            .map(pgmsAppRetirmntNmine -> new ResponseEntity<>(
                pgmsAppRetirmntNmine,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsAppRetirmntNmines/:id -> delete the "id" pgmsAppRetirmntNmine.
     */
    @RequestMapping(value = "/pgmsAppRetirmntNmines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsAppRetirmntNmine(@PathVariable Long id) {
        log.debug("REST request to delete PgmsAppRetirmntNmine : {}", id);
        pgmsAppRetirmntNmineRepository.delete(id);
        pgmsAppRetirmntNmineSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsAppRetirmntNmine", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsAppRetirmntNmines/:query -> search for the pgmsAppRetirmntNmine corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsAppRetirmntNmines/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsAppRetirmntNmine> searchPgmsAppRetirmntNmines(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsAppRetirmntNmineSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Hr Nominee Infor List for PGMS Retirement Pension Application
     * to the query.
     */
    @RequestMapping(value = "/HrRetirmntNminesInfo/{empId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrNomineeInfo> getPgmsElpcBankInfoByemployeeInfoId(@PathVariable long empId) {
        log.debug("REST request to get HrRetirmntNminesInfo : empId: {}", empId );
        List<HrNomineeInfo> hrNomineInfo = hrNomineeInfoRepository.findAllByEmployeeInfo(empId);
        return hrNomineInfo;
    }
}
