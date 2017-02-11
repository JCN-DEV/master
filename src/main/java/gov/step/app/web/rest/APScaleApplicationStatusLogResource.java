package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.APScaleApplication;
import gov.step.app.domain.APScaleApplicationStatusLog;
import gov.step.app.domain.TimeScaleApplication;
import gov.step.app.domain.TimeScaleApplicationStatusLog;
import gov.step.app.domain.enumeration.MpoApplicationStatusType;
import gov.step.app.repository.APScaleApplicationRepository;
import gov.step.app.repository.APScaleApplicationStatusLogRepository;
import gov.step.app.repository.search.APScaleApplicationStatusLogSearchRepository;
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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing APScaleApplicationStatusLog.
 */
@RestController
@RequestMapping("/api")
public class APScaleApplicationStatusLogResource {

    private final Logger log = LoggerFactory.getLogger(APScaleApplicationStatusLogResource.class);

    @Inject
    private APScaleApplicationStatusLogRepository aPScaleApplicationStatusLogRepository;

    @Inject
    private APScaleApplicationRepository apScaleApplicationRepository;

    @Inject
    private APScaleApplicationStatusLogSearchRepository aPScaleApplicationStatusLogSearchRepository;

    /**
     * POST  /aPScaleApplicationStatusLogs -> Create a new aPScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationStatusLog> createAPScaleApplicationStatusLog(@RequestBody APScaleApplicationStatusLog aPScaleApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to save APScaleApplicationStatusLog : {}", aPScaleApplicationStatusLog);
        if (aPScaleApplicationStatusLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new aPScaleApplicationStatusLog cannot already have an ID").body(null);
        }
        /*APScaleApplicationStatusLog result = aPScaleApplicationStatusLogRepository.save(aPScaleApplicationStatusLog);
        aPScaleApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/aPScaleApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("aPScaleApplicationStatusLog", result.getId().toString()))
            .body(result);
    }*/ if(aPScaleApplicationStatusLog.getStatus()== MpoApplicationStatusType.DECLINED.getCode()){
            APScaleApplication aPScaleApplication= aPScaleApplicationStatusLog.getApScaleApplication();
            aPScaleApplicationStatusLog.setRemarks(MpoApplicationStatusType.nextMpoApplicationStatusType(aPScaleApplication.getStatus()).getDeclineRemarks());
            aPScaleApplication.setStatus(MpoApplicationStatusType.DECLINED.getCode());
            apScaleApplicationRepository.save(aPScaleApplication);
        }
        APScaleApplicationStatusLog result = aPScaleApplicationStatusLogRepository.save(aPScaleApplicationStatusLog);
        aPScaleApplicationStatusLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/timeScaleApplicationStatusLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("timeScaleApplicationStatusLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /aPScaleApplicationStatusLogs -> Updates an existing aPScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationStatusLog> updateAPScaleApplicationStatusLog(@RequestBody APScaleApplicationStatusLog aPScaleApplicationStatusLog) throws URISyntaxException {
        log.debug("REST request to update APScaleApplicationStatusLog : {}", aPScaleApplicationStatusLog);
        if (aPScaleApplicationStatusLog.getId() == null) {
            return createAPScaleApplicationStatusLog(aPScaleApplicationStatusLog);
        }
        APScaleApplicationStatusLog result = aPScaleApplicationStatusLogRepository.save(aPScaleApplicationStatusLog);
        aPScaleApplicationStatusLogSearchRepository.save(aPScaleApplicationStatusLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("aPScaleApplicationStatusLog", aPScaleApplicationStatusLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /aPScaleApplicationStatusLogs -> get all the aPScaleApplicationStatusLogs.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<APScaleApplicationStatusLog>> getAllAPScaleApplicationStatusLogs(Pageable pageable)
        throws URISyntaxException {
        Page<APScaleApplicationStatusLog> page = aPScaleApplicationStatusLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/aPScaleApplicationStatusLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /aPScaleApplicationStatusLogs/:id -> get the "id" aPScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<APScaleApplicationStatusLog> getAPScaleApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to get APScaleApplicationStatusLog : {}", id);
        return Optional.ofNullable(aPScaleApplicationStatusLogRepository.findOne(id))
            .map(aPScaleApplicationStatusLog -> new ResponseEntity<>(
                aPScaleApplicationStatusLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /aPScaleApplicationStatusLogs/:id -> delete the "id" aPScaleApplicationStatusLog.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAPScaleApplicationStatusLog(@PathVariable Long id) {
        log.debug("REST request to delete APScaleApplicationStatusLog : {}", id);
        aPScaleApplicationStatusLogRepository.delete(id);
        aPScaleApplicationStatusLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("aPScaleApplicationStatusLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/aPScaleApplicationStatusLogs/:query -> search for the aPScaleApplicationStatusLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/aPScaleApplicationStatusLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<APScaleApplicationStatusLog> searchAPScaleApplicationStatusLogs(@PathVariable String query) {
        return StreamSupport
            .stream(aPScaleApplicationStatusLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /mpoApplicationStatusLogs -> get all the mpoApplicationStatusLogs.
     */
    @RequestMapping(value = "/aPScaleApplicationStatusLogsByEmployee/instEmployee/{code}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<APScaleApplicationStatusLog>> getTimescaleApplicationStatusLogByInstEmployeeCode(@PathVariable String code)
        throws URISyntaxException {
        //Page<APScaleApplicationStatusLog> page = aPScaleApplicationStatusLogRepository.findStatusLogByEmployeeCode(pageable,code);
        List<APScaleApplicationStatusLog> list = aPScaleApplicationStatusLogRepository.findStatusLogByEmployeeCode(code);
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoApplicationStatusLogs");
        if(list !=null && list.size()>0){
            return new ResponseEntity<>(list, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
    }
}
