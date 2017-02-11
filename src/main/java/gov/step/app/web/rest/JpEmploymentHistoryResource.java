package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpEmployee;
import gov.step.app.domain.JpEmployeeTraining;
import gov.step.app.domain.JpEmploymentHistory;
import gov.step.app.repository.JpEmployeeRepository;
import gov.step.app.repository.JpEmploymentHistoryRepository;
import gov.step.app.repository.search.JpEmploymentHistorySearchRepository;
import gov.step.app.web.rest.util.AttachmentUtil;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing JpEmploymentHistory.
 */
@RestController
@RequestMapping("/api")
public class JpEmploymentHistoryResource {

    private final Logger log = LoggerFactory.getLogger(JpEmploymentHistoryResource.class);

    @Inject
    private JpEmploymentHistoryRepository jpEmploymentHistoryRepository;

    @Inject
    private JpEmploymentHistorySearchRepository jpEmploymentHistorySearchRepository;

    @Inject
    private JpEmployeeRepository jpEmployeeRepository;

    /**
     * POST  /jpEmploymentHistorys -> Create a new jpEmploymentHistory.
     */
    @RequestMapping(value = "/jpEmploymentHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmploymentHistory> createJpEmploymentHistory(@Valid @RequestBody JpEmploymentHistory jpEmploymentHistory) throws URISyntaxException {
        log.debug("REST request to save JpEmploymentHistory : {}", jpEmploymentHistory);
        if (jpEmploymentHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpEmploymentHistory cannot already have an ID").body(null);
        }
        JpEmploymentHistory result = jpEmploymentHistoryRepository.save(jpEmploymentHistory);
        jpEmploymentHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpEmploymentHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpEmploymentHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpEmploymentHistorys -> Updates an existing jpEmploymentHistory.
     */
    @RequestMapping(value = "/jpEmploymentHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmploymentHistory> updateJpEmploymentHistory(@Valid @RequestBody JpEmploymentHistory jpEmploymentHistory) throws URISyntaxException {
        log.debug("REST request to update JpEmploymentHistory : {}", jpEmploymentHistory);
        if (jpEmploymentHistory.getId() == null) {
            return createJpEmploymentHistory(jpEmploymentHistory);
        }
        JpEmploymentHistory result = jpEmploymentHistoryRepository.save(jpEmploymentHistory);
        jpEmploymentHistorySearchRepository.save(jpEmploymentHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpEmploymentHistory", jpEmploymentHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpEmploymentHistorys -> get all the jpEmploymentHistorys.
     */
    @RequestMapping(value = "/jpEmploymentHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpEmploymentHistory>> getAllJpEmploymentHistorys(Pageable pageable)
        throws URISyntaxException {
        Page<JpEmploymentHistory> page = jpEmploymentHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpEmploymentHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpEmploymentHistorys/:id -> get the "id" jpEmploymentHistory.
     */
    @RequestMapping(value = "/jpEmploymentHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmploymentHistory> getJpEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to get JpEmploymentHistory : {}", id);
        return Optional.ofNullable(jpEmploymentHistoryRepository.findOne(id))
            .map(jpEmploymentHistory -> new ResponseEntity<>(
                jpEmploymentHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpEmploymentHistorys/:id -> delete the "id" jpEmploymentHistory.
     */
    @RequestMapping(value = "/jpEmploymentHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpEmploymentHistory(@PathVariable Long id) {
        log.debug("REST request to delete JpEmploymentHistory : {}", id);
        jpEmploymentHistoryRepository.delete(id);
        jpEmploymentHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpEmploymentHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpEmploymentHistorys/:query -> search for the jpEmploymentHistory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpEmploymentHistorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmploymentHistory> searchJpEmploymentHistorys(@PathVariable String query) {
        return StreamSupport
            .stream(jpEmploymentHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/employmentHistorys/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmploymentHistory> getAllEmployementHistoryByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpEmploymentHistory> employmentHistories = jpEmploymentHistoryRepository.findByJpEmployee(id);
        return  employmentHistories;
    }

    @RequestMapping(value = "/employmentHistory/experience/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity <Map> getLowestEmployementHistoryByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        LocalDate employmentHistory = jpEmploymentHistoryRepository.getFirstEmployment(id);
        Map map = new HashMap();
        map.put("stDate", employmentHistory);
        return  new ResponseEntity<Map>(map, HttpStatus.OK);
    }

    /**
     * GET  /employees/my -> get my employee.
     */
    @RequestMapping(value = "/employmentHistory/firstExperience/curr",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public JpEmploymentHistory getMyFirst() throws Exception {

        JpEmployee jpEmployee = jpEmployeeRepository.findOneByEmployeeUserIsCurrentUser();

        return jpEmploymentHistoryRepository.getFirstEmploymentOfCurr(jpEmployee.getId());

    }
}
