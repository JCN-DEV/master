package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.TrainerInformation;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.TrainerInformationRepository;
import gov.step.app.repository.search.TrainerInformationSearchRepository;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
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
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TrainerInformation.
 */
@RestController
@RequestMapping("/api")
public class TrainerInformationResource {

    private final Logger log = LoggerFactory.getLogger(TrainerInformationResource.class);

    @Inject
    private TrainerInformationRepository trainerInformationRepository;

    @Inject
    private TrainerInformationSearchRepository trainerInformationSearchRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    /**
     * POST  /trainerInformations -> Create a new trainerInformation.
     */
    @RequestMapping(value = "/trainerInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerInformation> createTrainerInformation(@Valid @RequestBody TrainerInformation trainerInformation) throws URISyntaxException {
        log.debug("REST request to save TrainerInformation : {}", trainerInformation);
        if (trainerInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainerInformation cannot already have an ID").body(null);
        }
        DateResource dr = new DateResource();
        TransactionIdResource transactionIdResource = new TransactionIdResource();

        if(trainerInformation.getHrEmployeeInfo() != null){
            HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOne(trainerInformation.getHrEmployeeInfo().getId());
            trainerInformation.setTrainerName(hrEmployeeInfo.getFullName());
            // trainerInformation.setAddress(hrEmployeeInfo.designationInfo.);
            if (hrEmployeeInfo.getDesignationInfo() != null && hrEmployeeInfo.getDesignationInfo().getDesignationInfo()!= null){
                trainerInformation.setDesignation(hrEmployeeInfo.getDesignationInfo().getDesignationInfo().getDesignationName());
            }
            if(hrEmployeeInfo.getWorkAreaDtl() != null){
                trainerInformation.setOrganization(hrEmployeeInfo.getWorkAreaDtl().getName());
            }
        }
        trainerInformation.setTrainerId(transactionIdResource.getGeneratedid("tis-trainer-"));
        trainerInformation.setCreateDate(dr.getDateAsLocalDate());
        trainerInformation.setStatus(true);
        trainerInformation.setCreateBy(SecurityUtils.getCurrentUserId());
        TrainerInformation result = trainerInformationRepository.save(trainerInformation);
        trainerInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainerInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainerInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainerInformations -> Updates an existing trainerInformation.
     */
    @RequestMapping(value = "/trainerInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerInformation> updateTrainerInformation(@Valid @RequestBody TrainerInformation trainerInformation) throws URISyntaxException {
        log.debug("REST request to update TrainerInformation : {}", trainerInformation);
        if (trainerInformation.getId() == null) {
            return createTrainerInformation(trainerInformation);
        }
        DateResource dr = new DateResource();
        trainerInformation.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainerInformation.setUpdateDate(dr.getDateAsLocalDate());
        TrainerInformation result = trainerInformationRepository.save(trainerInformation);
        trainerInformationSearchRepository.save(trainerInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainerInformation", trainerInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainerInformations -> get all the trainerInformations.
     */
    @RequestMapping(value = "/trainerInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainerInformation>> getAllTrainerInformations(Pageable pageable)
        throws URISyntaxException {
        Page<TrainerInformation> page = trainerInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainerInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainerInformations/:id -> get the "id" trainerInformation.
     */
    @RequestMapping(value = "/trainerInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainerInformation> getTrainerInformation(@PathVariable Long id) {
        log.debug("REST request to get TrainerInformation : {}", id);
        return Optional.ofNullable(trainerInformationRepository.findOne(id))
            .map(trainerInformation -> new ResponseEntity<>(
                trainerInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainerInformations/:id -> delete the "id" trainerInformation.
     */
    @RequestMapping(value = "/trainerInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainerInformation(@PathVariable Long id) {
        log.debug("REST request to delete TrainerInformation : {}", id);
        trainerInformationRepository.delete(id);
        trainerInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainerInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainerInformations/:query -> search for the trainerInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainerInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainerInformation> searchTrainerInformations(@PathVariable String query) {
        return StreamSupport
            .stream(trainerInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /trainerInformations -> get all the trainerInformations.
     */
    @RequestMapping(value = "/trainerInformations/TrainerListByTrainingCode/{pTrainingCode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainerInformation> getAllTrainerByTrainingCode(@PathVariable String pTrainingCode)
        throws URISyntaxException {
        log.debug("REST request to getAll TrainerInformation By TrainingCode: {}", pTrainingCode);
        List<TrainerInformation> trainerList = trainerInformationRepository.findByTrainingCode(pTrainingCode);

        return trainerList;
    }

    /**
     * GET  /trainerInformations -> get all the trainerInformations.
     */
    @RequestMapping(value = "/trainerInformations/TrainerListByInitializationId/{pInitializationId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainerInformation> getAllTrainerTrainerByInitializationId(@PathVariable Long pInitializationId)
        throws URISyntaxException {
        log.debug("REST request to getAll TrainerInformation By Training Initialization ID: {}", pInitializationId);
        List<TrainerInformation> trainerList = trainerInformationRepository.findByInitializationId(pInitializationId);

        return trainerList;
    }
}
