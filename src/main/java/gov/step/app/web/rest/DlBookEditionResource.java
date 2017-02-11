package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.DlBookEdition;
import gov.step.app.repository.DlBookEditionRepository;
import gov.step.app.repository.search.DlBookEditionSearchRepository;
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
 * REST controller for managing DlBookEdition.
 */
@RestController
@RequestMapping("/api")
public class DlBookEditionResource {

    private final Logger log = LoggerFactory.getLogger(DlBookEditionResource.class);

    @Inject
    private DlBookEditionRepository dlBookEditionRepository;

    @Inject
    private DlBookEditionSearchRepository dlBookEditionSearchRepository;

    /**
     * POST  /dlBookEditions -> Create a new dlBookEdition.
     */
    @RequestMapping(value = "/dlBookEditions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookEdition> createDlBookEdition(@RequestBody DlBookEdition dlBookEdition) throws URISyntaxException {
        log.debug("REST request to save DlBookEdition : {}", dlBookEdition);
        if (dlBookEdition.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new dlBookEdition cannot already have an ID").body(null);
        }
        DlBookEdition result = dlBookEditionRepository.save(dlBookEdition);
        dlBookEditionSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/dlBookEditions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("dlBookEdition", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /dlBookEditions -> Updates an existing dlBookEdition.
     */
    @RequestMapping(value = "/dlBookEditions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookEdition> updateDlBookEdition(@RequestBody DlBookEdition dlBookEdition) throws URISyntaxException {
        log.debug("REST request to update DlBookEdition : {}", dlBookEdition);
        if (dlBookEdition.getId() == null) {
            return createDlBookEdition(dlBookEdition);
        }
        DlBookEdition result = dlBookEditionRepository.save(dlBookEdition);
        dlBookEditionSearchRepository.save(dlBookEdition);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("dlBookEdition", dlBookEdition.getId().toString()))
            .body(result);
    }

    /**
     * GET  /dlBookEditions -> get all the dlBookEditions.
     */
    @RequestMapping(value = "/dlBookEditions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<DlBookEdition>> getAllDlBookEditions(Pageable pageable)
        throws URISyntaxException {
        Page<DlBookEdition> page = dlBookEditionRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/dlBookEditions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /dlBookEditions/:id -> get the "id" dlBookEdition.
     */
    @RequestMapping(value = "/dlBookEditions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DlBookEdition> getDlBookEdition(@PathVariable Long id) {
        log.debug("REST request to get DlBookEdition : {}", id);
        return Optional.ofNullable(dlBookEditionRepository.findOne(id))
            .map(dlBookEdition -> new ResponseEntity<>(
                dlBookEdition,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /dlBookEditions/:id -> delete the "id" dlBookEdition.
     */
    @RequestMapping(value = "/dlBookEditions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDlBookEdition(@PathVariable Long id) {
        log.debug("REST request to delete DlBookEdition : {}", id);
        dlBookEditionRepository.delete(id);
        dlBookEditionSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("dlBookEdition", id.toString())).build();
    }

    /**
     * SEARCH  /_search/dlBookEditions/:query -> search for the dlBookEdition corresponding
     * to the query.
     */
    @RequestMapping(value = "dlBookEdition/getEditionListById/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookEdition> getEditionListById(@PathVariable Long id) {
        List dlBookEdition = dlBookEditionRepository.getEditionListById(id);
        return dlBookEdition;
    }




    @RequestMapping(value = "/_search/dlBookEditions/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DlBookEdition> searchDlBookEditions(@PathVariable String query) {
        return StreamSupport
            .stream(dlBookEditionSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
