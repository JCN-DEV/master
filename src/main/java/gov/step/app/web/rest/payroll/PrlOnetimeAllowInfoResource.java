package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlOnetimeAllowInfo;
import gov.step.app.repository.payroll.PrlOnetimeAllowInfoRepository;
import gov.step.app.repository.search.payroll.PrlOnetimeAllowInfoSearchRepository;
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
 * REST controller for managing PrlOnetimeAllowInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlOnetimeAllowInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlOnetimeAllowInfoResource.class);

    @Inject
    private PrlOnetimeAllowInfoRepository prlOnetimeAllowInfoRepository;

    @Inject
    private PrlOnetimeAllowInfoSearchRepository prlOnetimeAllowInfoSearchRepository;

    /**
     * POST  /prlOnetimeAllowInfos -> Create a new prlOnetimeAllowInfo.
     */
    @RequestMapping(value = "/prlOnetimeAllowInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlOnetimeAllowInfo> createPrlOnetimeAllowInfo(@Valid @RequestBody PrlOnetimeAllowInfo prlOnetimeAllowInfo) throws URISyntaxException {
        log.debug("REST request to save PrlOnetimeAllowInfo : {}", prlOnetimeAllowInfo);
        if (prlOnetimeAllowInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlOnetimeAllowInfo", "idexists", "A new prlOnetimeAllowInfo cannot already have an ID")).body(null);
        }
        PrlOnetimeAllowInfo result = prlOnetimeAllowInfoRepository.save(prlOnetimeAllowInfo);
        prlOnetimeAllowInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlOnetimeAllowInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlOnetimeAllowInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlOnetimeAllowInfos -> Updates an existing prlOnetimeAllowInfo.
     */
    @RequestMapping(value = "/prlOnetimeAllowInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlOnetimeAllowInfo> updatePrlOnetimeAllowInfo(@Valid @RequestBody PrlOnetimeAllowInfo prlOnetimeAllowInfo) throws URISyntaxException {
        log.debug("REST request to update PrlOnetimeAllowInfo : {}", prlOnetimeAllowInfo);
        if (prlOnetimeAllowInfo.getId() == null) {
            return createPrlOnetimeAllowInfo(prlOnetimeAllowInfo);
        }
        PrlOnetimeAllowInfo result = prlOnetimeAllowInfoRepository.save(prlOnetimeAllowInfo);
        prlOnetimeAllowInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlOnetimeAllowInfo", prlOnetimeAllowInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlOnetimeAllowInfos -> get all the prlOnetimeAllowInfos.
     */
    @RequestMapping(value = "/prlOnetimeAllowInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlOnetimeAllowInfo>> getAllPrlOnetimeAllowInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlOnetimeAllowInfos");
        Page<PrlOnetimeAllowInfo> page = prlOnetimeAllowInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlOnetimeAllowInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlOnetimeAllowInfos/:id -> get the "id" prlOnetimeAllowInfo.
     */
    @RequestMapping(value = "/prlOnetimeAllowInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlOnetimeAllowInfo> getPrlOnetimeAllowInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlOnetimeAllowInfo : {}", id);
        PrlOnetimeAllowInfo prlOnetimeAllowInfo = prlOnetimeAllowInfoRepository.findOne(id);
        return Optional.ofNullable(prlOnetimeAllowInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlOnetimeAllowInfos/:id -> delete the "id" prlOnetimeAllowInfo.
     */
    @RequestMapping(value = "/prlOnetimeAllowInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlOnetimeAllowInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlOnetimeAllowInfo : {}", id);
        prlOnetimeAllowInfoRepository.delete(id);
        prlOnetimeAllowInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlOnetimeAllowInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlOnetimeAllowInfos/:query -> search for the prlOnetimeAllowInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlOnetimeAllowInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlOnetimeAllowInfo> searchPrlOnetimeAllowInfos(@PathVariable String query) {
        log.debug("REST request to search PrlOnetimeAllowInfos for query {}", query);
        return StreamSupport
            .stream(prlOnetimeAllowInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
