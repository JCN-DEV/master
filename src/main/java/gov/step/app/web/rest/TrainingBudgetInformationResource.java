package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainingBudgetInformation;
import gov.step.app.repository.TrainingBudgetInformationRepository;
import gov.step.app.repository.search.TrainingBudgetInformationSearchRepository;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TrainingBudgetInformation.
 */
@RestController
@RequestMapping("/api")
public class TrainingBudgetInformationResource {

    private final Logger log = LoggerFactory.getLogger(TrainingBudgetInformationResource.class);

    @Inject
    private TrainingBudgetInformationRepository trainingBudgetInformationRepository;

    @Inject
    private TrainingBudgetInformationSearchRepository trainingBudgetInformationSearchRepository;

    /**
     * POST  /trainingBudgetInformations -> Create a new trainingBudgetInformation.
     */
    @RequestMapping(value = "/trainingBudgetInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingBudgetInformation> createTrainingBudgetInformation(@Valid @RequestBody TrainingBudgetInformation trainingBudgetInformation) throws URISyntaxException {
        log.debug("REST request to save TrainingBudgetInformation : {}", trainingBudgetInformation);
        if (trainingBudgetInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingBudgetInformation cannot already have an ID").body(null);
        }
        trainingBudgetInformation.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingBudgetInformation.setCreateDate(LocalDate.now());
        trainingBudgetInformation.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainingBudgetInformation.setUpdateDate(LocalDate.now());

        TrainingBudgetInformation result = trainingBudgetInformationRepository.save(trainingBudgetInformation);
        trainingBudgetInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingBudgetInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingBudgetInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingBudgetInformations -> Updates an existing trainingBudgetInformation.
     */
    @RequestMapping(value = "/trainingBudgetInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingBudgetInformation> updateTrainingBudgetInformation(@Valid @RequestBody TrainingBudgetInformation trainingBudgetInformation) throws URISyntaxException {
        log.debug("REST request to update TrainingBudgetInformation : {}", trainingBudgetInformation);
        if (trainingBudgetInformation.getId() == null) {
            return createTrainingBudgetInformation(trainingBudgetInformation);
        }
        trainingBudgetInformation.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingBudgetInformation.setCreateDate(LocalDate.now());
        trainingBudgetInformation.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainingBudgetInformation.setUpdateDate(LocalDate.now());

        TrainingBudgetInformation result = trainingBudgetInformationRepository.save(trainingBudgetInformation);
        trainingBudgetInformationSearchRepository.save(trainingBudgetInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingBudgetInformation", trainingBudgetInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingBudgetInformations -> get all the trainingBudgetInformations.
     */
    @RequestMapping(value = "/trainingBudgetInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingBudgetInformation>> getAllTrainingBudgetInformations(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingBudgetInformation> page = trainingBudgetInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingBudgetInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingBudgetInformations/:id -> get the "id" trainingBudgetInformation.
     */
    @RequestMapping(value = "/trainingBudgetInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingBudgetInformation> getTrainingBudgetInformation(@PathVariable Long id) {
        log.debug("REST request to get TrainingBudgetInformation : {}", id);
        return Optional.ofNullable(trainingBudgetInformationRepository.findOne(id))
            .map(trainingBudgetInformation -> new ResponseEntity<>(
                trainingBudgetInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingBudgetInformations/:id -> delete the "id" trainingBudgetInformation.
     */
    @RequestMapping(value = "/trainingBudgetInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingBudgetInformation(@PathVariable Long id) {
        log.debug("REST request to delete TrainingBudgetInformation : {}", id);
        trainingBudgetInformationRepository.delete(id);
        trainingBudgetInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingBudgetInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainingBudgetInformations/:query -> search for the trainingBudgetInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingBudgetInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingBudgetInformation> searchTrainingBudgetInformations(@PathVariable String query) {
        return StreamSupport
            .stream(trainingBudgetInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/trainingBudgetInformations/byInitializeId/{initializeId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingBudgetInformation> getBudgetInformationByInitializeId(@PathVariable Long initializeId) {
        log.debug("REST request to get TrainingBudgetInformation By Initialize Id : {}", initializeId);
//        TrainingBudgetInformation trainingBudgetInformation = null ;
//        trainingBudgetInformation = trainingBudgetInformationRepository.findByInitializeId(initializeId);
        return Optional.ofNullable(trainingBudgetInformationRepository.findByInitializeId(initializeId))
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
