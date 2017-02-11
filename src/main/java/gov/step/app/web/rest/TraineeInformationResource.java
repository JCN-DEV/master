package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmpAddressInfo;
import gov.step.app.domain.TraineeInformation;
import gov.step.app.domain.TrainingInitializationInfo;
import gov.step.app.domain.enumeration.addressTypes;
import gov.step.app.repository.HrEmpAddressInfoRepository;
import gov.step.app.repository.TraineeInformationRepository;
import gov.step.app.repository.TrainingInitializationInfoRepository;
import gov.step.app.repository.search.TraineeInformationSearchRepository;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import gov.step.app.domain.enumeration.addressTypes;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TraineeInformation.
 */
@RestController
@RequestMapping("/api")
public class TraineeInformationResource {

    private final Logger log = LoggerFactory.getLogger(TraineeInformationResource.class);

    @Inject
    private TraineeInformationRepository traineeInformationRepository;

    @Inject
    private TraineeInformationSearchRepository traineeInformationSearchRepository;

    @Inject
    private HrEmpAddressInfoRepository hrEmpAddressInfoRepository;

    @Inject
    private TrainingInitializationInfoRepository trainingInitializationInfoRepository;

    // private addressTypes addressTypes;

    /**
     * POST  /traineeInformations -> Create a new traineeInformation.
     */
    @RequestMapping(value = "/traineeInformations",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeInformation> createTraineeInformation(@Valid @RequestBody TraineeInformation traineeInformation) throws URISyntaxException {
        log.debug("REST request to save TraineeInformation : {}", traineeInformation);
        if (traineeInformation.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new traineeInformation cannot already have an ID").body(null);
        }
        DateResource dr = new DateResource();
        TransactionIdResource transactionIdResource = new TransactionIdResource();
        traineeInformation.setTraineeId(transactionIdResource.getGeneratedid("tis-trainee-"));
        traineeInformation.setStatus(true);
        traineeInformation.setCreateBy(SecurityUtils.getCurrentUserId());
        traineeInformation.setCreateDate(dr.getDateAsLocalDate());
        // Hr Employee Data
        if (traineeInformation.getHrEmployeeInfo() != null){
            if(traineeInformation.getHrEmployeeInfo().getGender()!=null ){
//                traineeInformation.setGender(traineeInformation.getHrEmployeeInfo().getGender());
            }
            if(traineeInformation.getHrEmployeeInfo().getWorkAreaDtl().getName()!= null){
                traineeInformation.setOrganization(traineeInformation.getHrEmployeeInfo().getWorkAreaDtl().getName());
            }
            HrEmpAddressInfo hrEmpAddressInfo =  hrEmpAddressInfoRepository.findByEmployeeIdAndType(traineeInformation.getHrEmployeeInfo().getId(), addressTypes.Present);
            System.out.println("--------------------------------------------------------------------------");
            System.out.println(hrEmpAddressInfo);
            traineeInformation.setTraineeName(traineeInformation.getHrEmployeeInfo().getFullName());
            if (hrEmpAddressInfo != null){
                if(hrEmpAddressInfo.getDistrict() != null){
                    traineeInformation.setDistrict(hrEmpAddressInfo.getDistrict());
                }
                if(hrEmpAddressInfo.getDivision() !=null){
                    traineeInformation.setDivision(hrEmpAddressInfo.getDivision());
                }

//                traineeInformation.setAddress("House:"+hrEmpAddressInfo.getHouseNumber()+" Road:"+hrEmpAddressInfo.getRoadNumber()+" Village: "+hrEmpAddressInfo.getVillageName());
            }
         // Institute Employee Data
        }else if(traineeInformation.getInstEmployee() !=null) {
            traineeInformation.setTraineeName(traineeInformation.getInstEmployee().getName());
            traineeInformation.setGender(traineeInformation.getInstEmployee().getGender());
            if(traineeInformation.getInstEmployee().getInstitute()!=null){
                traineeInformation.setOrganization(traineeInformation.getInstEmployee().getInstitute().getName());
            }
        }
        TraineeInformation result = traineeInformationRepository.save(traineeInformation);
        traineeInformationSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/traineeInformations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("traineeInformation", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /traineeInformations -> Updates an existing traineeInformation.
     */
    @RequestMapping(value = "/traineeInformations",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeInformation> updateTraineeInformation(@Valid @RequestBody TraineeInformation traineeInformation) throws URISyntaxException {
        log.debug("REST request to update TraineeInformation : {}", traineeInformation);
        if (traineeInformation.getId() == null) {
            return createTraineeInformation(traineeInformation);
        }
        TraineeInformation result = traineeInformationRepository.save(traineeInformation);
        traineeInformationSearchRepository.save(traineeInformation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("traineeInformation", traineeInformation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /traineeInformations -> get all the traineeInformations.
     */
    @RequestMapping(value = "/traineeInformations",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TraineeInformation>> getAllTraineeInformations(Pageable pageable)
        throws URISyntaxException {
        Page<TraineeInformation> page = traineeInformationRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/traineeInformations");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /traineeInformations/:id -> get the "id" traineeInformation.
     */
    @RequestMapping(value = "/traineeInformations/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TraineeInformation> getTraineeInformation(@PathVariable Long id) {
        log.debug("REST request to get TraineeInformation : {}", id);
        return Optional.ofNullable(traineeInformationRepository.findOne(id))
            .map(traineeInformation -> new ResponseEntity<>(
                traineeInformation,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /traineeInformations/:id -> delete the "id" traineeInformation.
     */
    @RequestMapping(value = "/traineeInformations/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTraineeInformation(@PathVariable Long id) {
        log.debug("REST request to delete TraineeInformation : {}", id);
        traineeInformationRepository.delete(id);
        traineeInformationSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("traineeInformation", id.toString())).build();
    }

    /**
     * SEARCH  /_search/traineeInformations/:query -> search for the traineeInformation corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/traineeInformations/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TraineeInformation> searchTraineeInformations(@PathVariable String query) {
        return StreamSupport
            .stream(traineeInformationSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/traineeInformations/traineeList/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TraineeInformation> getByTisHeadSetup(@PathVariable Long id) {
        log.debug("REST request to Get getByTisHeadSetup : {}", id);
        List traineeList = traineeInformationRepository.findAByTisHeadSetup(id);
        return traineeList;
    }

    @RequestMapping(value = "/traineeInformations/traineeListByTrainingCode/{pTrainingCode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TraineeInformation> getByTrainingCode(@PathVariable String pTrainingCode) {
        log.debug("REST request to Get getByTrainingCode : {}", pTrainingCode);
        TrainingInitializationInfo initializationInfo = trainingInitializationInfoRepository.findByTrainingCode(pTrainingCode);
        Long pTrainingFormId = initializationInfo.getTrainingRequisitionForm().getId();
        System.out.println("Training Requisition Id " + pTrainingFormId);
        List traineeList = traineeInformationRepository.findByTrainingRequisitionId(pTrainingFormId);

        return traineeList;
    }

    @RequestMapping(value = "/traineeInformations/traineeListByRequisition/{pTrainingReqId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TraineeInformation> getByTrainingRequisitionId(@PathVariable Long pTrainingReqId) {
        log.debug("REST request to Get getByTrainingRequisitionId : {}", pTrainingReqId);

        List traineeList = traineeInformationRepository.findByTrainingRequisitionId(pTrainingReqId);

        return traineeList;
    }
}
