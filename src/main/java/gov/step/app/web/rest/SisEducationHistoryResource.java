package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.SisEducationHistory;
import gov.step.app.repository.SisEducationHistoryRepository;
import gov.step.app.repository.search.SisEducationHistorySearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing SisEducationHistory.
 */
@RestController
@RequestMapping("/api")
public class SisEducationHistoryResource {

    private final Logger log = LoggerFactory.getLogger(SisEducationHistoryResource.class);

    @Inject
    private SisEducationHistoryRepository sisEducationHistoryRepository;

    @Inject
    private SisEducationHistorySearchRepository sisEducationHistorySearchRepository;

    /**
     * POST  /sisEducationHistorys -> Create a new sisEducationHistory.
     */
    @RequestMapping(value = "/sisEducationHistorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisEducationHistory> createSisEducationHistory(@Valid @RequestBody SisEducationHistory sisEducationHistory) throws URISyntaxException {
        log.debug("REST request to save SisEducationHistory : {}", sisEducationHistory);
        if (sisEducationHistory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new sisEducationHistory cannot already have an ID").body(null);
        }
        sisEducationHistory.setCreateBy(SecurityUtils.getCurrentUser().getId());
        sisEducationHistory.setCreateDate(DateResource.getDateAsLocalDate());
        SisEducationHistory result = sisEducationHistoryRepository.save(sisEducationHistory);
        sisEducationHistorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/sisEducationHistorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("sisEducationHistory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /sisEducationHistorys -> Updates an existing sisEducationHistory.
     */
    @RequestMapping(value = "/sisEducationHistorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisEducationHistory> updateSisEducationHistory(@Valid @RequestBody SisEducationHistory sisEducationHistory) throws URISyntaxException {
        log.debug("REST request to update SisEducationHistory : {}", sisEducationHistory);
        if (sisEducationHistory.getId() == null) {
            return createSisEducationHistory(sisEducationHistory);
        }
        sisEducationHistory.setUpdateBy(SecurityUtils.getCurrentUser().getId());
        sisEducationHistory.setUpdateDate(DateResource.getDateAsLocalDate());
        SisEducationHistory result = sisEducationHistoryRepository.save(sisEducationHistory);
        sisEducationHistorySearchRepository.save(sisEducationHistory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("sisEducationHistory", sisEducationHistory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /sisEducationHistorys -> get all the sisEducationHistorys.
     */
    @RequestMapping(value = "/sisEducationHistorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<SisEducationHistory>> getAllSisEducationHistorys(Pageable pageable)
        throws URISyntaxException {
        Page<SisEducationHistory> page = sisEducationHistoryRepository.findAll(pageable);
        if(SecurityUtils.isCurrentUserInRole("ROLE_GOVT_STUDENT")){
            page = sisEducationHistoryRepository.findStudentsByUserId(pageable, SecurityUtils.getCurrentUser().getId());
        }

        //Page<SisEducationHistory> page = sisEducationHistoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/sisEducationHistorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /sisEducationHistorys/:id -> get the "id" sisEducationHistory.
     */
    @RequestMapping(value = "/sisEducationHistorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SisEducationHistory> getSisEducationHistory(@PathVariable Long id) {
        log.debug("REST request to get SisEducationHistory : {}", id);
        return Optional.ofNullable(sisEducationHistoryRepository.findOne(id))
            .map(sisEducationHistory -> new ResponseEntity<>(
                sisEducationHistory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /sisEducationHistorys/:id -> delete the "id" sisEducationHistory.
     */
    @RequestMapping(value = "/sisEducationHistorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSisEducationHistory(@PathVariable Long id) {
        log.debug("REST request to delete SisEducationHistory : {}", id);
        sisEducationHistoryRepository.delete(id);
        sisEducationHistorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("sisEducationHistory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/sisEducationHistorys/:query -> search for the sisEducationHistory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/sisEducationHistorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SisEducationHistory> searchSisEducationHistorys(@PathVariable String query) {
        return StreamSupport
            .stream(sisEducationHistorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
