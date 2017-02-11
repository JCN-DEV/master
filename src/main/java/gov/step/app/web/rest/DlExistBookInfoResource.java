package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlExistBookInfo;
import gov.step.app.repository.DlExistBookInfoRepository;
import gov.step.app.repository.search.DlExistBookInfoSearchRepository;
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
 * REST controller for managing DlExistBookInfo.
 */
@RestController
@RequestMapping("/api")
public class DlExistBookInfoResource {

    private final Logger log = LoggerFactory.getLogger(DlExistBookInfoResource.class);

    @Inject
    private DlExistBookInfoRepository dlExistBookInfoRepository;

    @Inject
    private DlExistBookInfoSearchRepository dlExistBookInfoSearchRepository;

    /**
     * POST  /dlExistBookInfos -> Create a new dlExistBookInfo.
     */
    @RequestMapping(value = "/dlExistBookInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistBookInfo> createDlExistBookInfo(@Valid @RequestBody DlExistBookInfo dlExistBookInfo) throws URISyntaxException {
        log.debug("REST request to save DlExistBookInfo : {}", dlExistBookInfo);
        if (dlExistBookInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlExistBookInfo cannot already have an ID").body(null);
        }
        DlExistBookInfo result = dlExistBookInfoRepository.save(dlExistBookInfo);
        dlExistBookInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlExistBookInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlExistBookInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlExistBookInfos -> Updates an existing dlExistBookInfo.
     */
    @RequestMapping(value = "/dlExistBookInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistBookInfo> updateDlExistBookInfo(@Valid @RequestBody DlExistBookInfo dlExistBookInfo) throws URISyntaxException {
        log.debug("REST request to update DlExistBookInfo : {}", dlExistBookInfo);
        if (dlExistBookInfo.getId() == null) {
            return createDlExistBookInfo(dlExistBookInfo);
        }
        DlExistBookInfo result = dlExistBookInfoRepository.save(dlExistBookInfo);
        dlExistBookInfoSearchRepository.save(dlExistBookInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlExistBookInfo", dlExistBookInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlExistBookInfos -> get all the dlExistBookInfos.
     */
    @RequestMapping(value = "/dlExistBookInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlExistBookInfo>> getAllDlExistBookInfos(Pageable pageable)
        throws URISyntaxException {
        Page<DlExistBookInfo> page = dlExistBookInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlExistBookInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlExistBookInfos/:id -> get the "id" dlExistBookInfo.
     */
    @RequestMapping(value = "/dlExistBookInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlExistBookInfo> getDlExistBookInfo(@PathVariable Long id) {
        log.debug("REST request to get DlExistBookInfo : {}", id);
        return Optional.ofNullable(dlExistBookInfoRepository.findOne(id))
            .map(dlExistBookInfo -> new ResponseEntity<>(
                dlExistBookInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlExistBookInfos/:id -> delete the "id" dlExistBookInfo.
     */
    @RequestMapping(value = "/dlExistBookInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlExistBookInfo(@PathVariable Long id) {
        log.debug("REST request to delete DlExistBookInfo : {}", id);
        dlExistBookInfoRepository.delete(id);
        dlExistBookInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlExistBookInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlExistBookInfos/:query -> search for the dlExistBookInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlExistBookInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlExistBookInfo> searchDlExistBookInfos(@PathVariable String query) {
        return StreamSupport
            .stream(dlExistBookInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
