package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAuditObjectionInfo;
import gov.step.app.repository.HrEmpAuditObjectionInfoRepository;
import gov.step.app.repository.search.HrEmpAuditObjectionInfoSearchRepository;
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
 * REST controller for managing HrEmpAuditObjectionInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpAuditObjectionInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpAuditObjectionInfoResource.class);
        
    @Inject
    private HrEmpAuditObjectionInfoRepository hrEmpAuditObjectionInfoRepository;
    
    @Inject
    private HrEmpAuditObjectionInfoSearchRepository hrEmpAuditObjectionInfoSearchRepository;
    
    /**
     * POST  /hrEmpAuditObjectionInfos -> Create a new hrEmpAuditObjectionInfo.
     */
    @RequestMapping(value = "/hrEmpAuditObjectionInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAuditObjectionInfo> createHrEmpAuditObjectionInfo(@Valid @RequestBody HrEmpAuditObjectionInfo hrEmpAuditObjectionInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpAuditObjectionInfo : {}", hrEmpAuditObjectionInfo);
        if (hrEmpAuditObjectionInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpAuditObjectionInfo", "idexists", "A new hrEmpAuditObjectionInfo cannot already have an ID")).body(null);
        }
        HrEmpAuditObjectionInfo result = hrEmpAuditObjectionInfoRepository.save(hrEmpAuditObjectionInfo);
        hrEmpAuditObjectionInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpAuditObjectionInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpAuditObjectionInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpAuditObjectionInfos -> Updates an existing hrEmpAuditObjectionInfo.
     */
    @RequestMapping(value = "/hrEmpAuditObjectionInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAuditObjectionInfo> updateHrEmpAuditObjectionInfo(@Valid @RequestBody HrEmpAuditObjectionInfo hrEmpAuditObjectionInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpAuditObjectionInfo : {}", hrEmpAuditObjectionInfo);
        if (hrEmpAuditObjectionInfo.getId() == null) {
            return createHrEmpAuditObjectionInfo(hrEmpAuditObjectionInfo);
        }
        HrEmpAuditObjectionInfo result = hrEmpAuditObjectionInfoRepository.save(hrEmpAuditObjectionInfo);
        hrEmpAuditObjectionInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpAuditObjectionInfo", hrEmpAuditObjectionInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpAuditObjectionInfos -> get all the hrEmpAuditObjectionInfos.
     */
    @RequestMapping(value = "/hrEmpAuditObjectionInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpAuditObjectionInfo>> getAllHrEmpAuditObjectionInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpAuditObjectionInfos");
        Page<HrEmpAuditObjectionInfo> page = hrEmpAuditObjectionInfoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpAuditObjectionInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpAuditObjectionInfos/:id -> get the "id" hrEmpAuditObjectionInfo.
     */
    @RequestMapping(value = "/hrEmpAuditObjectionInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpAuditObjectionInfo> getHrEmpAuditObjectionInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpAuditObjectionInfo : {}", id);
        HrEmpAuditObjectionInfo hrEmpAuditObjectionInfo = hrEmpAuditObjectionInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpAuditObjectionInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpAuditObjectionInfos/:id -> delete the "id" hrEmpAuditObjectionInfo.
     */
    @RequestMapping(value = "/hrEmpAuditObjectionInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpAuditObjectionInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpAuditObjectionInfo : {}", id);
        hrEmpAuditObjectionInfoRepository.delete(id);
        hrEmpAuditObjectionInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpAuditObjectionInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpAuditObjectionInfos/:query -> search for the hrEmpAuditObjectionInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpAuditObjectionInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpAuditObjectionInfo> searchHrEmpAuditObjectionInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpAuditObjectionInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpAuditObjectionInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
