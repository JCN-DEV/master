package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstShopInfo;
import gov.step.app.repository.InstShopInfoRepository;
import gov.step.app.repository.search.InstShopInfoSearchRepository;
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
 * REST controller for managing InstShopInfo.
 */
@RestController
@RequestMapping("/api")
public class InstShopInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstShopInfoResource.class);

    @Inject
    private InstShopInfoRepository instShopInfoRepository;

    @Inject
    private InstShopInfoSearchRepository instShopInfoSearchRepository;

    /**
     * POST  /instShopInfos -> Create a new instShopInfo.
     */
    @RequestMapping(value = "/instShopInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfo> createInstShopInfo(@RequestBody InstShopInfo instShopInfo) throws URISyntaxException {
        log.debug("REST request to save InstShopInfo : {}", instShopInfo);
        if (instShopInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instShopInfo cannot already have an ID").body(null);
        }
        InstShopInfo result = instShopInfoRepository.save(instShopInfo);
        instShopInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instShopInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instShopInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instShopInfos -> Updates an existing instShopInfo.
     */
    @RequestMapping(value = "/instShopInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfo> updateInstShopInfo(@RequestBody InstShopInfo instShopInfo) throws URISyntaxException {
        log.debug("REST request to update InstShopInfo : {}", instShopInfo);
        if (instShopInfo.getId() == null) {
            return createInstShopInfo(instShopInfo);
        }
        InstShopInfo result = instShopInfoRepository.save(instShopInfo);
        instShopInfoSearchRepository.save(instShopInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instShopInfo", instShopInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instShopInfos -> get all the instShopInfos.
     */
    @RequestMapping(value = "/instShopInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstShopInfo>> getAllInstShopInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstShopInfo> page = instShopInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instShopInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instShopInfos/:id -> get the "id" instShopInfo.
     */
    @RequestMapping(value = "/instShopInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstShopInfo> getInstShopInfo(@PathVariable Long id) {
        log.debug("REST request to get InstShopInfo : {}", id);
        return Optional.ofNullable(instShopInfoRepository.findOne(id))
            .map(instShopInfo -> new ResponseEntity<>(
                instShopInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instShopInfos/:id -> delete the "id" instShopInfo.
     */
    @RequestMapping(value = "/instShopInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstShopInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstShopInfo : {}", id);
        instShopInfoRepository.delete(id);
        instShopInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instShopInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instShopInfos/:query -> search for the instShopInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instShopInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstShopInfo> searchInstShopInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instShopInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
