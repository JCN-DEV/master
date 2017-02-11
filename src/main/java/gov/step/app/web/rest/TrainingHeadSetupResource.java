package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainingHeadSetup;
import gov.step.app.repository.TrainingHeadSetupRepository;
import gov.step.app.repository.search.TrainingHeadSetupSearchRepository;
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
 * REST controller for managing TrainingHeadSetup.
 */
@RestController
@RequestMapping("/api")
public class TrainingHeadSetupResource {

    private final Logger log = LoggerFactory.getLogger(TrainingHeadSetupResource.class);

    @Inject
    private TrainingHeadSetupRepository trainingHeadSetupRepository;

    @Inject
    private TrainingHeadSetupSearchRepository trainingHeadSetupSearchRepository;

    /**
     * POST  /trainingHeadSetups -> Create a new trainingHeadSetup.
     */
    @RequestMapping(value = "/trainingHeadSetups",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingHeadSetup> createTrainingHeadSetup(@Valid @RequestBody TrainingHeadSetup trainingHeadSetup) throws URISyntaxException {
        log.debug("REST request to save TrainingHeadSetup : {}", trainingHeadSetup);
        if (trainingHeadSetup.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingHeadSetup cannot already have an ID").body(null);
        }
        trainingHeadSetup.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingHeadSetup.setCreateDate(LocalDate.now());

        TrainingHeadSetup result = trainingHeadSetupRepository.save(trainingHeadSetup);
        trainingHeadSetupSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingHeadSetups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingHeadSetup", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingHeadSetups -> Updates an existing trainingHeadSetup.
     */
    @RequestMapping(value = "/trainingHeadSetups",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingHeadSetup> updateTrainingHeadSetup(@Valid @RequestBody TrainingHeadSetup trainingHeadSetup) throws URISyntaxException {
        log.debug("REST request to update TrainingHeadSetup : {}", trainingHeadSetup);
        if (trainingHeadSetup.getId() == null) {
            return createTrainingHeadSetup(trainingHeadSetup);
        }
        trainingHeadSetup.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainingHeadSetup.setUpdateDate(LocalDate.now());

        TrainingHeadSetup result = trainingHeadSetupRepository.save(trainingHeadSetup);
        trainingHeadSetupSearchRepository.save(trainingHeadSetup);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingHeadSetup", trainingHeadSetup.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingHeadSetups -> get all the trainingHeadSetups.
     */
    @RequestMapping(value = "/trainingHeadSetups",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingHeadSetup>> getAllTrainingHeadSetups(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingHeadSetup> page = trainingHeadSetupRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingHeadSetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingHeadSetups/:id -> get the "id" trainingHeadSetup.
     */
    @RequestMapping(value = "/trainingHeadSetups/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingHeadSetup> getTrainingHeadSetup(@PathVariable Long id) {
        log.debug("REST request to get TrainingHeadSetup : {}", id);
        return Optional.ofNullable(trainingHeadSetupRepository.findOne(id))
            .map(trainingHeadSetup -> new ResponseEntity<>(
                trainingHeadSetup,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingHeadSetups/:id -> delete the "id" trainingHeadSetup.
     */
    @RequestMapping(value = "/trainingHeadSetups/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingHeadSetup(@PathVariable Long id) {
        log.debug("REST request to delete TrainingHeadSetup : {}", id);
        trainingHeadSetupRepository.delete(id);
        trainingHeadSetupSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingHeadSetup", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainingHeadSetups/:query -> search for the trainingHeadSetup corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingHeadSetups/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingHeadSetup> searchTrainingHeadSetups(@PathVariable String query) {
        return StreamSupport
            .stream(trainingHeadSetupSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

     /* Check Training Head Name is unique  */

    @RequestMapping(value = "/trainingHeadSetups/isHeadNameUnique/",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map> isHeadNameUnique(@RequestParam String value){

        log.debug("REST request to isCategoryNameUnique : {}",value);
        Optional<TrainingHeadSetup> trainingHeadSetup =  trainingHeadSetupRepository.findByHeadName(value);
        Map mapValue = new HashMap();
        mapValue.put("value",value);

        if(Optional.empty().equals(trainingHeadSetup)){
            mapValue.put("isValid",true);
            return new ResponseEntity<Map>(mapValue,HttpStatus.OK);
        }else {
            mapValue.put("isValid",false);
            return new ResponseEntity<Map>(mapValue,HttpStatus.OK);
        }
    }

    /**
     * GET  /trainingHeadSetups -> get all the trainingHeadSetups By Status.
     */
    @RequestMapping(value = "/trainingHeadSetups/findAllByStatus/{status}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingHeadSetup>> getTrainingHeadByStatus(@PathVariable Boolean status,Pageable pageable)
        throws URISyntaxException {
        Page<TrainingHeadSetup> page = trainingHeadSetupRepository.findAllByStatus(status,pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingCategorySetups");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }
}
