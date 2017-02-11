package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteShopInfo;
import gov.step.app.repository.InstituteShopInfoRepository;
import gov.step.app.repository.search.InstituteShopInfoSearchRepository;
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
 * REST controller for managing InstituteShopInfo.
 */
@RestController
@RequestMapping("/api")
public class InstituteShopInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstituteShopInfoResource.class);

    @Inject
    private InstituteShopInfoRepository instituteShopInfoRepository;

    @Inject
    private InstituteShopInfoSearchRepository instituteShopInfoSearchRepository;

    /**
     * POST  /instituteShopInfos -> Create a new instituteShopInfo.
     */
    @RequestMapping(value = "/instituteShopInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteShopInfo> createInstituteShopInfo(@RequestBody InstituteShopInfo instituteShopInfo) throws URISyntaxException {
        log.debug("REST request to save InstituteShopInfo : {}", instituteShopInfo);
        if (instituteShopInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteShopInfo cannot already have an ID").body(null);
        }
        InstituteShopInfo result = instituteShopInfoRepository.save(instituteShopInfo);
        instituteShopInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteShopInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteShopInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteShopInfos -> Updates an existing instituteShopInfo.
     */
    @RequestMapping(value = "/instituteShopInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteShopInfo> updateInstituteShopInfo(@RequestBody InstituteShopInfo instituteShopInfo) throws URISyntaxException {
        log.debug("REST request to update InstituteShopInfo : {}", instituteShopInfo);
        if (instituteShopInfo.getId() == null) {
            return createInstituteShopInfo(instituteShopInfo);
        }
        InstituteShopInfo result = instituteShopInfoRepository.save(instituteShopInfo);
        instituteShopInfoSearchRepository.save(instituteShopInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteShopInfo", instituteShopInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteShopInfos -> get all the instituteShopInfos.
     */
    @RequestMapping(value = "/instituteShopInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteShopInfo>> getAllInstituteShopInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteShopInfo> page = instituteShopInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteShopInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteShopInfos/:id -> get the "id" instituteShopInfo.
     */
    @RequestMapping(value = "/instituteShopInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteShopInfo> getInstituteShopInfo(@PathVariable Long id) {
        log.debug("REST request to get InstituteShopInfo : {}", id);
        return Optional.ofNullable(instituteShopInfoRepository.findOne(id))
            .map(instituteShopInfo -> new ResponseEntity<>(
                instituteShopInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteShopInfos/:id -> delete the "id" instituteShopInfo.
     */
    @RequestMapping(value = "/instituteShopInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteShopInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstituteShopInfo : {}", id);
        instituteShopInfoRepository.delete(id);
        instituteShopInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteShopInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteShopInfos/:query -> search for the instituteShopInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteShopInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteShopInfo> searchInstituteShopInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instituteShopInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
