package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.Training;
import gov.step.app.repository.TrainingRepository;
import gov.step.app.repository.search.TrainingSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
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
 * REST controller for managing Training.
 */
@RestController
@RequestMapping("/api")
public class TrainingResource {

    private final Logger log = LoggerFactory.getLogger(TrainingResource.class);

    @Inject
    private TrainingRepository trainingRepository;

    @Inject
    private TrainingSearchRepository trainingSearchRepository;

    /**
     * POST  /trainings -> Create a new training.
     */
    @RequestMapping(value = "/trainings",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Training> createTraining(@Valid @RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to save Training : {}", training);
        if (training.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new training cannot already have an ID").body(null);
        }
        Training result = trainingRepository.save(training);
        trainingSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("training", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainings -> Updates an existing training.
     */
    @RequestMapping(value = "/trainings",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Training> updateTraining(@Valid @RequestBody Training training) throws URISyntaxException {
        log.debug("REST request to update Training : {}", training);
        if (training.getId() == null) {
            return createTraining(training);
        }
        Training result = trainingRepository.save(training);
        trainingSearchRepository.save(training);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("training", training.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainings -> get all the trainings.
     */
    @RequestMapping(value = "/trainings",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Training>> getAllTrainings(Pageable pageable)
        throws URISyntaxException {
        Page<Training> page = trainingRepository.findByEmployeeUserIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MANAGER))
            page = trainingRepository.findByManagerIsCurrentUser(pageable);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN))
            page = trainingRepository.findAll(pageable);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainings");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainings/employee/:id -> get all training by employee
     */
    @RequestMapping(value = "/trainings/employee/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Training> getAllTrainingByEmployee(@PathVariable Long id)
        throws URISyntaxException {
        List<Training> trainings = trainingRepository.findByEmployee(id);
        return trainings;
    }

    /**
     * GET  /trainings/:id -> get the "id" training.
     */
    @RequestMapping(value = "/trainings/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Training> getTraining(@PathVariable Long id) {
        log.debug("REST request to get Training : {}", id);
        return Optional.ofNullable(trainingRepository.findOne(id))
            .map(training -> new ResponseEntity<>(
                training,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainings/:id -> delete the "id" training.
     */
    @RequestMapping(value = "/trainings/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTraining(@PathVariable Long id) {
        log.debug("REST request to delete Training : {}", id);
        trainingRepository.delete(id);
        trainingSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("training", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainings/:query -> search for the training corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainings/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Training> searchTrainings(@PathVariable String query) {
        return StreamSupport
            .stream(trainingSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


}
