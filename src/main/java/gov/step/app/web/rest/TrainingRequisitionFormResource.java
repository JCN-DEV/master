package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.HrEmployeeInfo;
import gov.step.app.domain.Institute;
import gov.step.app.domain.TrainingRequisitionForm;
import gov.step.app.repository.HrEmployeeInfoRepository;
import gov.step.app.repository.InstituteRepository;
import gov.step.app.repository.TrainingRequisitionFormRepository;
import gov.step.app.repository.search.TrainingRequisitionFormSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.service.util.MiscFileInfo;
import gov.step.app.service.util.MiscFileUtilities;
import gov.step.app.web.rest.util.AttachmentUtil;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import gov.step.app.web.rest.util.TransactionIdResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.servlet.ServletContext;
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
 * REST controller for managing TrainingRequisitionForm.
 */
@RestController
@RequestMapping("/api")
public class TrainingRequisitionFormResource {

    private final Logger log = LoggerFactory.getLogger(TrainingRequisitionFormResource.class);

    @Inject
    private TrainingRequisitionFormRepository trainingRequisitionFormRepository;

    @Inject
    private TrainingRequisitionFormSearchRepository trainingRequisitionFormSearchRepository;

    @Autowired
    ServletContext context;

    @Autowired
    HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Autowired
    InstituteRepository instituteRepository;


    MiscFileUtilities fileUtils = new MiscFileUtilities();


