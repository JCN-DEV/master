package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpExperienceCategory;
import gov.step.app.repository.JpExperienceCategoryRepository;
import gov.step.app.repository.search.JpExperienceCategorySearchRepository;
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
 * REST controller for managing JpExperienceCategory.
 */
@RestController
@RequestMapping("/api")
public class JpExperienceCategoryResource {

    private final Logger log = LoggerFactory.getLogger(JpExperienceCategoryResource.class);

    @Inject
    private JpExperienceCategoryRepository jpExperienceCategoryRepository;

    @Inject
    private JpExperienceCategorySearchRepository jpExperienceCategorySearchRepository;

    /**
     * POST  /jpExperienceCategorys -> Create a new jpExperienceCategory.
     */
    @RequestMapping(value = "/jpExperienceCategorys",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpExperienceCategory> createJpExperienceCategory(@Valid @RequestBody JpExperienceCategory jpExperienceCategory) throws URISyntaxException {
        log.debug("REST request to save JpExperienceCategory : {}", jpExperienceCategory);
        if (jpExperienceCategory.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpExperienceCategory cannot already have an ID").body(null);
        }
        JpExperienceCategory result = jpExperienceCategoryRepository.save(jpExperienceCategory);
        jpExperienceCategorySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpExperienceCategorys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpExperienceCategory", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpExperienceCategorys -> Updates an existing jpExperienceCategory.
     */
    @RequestMapping(value = "/jpExperienceCategorys",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpExperienceCategory> updateJpExperienceCategory(@Valid @RequestBody JpExperienceCategory jpExperienceCategory) throws URISyntaxException {
        log.debug("REST request to update JpExperienceCategory : {}", jpExperienceCategory);
        if (jpExperienceCategory.getId() == null) {
            return createJpExperienceCategory(jpExperienceCategory);
        }
        JpExperienceCategory result = jpExperienceCategoryRepository.save(jpExperienceCategory);
        jpExperienceCategorySearchRepository.save(jpExperienceCategory);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpExperienceCategory", jpExperienceCategory.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpExperienceCategorys -> get all the jpExperienceCategorys.
     */
    @RequestMapping(value = "/jpExperienceCategorys",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpExperienceCategory>> getAllJpExperienceCategorys(Pageable pageable)
        throws URISyntaxException {
        Page<JpExperienceCategory> page = jpExperienceCategoryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpExperienceCategorys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpExperienceCategorys/:id -> get the "id" jpExperienceCategory.
     */
    @RequestMapping(value = "/jpExperienceCategorys/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpExperienceCategory> getJpExperienceCategory(@PathVariable Long id) {
        log.debug("REST request to get JpExperienceCategory : {}", id);
        return Optional.ofNullable(jpExperienceCategoryRepository.findOne(id))
            .map(jpExperienceCategory -> new ResponseEntity<>(
                jpExperienceCategory,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpExperienceCategorys/:id -> delete the "id" jpExperienceCategory.
     */
    @RequestMapping(value = "/jpExperienceCategorys/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpExperienceCategory(@PathVariable Long id) {
        log.debug("REST request to delete JpExperienceCategory : {}", id);
        jpExperienceCategoryRepository.delete(id);
        jpExperienceCategorySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpExperienceCategory", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpExperienceCategorys/:query -> search for the jpExperienceCategory corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpExperienceCategorys/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpExperienceCategory> searchJpExperienceCategorys(@PathVariable String query) {
        return StreamSupport
            .stream(jpExperienceCategorySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
