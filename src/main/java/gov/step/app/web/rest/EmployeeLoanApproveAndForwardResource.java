package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationDTEStatus;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationOthersStatus;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationVocStatus;
import gov.step.app.repository.*;
import gov.step.app.repository.search.EmployeeLoanApproveAndForwardSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.util.DateResource;
import gov.step.app.web.rest.util.HeaderUtil;
import gov.step.app.web.rest.util.PaginationUtil;
import org.apache.commons.lang.ArrayUtils;
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
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EmployeeLoanApproveAndForward.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanApproveAndForwardResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanApproveAndForwardResource.class);

    @Inject
    private EmployeeLoanApproveAndForwardRepository employeeLoanApproveAndForwardRepository;

    @Inject
    private EmployeeLoanApproveAndForwardSearchRepository employeeLoanApproveAndForwardSearchRepository;

    @Inject
    private AuthorityRepository authorityRepository;

    @Inject
    private EmployeeLoanRequisitionFormRepository employeeLoanRequisitionFormRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    InstituteRepository instituteRepository;

    /**
     * POST  /employeeLoanApproveAndForwards -> Create a new employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> createEmployeeLoanApproveAndForward(@Valid @RequestBody EmployeeLoanApproveAndForward employeeLoanApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanApproveAndForward : {}", employeeLoanApproveAndForward);

        if (employeeLoanApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanApproveAndForward cannot already have an ID").body(null);
        }
        Integer approveStatus = 0;
        String approveBy = null;
        String ForwardTo = null;
        String logComments = null;
        String applyType = null;

        EmployeeLoanRequisitionFormResource loanRequisitionFormResource = new EmployeeLoanRequisitionFormResource();

        EmployeeLoanRequisitionForm loanRequisitionForm = employeeLoanApproveAndForward.getLoanRequisitionForm();

        if(loanRequisitionForm != null){
            applyType = loanRequisitionForm.getApplyType();
        }

        if (applyType != null){
            if (applyType.equals("VOC")){
                Integer statusCode = EmployeeLoanApplicationVocStatus.getLoanApplicationStatusByRole(employeeLoanApproveAndForward.getForwardToAuthority()).getCode();
                System.out.println("Statuscode "+statusCode);
                EmployeeLoanApplicationVocStatus forwardToStatus = EmployeeLoanApplicationVocStatus.currentLoanApplicationStatusType(statusCode);

                approveStatus = EmployeeLoanApplicationVocStatus.previousLoanApplicationStatusType(statusCode).getCode();
                ForwardTo = forwardToStatus.getRole();
                logComments = "Approved By "+EmployeeLoanApplicationVocStatus.getLoanApplicationStatusByRole(loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan()).getViewMessage() +" Forward To "+forwardToStatus.getViewMessage();
                approveBy = loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan();


            }else if(applyType.equals("Others")){
                Integer statusCode = EmployeeLoanApplicationOthersStatus.getLoanApplicationStatusByRole(employeeLoanApproveAndForward.getForwardToAuthority()).getCode();
                EmployeeLoanApplicationOthersStatus forwardToStatus = EmployeeLoanApplicationOthersStatus.currentLoanApplicationStatusType(statusCode);
                approveStatus = EmployeeLoanApplicationOthersStatus.previousLoanApplicationStatusType(statusCode).getCode();
                ForwardTo = forwardToStatus.getRole();
                logComments = "Approved By "+EmployeeLoanApplicationOthersStatus.getLoanApplicationStatusByRole(loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan()).getViewMessage() +" Approved By "+forwardToStatus.getViewMessage();
                approveBy = loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan();

            }else if (applyType.equals("DTE")){
                Integer statusCode = EmployeeLoanApplicationDTEStatus.getLoanApplicationStatusByRole(employeeLoanApproveAndForward.getForwardToAuthority()).getCode();
                EmployeeLoanApplicationDTEStatus forwardToStatus = EmployeeLoanApplicationDTEStatus.currentLoanApplicationStatusType(statusCode);
                approveStatus = EmployeeLoanApplicationDTEStatus.previousLoanApplicationStatusType(statusCode).getCode();
                ForwardTo = forwardToStatus.getRole();
                logComments = "Approved By "+ EmployeeLoanApplicationDTEStatus.getLoanApplicationStatusByRole(loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan()).getViewMessage() +" Approved By "+forwardToStatus.getViewMessage();
                approveBy = loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan();

            }else{
                approveStatus = 0;
            }
        }

        DateResource dataResource =  new DateResource();
        employeeLoanApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanApproveAndForward.setCreateDate(dataResource.getDateAsLocalDate());
        employeeLoanApproveAndForward.setApproveStatus(approveStatus);
        employeeLoanApproveAndForward.setApproveByAuthority(approveBy);
        employeeLoanApproveAndForward.setForwardToAuthority(ForwardTo);
        employeeLoanApproveAndForward.setLogComments(logComments);
        employeeLoanApproveAndForward.setStatus(true);

        EmployeeLoanApproveAndForward result = employeeLoanApproveAndForwardRepository.save(employeeLoanApproveAndForward);
        if (result.getId() != null){
            loanRequisitionForm.setApproveStatus(approveStatus);
            employeeLoanRequisitionFormRepository.save(loanRequisitionForm);
        }
        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
            NotificationStep notificationSteps = new NotificationStep();
            notificationSteps.setNotification("Loan Approved.");
            notificationSteps.setStatus(true);
            notificationSteps.setUrls("employeeLoanInfo.dashboard");
            notificationSteps.setUserId(employeeLoanApproveAndForward.getLoanRequisitionForm().getEmployeeInfo().getId());
            notificationStepRepository.save(notificationSteps);
        }

        employeeLoanApproveAndForwardSearchRepository.save(result);

        return ResponseEntity.created(new URI("/api/employeeLoanApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanApproveAndForward", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanApproveAndForwards -> Updates an existing employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> updateEmployeeLoanApproveAndForward(@Valid @RequestBody EmployeeLoanApproveAndForward employeeLoanApproveAndForward) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanApproveAndForward : {}", employeeLoanApproveAndForward);
        if (employeeLoanApproveAndForward.getId() == null) {
            return createEmployeeLoanApproveAndForward(employeeLoanApproveAndForward);
        }
        EmployeeLoanApproveAndForward result = employeeLoanApproveAndForwardRepository.save(employeeLoanApproveAndForward);
        employeeLoanApproveAndForwardSearchRepository.save(employeeLoanApproveAndForward);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanApproveAndForward", employeeLoanApproveAndForward.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanApproveAndForwards -> get all the employeeLoanApproveAndForwards.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanApproveAndForward>> getAllEmployeeLoanApproveAndForwards(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanApproveAndForward> page = employeeLoanApproveAndForwardRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanApproveAndForwards");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanApproveAndForwards/:id -> get the "id" employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> getEmployeeLoanApproveAndForward(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanApproveAndForward : {}", id);
        return Optional.ofNullable(employeeLoanApproveAndForwardRepository.findOne(id))
            .map(employeeLoanApproveAndForward -> new ResponseEntity<>(
                employeeLoanApproveAndForward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanApproveAndForwards/:id -> delete the "id" employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanApproveAndForward(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanApproveAndForward : {}", id);
        employeeLoanApproveAndForwardRepository.delete(id);
        employeeLoanApproveAndForwardSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanApproveAndForward", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanApproveAndForwards/:query -> search for the employeeLoanApproveAndForward corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanApproveAndForwards/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanApproveAndForward> searchEmployeeLoanApproveAndForwards(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanApproveAndForwardSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/findUserAuthority",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Authority> getUserAuthority() {
        log.debug("REST request to get Authority From Loan Approve and Forward : {}");

        return authorityRepository.findAll();

    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/employeeLoanDecline",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> employeeLoanDecline(@Valid @RequestBody EmployeeLoanApproveAndForward employeeLoanApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoan Decline : {}", employeeLoanApproveAndForward);
        if (employeeLoanApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanApproveAndForward cannot already have an ID").body(null);
        }
        Integer approveStatus = EmployeeLoanApplicationVocStatus.DECLINED.getCode();  // decline

        String declineBy = null;
        String logComments = null;

        EmployeeLoanRequisitionFormResource loanRequisitionFormResource = new EmployeeLoanRequisitionFormResource();

        declineBy = loanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan();


//        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)){
//            declineBy = AuthoritiesConstants.INSTITUTE;
//            logComments = "INSTITUTE";
//        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)){
//            declineBy = AuthoritiesConstants.AD;
//            logComments = "AD";
//        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DIRECTOR)){
//            declineBy = AuthoritiesConstants.DIRECTOR;
//            logComments = "DIRECTOR";
//        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
//            declineBy = AuthoritiesConstants.DG;
//            logComments = "DG";
//        }else {
//            declineBy = "None";
//            logComments = "None";
//        }


        DateResource dataResource =  new DateResource();
        employeeLoanApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanApproveAndForward.setCreateDate(dataResource.getDateAsLocalDate());
        employeeLoanApproveAndForward.setApproveStatus(approveStatus);

        EmployeeLoanRequisitionForm loanRequisitionForm = employeeLoanApproveAndForward.getLoanRequisitionForm();
        loanRequisitionForm.setApproveStatus(approveStatus);
        employeeLoanApproveAndForward.setApproveByAuthority(declineBy);
        employeeLoanApproveAndForward.setLogComments("Loan Application Decline By "+declineBy);
        employeeLoanApproveAndForward.setStatus(true);

        EmployeeLoanApproveAndForward result = employeeLoanApproveAndForwardRepository.save(employeeLoanApproveAndForward);
        if (result.getId() != null){
            employeeLoanRequisitionFormRepository.save(loanRequisitionForm);
        }
        // InstEmployee instEmp = instEmployeeRepository.findOne(loanRequisitionForm.getInstituteEmployeeId());

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Loan Application Decline.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employeeLoanInfo.dashboard");
        notificationSteps.setUserId(loanRequisitionForm.getEmployeeInfo().getId());
        notificationStepRepository.save(notificationSteps);

        employeeLoanApproveAndForwardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanApproveAndForward", result.getId().toString()))
            .body(result);

    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/employeeLoanReject",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> employeeLoanReject(@Valid @RequestBody EmployeeLoanApproveAndForward employeeLoanApproveAndForward) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoan Reject : {}", employeeLoanApproveAndForward);
        if (employeeLoanApproveAndForward.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanApproveAndForward cannot already have an ID").body(null);
        }
        Integer approveStatus = -1;

        DateResource dataResource =  new DateResource();
        employeeLoanApproveAndForward.setCreateBy(SecurityUtils.getCurrentUserId());
        employeeLoanApproveAndForward.setCreateDate(dataResource.getDateAsLocalDate());
        employeeLoanApproveAndForward.setApproveStatus(approveStatus);
        employeeLoanApproveAndForward.setLogComments("Loan Application Rejected");

        EmployeeLoanRequisitionForm loanRequisitionForm = employeeLoanApproveAndForward.getLoanRequisitionForm();
        loanRequisitionForm.setApproveStatus(approveStatus);

        EmployeeLoanApproveAndForward result = employeeLoanApproveAndForwardRepository.save(employeeLoanApproveAndForward);
        if (result.getId() != null){
            employeeLoanRequisitionFormRepository.save(loanRequisitionForm);
        }
        // InstEmployee instEmp = instEmployeeRepository.findOne(loanRequisitionForm.getEmployeeInfo().getId());


        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification("Loan Rejected.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employeeLoanInfo.dashboard");
        notificationSteps.setUserId(loanRequisitionForm.getEmployeeInfo().getId());
        notificationStepRepository.save(notificationSteps);

        employeeLoanApproveAndForwardSearchRepository.save(result);
        return ResponseEntity.created(new URI("/api/employeeLoanApproveAndForwards/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanApproveAndForward", result.getId().toString()))
            .body(result);

    }

    /**
     * GET  /employeeLoanApproveAndForwards/:id -> get the "id" employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards/GetApproveAmountByReqCode/{loanReqCode}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanApproveAndForward> getApproveAmountByRequisitionCode(@PathVariable String loanReqCode) {
        log.debug("REST request to get ApproveAmountByRequisitionCode : {}", loanReqCode);
        return Optional.ofNullable(employeeLoanApproveAndForwardRepository.findByRequisitionCodeAndApproveStatus(loanReqCode,5))
            .map(employeeLoanApproveAndForward -> new ResponseEntity<>(
                employeeLoanApproveAndForward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /employeeLoanApproveAndForwards/:id -> get the "id" employeeLoanApproveAndForward.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards/GetForwardMessage/{approveStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Map<String,String>> getForwardMessage(@PathVariable Integer approveStatus) {
        log.debug("REST request to  Get ForwardMessage : {}", approveStatus);
        Map<String,String> loanStatusMap = new HashMap<>();
        String prevStatus = null;
        String nextStatus = null;
        // Get previous and next status
        prevStatus = EmployeeLoanApplicationVocStatus.previousLoanApplicationStatusType(approveStatus+1).getViewMessage();
        if(approveStatus < EmployeeLoanApplicationVocStatus.TOTAL) {
             nextStatus = EmployeeLoanApplicationVocStatus.nextLoanApplicationStatusType(approveStatus + 1).getViewMessage();
        }else if(approveStatus == EmployeeLoanApplicationVocStatus.TOTAL){
            nextStatus = "Finally Approved";
        }else{
            nextStatus = null;
        }

        loanStatusMap.put("prev",prevStatus);
        loanStatusMap.put("next",nextStatus);

        return Optional.ofNullable(loanStatusMap)
            .map(employeeLoanApproveAndForward -> new ResponseEntity<>(
                employeeLoanApproveAndForward,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * GET  /employeeLoanApproveAndForwards -> get all the employeeLoanApproveAndForwards.
     */
    @RequestMapping(value = "/employeeLoanApproveAndForwards/getByRequisitionId/{loanReqId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanApproveAndForward> getAllByLoanRequisitionId(@PathVariable Long loanReqId)
        throws URISyntaxException {
        List<EmployeeLoanApproveAndForward> list = employeeLoanApproveAndForwardRepository.getByReqId(loanReqId);
        return list;
    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/getEmployeeLoanForVocApprovalRole",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public EmployeeLoanApplicationVocStatus [] getEmployeeLoanApprovalRole() throws URISyntaxException {
        EmployeeLoanApplicationVocStatus [] vocStatusRoleList = null ;
        EmployeeLoanRequisitionFormResource employeeLoanRequisitionFormResource = new EmployeeLoanRequisitionFormResource();
        vocStatusRoleList = EmployeeLoanApplicationVocStatus.getVocStatusList;
        if (vocStatusRoleList != null) {
            List<EmployeeLoanApplicationVocStatus> list = new ArrayList<EmployeeLoanApplicationVocStatus>(Arrays.asList(vocStatusRoleList));
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(EmployeeLoanApplicationVocStatus.getLoanApplicationStatusByRole(employeeLoanRequisitionFormResource.getCurrentUserRoleForEmployeeLoan()))){
                    list.remove(i);
                }
            }
            vocStatusRoleList = list.toArray(new EmployeeLoanApplicationVocStatus[list.size()]);
        }else{
            vocStatusRoleList = null;
        }
        return vocStatusRoleList;
    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/getEmployeeLoanForOthersApprovalRole",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public EmployeeLoanApplicationOthersStatus [] getEmployeeLoanForOthersApprovalRole() throws URISyntaxException {
        EmployeeLoanApplicationOthersStatus [] othersStatusRoleList = null;
        othersStatusRoleList = EmployeeLoanApplicationOthersStatus.getOthersStatusList;
        return othersStatusRoleList;
    }

    @RequestMapping(value = "/employeeLoanApproveAndForwards/getEmployeeLoanForDteApprovalRole",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public EmployeeLoanApplicationDTEStatus [] getEmployeeLoanForDteApprovalRole() throws URISyntaxException {
        EmployeeLoanApplicationDTEStatus [] dteStatusRoleList = null;
        dteStatusRoleList = EmployeeLoanApplicationDTEStatus.getDTEStatusList;
        return dteStatusRoleList;
    }

}
