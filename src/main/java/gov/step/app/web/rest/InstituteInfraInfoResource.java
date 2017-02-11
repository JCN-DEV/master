package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteInfraInfo;
import gov.step.app.repository.InstituteInfraInfoRepository;
import gov.step.app.repository.search.InstituteInfraInfoSearchRepository;
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
 * REST controller for managing InstituteInfraInfo.
 */
@RestController
@RequestMapping("/api")
public class InstituteInfraInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstituteInfraInfoResource.class);

    @Inject
    private InstituteInfraInfoRepository instituteInfraInfoRepository;

    @Inject
    private InstituteInfraInfoSearchRepository instituteInfraInfoSearchRepository;

    /**
     * POST  /instituteInfraInfos -> Create a new instituteInfraInfo.
     */
    @RequestMapping(value = "/instituteInfraInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfraInfo> createInstituteInfraInfo(@RequestBody InstituteInfraInfo instituteInfraInfo) throws URISyntaxException {
        log.debug("REST request to save InstituteInfraInfo : {}", instituteInfraInfo);
        if (instituteInfraInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteInfraInfo cannot already have an ID").body(null);
        }
        InstituteInfraInfo result = instituteInfraInfoRepository.save(instituteInfraInfo);
        instituteInfraInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteInfraInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteInfraInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteInfraInfos -> Updates an existing instituteInfraInfo.
     */
    @RequestMapping(value = "/instituteInfraInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfraInfo> updateInstituteInfraInfo(@RequestBody InstituteInfraInfo instituteInfraInfo) throws URISyntaxException {
        log.debug("REST request to update InstituteInfraInfo : {}", instituteInfraInfo);
        if (instituteInfraInfo.getId() == null) {
            return createInstituteInfraInfo(instituteInfraInfo);
        }
        InstituteInfraInfo result = instituteInfraInfoRepository.save(instituteInfraInfo);
        instituteInfraInfoSearchRepository.save(instituteInfraInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteInfraInfo", instituteInfraInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteInfraInfos -> get all the instituteInfraInfos.
     */
    @RequestMapping(value = "/instituteInfraInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteInfraInfo>> getAllInstituteInfraInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteInfraInfo> page = instituteInfraInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteInfraInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteInfraInfos/:id -> get the "id" instituteInfraInfo.
     */
    @RequestMapping(value = "/instituteInfraInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfraInfo> getInstituteInfraInfo(@PathVariable Long id) {
        log.debug("REST request to get InstituteInfraInfo : {}", id);
        return Optional.ofNullable(instituteInfraInfoRepository.findOne(id))
            .map(instituteInfraInfo -> new ResponseEntity<>(
                instituteInfraInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteInfraInfos/:id -> delete the "id" instituteInfraInfo.
     */
    @RequestMapping(value = "/instituteInfraInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteInfraInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstituteInfraInfo : {}", id);
        instituteInfraInfoRepository.delete(id);
        instituteInfraInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteInfraInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteInfraInfos/:query -> search for the instituteInfraInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteInfraInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteInfraInfo> searchInstituteInfraInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instituteInfraInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /**
     * GET  /instEmpCounts/:id -> get the "id" instEmpCount.
     */
    @RequestMapping(value = "/instituteInfraInfoByInstituteId/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfraInfo> getInstituteBuildingByInstituteId(@PathVariable Long id) {
        log.debug("REST request to get InstEmpCount : {}", id);
        return Optional.ofNullable(instituteInfraInfoRepository.findOneByInstituteId(id))
            .map(instituteInfraInfo -> new ResponseEntity<>(
                instituteInfraInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
