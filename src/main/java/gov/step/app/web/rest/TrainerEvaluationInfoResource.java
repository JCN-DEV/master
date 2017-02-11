package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TrainerEvaluationInfo;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.TraineeInformationRepository;
import gov.step.app.repository.TrainerEvaluationInfoRepository;
import gov.step.app.repository.search.TrainerEvaluationInfoSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.TISJdbcDao;
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
 * REST controller for managing TrainerEvaluationInfo.
 */
@RestController
@RequestMapping("/api")
public class TrainerEvaluationInfoResource {

    private final Logger log = LoggerFactory.getLogger(TrainerEvaluationInfoResource.class);

    @Inject
    private TrainerEvaluationInfoRepository trainerEvaluationInfoRepository;

    @Inject
    private TrainerEvaluationInfoSearchRepository trainerEvaluationInfoSearchRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private TraineeInformationRepository traineeInformationRepository;

    @Inject
    private TISJdbcDao tisJdbcDao;

    /**
     * POST  /trainerEvaluationInfos -> Create a new trainerEvaluationInfo.
     */
    @RequestMapping(value = "/trainerEvaluationInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerEvaluationInfo> createTrainerEvaluationInfo(@Valid @RequestBody TrainerEvaluationInfo trainerEvaluationInfo) throws URISyntaxException {
        log.debug("REST request to save TrainerEvaluationInfo : {}", trainerEvaluationInfo);
        if (trainerEvaluationInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainerEvaluationInfo cannot already have an ID").body(null);
        }
        trainerEvaluationInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        trainerEvaluationInfo.setCreateDate(LocalDate.now());
        trainerEvaluationInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainerEvaluationInfo.setUpdateDate(LocalDate.now());
        trainerEvaluationInfo.setStatus(true);
        trainerEvaluationInfo.setTraineeInformation(tisJdbcDao.getTraineeInfoByEmployeeIdAndTrainingCode
                                (hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser().getId(),trainerEvaluationInfo.getTrainingInitializationInfo().getTrainingCode()));
        TrainerEvaluationInfo result = trainerEvaluationInfoRepository.save(trainerEvaluationInfo);
        trainerEvaluationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainerEvaluationInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainerEvaluationInfo", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainerEvaluationInfos -> Updates an existing trainerEvaluationInfo.
     */
    @RequestMapping(value = "/trainerEvaluationInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerEvaluationInfo> updateTrainerEvaluationInfo(@Valid @RequestBody TrainerEvaluationInfo trainerEvaluationInfo) throws URISyntaxException {
        log.debug("REST request to update TrainerEvaluationInfo : {}", trainerEvaluationInfo);
        if (trainerEvaluationInfo.getId() == null) {
            return createTrainerEvaluationInfo(trainerEvaluationInfo);
        }
        TrainerEvaluationInfo result = trainerEvaluationInfoRepository.save(trainerEvaluationInfo);
        trainerEvaluationInfoSearchRepository.save(trainerEvaluationInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainerEvaluationInfo", trainerEvaluationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainerEvaluationInfos -> get all the trainerEvaluationInfos.
     */
    @RequestMapping(value = "/trainerEvaluationInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainerEvaluationInfo>> getAllTrainerEvaluationInfos(Pageable pageable)
        throws URISyntaxException {
        Page<TrainerEvaluationInfo> page = trainerEvaluationInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainerEvaluationInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainerEvaluationInfos/:id -> get the "id" trainerEvaluationInfo.
     */
    @RequestMapping(value = "/trainerEvaluationInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerEvaluationInfo> getTrainerEvaluationInfo(@PathVariable Long id) {
        log.debug("REST request to get TrainerEvaluationInfo : {}", id);
        return Optional.ofNullable(trainerEvaluationInfoRepository.findOne(id))
            .map(trainerEvaluationInfo -> new ResponseEntity<>(
                trainerEvaluationInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainerEvaluationInfos/:id -> delete the "id" trainerEvaluationInfo.
     */
    @RequestMapping(value = "/trainerEvaluationInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainerEvaluationInfo(@PathVariable Long id) {
        log.debug("REST request to delete TrainerEvaluationInfo : {}", id);
        trainerEvaluationInfoRepository.delete(id);
        trainerEvaluationInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainerEvaluationInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainerEvaluationInfos/:query -> search for the trainerEvaluationInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainerEvaluationInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainerEvaluationInfo> searchTrainerEvaluationInfos(@PathVariable String query) {
        return StreamSupport
            .stream(trainerEvaluationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
