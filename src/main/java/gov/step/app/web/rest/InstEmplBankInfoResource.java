package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.InstEmplBankInfo;
import gov.step.app.repository.InstEmplBankInfoRepository;
import gov.step.app.repository.search.InstEmplBankInfoSearchRepository;
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
 * REST controller for managing InstEmplBankInfo.
 */
@RestController
@RequestMapping("/api")
public class InstEmplBankInfoResource {

    private final Logger log = LoggerFactory.getLogger(InstEmplBankInfoResource.class);

    @Inject
    private InstEmplBankInfoRepository instEmplBankInfoRepository;

    @Inject
    private InstEmplBankInfoSearchRepository instEmplBankInfoSearchRepository;

    /**
     * POST  /instEmplBankInfos -> Create a new instEmplBankInfo.
     */
    @RequestMapping(value = "/instEmplBankInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplBankInfo> createInstEmplBankInfo(@Valid @RequestBody InstEmplBankInfo instEmplBankInfo) throws URISyntaxException {
        log.debug("REST request to save InstEmplBankInfo : {}", instEmplBankInfo);
        if (instEmplBankInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new instEmplBankInfo cannot already have an ID").body(null);
        }
        InstEmplBankInfo result = instEmplBankInfoRepository.save(instEmplBankInfo);
        instEmplBankInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/instEmplBankInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("instEmplBankInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /instEmplBankInfos -> Updates an existing instEmplBankInfo.
     */
    @RequestMapping(value = "/instEmplBankInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplBankInfo> updateInstEmplBankInfo(@Valid @RequestBody InstEmplBankInfo instEmplBankInfo) throws URISyntaxException {
        log.debug("REST request to update InstEmplBankInfo : {}", instEmplBankInfo);
        if (instEmplBankInfo.getId() == null) {
            return createInstEmplBankInfo(instEmplBankInfo);
        }
        InstEmplBankInfo result = instEmplBankInfoRepository.save(instEmplBankInfo);
        instEmplBankInfoSearchRepository.save(instEmplBankInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("instEmplBankInfo", instEmplBankInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /instEmplBankInfos -> get all the instEmplBankInfos.
     */
    @RequestMapping(value = "/instEmplBankInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<InstEmplBankInfo>> getAllInstEmplBankInfos(Pageable pageable)
        throws URISyntaxException {
        Page<InstEmplBankInfo> page = instEmplBankInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/instEmplBankInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /instEmplBankInfos/:id -> get the "id" instEmplBankInfo.
     */
    @RequestMapping(value = "/instEmplBankInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<InstEmplBankInfo> getInstEmplBankInfo(@PathVariable Long id) {
        log.debug("REST request to get InstEmplBankInfo : {}", id);
        return Optional.ofNullable(instEmplBankInfoRepository.findOne(id))
            .map(instEmplBankInfo -> new ResponseEntity<>(
                instEmplBankInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /instEmplBankInfos/:id -> delete the "id" instEmplBankInfo.
     */
    @RequestMapping(value = "/instEmplBankInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteInstEmplBankInfo(@PathVariable Long id) {
        log.debug("REST request to delete InstEmplBankInfo : {}", id);
        instEmplBankInfoRepository.delete(id);
        instEmplBankInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("instEmplBankInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/instEmplBankInfos/:query -> search for the instEmplBankInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/instEmplBankInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<InstEmplBankInfo> searchInstEmplBankInfos(@PathVariable String query) {
        return StreamSupport
            .stream(instEmplBankInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
