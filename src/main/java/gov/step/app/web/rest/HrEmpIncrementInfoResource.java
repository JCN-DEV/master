package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpIncrementInfo;
import gov.step.app.repository.HrEmpIncrementInfoRepository;
import gov.step.app.repository.search.HrEmpIncrementInfoSearchRepository;
import gov.step.app.service.constnt.HRMManagementConstant;
import gov.step.app.service.util.MiscFileInfo;
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
 * REST controller for managing HrEmpIncrementInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpIncrementInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpIncrementInfoResource.class);

    @Inject
    private HrEmpIncrementInfoRepository hrEmpIncrementInfoRepository;

    @Inject
    private HrEmpIncrementInfoSearchRepository hrEmpIncrementInfoSearchRepository;

    /**
     * POST  /hrEmpIncrementInfos -> Create a new hrEmpIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpIncrementInfo> createHrEmpIncrementInfo(@Valid @RequestBody HrEmpIncrementInfo hrEmpIncrementInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpIncrementInfo : {}", hrEmpIncrementInfo);
        if (hrEmpIncrementInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpIncrementInfo", "idexists", "A new hrEmpIncrementInfo cannot already have an ID")).body(null);
        }
        HrEmpIncrementInfo result = hrEmpIncrementInfoRepository.save(hrEmpIncrementInfo);
        hrEmpIncrementInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpIncrementInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpIncrementInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpIncrementInfos -> Updates an existing hrEmpIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpIncrementInfo> updateHrEmpIncrementInfo(@Valid @RequestBody HrEmpIncrementInfo hrEmpIncrementInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpIncrementInfo : {}", hrEmpIncrementInfo);
        if (hrEmpIncrementInfo.getId() == null) {
            return createHrEmpIncrementInfo(hrEmpIncrementInfo);
        }
        HrEmpIncrementInfo result = hrEmpIncrementInfoRepository.save(hrEmpIncrementInfo);
        hrEmpIncrementInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpIncrementInfo", hrEmpIncrementInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpIncrementInfos -> get all the hrEmpIncrementInfos.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpIncrementInfo>> getAllHrEmpIncrementInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpIncrementInfos");
        Page<HrEmpIncrementInfo> page = hrEmpIncrementInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpIncrementInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpIncrementInfos/:id -> get the "id" hrEmpIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpIncrementInfo> getHrEmpIncrementInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpIncrementInfo : {}", id);
        HrEmpIncrementInfo hrEmpIncrementInfo = hrEmpIncrementInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpIncrementInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpIncrementInfos/:id -> delete the "id" hrEmpIncrementInfo.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpIncrementInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpIncrementInfo : {}", id);
        hrEmpIncrementInfoRepository.delete(id);
        hrEmpIncrementInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpIncrementInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpIncrementInfos/:query -> search for the hrEmpIncrementInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpIncrementInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpIncrementInfo> searchHrEmpIncrementInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpIncrementInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpIncrementInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpAdvIncrementInfos/my -> get the current logged in hrEmpAdvIncrementInfos.
     */
    @RequestMapping(value = "/hrEmpIncrementInfos/my",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpIncrementInfo> getHrModelInfoByCurrentEmployee() {
        log.debug("REST request to get hrEmpIncrementInfos by current logged in ");
        HrEmpIncrementInfo modelInfo = hrEmpIncrementInfoRepository.findOneByEmployeeIsCurrentUser();

        return Optional.ofNullable(modelInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
