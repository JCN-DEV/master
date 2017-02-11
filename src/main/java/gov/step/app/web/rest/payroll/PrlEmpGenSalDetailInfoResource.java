package gov.step.app.web.rest.payroll;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.payroll.PrlEmpGenSalDetailInfo;
import gov.step.app.repository.payroll.PrlEmpGenSalDetailInfoRepository;
import gov.step.app.repository.search.payroll.PrlEmpGenSalDetailInfoSearchRepository;
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
 * REST controller for managing PrlEmpGenSalDetailInfo.
 */
@RestController
@RequestMapping("/api")
public class PrlEmpGenSalDetailInfoResource {

    private final Logger log = LoggerFactory.getLogger(PrlEmpGenSalDetailInfoResource.class);

    @Inject
    private PrlEmpGenSalDetailInfoRepository prlEmpGenSalDetailInfoRepository;

    @Inject
    private PrlEmpGenSalDetailInfoSearchRepository prlEmpGenSalDetailInfoSearchRepository;

    /**
     * POST  /prlEmpGenSalDetailInfos -> Create a new prlEmpGenSalDetailInfo.
     */
    @RequestMapping(value = "/prlEmpGenSalDetailInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGenSalDetailInfo> createPrlEmpGenSalDetailInfo(@RequestBody PrlEmpGenSalDetailInfo prlEmpGenSalDetailInfo) throws URISyntaxException {
        log.debug("REST request to save PrlEmpGenSalDetailInfo : {}", prlEmpGenSalDetailInfo);
        if (prlEmpGenSalDetailInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("prlEmpGenSalDetailInfo", "idexists", "A new prlEmpGenSalDetailInfo cannot already have an ID")).body(null);
        }
        PrlEmpGenSalDetailInfo result = prlEmpGenSalDetailInfoRepository.save(prlEmpGenSalDetailInfo);
        prlEmpGenSalDetailInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/prlEmpGenSalDetailInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("prlEmpGenSalDetailInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /prlEmpGenSalDetailInfos -> Updates an existing prlEmpGenSalDetailInfo.
     */
    @RequestMapping(value = "/prlEmpGenSalDetailInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGenSalDetailInfo> updatePrlEmpGenSalDetailInfo(@RequestBody PrlEmpGenSalDetailInfo prlEmpGenSalDetailInfo) throws URISyntaxException {
        log.debug("REST request to update PrlEmpGenSalDetailInfo : {}", prlEmpGenSalDetailInfo);
        if (prlEmpGenSalDetailInfo.getId() == null) {
            return createPrlEmpGenSalDetailInfo(prlEmpGenSalDetailInfo);
        }
        PrlEmpGenSalDetailInfo result = prlEmpGenSalDetailInfoRepository.save(prlEmpGenSalDetailInfo);
        prlEmpGenSalDetailInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("prlEmpGenSalDetailInfo", prlEmpGenSalDetailInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /prlEmpGenSalDetailInfos -> get all the prlEmpGenSalDetailInfos.
     */
    @RequestMapping(value = "/prlEmpGenSalDetailInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<PrlEmpGenSalDetailInfo>> getAllPrlEmpGenSalDetailInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of PrlEmpGenSalDetailInfos");
        Page<PrlEmpGenSalDetailInfo> page = prlEmpGenSalDetailInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/prlEmpGenSalDetailInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /prlEmpGenSalDetailInfos/:id -> get the "id" prlEmpGenSalDetailInfo.
     */
    @RequestMapping(value = "/prlEmpGenSalDetailInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PrlEmpGenSalDetailInfo> getPrlEmpGenSalDetailInfo(@PathVariable Long id) {
        log.debug("REST request to get PrlEmpGenSalDetailInfo : {}", id);
        PrlEmpGenSalDetailInfo prlEmpGenSalDetailInfo = prlEmpGenSalDetailInfoRepository.findOne(id);
        return Optional.ofNullable(prlEmpGenSalDetailInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /prlEmpGenSalDetailInfos/:id -> delete the "id" prlEmpGenSalDetailInfo.
     */
    @RequestMapping(value = "/prlEmpGenSalDetailInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePrlEmpGenSalDetailInfo(@PathVariable Long id) {
        log.debug("REST request to delete PrlEmpGenSalDetailInfo : {}", id);
        prlEmpGenSalDetailInfoRepository.delete(id);
        prlEmpGenSalDetailInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("prlEmpGenSalDetailInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/prlEmpGenSalDetailInfos/:query -> search for the prlEmpGenSalDetailInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/prlEmpGenSalDetailInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PrlEmpGenSalDetailInfo> searchPrlEmpGenSalDetailInfos(@PathVariable String query) {
        log.debug("REST request to search PrlEmpGenSalDetailInfos for query {}", query);
        return StreamSupport
            .stream(prlEmpGenSalDetailInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
