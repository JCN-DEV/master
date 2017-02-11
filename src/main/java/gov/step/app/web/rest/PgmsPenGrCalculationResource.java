package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.PgmsPenGrCalculation;
import gov.step.app.domain.PgmsPenGrSetup;
import gov.step.app.domain.PgmsPenGrRate;
import gov.step.app.repository.PgmsPenGrSetupRepository;
import gov.step.app.repository.PgmsPenGrRateRepository;
import gov.step.app.repository.PgmsPenGrCalculationRepository;
import gov.step.app.repository.search.PgmsPenGrCalculationSearchRepository;
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
import gov.step.app.web.rest.jdbc.dao.PGMSJdbcDao;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing PgmsPenGrCalculation.
 */
@RestController
@RequestMapping("/api")
public class PgmsPenGrCalculationResource {

    private final Logger log = LoggerFactory.getLogger(PgmsPenGrCalculationResource.class);

    @Inject
    private PgmsPenGrCalculationRepository pgmsPenGrCalculationRepository;

    @Inject
    private PgmsPenGrSetupRepository pgmsPenGrSetupRepository;

    @Inject
    private PgmsPenGrRateRepository pgmsPenGrRateRepository;

    @Inject
    private PgmsPenGrCalculationSearchRepository pgmsPenGrCalculationSearchRepository;

    @Inject
    private PGMSJdbcDao pgmsJdbcDao;

    /**
     * POST  /pgmsPenGrCalculations -> Create a new pgmsPenGrCalculation.
     */
    @RequestMapping(value = "/pgmsPenGrCalculations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrCalculation> createPgmsPenGrCalculation(@Valid @RequestBody PgmsPenGrCalculation pgmsPenGrCalculation) throws URISyntaxException {
        log.debug("REST request to save PgmsPenGrCalculation : {}", pgmsPenGrCalculation);
        if (pgmsPenGrCalculation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new pgmsPenGrCalculation cannot already have an ID").body(null);
        }
        PgmsPenGrCalculation result = pgmsPenGrCalculationRepository.save(pgmsPenGrCalculation);
        pgmsPenGrCalculationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/pgmsPenGrCalculations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pgmsPenGrCalculation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pgmsPenGrCalculations -> Updates an existing pgmsPenGrCalculation.
     */
    @RequestMapping(value = "/pgmsPenGrCalculations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrCalculation> updatePgmsPenGrCalculation(@Valid @RequestBody PgmsPenGrCalculation pgmsPenGrCalculation) throws URISyntaxException {
        log.debug("REST request to update PgmsPenGrCalculation : {}", pgmsPenGrCalculation);
        if (pgmsPenGrCalculation.getId() == null) {
            return createPgmsPenGrCalculation(pgmsPenGrCalculation);
        }
        PgmsPenGrCalculation result = pgmsPenGrCalculationRepository.save(pgmsPenGrCalculation);
        pgmsPenGrCalculationSearchRepository.save(pgmsPenGrCalculation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pgmsPenGrCalculation", pgmsPenGrCalculation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pgmsPenGrCalculations -> get all the pgmsPenGrCalculations.
     */
    @RequestMapping(value = "/pgmsPenGrCalculations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PgmsPenGrCalculation>> getAllPgmsPenGrCalculations(Pageable pageable)
        throws URISyntaxException {
        Page<PgmsPenGrCalculation> page = pgmsPenGrCalculationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pgmsPenGrCalculations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pgmsPenGrCalculations/:id -> get the "id" pgmsPenGrCalculation.
     */
    @RequestMapping(value = "/pgmsPenGrCalculations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PgmsPenGrCalculation> getPgmsPenGrCalculation(@PathVariable Long id) {
        log.debug("REST request to get PgmsPenGrCalculation : {}", id);
        return Optional.ofNullable(pgmsPenGrCalculationRepository.findOne(id))
            .map(pgmsPenGrCalculation -> new ResponseEntity<>(
                pgmsPenGrCalculation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pgmsPenGrCalculations/:id -> delete the "id" pgmsPenGrCalculation.
     */
    @RequestMapping(value = "/pgmsPenGrCalculations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePgmsPenGrCalculation(@PathVariable Long id) {
        log.debug("REST request to delete PgmsPenGrCalculation : {}", id);
        pgmsPenGrCalculationRepository.delete(id);
        pgmsPenGrCalculationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pgmsPenGrCalculation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/pgmsPenGrCalculations/:query -> search for the pgmsPenGrCalculation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/pgmsPenGrCalculations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrCalculation> searchPgmsPenGrCalculations(@PathVariable String query) {
        return StreamSupport
            .stream(pgmsPenGrCalculationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * Search Application No from Putup List
     * to the query.
     */
    @RequestMapping(value = "/pgmsPenGrCalculationsVersionId/{penGrVersion}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed /*
    public List<PgmsPenGrSetup> getPgmsPenGrCalculationByVersion(@PathVariable String penGrVersion) {
        log.debug("REST request to get pgmsPenGrCalculationsVersionId : penGrVersion: {}", penGrVersion);
        List<PgmsPenGrSetup> pgmsPrVersionlist = pgmsPenGrSetupRepository.findAllBySetupVersion(penGrVersion);
        return pgmsPrVersionlist;
    }*/

    public PgmsPenGrSetup getPgmsPenGrCalculationByVersion(@PathVariable String penGrVersion) {
        log.debug("REST request to get pgmsPenGrCalculationsVersionId : penGrVersion: {}", penGrVersion);
        PgmsPenGrSetup pgmsPrVersionlist = pgmsPenGrSetupRepository.findAllBySetupVersion(penGrVersion);
        return pgmsPrVersionlist;
    }

    /**
     * Search Pension and Gratuity Rate according to year
     * to the query.
     */
    @RequestMapping(value = "/pgmsPenGrCalculationsRate/{penVersion}/{grVersion}/{wrkYear}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PgmsPenGrRate> getPgmsPenGrCalculationIdByWrkYear(@PathVariable String penVersion, @PathVariable String grVersion, @PathVariable long wrkYear) {
        log.debug("REST request to get pgmsPenGrCalculationsRate : penVersion: {},grVersion {}, wrkYear: {}", penVersion,grVersion,wrkYear );
        List<PgmsPenGrRate> pgmsPrGrRateList = pgmsJdbcDao.findPensionRateByVersionAndYear(penVersion, grVersion, wrkYear);
        //PgmsPenGrRate pgmsPrGrRateList = pgmsPenGrRateRepository.findAllByPenGrSetIdAndWorkingYear(penGrId,wrkYear);
        return pgmsPrGrRateList;
    }
    /*public PgmsPenGrRate getPgmsPenGrCalculationIdByWrkYear(@PathVariable String penGrVersion, @PathVariable long wrkYear) {
        log.debug("REST request to get pgmsPenGrCalculationsRate : penGrVersion: {}, wrkYear: {}", penGrVersion,wrkYear );
        PgmsPenGrRate pgmsPrGrRateList = pgmsJdbcDao.findPensionRateByVersionAndYear(penGrVersion,wrkYear);
        ArrayList<Long> pgRateList = new ArrayList<Long>();
        pgRateList.add(pgmsPrGrRateList.getRateOfPenGr());
        //PgmsPenGrRate pgmsPrGrRateList = pgmsPenGrRateRepository.findAllByPenGrSetIdAndWorkingYear(penGrId,wrkYear);
        return pgmsPrGrRateList;
    }*/
}
