package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlEmpPaymentStopInfo;
import gov.step.app.repository.payroll.PrlEmpPaymentStopInfoRepository;
import gov.step.app.repository.search.payroll.PrlEmpPaymentStopInfoSearchRepository;
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
 * REST controller for managing PrlEmpPaymentStopInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlEmpPaymentStopInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlEmpPaymentStopInfoResource.class);

    @Inject
    private PrlEmpPaymentStopInfoRepository prlEmpPaymentStopInfoRepository;

    @Inject
    private PrlEmpPaymentStopInfoSearchRepository prlEmpPaymentStopInfoSearchRepository;

    /**
     * POST  /prlEmpPaymentStopInfos -> Create a new prlEmpPaymentStopInfo.
     */
    @RequestMapping(value = "/prlEmpPaymentStopInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpPaymentStopInfo> createPrlEmpPaymentStopInfo(@RequestBody PrlEmpPaymentStopInfo prlEmpPaymentStopInfo) throws URISyntaxException {
        log.debug("REST request to save PrlEmpPaymentStopInfo : {}", prlEmpPaymentStopInfo);
        if (prlEmpPaymentStopInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlEmpPaymentStopInfo", "idexists", "A new prlEmpPaymentStopInfo cannot already have an ID")).body(null);
        }
        PrlEmpPaymentStopInfo result = prlEmpPaymentStopInfoRepository.save(prlEmpPaymentStopInfo);
        prlEmpPaymentStopInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlEmpPaymentStopInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlEmpPaymentStopInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlEmpPaymentStopInfos -> Updates an existing prlEmpPaymentStopInfo.
     */
    @RequestMapping(value = "/prlEmpPaymentStopInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpPaymentStopInfo> updatePrlEmpPaymentStopInfo(@RequestBody PrlEmpPaymentStopInfo prlEmpPaymentStopInfo) throws URISyntaxException {
        log.debug("REST request to update PrlEmpPaymentStopInfo : {}", prlEmpPaymentStopInfo);
        if (prlEmpPaymentStopInfo.getId() == null) {
            return createPrlEmpPaymentStopInfo(prlEmpPaymentStopInfo);
        }
        PrlEmpPaymentStopInfo result = prlEmpPaymentStopInfoRepository.save(prlEmpPaymentStopInfo);
        prlEmpPaymentStopInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlEmpPaymentStopInfo", prlEmpPaymentStopInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlEmpPaymentStopInfos -> get all the prlEmpPaymentStopInfos.
     */
    @RequestMapping(value = "/prlEmpPaymentStopInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlEmpPaymentStopInfo>> getAllPrlEmpPaymentStopInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlEmpPaymentStopInfos");
        Page<PrlEmpPaymentStopInfo> page = prlEmpPaymentStopInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlEmpPaymentStopInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlEmpPaymentStopInfos/:id -> get the "id" prlEmpPaymentStopInfo.
     */
    @RequestMapping(value = "/prlEmpPaymentStopInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpPaymentStopInfo> getPrlEmpPaymentStopInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlEmpPaymentStopInfo : {}", id);
        PrlEmpPaymentStopInfo prlEmpPaymentStopInfo = prlEmpPaymentStopInfoRepository.findOne(id);
        return Optional.ofNullable(prlEmpPaymentStopInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlEmpPaymentStopInfos/:id -> delete the "id" prlEmpPaymentStopInfo.
     */
    @RequestMapping(value = "/prlEmpPaymentStopInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlEmpPaymentStopInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlEmpPaymentStopInfo : {}", id);
        prlEmpPaymentStopInfoRepository.delete(id);
        prlEmpPaymentStopInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlEmpPaymentStopInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlEmpPaymentStopInfos/:query -> search for the prlEmpPaymentStopInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlEmpPaymentStopInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEmpPaymentStopInfo> searchPrlEmpPaymentStopInfos(@PathVariable String query) {
        log.debug("REST request to search PrlEmpPaymentStopInfos for query {}", query);
        return StreamSupport
            .stream(prlEmpPaymentStopInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
