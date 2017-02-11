package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainingCategorySetup;
import gov.step.app.repository.TrainingCategorySetupRepository;
import gov.step.app.repository.UserRepository;
import gov.step.app.repository.search.TrainingCategorySetupSearchRepository;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TrainingCategorySetup.
 */
@RestController
@RequestMapping("/api")
public class TrainingCategorySetupResource {

    private final Logger log = LoggerFactory.getLogger(TrainingCategorySetupResource.class);

    @Inject
    private TrainingCategorySetupRepository trainingCategorySetupRepository;

    @Inject
    private TrainingCategorySetupSearchRepository trainingCategorySetupSearchRepository;

    @Inject
    private UserRepository userRepository;

    /**
     * POST  /trainingCategorySetups -> Create a new trainingCategorySetup.
     */
    @RequestMapping(value = "/trainingCategorySetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingCategorySetup> createTrainingCategorySetup(@Valid @RequestBody TrainingCategorySetup trainingCategorySetup) throws URISyntaxException {
        log.debug("REST request to save TrainingCategorySetup : {}", trainingCategorySetup);
        if (trainingCategorySetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingCategorySetup cannot already have an ID").body(null);
        }
        trainingCategorySetup.setCreateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        trainingCategorySetup.setCreateDate(LocalDate.now());
        trainingCategorySetup.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        trainingCategorySetup.setUpdateDate(LocalDate.now());

        TrainingCategorySetup result = trainingCategorySetupRepository.save(trainingCategorySetup);
        trainingCategorySetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingCategorySetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingCategorySetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingCategorySetups -> Updates an existing trainingCategorySetup.
     */
    @RequestMapping(value = "/trainingCategorySetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingCategorySetup> updateTrainingCategorySetup(@Valid @RequestBody TrainingCategorySetup trainingCategorySetup) throws URISyntaxException {
        log.debug("REST request to update TrainingCategorySetup : {}", trainingCategorySetup);
        if (trainingCategorySetup.getId() == null) {
            return createTrainingCategorySetup(trainingCategorySetup);
        }
        trainingCategorySetup.setUpdateBy(userRepository.findOne(SecurityUtils.getCurrentUserId()));
        trainingCategorySetup.setUpdateDate(LocalDate.now());
        TrainingCategorySetup result = trainingCategorySetupRepository.save(trainingCategorySetup);
        trainingCategorySetupSearchRepository.save(trainingCategorySetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingCategorySetup", trainingCategorySetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingCategorySetups -> get all the trainingCategorySetups.
     */
    @RequestMapping(value = "/trainingCategorySetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingCategorySetup>> getAllTrainingCategorySetups(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingCategorySetup> page = trainingCategorySetupRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingCategorySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingCategorySetups/:id -> get the "id" trainingCategorySetup.
     */
    @RequestMapping(value = "/trainingCategorySetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingCategorySetup> getTrainingCategorySetup(@PathVariable Long id) {
        log.debug("REST request to get TrainingCategorySetup : {}", id);
        return Optional.ofNullable(trainingCategorySetupRepository.findOne(id))
            .map(trainingCategorySetup -> new ResponseEntity<>(
                trainingCategorySetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingCategorySetups/:id -> delete the "id" trainingCategorySetup.
     */
    @RequestMapping(value = "/trainingCategorySetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingCategorySetup(@PathVariable Long id) {
        log.debug("REST request to delete TrainingCategorySetup : {}", id);
        trainingCategorySetupRepository.delete(id);
        trainingCategorySetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingCategorySetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainingCategorySetups/:query -> search for the trainingCategorySetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingCategorySetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingCategorySetup> searchTrainingCategorySetups(@PathVariable String query) {
        return StreamSupport
            .stream(trainingCategorySetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    /* Check Training Category Name is unique  */

    @RequestMapping(value = "/trainingCategorySetups/isCategoryNameUnique/",
                    method = RequestMethod.GET,
                    produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> isCategoryNameUnique(@RequestParam String value){

        log.debug("REST request to isCategoryNameUnique : {}",value);
        Optional<TrainingCategorySetup> trainingCategorySetup =  trainingCategorySetupRepository.findByCategoryName(value);
        Map mapValue = new HashMap();
        mapValue.put("value",value);

        if(Optional.empty().equals(trainingCategorySetup)){
            mapValue.put("isValid",true);
            return new ResponseEntity<Map>(mapValue,HttpStatus.OK);
        }else {
            mapValue.put("isValid",false);
            return new ResponseEntity<Map>(mapValue,HttpStatus.OK);
        }
    }

    /**
     * GET  /trainingCategorySetups -> get all the trainingCategorySetups By Status.
     */
    @RequestMapping(value = "/trainingCategorySetups/findAllByStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingCategorySetup>> getTrainingCategoryByStatus(@PathVariable Boolean status,Pageable pageable)
        throws URISyntaxException {
        Page<TrainingCategorySetup> page = trainingCategorySetupRepository.findAllByStatus(status,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingCategorySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

}
