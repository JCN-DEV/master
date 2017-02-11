package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainingInitializationInfo;
import gov.step.app.repository.TrainingInitializationInfoRepository;
import gov.step.app.repository.search.TrainingInitializationInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
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
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TrainingInitializationInfo.
 */
@RestController
@RequestMapping("/api")
public class TrainingInitializationInfoResource {

    private final Logger log = LoggerFactory.getLogger(TrainingInitializationInfoResource.class);

    @Inject
    private TrainingInitializationInfoRepository trainingInitializationInfoRepository;

    @Inject
    private TrainingInitializationInfoSearchRepository trainingInitializationInfoSearchRepository;

    /**
     * POST  /trainingInitializationInfos -> Create a new trainingInitializationInfo.
     */
    @RequestMapping(value = "/trainingInitializationInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingInitializationInfo> createTrainingInitializationInfo(@RequestBody TrainingInitializationInfo trainingInitializationInfo) throws URISyntaxException {
        log.debug("REST request to save TrainingInitializationInfo : {}", trainingInitializationInfo);
        if (trainingInitializationInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingInitializationInfo cannot already have an ID").body(null);
        }
        TransactionIdResource transactionId = new TransactionIdResource();
        trainingInitializationInfo.setTrainingCode(transactionId.getGeneratedid("tis-code-"));
        trainingInitializationInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingInitializationInfo.setCreateDate(LocalDate.now());
        trainingInitializationInfo.setStatus(true);

        TrainingInitializationInfo result = trainingInitializationInfoRepository.save(trainingInitializationInfo);
        trainingInitializationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingInitializationInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingInitializationInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingInitializationInfos -> Updates an existing trainingInitializationInfo.
     */
    @RequestMapping(value = "/trainingInitializationInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingInitializationInfo> updateTrainingInitializationInfo(@RequestBody TrainingInitializationInfo trainingInitializationInfo) throws URISyntaxException {
        log.debug("REST request to update TrainingInitializationInfo : {}", trainingInitializationInfo);
        if (trainingInitializationInfo.getId() == null) {
            return createTrainingInitializationInfo(trainingInitializationInfo);
        }
        TrainingInitializationInfo result = trainingInitializationInfoRepository.save(trainingInitializationInfo);
        trainingInitializationInfoSearchRepository.save(trainingInitializationInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingInitializationInfo", trainingInitializationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingInitializationInfos -> get all the trainingInitializationInfos.
     */
    @RequestMapping(value = "/trainingInitializationInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingInitializationInfo>> getAllTrainingInitializationInfos(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingInitializationInfo> page = trainingInitializationInfoRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingInitializationInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingInitializationInfos/:id -> get the "id" trainingInitializationInfo.
     */
    @RequestMapping(value = "/trainingInitializationInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingInitializationInfo> getTrainingInitializationInfo(@PathVariable Long id) {
        log.debug("REST request to get TrainingInitializationInfo : {}", id);
        return Optional.ofNullable(trainingInitializationInfoRepository.findOne(id))
            .map(trainingInitializationInfo -> new ResponseEntity<>(
                trainingInitializationInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingInitializationInfos/:id -> delete the "id" trainingInitializationInfo.
     */
    @RequestMapping(value = "/trainingInitializationInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingInitializationInfo(@PathVariable Long id) {
        log.debug("REST request to delete TrainingInitializationInfo : {}", id);
        trainingInitializationInfoRepository.delete(id);
        trainingInitializationInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingInitializationInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainingInitializationInfos/:query -> search for the trainingInitializationInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingInitializationInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingInitializationInfo> searchTrainingInitializationInfos(@PathVariable String query) {
        return StreamSupport
            .stream(trainingInitializationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/trainingInitializationInfos/getByTrainingCode/{pTrainingCode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public TrainingInitializationInfo getByTrainingCode(@PathVariable String pTrainingCode) {
        log.debug("REST request to Get TrainingInitializationInfo By Code : {}", pTrainingCode);
       TrainingInitializationInfo initializationInfo = trainingInitializationInfoRepository.findByTrainingCode(pTrainingCode);
        return initializationInfo;
    }
}
