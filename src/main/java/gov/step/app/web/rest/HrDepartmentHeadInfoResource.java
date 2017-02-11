package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrDepartmentHeadInfo;
import gov.step.app.repository.HrDepartmentHeadInfoRepository;
import gov.step.app.repository.search.HrDepartmentHeadInfoSearchRepository;
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
 * REST controller for managing HrDepartmentHeadInfo.
 */
@RestController
@RequestMapping("/api")
public class HrDepartmentHeadInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrDepartmentHeadInfoResource.class);

    @Inject
    private HrDepartmentHeadInfoRepository hrDepartmentHeadInfoRepository;

    @Inject
    private HrDepartmentHeadInfoSearchRepository hrDepartmentHeadInfoSearchRepository;

    /**
     * POST  /hrDepartmentHeadInfos -> Create a new hrDepartmentHeadInfo.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadInfo> createHrDepartmentHeadInfo(@Valid @RequestBody HrDepartmentHeadInfo hrDepartmentHeadInfo) throws URISyntaxException {
        log.debug("REST request to save HrDepartmentHeadInfo : {}", hrDepartmentHeadInfo);
        if (hrDepartmentHeadInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new hrDepartmentHeadInfo cannot already have an ID").body(null);
        }

        if(hrDepartmentHeadInfo.getActiveHead())
        {
            hrDepartmentHeadInfoRepository.updateAllDepartmentHeadActiveStatus(hrDepartmentHeadInfo.getDepartmentInfo().getId(), false);
        }

        HrDepartmentHeadInfo result = hrDepartmentHeadInfoRepository.save(hrDepartmentHeadInfo);
        hrDepartmentHeadInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrDepartmentHeadInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrDepartmentHeadInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrDepartmentHeadInfos -> Updates an existing hrDepartmentHeadInfo.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadInfo> updateHrDepartmentHeadInfo(@Valid @RequestBody HrDepartmentHeadInfo hrDepartmentHeadInfo) throws URISyntaxException {
        log.debug("REST request to update HrDepartmentHeadInfo : {}", hrDepartmentHeadInfo);
        if (hrDepartmentHeadInfo.getId() == null) {
            return createHrDepartmentHeadInfo(hrDepartmentHeadInfo);
        }

        if(hrDepartmentHeadInfo.getActiveHead())
        {
            hrDepartmentHeadInfoRepository.updateAllDepartmentHeadActiveStatus(hrDepartmentHeadInfo.getDepartmentInfo().getId(), false);
        }

        HrDepartmentHeadInfo result = hrDepartmentHeadInfoRepository.save(hrDepartmentHeadInfo);
        hrDepartmentHeadInfoSearchRepository.save(hrDepartmentHeadInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrDepartmentHeadInfo", hrDepartmentHeadInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrDepartmentHeadInfos -> get all the hrDepartmentHeadInfos.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrDepartmentHeadInfo>> getAllHrDepartmentHeadInfos(Pageable pageable)
        throws URISyntaxException {
        Page<HrDepartmentHeadInfo> page = hrDepartmentHeadInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrDepartmentHeadInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrDepartmentHeadInfos/:id -> get the "id" hrDepartmentHeadInfo.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrDepartmentHeadInfo> getHrDepartmentHeadInfo(@PathVariable Long id) {
        log.debug("REST request to get HrDepartmentHeadInfo : {}", id);
        return Optional.ofNullable(hrDepartmentHeadInfoRepository.findOne(id))
            .map(hrDepartmentHeadInfo -> new ResponseEntity<>(
                hrDepartmentHeadInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrDepartmentHeadInfos/:id -> delete the "id" hrDepartmentHeadInfo.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrDepartmentHeadInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrDepartmentHeadInfo : {}", id);
        hrDepartmentHeadInfoRepository.delete(id);
        hrDepartmentHeadInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrDepartmentHeadInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrDepartmentHeadInfos/:query -> search for the hrDepartmentHeadInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrDepartmentHeadInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentHeadInfo> searchHrDepartmentHeadInfos(@PathVariable String query) {
        return StreamSupport
            .stream(hrDepartmentHeadInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrDepartmentHeadInfosByDept/:deptId -> get department list by department id.
     */
    @RequestMapping(value = "/hrDepartmentHeadInfosByDept/{deptId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrDepartmentHeadInfo> getDepartmentHeadListByDepartment(@PathVariable Long deptId) {
        log.debug("REST hrDepartmentHeadInfosByDept List : logStatus: {} ",deptId);
        List<HrDepartmentHeadInfo> modelList = hrDepartmentHeadInfoRepository.findAllByDepartment(deptId);

        return modelList;
    }
}
