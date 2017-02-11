package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainingSummary;
import gov.step.app.repository.TrainingSummaryRepository;
import gov.step.app.repository.search.TrainingSummarySearchRepository;
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
 * REST controller for managing TrainingSummary.
 */
@RestController
@RequestMapping("/api")
public class TrainingSummaryResource {

    private final Logger log = LoggerFactory.getLogger(TrainingSummaryResource.class);

    @Inject
    private TrainingSummaryRepository trainingSummaryRepository;

    @Inject
    private TrainingSummarySearchRepository trainingSummarySearchRepository;

    /**
     * POST /trainingSummarys -> Create a new trainingSummary.
     */
    @RequestMapping(value = "/trainingSummarys", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingSummary> createTrainingSummary(@Valid @RequestBody TrainingSummary trainingSummary)
        throws URISyntaxException {
        log.debug("REST request to save TrainingSummary : {}", trainingSummary);
        if (trainingSummary.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingSummary cannot already have an ID")
                .body(null);
        }
        TrainingSummary result = trainingSummaryRepository.save(trainingSummary);
        trainingSummarySearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingSummarys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingSummary", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT /trainingSummarys -> Updates an existing trainingSummary.
     */
    @RequestMapping(value = "/trainingSummarys", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingSummary> updateTrainingSummary(@Valid @RequestBody TrainingSummary trainingSummary)
        throws URISyntaxException {
        log.debug("REST request to update TrainingSummary : {}", trainingSummary);
        if (trainingSummary.getId() == null) {
            return createTrainingSummary(trainingSummary);
        }
        TrainingSummary result = trainingSummaryRepository.save(trainingSummary);
        trainingSummarySearchRepository.save(trainingSummary);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingSummary", trainingSummary.getId().toString()))
            .body(result);
    }

    /**
     * GET /trainingSummarys -> get all the trainingSummarys.
     */
    @RequestMapping(value = "/trainingSummarys", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingSummary>> getAllTrainingSummarys(Pageable pageable) throws URISyntaxException {
        Page<TrainingSummary> page = trainingSummaryRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingSummarys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET /trainingSummarys/:id -> get the "id" trainingSummary.
     */
    @RequestMapping(value = "/trainingSummarys/{id}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingSummary> getTrainingSummary(@PathVariable Long id) {
        log.debug("REST request to get TrainingSummary : {}", id);
        return Optional.ofNullable(trainingSummaryRepository.findOne(id))
            .map(trainingSummary -> new ResponseEntity<>(trainingSummary, HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE /trainingSummarys/:id -> delete the "id" trainingSummary.
     */
    @RequestMapping(value = "/trainingSummarys/{id}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingSummary(@PathVariable Long id) {
        log.debug("REST request to delete TrainingSummary : {}", id);
        trainingSummaryRepository.delete(id);
        trainingSummarySearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingSummary", id.toString()))
            .build();
    }

    /**
     * SEARCH /_search/trainingSummarys/:query -> search for the trainingSummary
     * corresponding to the query.
     */
    @RequestMapping(value = "/_search/trainingSummarys/{query}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingSummary> searchTrainingSummarys(@PathVariable String query) {
        return StreamSupport
            .stream(trainingSummarySearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
