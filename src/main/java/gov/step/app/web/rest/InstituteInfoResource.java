package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteInfo;
import gov.step.app.repository.InstituteInfoRepository;
import gov.step.app.repository.search.InstituteInfoSearchRepository;
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
 * REST controller for managing InstituteInfo.
 */
@RestController
@RequestMapping("/api")
public class InstituteInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstituteInfoResource.class);

    @Inject
    private InstituteInfoRepository instituteInfoRepository;

    @Inject
    private InstituteInfoSearchRepository instituteInfoSearchRepository;

    /**
     * POST  /instituteInfos -> Create a new instituteInfo.
     */
    @RequestMapping(value = "/instituteInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfo> createInstituteInfo(@Valid @RequestBody InstituteInfo instituteInfo) throws URISyntaxException {
        log.debug("REST request to save InstituteInfo : {}", instituteInfo);
        if (instituteInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteInfo cannot already have an ID").body(null);
        }
        InstituteInfo result = instituteInfoRepository.save(instituteInfo);
        instituteInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteInfos -> Updates an existing instituteInfo.
     */
    @RequestMapping(value = "/instituteInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfo> updateInstituteInfo(@Valid @RequestBody InstituteInfo instituteInfo) throws URISyntaxException {
        log.debug("REST request to update InstituteInfo : {}", instituteInfo);
        if (instituteInfo.getId() == null) {
            return createInstituteInfo(instituteInfo);
        }
        InstituteInfo result = instituteInfoRepository.save(instituteInfo);
        instituteInfoSearchRepository.save(instituteInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteInfo", instituteInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteInfos -> get all the instituteInfos.
     */
    @RequestMapping(value = "/instituteInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteInfo>> getAllInstituteInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteInfo> page = instituteInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteInfos/:id -> get the "id" instituteInfo.
     */
    @RequestMapping(value = "/instituteInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteInfo> getInstituteInfo(@PathVariable Long id) {
        log.debug("REST request to get InstituteInfo : {}", id);
        return Optional.ofNullable(instituteInfoRepository.findOne(id))
            .map(instituteInfo -> new ResponseEntity<>(
                instituteInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteInfos/:id -> delete the "id" instituteInfo.
     */
    @RequestMapping(value = "/instituteInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstituteInfo : {}", id);
        instituteInfoRepository.delete(id);
        instituteInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteInfos/:query -> search for the instituteInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteInfo> searchInstituteInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instituteInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
