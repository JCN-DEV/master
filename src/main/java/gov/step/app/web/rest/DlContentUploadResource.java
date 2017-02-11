package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlContentUpload;
import gov.step.app.repository.DlContentUploadRepository;
import gov.step.app.repository.search.DlContentUploadSearchRepository;
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
 * REST controller for managing DlContentUpload.
 */
@RestController
@RequestMapping("/api")
public class DlContentUploadResource {

    private final Logger log = LoggerFactory.getLogger(DlContentUploadResource.class);

    @Inject
    private DlContentUploadRepository dlContentUploadRepository;

    @Inject
    private DlContentUploadSearchRepository dlContentUploadSearchRepository;

    /**
     * POST  /dlContentUploads -> Create a new dlContentUpload.
     */
    @RequestMapping(value = "/dlContentUploads",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentUpload> createDlContentUpload(@Valid @RequestBody DlContentUpload dlContentUpload) throws URISyntaxException {
        log.debug("REST request to save DlContentUpload : {}", dlContentUpload);
        if (dlContentUpload.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlContentUpload cannot already have an ID").body(null);
        }
        DlContentUpload result = dlContentUploadRepository.save(dlContentUpload);
        dlContentUploadSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlContentUploads/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlContentUpload", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlContentUploads -> Updates an existing dlContentUpload.
     */
    @RequestMapping(value = "/dlContentUploads",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentUpload> updateDlContentUpload(@Valid @RequestBody DlContentUpload dlContentUpload) throws URISyntaxException {
        log.debug("REST request to update DlContentUpload : {}", dlContentUpload);
        if (dlContentUpload.getId() == null) {
            return createDlContentUpload(dlContentUpload);
        }
        DlContentUpload result = dlContentUploadRepository.save(dlContentUpload);
        dlContentUploadSearchRepository.save(dlContentUpload);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlContentUpload", dlContentUpload.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlContentUploads -> get all the dlContentUploads.
     */
    @RequestMapping(value = "/dlContentUploads",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlContentUpload>> getAllDlContentUploads(Pageable pageable)
        throws URISyntaxException {
        Page<DlContentUpload> page = dlContentUploadRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlContentUploads");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlContentUploads/:id -> get the "id" dlContentUpload.
     */
    @RequestMapping(value = "/dlContentUploads/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlContentUpload> getDlContentUpload(@PathVariable Long id) {
        log.debug("REST request to get DlContentUpload : {}", id);
        return Optional.ofNullable(dlContentUploadRepository.findOne(id))
            .map(dlContentUpload -> new ResponseEntity<>(
                dlContentUpload,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlContentUploads/:id -> delete the "id" dlContentUpload.
     */
    @RequestMapping(value = "/dlContentUploads/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlContentUpload(@PathVariable Long id) {
        log.debug("REST request to delete DlContentUpload : {}", id);
        dlContentUploadRepository.delete(id);
        dlContentUploadSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlContentUpload", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlContentUploads/:query -> search for the dlContentUpload corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlContentUploads/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlContentUpload> searchDlContentUploads(@PathVariable String query) {
        return StreamSupport
            .stream(dlContentUploadSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
