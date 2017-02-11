package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstPlayGroundInfo;
import gov.step.app.repository.InstPlayGroundInfoRepository;
import gov.step.app.repository.search.InstPlayGroundInfoSearchRepository;
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
 * REST controller for managing InstPlayGroundInfo.
 */
@RestController
@RequestMapping("/api")
public class InstPlayGroundInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstPlayGroundInfoResource.class);

    @Inject
    private InstPlayGroundInfoRepository instPlayGroundInfoRepository;

    @Inject
    private InstPlayGroundInfoSearchRepository instPlayGroundInfoSearchRepository;

    /**
     * POST  /instPlayGroundInfos -> Create a new instPlayGroundInfo.
     */
    @RequestMapping(value = "/instPlayGroundInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfo> createInstPlayGroundInfo(@RequestBody InstPlayGroundInfo instPlayGroundInfo) throws URISyntaxException {
        log.debug("REST request to save InstPlayGroundInfo : {}", instPlayGroundInfo);
        if (instPlayGroundInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instPlayGroundInfo cannot already have an ID").body(null);
        }
        InstPlayGroundInfo result = instPlayGroundInfoRepository.save(instPlayGroundInfo);
        instPlayGroundInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instPlayGroundInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instPlayGroundInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instPlayGroundInfos -> Updates an existing instPlayGroundInfo.
     */
    @RequestMapping(value = "/instPlayGroundInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfo> updateInstPlayGroundInfo(@RequestBody InstPlayGroundInfo instPlayGroundInfo) throws URISyntaxException {
        log.debug("REST request to update InstPlayGroundInfo : {}", instPlayGroundInfo);
        if (instPlayGroundInfo.getId() == null) {
            return createInstPlayGroundInfo(instPlayGroundInfo);
        }
        InstPlayGroundInfo result = instPlayGroundInfoRepository.save(instPlayGroundInfo);
        instPlayGroundInfoSearchRepository.save(instPlayGroundInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instPlayGroundInfo", instPlayGroundInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instPlayGroundInfos -> get all the instPlayGroundInfos.
     */
    @RequestMapping(value = "/instPlayGroundInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstPlayGroundInfo>> getAllInstPlayGroundInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstPlayGroundInfo> page = instPlayGroundInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instPlayGroundInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instPlayGroundInfos/:id -> get the "id" instPlayGroundInfo.
     */
    @RequestMapping(value = "/instPlayGroundInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstPlayGroundInfo> getInstPlayGroundInfo(@PathVariable Long id) {
        log.debug("REST request to get InstPlayGroundInfo : {}", id);
        return Optional.ofNullable(instPlayGroundInfoRepository.findOne(id))
            .map(instPlayGroundInfo -> new ResponseEntity<>(
                instPlayGroundInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instPlayGroundInfos/:id -> delete the "id" instPlayGroundInfo.
     */
    @RequestMapping(value = "/instPlayGroundInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstPlayGroundInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstPlayGroundInfo : {}", id);
        instPlayGroundInfoRepository.delete(id);
        instPlayGroundInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instPlayGroundInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instPlayGroundInfos/:query -> search for the instPlayGroundInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instPlayGroundInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstPlayGroundInfo> searchInstPlayGroundInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instPlayGroundInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
