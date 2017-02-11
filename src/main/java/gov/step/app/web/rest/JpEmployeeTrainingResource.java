package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.JpEmployeeReference;
import gov.step.app.domain.JpEmployeeTraining;
import gov.step.app.repository.JpEmployeeTrainingRepository;
import gov.step.app.repository.search.JpEmployeeTrainingSearchRepository;
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
 * REST controller for managing JpEmployeeTraining.
 */
@RestController
@RequestMapping("/api")
public class JpEmployeeTrainingResource {

    private final Logger log = LoggerFactory.getLogger(JpEmployeeTrainingResource.class);

    @Inject
    private JpEmployeeTrainingRepository jpEmployeeTrainingRepository;

    @Inject
    private JpEmployeeTrainingSearchRepository jpEmployeeTrainingSearchRepository;

    /**
     * POST  /jpEmployeeTrainings -> Create a new jpEmployeeTraining.
     */
    @RequestMapping(value = "/jpEmployeeTrainings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeTraining> createJpEmployeeTraining(@Valid @RequestBody JpEmployeeTraining jpEmployeeTraining) throws URISyntaxException {
        log.debug("REST request to save JpEmployeeTraining : {}", jpEmployeeTraining);
        if (jpEmployeeTraining.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new jpEmployeeTraining cannot already have an ID").body(null);
        }
        JpEmployeeTraining result = jpEmployeeTrainingRepository.save(jpEmployeeTraining);
        jpEmployeeTrainingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/jpEmployeeTrainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("jpEmployeeTraining", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /jpEmployeeTrainings -> Updates an existing jpEmployeeTraining.
     */
    @RequestMapping(value = "/jpEmployeeTrainings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeTraining> updateJpEmployeeTraining(@Valid @RequestBody JpEmployeeTraining jpEmployeeTraining) throws URISyntaxException {
        log.debug("REST request to update JpEmployeeTraining : {}", jpEmployeeTraining);
        if (jpEmployeeTraining.getId() == null) {
            return createJpEmployeeTraining(jpEmployeeTraining);
        }
        JpEmployeeTraining result = jpEmployeeTrainingRepository.save(jpEmployeeTraining);
        jpEmployeeTrainingSearchRepository.save(jpEmployeeTraining);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("jpEmployeeTraining", jpEmployeeTraining.getId().toString()))
            .body(result);
    }

    /**
     * GET  /jpEmployeeTrainings -> get all the jpEmployeeTrainings.
     */
    @RequestMapping(value = "/jpEmployeeTrainings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<JpEmployeeTraining>> getAllJpEmployeeTrainings(Pageable pageable)
        throws URISyntaxException {
        Page<JpEmployeeTraining> page = jpEmployeeTrainingRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/jpEmployeeTrainings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /jpEmployeeTrainings/:id -> get the "id" jpEmployeeTraining.
     */
    @RequestMapping(value = "/jpEmployeeTrainings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<JpEmployeeTraining> getJpEmployeeTraining(@PathVariable Long id) {
        log.debug("REST request to get JpEmployeeTraining : {}", id);
        return Optional.ofNullable(jpEmployeeTrainingRepository.findOne(id))
            .map(jpEmployeeTraining -> new ResponseEntity<>(
                jpEmployeeTraining,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /jpEmployeeTrainings/:id -> delete the "id" jpEmployeeTraining.
     */
    @RequestMapping(value = "/jpEmployeeTrainings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteJpEmployeeTraining(@PathVariable Long id) {
        log.debug("REST request to delete JpEmployeeTraining : {}", id);
        jpEmployeeTrainingRepository.delete(id);
        jpEmployeeTrainingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("jpEmployeeTraining", id.toString())).build();
    }

    /**
     * SEARCH  /_search/jpEmployeeTrainings/:query -> search for the jpEmployeeTraining corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/jpEmployeeTrainings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeTraining> searchJpEmployeeTrainings(@PathVariable String query) {
        return StreamSupport
            .stream(jpEmployeeTrainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/trainings/jpEmployee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<JpEmployeeTraining> getAllEmployeeTrainingsByJpEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<JpEmployeeTraining> experiences = jpEmployeeTrainingRepository.findByJpEmployee(id);
        return  experiences;
    }
}
