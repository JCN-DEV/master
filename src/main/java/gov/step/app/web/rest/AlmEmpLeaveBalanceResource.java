package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.AlmEmpLeaveBalance;
import gov.step.app.repository.AlmEmpLeaveBalanceRepository;
import gov.step.app.repository.search.AlmEmpLeaveBalanceSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing AlmEmpLeaveBalance.
 */
@RestController
@RequestMapping("/api")
public class AlmEmpLeaveBalanceResource {

    private final Logger log = LoggerFactory.getLogger(AlmEmpLeaveBalanceResource.class);

    @Inject
    private AlmEmpLeaveBalanceRepository almEmpLeaveBalanceRepository;

    @Inject
    private AlmEmpLeaveBalanceSearchRepository almEmpLeaveBalanceSearchRepository;

    /**
     * POST  /almEmpLeaveBalances -> Create a new almEmpLeaveBalance.
     */
    @RequestMapping(value = "/almEmpLeaveBalances",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveBalance> createAlmEmpLeaveBalance(@RequestBody AlmEmpLeaveBalance almEmpLeaveBalance) throws URISyntaxException {
        log.debug("REST request to save AlmEmpLeaveBalance : {}", almEmpLeaveBalance);
        if (almEmpLeaveBalance.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new almEmpLeaveBalance cannot already have an ID").body(null);
        }
        AlmEmpLeaveBalance result = almEmpLeaveBalanceRepository.save(almEmpLeaveBalance);
        almEmpLeaveBalanceSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/almEmpLeaveBalances/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("almEmpLeaveBalance", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /almEmpLeaveBalances -> Updates an existing almEmpLeaveBalance.
     */
    @RequestMapping(value = "/almEmpLeaveBalances",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveBalance> updateAlmEmpLeaveBalance(@RequestBody AlmEmpLeaveBalance almEmpLeaveBalance) throws URISyntaxException {
        log.debug("REST request to update AlmEmpLeaveBalance : {}", almEmpLeaveBalance);
        if (almEmpLeaveBalance.getId() == null) {
            return createAlmEmpLeaveBalance(almEmpLeaveBalance);
        }
        AlmEmpLeaveBalance result = almEmpLeaveBalanceRepository.save(almEmpLeaveBalance);
        almEmpLeaveBalanceSearchRepository.save(almEmpLeaveBalance);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("almEmpLeaveBalance", almEmpLeaveBalance.getId().toString()))
            .body(result);
    }

    /**
     * GET  /almEmpLeaveBalances -> get all the almEmpLeaveBalances.
     */
    @RequestMapping(value = "/almEmpLeaveBalances",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<AlmEmpLeaveBalance>> getAllAlmEmpLeaveBalances(Pageable pageable)
        throws URISyntaxException {
        Page<AlmEmpLeaveBalance> page = almEmpLeaveBalanceRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/almEmpLeaveBalances");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /almEmpLeaveBalances/:id -> get the "id" almEmpLeaveBalance.
     */
    @RequestMapping(value = "/almEmpLeaveBalances/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<AlmEmpLeaveBalance> getAlmEmpLeaveBalance(@PathVariable Long id) {
        log.debug("REST request to get AlmEmpLeaveBalance : {}", id);
        return Optional.ofNullable(almEmpLeaveBalanceRepository.findOne(id))
            .map(almEmpLeaveBalance -> new ResponseEntity<>(
                almEmpLeaveBalance,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /almEmpLeaveBalances/:id -> delete the "id" almEmpLeaveBalance.
     */
    @RequestMapping(value = "/almEmpLeaveBalances/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteAlmEmpLeaveBalance(@PathVariable Long id) {
        log.debug("REST request to delete AlmEmpLeaveBalance : {}", id);
        almEmpLeaveBalanceRepository.delete(id);
        almEmpLeaveBalanceSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("almEmpLeaveBalance", id.toString())).build();
    }

    /**
     * SEARCH  /_search/almEmpLeaveBalances/:query -> search for the almEmpLeaveBalance corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/almEmpLeaveBalances/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<AlmEmpLeaveBalance> searchAlmEmpLeaveBalances(@PathVariable String query) {
        return StreamSupport
            .stream(almEmpLeaveBalanceSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
