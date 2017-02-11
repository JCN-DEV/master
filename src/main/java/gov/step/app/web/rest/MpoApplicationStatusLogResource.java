package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoApplication;
import gov.step.app.domain.MpoApplicationStatusLog;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.MpoApplicationRepository;
import gov.step.app.repository.MpoApplicationStatusLogRepository;
import gov.step.app.repository.search.MpoApplicationStatusLogSearchRepository;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing MpoApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class MpoApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(MpoApplicationStatusLogResource.class);

    @Inject
    private MpoApplicationStatusLogRepository mpoApplicationStatusLogRepository;

    @Inject
    private MpoApplicationStatusLogSearchRepository mpoApplicationStatusLogSearchRepository;

    @Inject
    private MpoApplicationRepository mpoApplicationRepository;

    /**
     * POST  /mpoApplicationStatusLogs -> Create a new mpoApplicationStatusLog.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationStatusLog> createMpoApplicationStatusLog(@RequestBody MpoApplicationStatusLog mpoApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save MpoApplicationStatusLog : {}", mpoApplicationStatusLog);
        if (mpoApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoApplicationStatusLog cannot already have an ID").body(null);
        }
        if(mpoApplicationStatusLog.getStatus()==MpoApplicationStatusType.DECLINED.getCode()){
           MpoApplication mpoApplication= mpoApplicationStatusLog.getMpoApplicationId();
            mpoApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(mpoApplication.getStatus()).getDeclineRemarks());
                mpoApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            mpoApplicationRepository.save(mpoApplication);
        }
        MpoApplicationStatusLog result = mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);
        mpoApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoApplicationStatusLogs -> Updates an existing mpoApplicationStatusLog.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationStatusLog> updateMpoApplicationStatusLog(@RequestBody MpoApplicationStatusLog mpoApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update MpoApplicationStatusLog : {}", mpoApplicationStatusLog);
        if (mpoApplicationStatusLog.getId() == null) {
            return createMpoApplicationStatusLog(mpoApplicationStatusLog);
        }
        MpoApplicationStatusLog result = mpoApplicationStatusLogRepository.save(mpoApplicationStatusLog);
        mpoApplicationStatusLogSearchRepository.save(mpoApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoApplicationStatusLog", mpoApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplicationStatusLog>> getAllMpoApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<MpoApplicationStatusLog> page = mpoApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoApplicationStatusLogs/:id -> get the "id" mpoApplicationStatusLog.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoApplicationStatusLog> getMpoApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get MpoApplicationStatusLog : {}", id);
        return Optional.ofNullable(mpoApplicationStatusLogRepository.findOne(id))
            .map(mpoApplicationStatusLog -> new ResponseEntity<>(
                mpoApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoApplicationStatusLogs/:id -> delete the "id" mpoApplicationStatusLog.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete MpoApplicationStatusLog : {}", id);
        mpoApplicationStatusLogRepository.delete(id);
        mpoApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoApplicationStatusLogs/:query -> search for the mpoApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoApplicationStatusLog> searchMpoApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(mpoApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/mpoApplicationStatusLogs/instEmployee/{code:.+}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoApplicationStatusLog>> getMpoApplicationStatusLogByInstEmployeeCode(Pageable pageable, @PathVariable String code)
        throws URISyntaxException {
        Page<MpoApplicationStatusLog> page = mpoApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
