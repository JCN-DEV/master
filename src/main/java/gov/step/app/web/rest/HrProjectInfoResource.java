package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrProjectInfo;
import gov.step.app.repository.HrProjectInfoRepository;
import gov.step.app.repository.search.HrProjectInfoSearchRepository;
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
 * REST controller for managing HrProjectInfo.
 */
@RestController
@RequestMapping("/api")
public class HrProjectInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrProjectInfoResource.class);
        
    @Inject
    private HrProjectInfoRepository hrProjectInfoRepository;
    
    @Inject
    private HrProjectInfoSearchRepository hrProjectInfoSearchRepository;
    
    /**
     * POST  /hrProjectInfos -> Create a new hrProjectInfo.
     */
    @RequestMapping(value = "/hrProjectInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrProjectInfo> createHrProjectInfo(@Valid @RequestBody HrProjectInfo hrProjectInfo) throws URISyntaxException {
        log.debug("REST request to save HrProjectInfo : {}", hrProjectInfo);
        if (hrProjectInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrProjectInfo", "idexists", "A new hrProjectInfo cannot already have an ID")).body(null);
        }
        HrProjectInfo result = hrProjectInfoRepository.save(hrProjectInfo);
        hrProjectInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrProjectInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrProjectInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrProjectInfos -> Updates an existing hrProjectInfo.
     */
    @RequestMapping(value = "/hrProjectInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrProjectInfo> updateHrProjectInfo(@Valid @RequestBody HrProjectInfo hrProjectInfo) throws URISyntaxException {
        log.debug("REST request to update HrProjectInfo : {}", hrProjectInfo);
        if (hrProjectInfo.getId() == null) {
            return createHrProjectInfo(hrProjectInfo);
        }
        HrProjectInfo result = hrProjectInfoRepository.save(hrProjectInfo);
        hrProjectInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrProjectInfo", hrProjectInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrProjectInfos -> get all the hrProjectInfos.
     */
    @RequestMapping(value = "/hrProjectInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrProjectInfo>> getAllHrProjectInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrProjectInfos");
        Page<HrProjectInfo> page = hrProjectInfoRepository.findAll(pageable); 
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrProjectInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrProjectInfos/:id -> get the "id" hrProjectInfo.
     */
    @RequestMapping(value = "/hrProjectInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrProjectInfo> getHrProjectInfo(@PathVariable Long id) {
        log.debug("REST request to get HrProjectInfo : {}", id);
        HrProjectInfo hrProjectInfo = hrProjectInfoRepository.findOne(id);
        return Optional.ofNullable(hrProjectInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrProjectInfos/:id -> delete the "id" hrProjectInfo.
     */
    @RequestMapping(value = "/hrProjectInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrProjectInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrProjectInfo : {}", id);
        hrProjectInfoRepository.delete(id);
        hrProjectInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrProjectInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrProjectInfos/:query -> search for the hrProjectInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrProjectInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrProjectInfo> searchHrProjectInfos(@PathVariable String query) {
        log.debug("REST request to search HrProjectInfos for query {}", query);
        return StreamSupport
            .stream(hrProjectInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
