package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.TraineeEvaluationInfo;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.TraineeEvaluationInfoRepository;
import gov.step.app.repository.search.TraineeEvaluationInfoSearchRepository;
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
 * REST controller for managing TraineeEvaluationInfo.
 */
@RestController
@RequestMapping("/api")
public class TraineeEvaluationInfoResource {

    private final Logger log = LoggerFactory.getLogger(TraineeEvaluationInfoResource.class);

    @Inject
    private TraineeEvaluationInfoRepository traineeEvaluationInfoRepository;

    @Inject
    private TraineeEvaluationInfoSearchRepository traineeEvaluationInfoSearchRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository ;

    /**
     * POST  /traineeEvaluationInfos -> Create a new traineeEvaluationInfo.
     */
    @RequestMapping(value = "/traineeEvaluationInfos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeEvaluationInfo> createTraineeEvaluationInfo(@Valid @RequestBody TraineeEvaluationInfo traineeEvaluationInfo) throws URISyntaxException {
        log.debug("REST request to save TraineeEvaluationInfo : {}", traineeEvaluationInfo);
        if (traineeEvaluationInfo.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new traineeEvaluationInfo cannot already have an ID").body(null);
        }

        traineeEvaluationInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        traineeEvaluationInfo.setCreateDate(LocalDate.now());
        traineeEvaluationInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        traineeEvaluationInfo.setUpdateDate(LocalDate.now());
        traineeEvaluationInfo.setStatus(true);

        // traineeEvaluationInfo.setHrEmployeeInfo(hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser());

        TraineeEvaluationInfo result = traineeEvaluationInfoRepository.save(traineeEvaluationInfo);
        traineeEvaluationInfoSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/traineeEvaluationInfos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("traineeEvaluationInfo", result.getId().toString()))
            .body(result);
    }

//    @RequestMapping(value = "/traineeEvaluationInfos/saveTraineeEvaluationList",
//        method = RequestMethod.POST,
//        produces = MediaType.APPLICATION_JSON_VALUE)
//    @Timed
//    public ResponseEntity<TraineeEvaluationInfo> saveTrainerEvaluationList(@Valid @RequestBody List<TraineeEvaluationInfo> traineeEvaluationList) throws URISyntaxException {
//        log.debug("REST request to save TraineeEvaluationInfo List : {}", traineeEvaluationList);
//
//        if (traineeEvaluationList.isEmpty()) {
//            return ResponseEntity.badRequest().header("Failure", "No Data Found").body(null);
//        }
//        for(TraineeEvaluationInfo traineeEvaluationInfo : traineeEvaluationList){
//            if (traineeEvaluationInfo.getId() != null) {
//                return ResponseEntity.badRequest().header("Failure", "A new traineeEvaluationInfo cannot already have an ID").body(null);
//            }
//            traineeEvaluationInfo.setCreateBy(SecurityUtils.getCurrentUserId());
//            traineeEvaluationInfo.setCreateDate(LocalDate.now());
//            traineeEvaluationInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
//            traineeEvaluationInfo.setUpdateDate(LocalDate.now());
//            traineeEvaluationInfo.setStatus(true);
//            TraineeEvaluationInfo result = traineeEvaluationInfoRepository.save(traineeEvaluationInfo);
//        }
//
//        TraineeEvaluationInfo result = traineeEvaluationInfoRepository.save(traineeEvaluationInfo);
//        // traineeEvaluationInfoSearchRepository.save(result);
//        return ResponseEntity.created(new URI("/api/traineeEvaluationInfos/" + result.getId()))
//            .headers(HeaderUtil.createEntityCreationAlert("traineeEvaluationInfo", result.getId().toString()))
//            .body(result);
//    }

    /**
     * PUT  /traineeEvaluationInfos -> Updates an existing traineeEvaluationInfo.
     */
    @RequestMapping(value = "/traineeEvaluationInfos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeEvaluationInfo> updateTraineeEvaluationInfo(@Valid @RequestBody TraineeEvaluationInfo traineeEvaluationInfo) throws URISyntaxException {
        log.debug("REST request to update TraineeEvaluationInfo : {}", traineeEvaluationInfo);
        if (traineeEvaluationInfo.getId() == null) {
            return createTraineeEvaluationInfo(traineeEvaluationInfo);
        }
        traineeEvaluationInfo.setCreateBy(SecurityUtils.getCurrentUserId());
        traineeEvaluationInfo.setCreateDate(LocalDate.now());
        traineeEvaluationInfo.setUpdateBy(SecurityUtils.getCurrentUserId());
        traineeEvaluationInfo.setUpdateDate(LocalDate.now());

        TraineeEvaluationInfo result = traineeEvaluationInfoRepository.save(traineeEvaluationInfo);
        traineeEvaluationInfoSearchRepository.save(traineeEvaluationInfo);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("traineeEvaluationInfo", traineeEvaluationInfo.getId().toString()))
            .body(result);
    }

    /**
     * GET  /traineeEvaluationInfos -> get all the traineeEvaluationInfos.
     */
    @RequestMapping(value = "/traineeEvaluationInfos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TraineeEvaluationInfo>> getAllTraineeEvaluationInfos(Pageable pageable)
        throws URISyntaxException {
        Page<TraineeEvaluationInfo> page = traineeEvaluationInfoRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/traineeEvaluationInfos");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /traineeEvaluationInfos/:id -> get the "id" traineeEvaluationInfo.
     */
    @RequestMapping(value = "/traineeEvaluationInfos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeEvaluationInfo> getTraineeEvaluationInfo(@PathVariable Long id) {
        log.debug("REST request to get TraineeEvaluationInfo : {}", id);
        return Optional.ofNullable(traineeEvaluationInfoRepository.findOne(id))
            .map(traineeEvaluationInfo -> new ResponseEntity<>(
                traineeEvaluationInfo,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /traineeEvaluationInfos/:id -> delete the "id" traineeEvaluationInfo.
     */
    @RequestMapping(value = "/traineeEvaluationInfos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTraineeEvaluationInfo(@PathVariable Long id) {
        log.debug("REST request to delete TraineeEvaluationInfo : {}", id);
        traineeEvaluationInfoRepository.delete(id);
        traineeEvaluationInfoSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traineeEvaluationInfo", id.toString())).build();
    }

    /**
     * SEARCH  /_search/traineeEvaluationInfos/:query -> search for the traineeEvaluationInfo corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/traineeEvaluationInfos/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TraineeEvaluationInfo> searchTraineeEvaluationInfos(@PathVariable String query) {
        return StreamSupport
            .stream(traineeEvaluationInfoSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
