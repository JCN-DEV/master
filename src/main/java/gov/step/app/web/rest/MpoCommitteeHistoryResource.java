package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.MpoCommitteeHistory;
import gov.step.app.repository.MpoCommitteeHistoryRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.MpoCommitteeHistorySearchRepository;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing MpoCommitteeHistory.
 */
@RestController
@RequestMapping("/api")
public class MpoCommitteeHistoryResource {

    private final Logger log = LoggerFactory.getLogger(MpoCommitteeHistoryResource.class);

    @Inject
    private MpoCommitteeHistoryRepository mpoCommitteeHistoryRepository;

    @Inject
    private MpoCommitteeHistorySearchRepository mpoCommitteeHistorySearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /mpoCommitteeHistorys -> Create a new mpoCommitteeHistory.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeHistory> createMpoCommitteeHistory(@Valid @RequestBody MpoCommitteeHistory mpoCommitteeHistory) throws URISyntaxException {
        log.debug("REST request to save MpoCommitteeHistory : {}", mpoCommitteeHistory);
        if (mpoCommitteeHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new mpoCommitteeHistory cannot already have an ID").body(null);
        }
        mpoCommitteeHistory.setDateCrated(LocalDate.now());
        mpoCommitteeHistory.setDateModified(LocalDate.now());
        mpoCommitteeHistory.setCreatedBy(userRepository.findOne(SecurityUtils.getCurrentUser().getId()));
        MpoCommitteeHistory result = mpoCommitteeHistoryRepository.save(mpoCommitteeHistory);
        mpoCommitteeHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/mpoCommitteeHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mpoCommitteeHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mpoCommitteeHistorys -> Updates an existing mpoCommitteeHistory.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeHistory> updateMpoCommitteeHistory(@Valid @RequestBody MpoCommitteeHistory mpoCommitteeHistory) throws URISyntaxException {
        log.debug("REST request to update MpoCommitteeHistory : {}", mpoCommitteeHistory);
        if (mpoCommitteeHistory.getId() == null) {
            return createMpoCommitteeHistory(mpoCommitteeHistory);
        }
        MpoCommitteeHistory result = mpoCommitteeHistoryRepository.save(mpoCommitteeHistory);
        mpoCommitteeHistorySearchRepository.save(mpoCommitteeHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mpoCommitteeHistory", mpoCommitteeHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mpoCommitteeHistorys -> get all the mpoCommitteeHistorys.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoCommitteeHistory>> getAllMpoCommitteeHistorys(Pageable pageable)
        throws URISyntaxException {
        Page<MpoCommitteeHistory> page = mpoCommitteeHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoCommitteeHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /mpoCommitteeHistorys -> get all the mpoCommitteeHistorys.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys/search/{month}/{year}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<MpoCommitteeHistory>> getMpoCommitteeHistorysByMonthAndYear(@PathVariable Integer month, @PathVariable Integer year, Pageable pageable)
        throws URISyntaxException {
        Page<MpoCommitteeHistory> page = mpoCommitteeHistoryRepository.findComitteeByMonthAndYear(month, year, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/mpoCommitteeHistorys/search/"+month+"/"+year);
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    } /**
     * GET  /mpoCommitteeHistorys -> get all the mpoCommitteeHistorys.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys/user/{email:.+}/{month}/{year}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeHistory> getOneMpoCommitteeHistorysByEmailAndMonthAndYear(@PathVariable String email,@PathVariable Integer month, @PathVariable Integer year)
        {
        return Optional.ofNullable(mpoCommitteeHistoryRepository.findComitteeMemberByEmailAndMonthAndYear(month, year,email))
            .map(mpoCommitteeHistory -> new ResponseEntity<>(
                mpoCommitteeHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /mpoCommitteeHistorys/:id -> get the "id" mpoCommitteeHistory.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<MpoCommitteeHistory> getMpoCommitteeHistory(@PathVariable Long id) {
        log.debug("REST request to get MpoCommitteeHistory : {}", id);
        return Optional.ofNullable(mpoCommitteeHistoryRepository.findOne(id))
            .map(mpoCommitteeHistory -> new ResponseEntity<>(
                mpoCommitteeHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mpoCommitteeHistorys/:id -> delete the "id" mpoCommitteeHistory.
     */
    @RequestMapping(value = "/mpoCommitteeHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMpoCommitteeHistory(@PathVariable Long id) {
        log.debug("REST request to delete MpoCommitteeHistory : {}", id);
        mpoCommitteeHistoryRepository.delete(id);
        mpoCommitteeHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mpoCommitteeHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/mpoCommitteeHistorys/:query -> search for the mpoCommitteeHistory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/mpoCommitteeHistorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<MpoCommitteeHistory> searchMpoCommitteeHistorys(@PathVariable String query) {
        return StreamSupport
            .stream(mpoCommitteeHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
