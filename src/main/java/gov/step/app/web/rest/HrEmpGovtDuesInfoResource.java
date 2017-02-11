package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpGovtDuesInfo;
import gov.step.app.repository.HrEmpGovtDuesInfoRepository;
import gov.step.app.repository.search.HrEmpGovtDuesInfoSearchRepository;
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
 * REST controller for managing HrEmpGovtDuesInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpGovtDuesInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpGovtDuesInfoResource.class);

    @Inject
    private HrEmpGovtDuesInfoRepository hrEmpGovtDuesInfoRepository;

    @Inject
    private HrEmpGovtDuesInfoSearchRepository hrEmpGovtDuesInfoSearchRepository;

    /**
     * POST  /hrEmpGovtDuesInfos -> Create a new hrEmpGovtDuesInfo.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpGovtDuesInfo> createHrEmpGovtDuesInfo(@Valid @RequestBody HrEmpGovtDuesInfo hrEmpGovtDuesInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpGovtDuesInfo : {}", hrEmpGovtDuesInfo);
        if (hrEmpGovtDuesInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpGovtDuesInfo", "idexists", "A new hrEmpGovtDuesInfo cannot already have an ID")).body(null);
        }
        HrEmpGovtDuesInfo result = hrEmpGovtDuesInfoRepository.save(hrEmpGovtDuesInfo);
        hrEmpGovtDuesInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpGovtDuesInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpGovtDuesInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpGovtDuesInfos -> Updates an existing hrEmpGovtDuesInfo.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpGovtDuesInfo> updateHrEmpGovtDuesInfo(@Valid @RequestBody HrEmpGovtDuesInfo hrEmpGovtDuesInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpGovtDuesInfo : {}", hrEmpGovtDuesInfo);
        if (hrEmpGovtDuesInfo.getId() == null) {
            return createHrEmpGovtDuesInfo(hrEmpGovtDuesInfo);
        }
        HrEmpGovtDuesInfo result = hrEmpGovtDuesInfoRepository.save(hrEmpGovtDuesInfo);
        hrEmpGovtDuesInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpGovtDuesInfo", hrEmpGovtDuesInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpGovtDuesInfos -> get all the hrEmpGovtDuesInfos.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpGovtDuesInfo>> getAllHrEmpGovtDuesInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpGovtDuesInfos");
        Page<HrEmpGovtDuesInfo> page = hrEmpGovtDuesInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpGovtDuesInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpGovtDuesInfos/:id -> get the "id" hrEmpGovtDuesInfo.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpGovtDuesInfo> getHrEmpGovtDuesInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpGovtDuesInfo : {}", id);
        HrEmpGovtDuesInfo hrEmpGovtDuesInfo = hrEmpGovtDuesInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpGovtDuesInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpGovtDuesInfos/:id -> delete the "id" hrEmpGovtDuesInfo.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpGovtDuesInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpGovtDuesInfo : {}", id);
        hrEmpGovtDuesInfoRepository.delete(id);
        hrEmpGovtDuesInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpGovtDuesInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpGovtDuesInfos/:query -> search for the hrEmpGovtDuesInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpGovtDuesInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpGovtDuesInfo> searchHrEmpGovtDuesInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpGovtDuesInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpGovtDuesInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpGovtDuesInfos/my -> get the current logged in hrEmpGovtDuesInfos.
     */
    @RequestMapping(value = "/hrEmpGovtDuesInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpGovtDuesInfo> getHrModelInfoByCurrentEmployee() {
        log.debug("REST request to get hrEmpGovtDuesInfos by current logged in ");
        HrEmpGovtDuesInfo modelInfo = hrEmpGovtDuesInfoRepository.findOneByEmployeeIsCurrentUser();

        return Optional.ofNullable(modelInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
