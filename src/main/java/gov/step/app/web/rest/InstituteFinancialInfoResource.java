package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstituteFinancialInfo;
import gov.step.app.repository.InstituteFinancialInfoRepository;
import gov.step.app.repository.search.InstituteFinancialInfoSearchRepository;
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
 * REST controller for managing InstituteFinancialInfo.
 */
@RestController
@RequestMapping("/api")
public class InstituteFinancialInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstituteFinancialInfoResource.class);

    @Inject
    private InstituteFinancialInfoRepository instituteFinancialInfoRepository;

    @Inject
    private InstituteFinancialInfoSearchRepository instituteFinancialInfoSearchRepository;

    /**
     * POST  /instituteFinancialInfos -> Create a new instituteFinancialInfo.
     */
    @RequestMapping(value = "/instituteFinancialInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteFinancialInfo> createInstituteFinancialInfo(@Valid @RequestBody InstituteFinancialInfo instituteFinancialInfo) throws URISyntaxException {
        log.debug("REST request to save InstituteFinancialInfo : {}", instituteFinancialInfo);
        if (instituteFinancialInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instituteFinancialInfo cannot already have an ID").body(null);
        }
        InstituteFinancialInfo result = instituteFinancialInfoRepository.save(instituteFinancialInfo);
        instituteFinancialInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instituteFinancialInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instituteFinancialInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instituteFinancialInfos -> Updates an existing instituteFinancialInfo.
     */
    @RequestMapping(value = "/instituteFinancialInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteFinancialInfo> updateInstituteFinancialInfo(@Valid @RequestBody InstituteFinancialInfo instituteFinancialInfo) throws URISyntaxException {
        log.debug("REST request to update InstituteFinancialInfo : {}", instituteFinancialInfo);
        if (instituteFinancialInfo.getId() == null) {
            return createInstituteFinancialInfo(instituteFinancialInfo);
        }
        InstituteFinancialInfo result = instituteFinancialInfoRepository.save(instituteFinancialInfo);
        instituteFinancialInfoSearchRepository.save(instituteFinancialInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instituteFinancialInfo", instituteFinancialInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instituteFinancialInfos -> get all the instituteFinancialInfos.
     */
    @RequestMapping(value = "/instituteFinancialInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstituteFinancialInfo>> getAllInstituteFinancialInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstituteFinancialInfo> page = instituteFinancialInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instituteFinancialInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instituteFinancialInfos/:id -> get the "id" instituteFinancialInfo.
     */
    @RequestMapping(value = "/instituteFinancialInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstituteFinancialInfo> getInstituteFinancialInfo(@PathVariable Long id) {
        log.debug("REST request to get InstituteFinancialInfo : {}", id);
        return Optional.ofNullable(instituteFinancialInfoRepository.findOne(id))
            .map(instituteFinancialInfo -> new ResponseEntity<>(
                instituteFinancialInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instituteFinancialInfos/:id -> delete the "id" instituteFinancialInfo.
     */
    @RequestMapping(value = "/instituteFinancialInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstituteFinancialInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstituteFinancialInfo : {}", id);
        instituteFinancialInfoRepository.delete(id);
        instituteFinancialInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instituteFinancialInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instituteFinancialInfos/:query -> search for the instituteFinancialInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instituteFinancialInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstituteFinancialInfo> searchInstituteFinancialInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instituteFinancialInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
