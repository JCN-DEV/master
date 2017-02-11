package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.NotificationStep;
import gov.step.app.domain.TrainingRequisitionApproveAndForward;
import gov.step.app.domain.TrainingRequisitionForm;
import gov.step.app.repository.TrainingRequisitionApproveAndForwardRepository;
import gov.step.app.repository.TrainingRequisitionFormRepository;
import gov.step.app.repository.search.TrainingRequisitionApproveAndForwardSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
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
 * REST controller for managing TrainingRequisitionApproveAndForward.
 */
@RestController
@RequestMapping("/api")
public class TrainingRequisitionApproveAndForwardResource {

    private final Logger log = LoggerFactory.getLogger(TrainingRequisitionApproveAndForwardResource.class);

    @Inject
    private TrainingRequisitionApproveAndForwardRepository trainingRequisitionApproveAndForwardRepository;

    @Inject
    private TrainingRequisitionApproveAndForwardSearchRepository trainingRequisitionApproveAndForwardSearchRepository;

    @Inject
    private TrainingRequisitionFormRepository trainingRequisitionFormRepository;

    /**
     * POST  /trainingRequisitionApproveAndForwards -> Create a new trainingRequisitionApproveAndForward.
     */
    @RequestMapping(value = "/trainingRequisitionApproveAndForwards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> createTrainingRequisitionApproveAndForward(@RequestBody TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save TrainingRequisitionApproveAndForward : {}", trainingRequisitionApproveAndForward);
        if (trainingRequisitionApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingRequisitionApproveAndForward cannot already have an ID").body(null);
        }
        Integer approveStatus = 0;
        String logComments = "";
        DateResource dr = new DateResource();
        trainingRequisitionApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionApproveAndForward.setCreateDate(dr.getDateAsLocalDate());
        trainingRequisitionApproveAndForward.setStatus(true);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)){
            approveStatus = 2;
            logComments = "Approved From AD";
        }if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
            approveStatus = 3;
            logComments = "Approved From DG";
        }
        trainingRequisitionApproveAndForward.setApproveStatus(approveStatus);
        trainingRequisitionApproveAndForward.setLogComments(logComments);
        TrainingRequisitionApproveAndForward result = trainingRequisitionApproveAndForwardRepository.save(trainingRequisitionApproveAndForward);

        if(result.getId() !=null){
            TrainingRequisitionForm trainingRequisitionForm = result.getTrainingRequisitionForm();
            trainingRequisitionForm.setApproveStatus(approveStatus);
            trainingRequisitionForm.setApprovedDate(dr.getDateAsLocalDate());
            trainingRequisitionFormRepository.save(trainingRequisitionForm);
        }

//        trainingRequisitionApproveAndForwardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingRequisitionApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingRequisitionApproveAndForward", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingRequisitionApproveAndForwards -> Updates an existing trainingRequisitionApproveAndForward.
     */
    @RequestMapping(value = "/trainingRequisitionApproveAndForwards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> updateTrainingRequisitionApproveAndForward(@RequestBody TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward) throws URISyntaxException {
        log.debug("REST request to update TrainingRequisitionApproveAndForward : {}", trainingRequisitionApproveAndForward);
        if (trainingRequisitionApproveAndForward.getId() == null) {
            return createTrainingRequisitionApproveAndForward(trainingRequisitionApproveAndForward);
        }
        TrainingRequisitionApproveAndForward result = trainingRequisitionApproveAndForwardRepository.save(trainingRequisitionApproveAndForward);
        trainingRequisitionApproveAndForwardSearchRepository.save(trainingRequisitionApproveAndForward);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingRequisitionApproveAndForward", trainingRequisitionApproveAndForward.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingRequisitionApproveAndForwards -> get all the trainingRequisitionApproveAndForwards.
     */
    @RequestMapping(value = "/trainingRequisitionApproveAndForwards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionApproveAndForward>> getAllTrainingRequisitionApproveAndForwards(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingRequisitionApproveAndForward> page = trainingRequisitionApproveAndForwardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingRequisitionApproveAndForwards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingRequisitionApproveAndForwards/:id -> get the "id" trainingRequisitionApproveAndForward.
     */
    @RequestMapping(value = "/trainingRequisitionApproveAndForwards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> getTrainingRequisitionApproveAndForward(@PathVariable Long id) {
        log.debug("REST request to get TrainingRequisitionApproveAndForward : {}", id);
        return Optional.ofNullable(trainingRequisitionApproveAndForwardRepository.findOne(id))
            .map(trainingRequisitionApproveAndForward -> new ResponseEntity<>(
                trainingRequisitionApproveAndForward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingRequisitionApproveAndForwards/:id -> delete the "id" trainingRequisitionApproveAndForward.
     */
    @RequestMapping(value = "/trainingRequisitionApproveAndForwards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingRequisitionApproveAndForward(@PathVariable Long id) {
        log.debug("REST request to delete TrainingRequisitionApproveAndForward : {}", id);
        trainingRequisitionApproveAndForwardRepository.delete(id);
        trainingRequisitionApproveAndForwardSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingRequisitionApproveAndForward", id.toString())).build();
    }

    /**
     * SEARCH  /_search/trainingRequisitionApproveAndForwards/:query -> search for the trainingRequisitionApproveAndForward corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingRequisitionApproveAndForwards/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingRequisitionApproveAndForward> searchTrainingRequisitionApproveAndForwards(@PathVariable String query) {
        return StreamSupport
            .stream(trainingRequisitionApproveAndForwardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/trainingRequisitionApproveAndForwards/trainingRequisitionDecline",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> trainingRequisitionDecline(@Valid @RequestBody TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save TrainingRequisitionApproveAndForward : {}", trainingRequisitionApproveAndForward);
        if (trainingRequisitionApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new TrainingRequisitionApproveAndForward cannot already have an ID").body(null);
        }

        Integer approveStatus = 0;  // decline

        String declineBy = null;
        String logComments = null;

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)) {
            declineBy = AuthoritiesConstants.AD;
            logComments = "AD";
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
            declineBy = AuthoritiesConstants.DG;
            logComments = "DG";
        }

        DateResource dateResource =  new DateResource();
        trainingRequisitionApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionApproveAndForward.setCreateDate(dateResource.getDateAsLocalDate());
        trainingRequisitionApproveAndForward.setApproveStatus(approveStatus);

        trainingRequisitionApproveAndForward.setApproveByAuthority(declineBy);
        trainingRequisitionApproveAndForward.setLogComments("Training Requisition Decline By "+logComments);
        trainingRequisitionApproveAndForward.setStatus(true);

        TrainingRequisitionApproveAndForward result = trainingRequisitionApproveAndForwardRepository.save(trainingRequisitionApproveAndForward);

        if(result.getId() !=null){

            TrainingRequisitionForm trainingRequisitionForm = result.getTrainingRequisitionForm();
            trainingRequisitionForm.setApprovedDate(dateResource.getDateAsLocalDate());
            trainingRequisitionForm.setApproveStatus(approveStatus);
            trainingRequisitionFormRepository.save(trainingRequisitionForm);

        }
        // InstEmployee instEmp = instEmployeeRepository.findOne(loanRequisitionForm.getInstituteEmployeeId());

//        NotificationStep notificationSteps = new NotificationStep();
//        notificationSteps.setNotification("Loan Requisition Decline.");
//        notificationSteps.setStatus(true);
//        notificationSteps.setUrls("trainingInfo.dashboard");
//        notificationSteps.setUserId(trainingRequisitionForm.getEmployeeInfo().getId());
//        notificationStepRepository.save(notificationSteps);

//        employeeLoanApproveAndForwardSearchRepository.save(result);

        return ResponseEntity.created(new URI("/api/employeeLoanApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanApproveAndForward", result.getId().toString()))
            .body(result);

    }

    @RequestMapping(value = "/trainingRequisitionApproveAndForwards/trainingRequisitionReject",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> trainingRequisitionReject(@Valid @RequestBody TrainingRequisitionApproveAndForward trainingRequisitionApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save Training Requisition  Reject : {}", trainingRequisitionApproveAndForward);
        if (trainingRequisitionApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingRequisitionApproveAndForward cannot already have an ID").body(null);
        }
        Integer approveStatus = -1;

        DateResource dateResource =  new DateResource();
        trainingRequisitionApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionApproveAndForward.setCreateDate(dateResource.getDateAsLocalDate());
        trainingRequisitionApproveAndForward.setApproveStatus(approveStatus);
        trainingRequisitionApproveAndForward.setLogComments("Training Requisition Rejected");

        TrainingRequisitionApproveAndForward result = trainingRequisitionApproveAndForwardRepository.save(trainingRequisitionApproveAndForward);

        if(result.getId() !=null){
            TrainingRequisitionForm trainingRequisitionForm = result.getTrainingRequisitionForm();
            trainingRequisitionForm.setApprovedDate(dateResource.getDateAsLocalDate());
            trainingRequisitionForm.setApproveStatus(approveStatus);
            trainingRequisitionFormRepository.save(trainingRequisitionForm);
        }
        // InstEmployee instEmp = instEmployeeRepository.findOne(loanRequisitionForm.getEmployeeInfo().getId());


//        NotificationStep notificationSteps = new NotificationStep();
//        notificationSteps.setNotification("Loan Rejected.");
//        notificationSteps.setStatus(true);
//        notificationSteps.setUrls("employeeLoanInfo.dashboard");
//        notificationSteps.setUserId(loanRequisitionForm.getEmployeeInfo().getId());
//        notificationStepRepository.save(notificationSteps);
//
//        employeeLoanApproveAndForwardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanApproveAndForward", result.getId().toString()))
            .body(result);

    }

    @RequestMapping(value = "/trainingRequisitionApproveAndForwards/findByRequisitionAndApproveStatus/{requisitionId}/{approveStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionApproveAndForward> getByRequisitionAndApproveStatus(@PathVariable Long requisitionId,@PathVariable Integer approveStatus ) {
        log.debug("REST request to get TrainingRequisitionApproveAndForward by Requisition Id : {}", requisitionId);
        return Optional.ofNullable(trainingRequisitionApproveAndForwardRepository.findByRequisitionAndApproveStatus(approveStatus,requisitionId))
            .map(trainingRequisitionApproveAndForward -> new ResponseEntity<>(
                trainingRequisitionApproveAndForward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
