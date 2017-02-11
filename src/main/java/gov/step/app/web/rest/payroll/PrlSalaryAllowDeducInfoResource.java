package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlSalaryAllowDeducInfo;
import gov.step.app.repository.payroll.PrlSalaryAllowDeducInfoRepository;
import gov.step.app.repository.search.payroll.PrlSalaryAllowDeducInfoSearchRepository;
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
 * REST controller for managing PrlSalaryAllowDeducInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlSalaryAllowDeducInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlSalaryAllowDeducInfoResource.class);

    @Inject
    private PrlSalaryAllowDeducInfoRepository prlSalaryAllowDeducInfoRepository;

    @Inject
    private PrlSalaryAllowDeducInfoSearchRepository prlSalaryAllowDeducInfoSearchRepository;

    /**
     * POST  /prlSalaryAllowDeducInfos -> Create a new prlSalaryAllowDeducInfo.
     */
    @RequestMapping(value = "/prlSalaryAllowDeducInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryAllowDeducInfo> createPrlSalaryAllowDeducInfo(@Valid @RequestBody PrlSalaryAllowDeducInfo prlSalaryAllowDeducInfo) throws URISyntaxException {
        log.debug("REST request to save PrlSalaryAllowDeducInfo : {}", prlSalaryAllowDeducInfo);
        if (prlSalaryAllowDeducInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlSalaryAllowDeducInfo", "idexists", "A new prlSalaryAllowDeducInfo cannot already have an ID")).body(null);
        }
        PrlSalaryAllowDeducInfo result = prlSalaryAllowDeducInfoRepository.save(prlSalaryAllowDeducInfo);
        prlSalaryAllowDeducInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlSalaryAllowDeducInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlSalaryAllowDeducInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlSalaryAllowDeducInfos -> Updates an existing prlSalaryAllowDeducInfo.
     */
    @RequestMapping(value = "/prlSalaryAllowDeducInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryAllowDeducInfo> updatePrlSalaryAllowDeducInfo(@Valid @RequestBody PrlSalaryAllowDeducInfo prlSalaryAllowDeducInfo) throws URISyntaxException {
        log.debug("REST request to update PrlSalaryAllowDeducInfo : {}", prlSalaryAllowDeducInfo);
        if (prlSalaryAllowDeducInfo.getId() == null) {
            return createPrlSalaryAllowDeducInfo(prlSalaryAllowDeducInfo);
        }
        PrlSalaryAllowDeducInfo result = prlSalaryAllowDeducInfoRepository.save(prlSalaryAllowDeducInfo);
        prlSalaryAllowDeducInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlSalaryAllowDeducInfo", prlSalaryAllowDeducInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlSalaryAllowDeducInfos -> get all the prlSalaryAllowDeducInfos.
     */
    @RequestMapping(value = "/prlSalaryAllowDeducInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlSalaryAllowDeducInfo>> getAllPrlSalaryAllowDeducInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlSalaryAllowDeducInfos");
        Page<PrlSalaryAllowDeducInfo> page = prlSalaryAllowDeducInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlSalaryAllowDeducInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlSalaryAllowDeducInfos/:id -> get the "id" prlSalaryAllowDeducInfo.
     */
    @RequestMapping(value = "/prlSalaryAllowDeducInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlSalaryAllowDeducInfo> getPrlSalaryAllowDeducInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlSalaryAllowDeducInfo : {}", id);
        PrlSalaryAllowDeducInfo prlSalaryAllowDeducInfo = prlSalaryAllowDeducInfoRepository.findOne(id);
        return Optional.ofNullable(prlSalaryAllowDeducInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlSalaryAllowDeducInfos/:id -> delete the "id" prlSalaryAllowDeducInfo.
     */
    @RequestMapping(value = "/prlSalaryAllowDeducInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlSalaryAllowDeducInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlSalaryAllowDeducInfo : {}", id);
        prlSalaryAllowDeducInfoRepository.delete(id);
        prlSalaryAllowDeducInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlSalaryAllowDeducInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlSalaryAllowDeducInfos/:query -> search for the prlSalaryAllowDeducInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlSalaryAllowDeducInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlSalaryAllowDeducInfo> searchPrlSalaryAllowDeducInfos(@PathVariable String query) {
        log.debug("REST request to search PrlSalaryAllowDeducInfos for query {}", query);
        return StreamSupport
            .stream(prlSalaryAllowDeducInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
