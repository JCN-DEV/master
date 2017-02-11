package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsElpc;
import gov.step.app.domain.HrEmpBankAccountInfo;
import gov.step.app.repository.PgmsElpcRepository;
import gov.step.app.repository.HrEmpBankAccountInfoRepository;
import gov.step.app.repository.search.PgmsElpcSearchRepository;
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
 * REST controller for managing PgmsElpc.
 */
@RestController
@RequestMapping("/api")
public class PgmsElpcResource {

    private final Logger log = LoggerFactory.getLogger(PgmsElpcResource.class);

    @Inject
    private PgmsElpcRepository pgmsElpcRepository;

    @Inject
    private HrEmpBankAccountInfoRepository hrEmpBankAccountInfoRepository;

    @Inject
    private PgmsElpcSearchRepository pgmsElpcSearchRepository;

    /**
     * POST  /pgmsElpcs -> Create a new pgmsElpc.
     */
    @RequestMapping(value = "/pgmsElpcs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsElpc> createPgmsElpc(@Valid @RequestBody PgmsElpc pgmsElpc) throws URISyntaxException {
        log.debug("REST request to save PgmsElpc : {}", pgmsElpc);
        if (pgmsElpc.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsElpc cannot already have an ID").body(null);
        }
        PgmsElpc result = pgmsElpcRepository.save(pgmsElpc);
        pgmsElpcSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsElpcs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsElpc", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsElpcs -> Updates an existing pgmsElpc.
     */
    @RequestMapping(value = "/pgmsElpcs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsElpc> updatePgmsElpc(@Valid @RequestBody PgmsElpc pgmsElpc) throws URISyntaxException {
        log.debug("REST request to update PgmsElpc : {}", pgmsElpc);
        if (pgmsElpc.getId() == null) {
            return createPgmsElpc(pgmsElpc);
        }
        PgmsElpc result = pgmsElpcRepository.save(pgmsElpc);
        pgmsElpcSearchRepository.save(pgmsElpc);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsElpc", pgmsElpc.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsElpcs -> get all the pgmsElpcs.
     */
    @RequestMapping(value = "/pgmsElpcs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsElpc>> getAllPgmsElpcs(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsElpc> page = pgmsElpcRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsElpcs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsElpcs/:id -> get the "id" pgmsElpc.
     */
    @RequestMapping(value = "/pgmsElpcs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsElpc> getPgmsElpc(@PathVariable Long id) {
        log.debug("REST request to get PgmsElpc : {}", id);
        return Optional.ofNullable(pgmsElpcRepository.findOne(id))
            .map(pgmsElpc -> new ResponseEntity<>(
                pgmsElpc,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsElpcs/:id -> delete the "id" pgmsElpc.
     */
    @RequestMapping(value = "/pgmsElpcs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsElpc(@PathVariable Long id) {
        log.debug("REST request to delete PgmsElpc : {}", id);
        pgmsElpcRepository.delete(id);
        pgmsElpcSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsElpc", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsElpcs/:query -> search for the pgmsElpc corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsElpcs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsElpc> searchPgmsElpcs(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsElpcSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search Elpc Bank Information
     * to the query.
     */
    @RequestMapping(value = "/pgmsElpcBankInfo/{empInfoId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public HrEmpBankAccountInfo getPgmsElpcBankInfoByemployeeInfoId(@PathVariable long empInfoId) {
        log.debug("REST request to get pgmsElpcBankInfo : empInfoId: {}", empInfoId );
        HrEmpBankAccountInfo elpcBankInfo = hrEmpBankAccountInfoRepository.findByEmployeeInfo(empInfoId);
        return elpcBankInfo;
    }
}
