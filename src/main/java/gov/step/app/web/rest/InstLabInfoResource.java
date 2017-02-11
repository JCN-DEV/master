package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstLabInfo;
import gov.step.app.repository.InstLabInfoRepository;
import gov.step.app.repository.search.InstLabInfoSearchRepository;
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
 * REST controller for managing InstLabInfo.
 */
@RestController
@RequestMapping("/api")
public class InstLabInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstLabInfoResource.class);

    @Inject
    private InstLabInfoRepository instLabInfoRepository;

    @Inject
    private InstLabInfoSearchRepository instLabInfoSearchRepository;

    /**
     * POST  /instLabInfos -> Create a new instLabInfo.
     */
    @RequestMapping(value = "/instLabInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfo> createInstLabInfo(@RequestBody InstLabInfo instLabInfo) throws URISyntaxException {
        log.debug("REST request to save InstLabInfo : {}", instLabInfo);
        if (instLabInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instLabInfo cannot already have an ID").body(null);
        }
        InstLabInfo result = instLabInfoRepository.save(instLabInfo);
        instLabInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instLabInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instLabInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instLabInfos -> Updates an existing instLabInfo.
     */
    @RequestMapping(value = "/instLabInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfo> updateInstLabInfo(@RequestBody InstLabInfo instLabInfo) throws URISyntaxException {
        log.debug("REST request to update InstLabInfo : {}", instLabInfo);
        if (instLabInfo.getId() == null) {
            return createInstLabInfo(instLabInfo);
        }
        InstLabInfo result = instLabInfoRepository.save(instLabInfo);
        instLabInfoSearchRepository.save(instLabInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instLabInfo", instLabInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instLabInfos -> get all the instLabInfos.
     */
    @RequestMapping(value = "/instLabInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstLabInfo>> getAllInstLabInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstLabInfo> page = instLabInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instLabInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instLabInfos/:id -> get the "id" instLabInfo.
     */
    @RequestMapping(value = "/instLabInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstLabInfo> getInstLabInfo(@PathVariable Long id) {
        log.debug("REST request to get InstLabInfo : {}", id);
        return Optional.ofNullable(instLabInfoRepository.findOne(id))
            .map(instLabInfo -> new ResponseEntity<>(
                instLabInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instLabInfos/:id -> delete the "id" instLabInfo.
     */
    @RequestMapping(value = "/instLabInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstLabInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstLabInfo : {}", id);
        instLabInfoRepository.delete(id);
        instLabInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instLabInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instLabInfos/:query -> search for the instLabInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instLabInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstLabInfo> searchInstLabInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instLabInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
