package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteLabInfo;
import gov.step.app.repository.InstituteLabInfoRepository;
import gov.step.app.repository.search.InstituteLabInfoSearchRepository;
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
 * REST controller for managing InstituteLabInfo.
 */
@RestController
@RequestMapping("/api")
public class InstituteLabInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstituteLabInfoResource.class);

    @Inject
    private InstituteLabInfoRepository instituteLabInfoRepository;

    @Inject
    private InstituteLabInfoSearchRepository instituteLabInfoSearchRepository;

    /**
     * POST  /instituteLabInfos -> Create a new instituteLabInfo.
     */
    @RequestMapping(value = "/instituteLabInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLabInfo> createInstituteLabInfo(@RequestBody InstituteLabInfo instituteLabInfo) throws URISyntaxException {
        log.debug("REST request to save InstituteLabInfo : {}", instituteLabInfo);
        if (instituteLabInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteLabInfo cannot already have an ID").body(null);
        }
        InstituteLabInfo result = instituteLabInfoRepository.save(instituteLabInfo);
        instituteLabInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteLabInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteLabInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteLabInfos -> Updates an existing instituteLabInfo.
     */
    @RequestMapping(value = "/instituteLabInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLabInfo> updateInstituteLabInfo(@RequestBody InstituteLabInfo instituteLabInfo) throws URISyntaxException {
        log.debug("REST request to update InstituteLabInfo : {}", instituteLabInfo);
        if (instituteLabInfo.getId() == null) {
            return createInstituteLabInfo(instituteLabInfo);
        }
        InstituteLabInfo result = instituteLabInfoRepository.save(instituteLabInfo);
        instituteLabInfoSearchRepository.save(instituteLabInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteLabInfo", instituteLabInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteLabInfos -> get all the instituteLabInfos.
     */
    @RequestMapping(value = "/instituteLabInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteLabInfo>> getAllInstituteLabInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteLabInfo> page = instituteLabInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteLabInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteLabInfos/:id -> get the "id" instituteLabInfo.
     */
    @RequestMapping(value = "/instituteLabInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteLabInfo> getInstituteLabInfo(@PathVariable Long id) {
        log.debug("REST request to get InstituteLabInfo : {}", id);
        return Optional.ofNullable(instituteLabInfoRepository.findOne(id))
            .map(instituteLabInfo -> new ResponseEntity<>(
                instituteLabInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteLabInfos/:id -> delete the "id" instituteLabInfo.
     */
    @RequestMapping(value = "/instituteLabInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteLabInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstituteLabInfo : {}", id);
        instituteLabInfoRepository.delete(id);
        instituteLabInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteLabInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteLabInfos/:query -> search for the instituteLabInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteLabInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteLabInfo> searchInstituteLabInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instituteLabInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
