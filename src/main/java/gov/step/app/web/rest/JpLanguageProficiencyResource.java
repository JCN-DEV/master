package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpEmploymentHistory;
import gov.step.app.domain.JpLanguageProficiency;
import gov.step.app.repository.JpLanguageProficiencyRepository;
import gov.step.app.repository.search.JpLanguageProficiencySearchRepository;
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
 * REST controller for managing JpLanguageProficiency.
 */
@RestController
@RequestMapping("/api")
public class JpLanguageProficiencyResource {

    private final Logger log = LoggerFactory.getLogger(JpLanguageProficiencyResource.class);

    @Inject
    private JpLanguageProficiencyRepository jpLanguageProficiencyRepository;

    @Inject
    private JpLanguageProficiencySearchRepository jpLanguageProficiencySearchRepository;

    /**
     * POST  /jpLanguageProficiencys -> Create a new jpLanguageProficiency.
     */
    @RequestMapping(value = "/jpLanguageProficiencys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpLanguageProficiency> createJpLanguageProficiency(@Valid @RequestBody JpLanguageProficiency jpLanguageProficiency) throws URISyntaxException {
        log.debug("REST request to save JpLanguageProficiency : {}", jpLanguageProficiency);
        if (jpLanguageProficiency.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpLanguageProficiency cannot already have an ID").body(null);
        }
        JpLanguageProficiency result = jpLanguageProficiencyRepository.save(jpLanguageProficiency);
        jpLanguageProficiencySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpLanguageProficiencys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpLanguageProficiency", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpLanguageProficiencys -> Updates an existing jpLanguageProficiency.
     */
    @RequestMapping(value = "/jpLanguageProficiencys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpLanguageProficiency> updateJpLanguageProficiency(@Valid @RequestBody JpLanguageProficiency jpLanguageProficiency) throws URISyntaxException {
        log.debug("REST request to update JpLanguageProficiency : {}", jpLanguageProficiency);
        if (jpLanguageProficiency.getId() == null) {
            return createJpLanguageProficiency(jpLanguageProficiency);
        }
        JpLanguageProficiency result = jpLanguageProficiencyRepository.save(jpLanguageProficiency);
        jpLanguageProficiencySearchRepository.save(jpLanguageProficiency);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpLanguageProficiency", jpLanguageProficiency.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpLanguageProficiencys -> get all the jpLanguageProficiencys.
     */
    @RequestMapping(value = "/jpLanguageProficiencys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpLanguageProficiency>> getAllJpLanguageProficiencys(Pageable pageable)
        throws URISyntaxException {
        Page<JpLanguageProficiency> page = jpLanguageProficiencyRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpLanguageProficiencys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpLanguageProficiencys/:id -> get the "id" jpLanguageProficiency.
     */
    @RequestMapping(value = "/jpLanguageProficiencys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpLanguageProficiency> getJpLanguageProficiency(@PathVariable Long id) {
        log.debug("REST request to get JpLanguageProficiency : {}", id);
        return Optional.ofNullable(jpLanguageProficiencyRepository.findOne(id))
            .map(jpLanguageProficiency -> new ResponseEntity<>(
                jpLanguageProficiency,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpLanguageProficiencys/:id -> delete the "id" jpLanguageProficiency.
     */
    @RequestMapping(value = "/jpLanguageProficiencys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpLanguageProficiency(@PathVariable Long id) {
        log.debug("REST request to delete JpLanguageProficiency : {}", id);
        jpLanguageProficiencyRepository.delete(id);
        jpLanguageProficiencySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpLanguageProficiency", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpLanguageProficiencys/:query -> search for the jpLanguageProficiency corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpLanguageProficiencys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpLanguageProficiency> searchJpLanguageProficiencys(@PathVariable String query) {
        return StreamSupport
            .stream(jpLanguageProficiencySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/languageProficencies/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpLanguageProficiency> getAllLanguageProficiencyByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpLanguageProficiency> languageProficiencies = jpLanguageProficiencyRepository.findByJpEmployee(id);
        return  languageProficiencies;
    }
}
