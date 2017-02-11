package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Result;
import gov.step.app.repository.ResultRepository;
import gov.step.app.repository.search.ResultSearchRepository;
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

import static org.elasticsearch.index.query.QueryBuilders.queryStringQuery;

/**
 * REST controller for managing Result.
 */
@RestController
@RequestMapping("/api")
public class ResultResource {

    private final Logger log = LoggerFactory.getLogger(ResultResource.class);

    @Inject
    private ResultRepository resultRepository;

    @Inject
    private ResultSearchRepository resultSearchRepository;

    /**
     * POST /results -> Create a new result.
     */
    @RequestMapping(value = "/results", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Result> createResult(@Valid @RequestBody Result result) throws URISyntaxException {
        log.debug("REST request to save Result : {}", result);
        if (result.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new result cannot already have an ID").body(null);
        }
        Result item = resultRepository.save(result);
        resultSearchRepository.save(item);
        return ResponseEntity.created(new URI("/api/results/" + item.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("result", item.getId().toString())).body(item);
    }

    /**
     * PUT /results -> Updates an existing result.
     */
    @RequestMapping(value = "/results", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Result> updateResult(@Valid @RequestBody Result result) throws URISyntaxException {
        log.debug("REST request to update Result : {}", result);
        if (result.getId() == null) {
            return createResult(result);
        }
        Result item = resultRepository.save(result);
        resultSearchRepository.save(item);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityUpdateAlert("result", item.getId().toString()))
            .body(item);
    }

    /**
     * GET /results -> get all the results.
     */
    @RequestMapping(value = "/results", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Result>> getAllResults(Pageable pageable) throws URISyntaxException {
        Page<Result> page = resultRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/results");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /results/:id -> get the "id" result.
     */
    @RequestMapping(value = "/results/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Result> getResult(@PathVariable Long id) {
        log.debug("REST request to get Result : {}", id);
        return Optional.ofNullable(resultRepository.findOne(id))
            .map(result -> new ResponseEntity<>(result, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /results/:id -> delete the "id" result.
     */
    @RequestMapping(value = "/results/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteResult(@PathVariable Long id) {
        log.debug("REST request to delete Result : {}", id);
        resultRepository.delete(id);
        resultSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("result", id.toString())).build();
    }

    /**
     * SEARCH /_search/results/:query -> search for the result corresponding to
     * the query.
     */
    @RequestMapping(value = "/_search/results/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Result> searchResults(@PathVariable String query) {
        return StreamSupport.stream(resultSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
