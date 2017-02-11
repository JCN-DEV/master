package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookType;
import gov.step.app.repository.DlBookTypeRepository;
import gov.step.app.repository.search.DlBookTypeSearchRepository;
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
 * REST controller for managing DlBookType.
 */
@RestController
@RequestMapping("/api")
public class DlBookTypeResource {

    private final Logger log = LoggerFactory.getLogger(DlBookTypeResource.class);

    @Inject
    private DlBookTypeRepository dlBookTypeRepository;

    @Inject
    private DlBookTypeSearchRepository dlBookTypeSearchRepository;

    /**
     * POST  /dlBookTypes -> Create a new dlBookType.
     */
    @RequestMapping(value = "/dlBookTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookType> createDlBookType(@Valid @RequestBody DlBookType dlBookType) throws URISyntaxException {
        log.debug("REST request to save DlBookType : {}", dlBookType);
        if (dlBookType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookType cannot already have an ID").body(null);
        }
        DlBookType result = dlBookTypeRepository.save(dlBookType);
        dlBookTypeSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookTypes -> Updates an existing dlBookType.
     */
    @RequestMapping(value = "/dlBookTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookType> updateDlBookType(@Valid @RequestBody DlBookType dlBookType) throws URISyntaxException {
        log.debug("REST request to update DlBookType : {}", dlBookType);
        if (dlBookType.getId() == null) {
            return createDlBookType(dlBookType);
        }
        DlBookType result = dlBookTypeRepository.save(dlBookType);
        dlBookTypeSearchRepository.save(dlBookType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookType", dlBookType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookTypes -> get all the dlBookTypes.
     */
    @RequestMapping(value = "/dlBookTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookType>> getAllDlBookTypes(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookType> page = dlBookTypeRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookTypes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlBookTypes/:id -> get the "id" dlBookType.
     */
    @RequestMapping(value = "/dlBookTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookType> getDlBookType(@PathVariable Long id) {
        log.debug("REST request to get DlBookType : {}", id);
        return Optional.ofNullable(dlBookTypeRepository.findOne(id))
            .map(dlBookType -> new ResponseEntity<>(
                dlBookType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlBookTypes/:id -> delete the "id" dlBookType.
     */
    @RequestMapping(value = "/dlBookTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookType(@PathVariable Long id) {
        log.debug("REST request to delete DlBookType : {}", id);
        dlBookTypeRepository.delete(id);
        dlBookTypeSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookType", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookTypes/:query -> search for the dlBookType corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/dlBookTypes/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookType> searchDlBookTypes(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
