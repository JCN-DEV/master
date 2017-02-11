package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TempInstGenInfo;
import gov.step.app.repository.TempInstGenInfoRepository;
import gov.step.app.repository.search.TempInstGenInfoSearchRepository;
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
 * REST controller for managing TempInstGenInfo.
 */
@RestController
@RequestMapping("/api")
public class TempInstGenInfoResource {

    private final Logger log = LoggerFactory.getLogger(TempInstGenInfoResource.class);

    @Inject
    private TempInstGenInfoRepository tempInstGenInfoRepository;

    @Inject
    private TempInstGenInfoSearchRepository tempInstGenInfoSearchRepository;

    /**
     * POST  /tempInstGenInfos -> Create a new tempInstGenInfo.
     */
    @RequestMapping(value = "/tempInstGenInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempInstGenInfo> createTempInstGenInfo(@Valid @RequestBody TempInstGenInfo tempInstGenInfo) throws URISyntaxException {
        log.debug("REST request to save TempInstGenInfo : {}", tempInstGenInfo);
        if (tempInstGenInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new tempInstGenInfo cannot already have an ID").body(null);
        }
        TempInstGenInfo result = tempInstGenInfoRepository.save(tempInstGenInfo);
        tempInstGenInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/tempInstGenInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("tempInstGenInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /tempInstGenInfos -> Updates an existing tempInstGenInfo.
     */
    @RequestMapping(value = "/tempInstGenInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempInstGenInfo> updateTempInstGenInfo(@Valid @RequestBody TempInstGenInfo tempInstGenInfo) throws URISyntaxException {
        log.debug("REST request to update TempInstGenInfo : {}", tempInstGenInfo);
        if (tempInstGenInfo.getId() == null) {
            return createTempInstGenInfo(tempInstGenInfo);
        }
        TempInstGenInfo result = tempInstGenInfoRepository.save(tempInstGenInfo);
        tempInstGenInfoSearchRepository.save(tempInstGenInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("tempInstGenInfo", tempInstGenInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /tempInstGenInfos -> get all the tempInstGenInfos.
     */
    @RequestMapping(value = "/tempInstGenInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TempInstGenInfo>> getAllTempInstGenInfos(Pageable pageable)
        throws URISyntaxException {
        Page<TempInstGenInfo> page = tempInstGenInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/tempInstGenInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /tempInstGenInfos/:id -> get the "id" tempInstGenInfo.
     */
    @RequestMapping(value = "/tempInstGenInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TempInstGenInfo> getTempInstGenInfo(@PathVariable Long id) {
        log.debug("REST request to get TempInstGenInfo : {}", id);
        return Optional.ofNullable(tempInstGenInfoRepository.findOne(id))
            .map(tempInstGenInfo -> new ResponseEntity<>(
                tempInstGenInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /tempInstGenInfos/:id -> delete the "id" tempInstGenInfo.
     */
    @RequestMapping(value = "/tempInstGenInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTempInstGenInfo(@PathVariable Long id) {
        log.debug("REST request to delete TempInstGenInfo : {}", id);
        tempInstGenInfoRepository.delete(id);
        tempInstGenInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("tempInstGenInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/tempInstGenInfos/:query -> search for the tempInstGenInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/tempInstGenInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TempInstGenInfo> searchTempInstGenInfos(@PathVariable String query) {
        return StreamSupport
            .stream(tempInstGenInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
