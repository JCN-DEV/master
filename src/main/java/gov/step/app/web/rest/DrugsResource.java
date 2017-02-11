package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Drugs;
import gov.step.app.repository.DrugsRepository;
import gov.step.app.repository.search.DrugsSearchRepository;
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
 * REST controller for managing Drugs.
 */
@RestController
@RequestMapping("/api")
public class DrugsResource {

    private final Logger log = LoggerFactory.getLogger(DrugsResource.class);

    @Inject
    private DrugsRepository drugsRepository;

    @Inject
    private DrugsSearchRepository drugsSearchRepository;

    /**
     * POST  /drugss -> Create a new drugs.
     */
    @RequestMapping(value = "/drugss",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drugs> createDrugs(@RequestBody Drugs drugs) throws URISyntaxException {
        log.debug("REST request to save Drugs : {}", drugs);
        if (drugs.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new drugs cannot already have an ID").body(null);
        }
        Drugs result = drugsRepository.save(drugs);
        drugsSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/drugss/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("drugs", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /drugss -> Updates an existing drugs.
     */
    @RequestMapping(value = "/drugss",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drugs> updateDrugs(@RequestBody Drugs drugs) throws URISyntaxException {
        log.debug("REST request to update Drugs : {}", drugs);
        if (drugs.getId() == null) {
            return createDrugs(drugs);
        }
        Drugs result = drugsRepository.save(drugs);
        drugsSearchRepository.save(drugs);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("drugs", drugs.getId().toString()))
            .body(result);
    }

    /**
     * GET  /drugss -> get all the drugss.
     */
    @RequestMapping(value = "/drugss",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Drugs>> getAllDrugss(Pageable pageable)
        throws URISyntaxException {
        Page<Drugs> page = drugsRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/drugss");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /drugss/:id -> get the "id" drugs.
     */
    @RequestMapping(value = "/drugss/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Drugs> getDrugs(@PathVariable Long id) {
        log.debug("REST request to get Drugs : {}", id);
        return Optional.ofNullable(drugsRepository.findOne(id))
            .map(drugs -> new ResponseEntity<>(
                drugs,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /drugss/:id -> delete the "id" drugs.
     */
    @RequestMapping(value = "/drugss/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDrugs(@PathVariable Long id) {
        log.debug("REST request to delete Drugs : {}", id);
        drugsRepository.delete(id);
        drugsSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("drugs", id.toString())).build();
    }

    /**
     * SEARCH  /_search/drugss/:query -> search for the drugs corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/drugss/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Drugs> searchDrugss(@PathVariable String query) {
        return StreamSupport
            .stream(drugsSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
