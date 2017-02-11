package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstitutePlayGroundInfo;
import gov.step.app.repository.InstitutePlayGroundInfoRepository;
import gov.step.app.repository.search.InstitutePlayGroundInfoSearchRepository;
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
 * REST controller for managing InstitutePlayGroundInfo.
 */
@RestController
@RequestMapping("/api")
public class InstitutePlayGroundInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstitutePlayGroundInfoResource.class);

    @Inject
    private InstitutePlayGroundInfoRepository institutePlayGroundInfoRepository;

    @Inject
    private InstitutePlayGroundInfoSearchRepository institutePlayGroundInfoSearchRepository;

    /**
     * POST  /institutePlayGroundInfos -> Create a new institutePlayGroundInfo.
     */
    @RequestMapping(value = "/institutePlayGroundInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstitutePlayGroundInfo> createInstitutePlayGroundInfo(@RequestBody InstitutePlayGroundInfo institutePlayGroundInfo) throws URISyntaxException {
        log.debug("REST request to save InstitutePlayGroundInfo : {}", institutePlayGroundInfo);
        if (institutePlayGroundInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new institutePlayGroundInfo cannot already have an ID").body(null);
        }
        InstitutePlayGroundInfo result = institutePlayGroundInfoRepository.save(institutePlayGroundInfo);
        institutePlayGroundInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/institutePlayGroundInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("institutePlayGroundInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /institutePlayGroundInfos -> Updates an existing institutePlayGroundInfo.
     */
    @RequestMapping(value = "/institutePlayGroundInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstitutePlayGroundInfo> updateInstitutePlayGroundInfo(@RequestBody InstitutePlayGroundInfo institutePlayGroundInfo) throws URISyntaxException {
        log.debug("REST request to update InstitutePlayGroundInfo : {}", institutePlayGroundInfo);
        if (institutePlayGroundInfo.getId() == null) {
            return createInstitutePlayGroundInfo(institutePlayGroundInfo);
        }
        InstitutePlayGroundInfo result = institutePlayGroundInfoRepository.save(institutePlayGroundInfo);
        institutePlayGroundInfoSearchRepository.save(institutePlayGroundInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("institutePlayGroundInfo", institutePlayGroundInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /institutePlayGroundInfos -> get all the institutePlayGroundInfos.
     */
    @RequestMapping(value = "/institutePlayGroundInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstitutePlayGroundInfo>> getAllInstitutePlayGroundInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstitutePlayGroundInfo> page = institutePlayGroundInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/institutePlayGroundInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /institutePlayGroundInfos/:id -> get the "id" institutePlayGroundInfo.
     */
    @RequestMapping(value = "/institutePlayGroundInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstitutePlayGroundInfo> getInstitutePlayGroundInfo(@PathVariable Long id) {
        log.debug("REST request to get InstitutePlayGroundInfo : {}", id);
        return Optional.ofNullable(institutePlayGroundInfoRepository.findOne(id))
            .map(institutePlayGroundInfo -> new ResponseEntity<>(
                institutePlayGroundInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /institutePlayGroundInfos/:id -> delete the "id" institutePlayGroundInfo.
     */
    @RequestMapping(value = "/institutePlayGroundInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstitutePlayGroundInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstitutePlayGroundInfo : {}", id);
        institutePlayGroundInfoRepository.delete(id);
        institutePlayGroundInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("institutePlayGroundInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/institutePlayGroundInfos/:query -> search for the institutePlayGroundInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/institutePlayGroundInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstitutePlayGroundInfo> searchInstitutePlayGroundInfos(@PathVariable String query) {
        return StreamSupport
            .stream(institutePlayGroundInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