    /**
     * POST  /trainingRequisitionForms -> Create a new trainingRequisitionForm.
     */
    @RequestMapping(value = "/trainingRequisitionForms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionForm> createTrainingRequisitionForm(@Valid @RequestBody TrainingRequisitionForm trainingRequisitionForm) throws Exception {
        log.debug("REST request to save TrainingRequisitionForm : {}", trainingRequisitionForm);

        if (trainingRequisitionForm.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new trainingRequisitionForm cannot already have an ID").body(null);
        }
        String filepath = "/backup/mpo/";

        MiscFileInfo requisitionFile = new MiscFileInfo();
        requisitionFile.fileData(trainingRequisitionForm.getFile())
            .fileName(trainingRequisitionForm.getFileName())
            .contentType(trainingRequisitionForm.getFileContentType())
            .filePath(filepath);

        requisitionFile = fileUtils.saveFileAsByte(requisitionFile);
        trainingRequisitionForm.setFile(new byte[2]);
        trainingRequisitionForm.setFileContentName(requisitionFile.fileName());


        TransactionIdResource transactionId = new TransactionIdResource();

        trainingRequisitionForm.setRequisitionCode(transactionId.getGeneratedid("tis-req-"));
        trainingRequisitionForm.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionForm.setCreateDate(LocalDate.now());
        trainingRequisitionForm.setStatus(true);
        trainingRequisitionForm.setApproveStatus(1);

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            Institute institute = instituteRepository.findOneByUserIsCurrentUser();
            if (institute != null){
                trainingRequisitionForm.setInstitute(institute);
            }
            trainingRequisitionForm.setApplyBy("Institute");
        }else {
            HrEmployeeInfo modelInfo = hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser();
            if (modelInfo !=null){
                trainingRequisitionForm.setHrEmployeeInfo(modelInfo);
            }
            trainingRequisitionForm.setApplyBy("DTE Employee");
        }
        TrainingRequisitionForm result = trainingRequisitionFormRepository.save(trainingRequisitionForm);
        trainingRequisitionFormSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/trainingRequisitionForms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("trainingRequisitionForm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /trainingRequisitionForms -> Updates an existing trainingRequisitionForm.
     */
    @RequestMapping(value = "/trainingRequisitionForms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionForm> updateTrainingRequisitionForm(@Valid @RequestBody TrainingRequisitionForm trainingRequisitionForm) throws Exception {
        log.debug("REST request to update TrainingRequisitionForm : {}", trainingRequisitionForm);
        if (trainingRequisitionForm.getId() == null) {
            return createTrainingRequisitionForm(trainingRequisitionForm);
        }

        String filepath = "/backup/mpo/";

        MiscFileInfo requisitionFile = new MiscFileInfo();
        requisitionFile.fileData(trainingRequisitionForm.getFile())
            .fileName(trainingRequisitionForm.getFileName())
            .contentType(trainingRequisitionForm.getFileContentType())
            .filePath(filepath);

        requisitionFile = fileUtils.saveFileAsByte(requisitionFile);
        trainingRequisitionForm.setFile(new byte[2]);
        trainingRequisitionForm.setFileContentName(requisitionFile.fileName());

        trainingRequisitionForm.setFile(null);
        trainingRequisitionForm.setCreateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionForm.setCreateDate(LocalDate.now());
        trainingRequisitionForm.setUpdateBy(SecurityUtils.getCurrentUserId());
        trainingRequisitionForm.setUpdateDate(LocalDate.now());

        TrainingRequisitionForm result = trainingRequisitionFormRepository.save(trainingRequisitionForm);
        trainingRequisitionFormSearchRepository.save(trainingRequisitionForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("trainingRequisitionForm", trainingRequisitionForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /trainingRequisitionForms -> get all the trainingRequisitionForms.
     */
    @RequestMapping(value = "/trainingRequisitionForms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionForm>> getAllTrainingRequisitionForms(Pageable pageable)
        throws URISyntaxException {
        Page<TrainingRequisitionForm> page = trainingRequisitionFormRepository.findAllByOrderByIdDesc(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/trainingRequisitionForms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /trainingRequisitionForms/:id -> get the "id" trainingRequisitionForm.
     */
    @RequestMapping(value = "/trainingRequisitionForms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionForm> getTrainingRequisitionForm(@PathVariable Long id) {
        log.debug("REST request to get TrainingRequisitionForm : {}", id);
        return Optional.ofNullable(trainingRequisitionFormRepository.findOne(id))
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /trainingRequisitionForms/:id -> delete the "id" trainingRequisitionForm.
     */
    @RequestMapping(value = "/trainingRequisitionForms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteTrainingRequisitionForm(@PathVariable Long id) {
        log.debug("REST request to delete TrainingRequisitionForm : {}", id);
        trainingRequisitionFormRepository.delete(id);
        trainingRequisitionFormSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("trainingRequisitionForm", id.toString())).build();
    }



    /**
     * SEARCH  /_search/trainingRequisitionForms/:query -> search for the trainingRequisitionForm corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/trainingRequisitionForms/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<TrainingRequisitionForm> searchTrainingRequisitionForms(@PathVariable String query) {
        return StreamSupport
            .stream(trainingRequisitionFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * GET  /trainingRequisitionForms/:id -> get the "id" trainingRequisitionForm.
     */
    @RequestMapping(value = "/trainingRequisitionForms/trainingReqData/{trainingReqcode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<TrainingRequisitionForm> getTrainingRequisitionByRequisitionCode(@PathVariable String trainingReqcode) {
        log.debug("REST request to get TrainingRequisitionForm By Req code : {}", trainingReqcode);
        return Optional.ofNullable(trainingRequisitionFormRepository.findByRequisitionCode(trainingReqcode))
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    // Get Requisition Pending List
    @RequestMapping(value = "/trainingRequisitionForms/trainingReqPending",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionForm>> getTrainingRequisitionPendingList() {
        log.debug("REST request to get Training Requisition Form Pending : {}");
        Integer approveStatus = 0;
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)){
            approveStatus = 1;
        }
        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
            approveStatus = 2;
        }
        return Optional.ofNullable(trainingRequisitionFormRepository.findByApproveStatus(approveStatus))
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get Requisition Approved List
    @RequestMapping(value = "/trainingRequisitionForms/trainingApprovedList",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionForm>> getTrainingRequisitionApprovedList() {
        log.debug("REST request to get Training Requisition Form Approved List : {}");
        Integer approveStatus = 3;
        return Optional.ofNullable(trainingRequisitionFormRepository.findByApproveStatus(approveStatus))
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Get Requisition Data By Current User

    @RequestMapping(value = "/trainingRequisitionForms/trainingReqByCurrentUser",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionForm>> getRequisitionFormsByCurrentUser(Pageable pageable) throws URISyntaxException {
        log.debug("REST request to get Training Requisition Form By Current User : {}");
        List<TrainingRequisitionForm> requisitionFormList = null ;

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)){
             requisitionFormList = trainingRequisitionFormRepository.findByCurrentUser(hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser().getId());
        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
            requisitionFormList = trainingRequisitionFormRepository.findByCurrentInstitute(instituteRepository.findOneByUserIsCurrentUserID());
        }else{
            requisitionFormList = null;
        }

        return Optional.ofNullable(requisitionFormList)
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

    // Get Requisition Data By SApproveStatus

    @RequestMapping(value = "/trainingRequisitionForms/trainingReqByApproveStatus/{approveStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<TrainingRequisitionForm>> getRequisitionFormsByApproveStatus(@PathVariable Integer approveStatus) throws URISyntaxException {
        log.debug("REST request to get Training Requisition Forms By ApproveStatus : {}",approveStatus);

        return Optional.ofNullable(trainingRequisitionFormRepository.findByApproveStatus(approveStatus))
            .map(trainingRequisitionForm -> new ResponseEntity<>(
                trainingRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }



}
