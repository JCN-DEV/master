package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpWorkAreaDtlInfo;
import gov.step.app.repository.HrEmpWorkAreaDtlInfoRepository;
import gov.step.app.repository.search.HrEmpWorkAreaDtlInfoSearchRepository;
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
 * REST controller for managing HrEmpWorkAreaDtlInfo.
 */
@RestController
@RequestMapping("/api")
public class HrEmpWorkAreaDtlInfoResource {

    private final Logger log = LoggerFactory.getLogger(HrEmpWorkAreaDtlInfoResource.class);

    @Inject
    private HrEmpWorkAreaDtlInfoRepository hrEmpWorkAreaDtlInfoRepository;

    @Inject
    private HrEmpWorkAreaDtlInfoSearchRepository hrEmpWorkAreaDtlInfoSearchRepository;

    /**
     * POST  /hrEmpWorkAreaDtlInfos -> Create a new hrEmpWorkAreaDtlInfo.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpWorkAreaDtlInfo> createHrEmpWorkAreaDtlInfo(@Valid @RequestBody HrEmpWorkAreaDtlInfo hrEmpWorkAreaDtlInfo) throws URISyntaxException {
        log.debug("REST request to save HrEmpWorkAreaDtlInfo : {}", hrEmpWorkAreaDtlInfo);
        if (hrEmpWorkAreaDtlInfo.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("hrEmpWorkAreaDtlInfo", "idexists", "A new hrEmpWorkAreaDtlInfo cannot already have an ID")).body(null);
        }
        HrEmpWorkAreaDtlInfo result = hrEmpWorkAreaDtlInfoRepository.save(hrEmpWorkAreaDtlInfo);
        hrEmpWorkAreaDtlInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/hrEmpWorkAreaDtlInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("hrEmpWorkAreaDtlInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /hrEmpWorkAreaDtlInfos -> Updates an existing hrEmpWorkAreaDtlInfo.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpWorkAreaDtlInfo> updateHrEmpWorkAreaDtlInfo(@Valid @RequestBody HrEmpWorkAreaDtlInfo hrEmpWorkAreaDtlInfo) throws URISyntaxException {
        log.debug("REST request to update HrEmpWorkAreaDtlInfo : {}", hrEmpWorkAreaDtlInfo);
        if (hrEmpWorkAreaDtlInfo.getId() == null) {
            return createHrEmpWorkAreaDtlInfo(hrEmpWorkAreaDtlInfo);
        }
        HrEmpWorkAreaDtlInfo result = hrEmpWorkAreaDtlInfoRepository.save(hrEmpWorkAreaDtlInfo);
        hrEmpWorkAreaDtlInfoSearchRepository.save(result);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("hrEmpWorkAreaDtlInfo", hrEmpWorkAreaDtlInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /hrEmpWorkAreaDtlInfos -> get all the hrEmpWorkAreaDtlInfos.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<HrEmpWorkAreaDtlInfo>> getAllHrEmpWorkAreaDtlInfos(Pageable pageable)
        throws URISyntaxException {
        log.debug("REST request to get a page of HrEmpWorkAreaDtlInfos");
        Page<HrEmpWorkAreaDtlInfo> page = hrEmpWorkAreaDtlInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/hrEmpWorkAreaDtlInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /hrEmpWorkAreaDtlInfos/:id -> get the "id" hrEmpWorkAreaDtlInfo.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<HrEmpWorkAreaDtlInfo> getHrEmpWorkAreaDtlInfo(@PathVariable Long id) {
        log.debug("REST request to get HrEmpWorkAreaDtlInfo : {}", id);
        HrEmpWorkAreaDtlInfo hrEmpWorkAreaDtlInfo = hrEmpWorkAreaDtlInfoRepository.findOne(id);
        return Optional.ofNullable(hrEmpWorkAreaDtlInfo)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /hrEmpWorkAreaDtlInfos/:id -> delete the "id" hrEmpWorkAreaDtlInfo.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteHrEmpWorkAreaDtlInfo(@PathVariable Long id) {
        log.debug("REST request to delete HrEmpWorkAreaDtlInfo : {}", id);
        hrEmpWorkAreaDtlInfoRepository.delete(id);
        hrEmpWorkAreaDtlInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("hrEmpWorkAreaDtlInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/hrEmpWorkAreaDtlInfos/:query -> search for the hrEmpWorkAreaDtlInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/hrEmpWorkAreaDtlInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpWorkAreaDtlInfo> searchHrEmpWorkAreaDtlInfos(@PathVariable String query) {
        log.debug("REST request to search HrEmpWorkAreaDtlInfos for query {}", query);
        return StreamSupport
            .stream(hrEmpWorkAreaDtlInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /hrEmpWorkAreaDtlInfosByStat/:stat -> get the all work area detail by active status.
     */
    @RequestMapping(value = "/hrEmpWorkAreaDtlInfosByStat/{stat}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<HrEmpWorkAreaDtlInfo> getAllDepartmentByActiveStatus(@PathVariable boolean stat)
        throws URISyntaxException
    {
        log.debug("REST all workarea detail by status : {}", stat);
        List<HrEmpWorkAreaDtlInfo> workAreaDtlInfoList = hrEmpWorkAreaDtlInfoRepository.findAllByActiveStatus(stat);
        return  workAreaDtlInfoList;
    }
}
