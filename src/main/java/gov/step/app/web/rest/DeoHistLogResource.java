package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DeoHistLog;
import gov.step.app.repository.DeoHistLogRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.DeoHistLogSearchRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing DeoHistLog.
 */
@RestController
@RequestMapping("/api")
public class DeoHistLogResource {

    private final Logger log = LoggerFactory.getLogger(DeoHistLogResource.class);

    @Inject
    private DeoHistLogRepository deoHistLogRepository;

    @Inject
    private DeoHistLogSearchRepository deoHistLogSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /deoHistLogs -> Create a new deoHistLog.
     */
    @RequestMapping(value = "/deoHistLogs",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeoHistLog> createDeoHistLog(@RequestBody DeoHistLog deoHistLog) throws URISyntaxException {
        log.debug("REST request to save DeoHistLog : {}", deoHistLog);
        if (deoHistLog.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new deoHistLog cannot already have an ID").body(null);
        }

        //deactivate deoLogHist of this district
        if(deoHistLog.getDistrict() != null){
            DeoHistLog preDeoHistLog = deoHistLogRepository.findActiveLogByDistrict(deoHistLog.getDistrict().getId());
            if(preDeoHistLog != null){
                preDeoHistLog.setActivated(false);
                preDeoHistLog.setDateModified(LocalDate.now());
                preDeoHistLog.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
                deoHistLogRepository.save(preDeoHistLog);
                deoHistLogSearchRepository.save(preDeoHistLog);
            }

        }

        //deactivate current deo from other district
        if(deoHistLog.getDeo() != null){
            DeoHistLog preDeoHistLog2 = deoHistLogRepository.findActiveLogByDeo(deoHistLog.getDeo().getId());
            if(preDeoHistLog2 != null){
                preDeoHistLog2.setActivated(false);
                preDeoHistLog2.setDateModified(LocalDate.now());
                preDeoHistLog2.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
                deoHistLogRepository.save(preDeoHistLog2);
                deoHistLogSearchRepository.save(preDeoHistLog2);
            }
        }


        deoHistLog.setActivated(true);
        deoHistLog.setDateCrated(LocalDate.now());
        deoHistLog.setDateModified(LocalDate.now());
        deoHistLog.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        DeoHistLog result = deoHistLogRepository.save(deoHistLog);
        deoHistLogSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/deoHistLogs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("deoHistLog", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /deoHistLogs -> Updates an existing deoHistLog.
     */
    @RequestMapping(value = "/deoHistLogs",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeoHistLog> updateDeoHistLog(@RequestBody DeoHistLog deoHistLog) throws URISyntaxException {
        log.debug("REST request to update DeoHistLog : {}", deoHistLog);
        if (deoHistLog.getId() == null) {
            return createDeoHistLog(deoHistLog);
        }
        deoHistLog.setDateModified(LocalDate.now());
        deoHistLog.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        DeoHistLog result = deoHistLogRepository.save(deoHistLog);
        deoHistLogSearchRepository.save(deoHistLog);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("deoHistLog", deoHistLog.getId().toString()))
            .body(result);
    }

    /**
     * GET  /deoHistLogs -> get all the deoHistLogs.
     */
    @RequestMapping(value = "/deoHistLogs",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DeoHistLog>> getAllDeoHistLogs(Pageable pageable)
        throws URISyntaxException {
        Page<DeoHistLog> page = deoHistLogRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/deoHistLogs");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /deoHistLogs -> get all the deoHistLogs.
     */
    @RequestMapping(value = "/deoHistLogs/activeDeos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DeoHistLog>> getAllActiveDeoHistLogs(Pageable pageable)
        throws URISyntaxException {
        List<DeoHistLog> page = deoHistLogRepository.findAllActiveDeosLog();
        if(page.size() > 0){
            return new ResponseEntity<>(page, HttpStatus.OK);
        }
        //HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders, "/api/deoHistLogs");
        return new ResponseEntity<>(page, HttpStatus.NO_CONTENT);
    }

    /**
     * GET  /deoHistLogs/:id -> get the "id" deoHistLog.
     */
    @RequestMapping(value = "/deoHistLogs/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DeoHistLog> getDeoHistLog(@PathVariable Long id) {
        log.debug("REST request to get DeoHistLog : {}", id);
        return Optional.ofNullable(deoHistLogRepository.findOne(id))
            .map(deoHistLog -> new ResponseEntity<>(
                deoHistLog,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /deoHistLogs/:id -> delete the "id" deoHistLog.
     */
    @RequestMapping(value = "/deoHistLogs/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDeoHistLog(@PathVariable Long id) {
        log.debug("REST request to delete DeoHistLog : {}", id);
        deoHistLogRepository.delete(id);
        deoHistLogSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("deoHistLog", id.toString())).build();
    }

    /**
     * SEARCH  /_search/deoHistLogs/:query -> search for the deoHistLog corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/deoHistLogs/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DeoHistLog> searchDeoHistLogs(@PathVariable String query) {
        return StreamSupport
            .stream(deoHistLogSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
