package gov.step.app.web.rest;

import com.codahale.metrics.annotation.Timed;
import gov.step.app.domain.*;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationDTEStatus;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationOthersStatus;
import gov.step.app.domain.enumeration.EmployeeLoanApplicationVocStatus;
import gov.step.app.repository.*;
import gov.step.app.repository.search.EmployeeLoanRequisitionFormSearchRepository;
import gov.step.app.security.AuthoritiesConstants;
import gov.step.app.security.SecurityUtils;
import gov.step.app.web.rest.jdbc.dao.ELMSJdbcDao;
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
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing EmployeeLoanRequisitionForm.
 */
@RestController
@RequestMapping("/api")
public class EmployeeLoanRequisitionFormResource {

    private final Logger log = LoggerFactory.getLogger(EmployeeLoanRequisitionFormResource.class);

    @Inject
    private EmployeeLoanRequisitionFormRepository employeeLoanRequisitionFormRepository;

    @Inject
    private EmployeeLoanRequisitionFormSearchRepository employeeLoanRequisitionFormSearchRepository;

    @Inject
    private EmployeeLoanRulesSetupRepository employeeLoanRulesSetupRepository;

    @Inject
    private NotificationStepRepository notificationStepRepository;

    @Inject
    private InstituteRepository instituteRepository;

    @Inject
    private HrEmployeeInfoRepository hrEmployeeInfoRepository;

    @Inject
    private HrEmploymentInfoRepository hrEmploymentInfoRepository;

    @Inject
    private ELMSJdbcDao elmsJdbcDao;


    /**
     * POST  /employeeLoanRequisitionForms -> Create a new employeeLoanRequisitionForm.
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRequisitionForm> createEmployeeLoanRequisitionForm(@Valid @RequestBody EmployeeLoanRequisitionForm employeeLoanRequisitionForm) throws URISyntaxException {
        log.debug("REST request to save EmployeeLoanRequisitionForm : {}", employeeLoanRequisitionForm);
        Integer approveStatus = 0;
        Long userIdToSendNotification = 0L;
        String type = null;

        HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser();
        if (employeeLoanRequisitionForm.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new employeeLoanRequisitionForm cannot already have an ID").body(null);
        }
        TransactionIdResource transactionId = new TransactionIdResource();
        employeeLoanRequisitionForm.setLoanRequisitionCode(transactionId.getGeneratedid("elms-req-"));
        employeeLoanRequisitionForm.setCreateBy(SecurityUtils.getCurrentUserId());


        if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTEMP)){
            approveStatus = EmployeeLoanApplicationVocStatus.APPLICATION_COMPLETED.getCode();
            String instLevelName = hrEmployeeInfo.getInstitute().getInstLevel().getName().trim();
            System.out.println("Institute level Name "+instLevelName);

            if(instLevelName.equals("Madrasha (VOC)") || instLevelName.equals("SSC (VOC)")){
                type = "VOC"; // VOC Institute
            }else {
                type = "Others"; // PolyTechnique and Others Institute
            }
            userIdToSendNotification = hrEmployeeInfo.getInstitute().getUser().getId();
        }
        else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.USER)) {
            approveStatus = EmployeeLoanApplicationVocStatus.APPLICATION_COMPLETED.getCode();
            type = "DTE"; // DTE  Employee;
            userIdToSendNotification = 62L; // go to AD
        }
// else if(SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD)){
//            approveStatus = 3;
//            userIdToSendNotification = 63L; // GO TO DIRECTOR
//        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DIRECTOR)){
//            approveStatus = 4; //   go to DG
//            userIdToSendNotification = 64L;
//        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)){
//            approveStatus = 5;
//            userIdToSendNotification = 64L;
//        }
        employeeLoanRequisitionForm.setApproveStatus(approveStatus);
        employeeLoanRequisitionForm.setEmployeeInfo(hrEmployeeInfo);
        employeeLoanRequisitionForm.setStatus(true);
        employeeLoanRequisitionForm.setApplyType(type);

        EmployeeLoanRequisitionForm result = employeeLoanRequisitionFormRepository.save(employeeLoanRequisitionForm);
        employeeLoanRequisitionFormSearchRepository.save(result);

        NotificationStep notificationSteps = new NotificationStep();
        notificationSteps.setNotification(hrEmployeeInfo.getFullName()+" has applied for Loan.");
        notificationSteps.setStatus(true);
        notificationSteps.setUrls("employeeLoanInfo.dashboard");
        notificationSteps.setUserId(userIdToSendNotification);
        notificationStepRepository.save(notificationSteps);

        return ResponseEntity.created(new URI("/api/employeeLoanRequisitionForms/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("employeeLoanRequisitionForm", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /employeeLoanRequisitionForms -> Updates an existing employeeLoanRequisitionForm.
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRequisitionForm> updateEmployeeLoanRequisitionForm(@Valid @RequestBody EmployeeLoanRequisitionForm employeeLoanRequisitionForm) throws URISyntaxException {
        log.debug("REST request to update EmployeeLoanRequisitionForm : {}", employeeLoanRequisitionForm);
        if (employeeLoanRequisitionForm.getId() == null) {
            return createEmployeeLoanRequisitionForm(employeeLoanRequisitionForm);
        }
        EmployeeLoanRequisitionForm result = employeeLoanRequisitionFormRepository.save(employeeLoanRequisitionForm);
        employeeLoanRequisitionFormSearchRepository.save(employeeLoanRequisitionForm);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("employeeLoanRequisitionForm", employeeLoanRequisitionForm.getId().toString()))
            .body(result);
    }

    /**
     * GET  /employeeLoanRequisitionForms -> get all the employeeLoanRequisitionForms.
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanRequisitionForm>> getAllEmployeeLoanRequisitionForms(Pageable pageable)
        throws URISyntaxException {
        Page<EmployeeLoanRequisitionForm> page = employeeLoanRequisitionFormRepository.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/employeeLoanRequisitionForms");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /employeeLoanRequisitionForms/:id -> get the "id" employeeLoanRequisitionForm.
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRequisitionForm> getEmployeeLoanRequisitionForm(@PathVariable Long id) {
        log.debug("REST request to get EmployeeLoanRequisitionForm : {}", id);
        return Optional.ofNullable(employeeLoanRequisitionFormRepository.findOne(id))
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /employeeLoanRequisitionForms/:id -> delete the "id" employeeLoanRequisitionForm.
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteEmployeeLoanRequisitionForm(@PathVariable Long id) {
        log.debug("REST request to delete EmployeeLoanRequisitionForm : {}", id);
        employeeLoanRequisitionFormRepository.delete(id);
        employeeLoanRequisitionFormSearchRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("employeeLoanRequisitionForm", id.toString())).build();
    }

    /**
     * SEARCH  /_search/employeeLoanRequisitionForms/:query -> search for the employeeLoanRequisitionForm corresponding
     * to the query.
     */
    @RequestMapping(value = "/_search/employeeLoanRequisitionForms/{query}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanRequisitionForm> searchEmployeeLoanRequisitionForms(@PathVariable String query) {
        return StreamSupport
            .stream(employeeLoanRequisitionFormSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }


    /**
     * Check Employee Loan amount and Basic Salary
     */
    @RequestMapping(value = "/employeeLoanRequisitionForms/validateLoanAmountAndBasicSalary/{loanRuleId}/{amount}/{employeeInfoId}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public Integer checkLoanAmountAndBasicSalary(@PathVariable Long loanRuleId,
                                                @PathVariable Long amount,
                                                @PathVariable Long employeeInfoId) {
        log.debug("REST request to get Check Employee Loan From API: {}", loanRuleId);
        Integer status = 0;

        EmployeeLoanRulesSetup employeeLoanRulesSetup = employeeLoanRulesSetupRepository.findOne(loanRuleId);
        HrEmployeeInfo hrEmployeeInfo = hrEmployeeInfoRepository.findOneByEmployeeUserIsCurrentUser();
        List<HrEmploymentInfo> hrEmploymentInfo = hrEmploymentInfoRepository.findAllByEmployeeIsCurrentUser();
        // InstEmployee instEmployee = instEmployeeRepository.findOne(employeeInstId);

        System.out.println("employee Info ID --- "+employeeInfoId);
        System.out.println("HR Employee "+hrEmployeeInfo.getEmployementType().getTypeName());
        // System.out.println("Pay Scale ------ "+instEmployee.getPayScale().getBasicAmount()) ;
        if (employeeLoanRulesSetup != null) {
            System.out.println("****************************************************");
//            System.out.println("Type "+instEmployee.getInstitute().getType());
//            System.out.println("Position "+(instEmployee.getTypeOfPosition()));

            // Check institute is govt and full time employee
            if(hrEmployeeInfo.getEmployementType().getTypeName().equals("Full Time")){
                System.out.println("///////////////////////////////////");
                // check loan amount is less than loan rules , if true then it check basic salary otherwise it will return status 3
                if (employeeLoanRulesSetup.getMaximumWithdrawal() >= amount) {
                    System.out.println("Basic amount -- "+ BigDecimal.valueOf(employeeLoanRulesSetup.getMinimumAmountBasic()));
                    System.out.println(" Compare -- " + hrEmploymentInfo.get(0).getPresentPayScale().getBasicPayScale().compareTo(BigDecimal.valueOf(employeeLoanRulesSetup.getMinimumAmountBasic())));
                    // Compare Employee basic salary with Loan Rules Basic Amount
                    if (hrEmploymentInfo.get(0).getPresentPayScale().getBasicPayScale().compareTo(BigDecimal.valueOf(employeeLoanRulesSetup.getMinimumAmountBasic())) >= 0) {
                        status = 1; // status 1 means Basic Salary meet the rules
                    } else {
                        status = 2; // status 2 means Basic Salary does'nt meet the condition
                    }
                }else{
                    status = 3; // loan amount is greater than loan Rules
                }
            }else{
                status = 4; // can't apply because he/she is not a govt full time employee
            }
        }
        return status;
    }

    @RequestMapping(value = "/employeeLoanRequisitionForms/findLoanRequisitionDataByHrEmpID/{employeeInfoID}/{approveStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<EmployeeLoanRequisitionForm> getLoanRequisitionFormByInstEmp(@PathVariable Long employeeInfoID,@PathVariable Integer approveStatus) {
        log.debug("REST request to get EmployeeLoanRequisitionForm By HR Employee ID : {}", employeeInfoID);
        return Optional.ofNullable(employeeLoanRequisitionFormRepository.findByHrEmpID(employeeInfoID,approveStatus))
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/employeeLoanRequisitionForms/searchRequisitionDataByHrEmpID/{employeeInfoID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanRequisitionForm>> searchLoanRequisitionFormByHrEmp(@PathVariable Long employeeInfoID) {
        log.debug("REST request to get EmployeeLoanRequisitionForm By institute Employee ID : {}", employeeInfoID);
        return Optional.ofNullable(employeeLoanRequisitionFormRepository.findReqDataByHrEmpID(employeeInfoID))
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/employeeLoanRequisitionForms/RequisitionDataByInstitute",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanRequisitionForm>> RequisitionDataByInstitute() {
        log.debug("REST request to get EmployeeLoanRequisitionForm By institute Employee :");

        return Optional.ofNullable(employeeLoanRequisitionFormRepository.findReqDataByApproveStatus())
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @RequestMapping(value = "/employeeLoanRequisitionForms/getLoanPendingDataForApprove/{applyType}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<Map<String,Object>>> getLoanPendingDataForApprove(@PathVariable String applyType) throws URISyntaxException{
        log.debug("REST request to get EmployeeLoanPendingData For Approve By ApplyType: {}",applyType);

        int approveStatus = 0;
        List<Integer> approveStatusList = new ArrayList<Integer>();
        List<Map<String,Object>> loanPendingListPage = null;
        Integer activeStatus = 1;
            if(applyType.equals("VOC")) {
                if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
                    // status 1 then institute get loan application data
                    System.out.println("Role --- " + AuthoritiesConstants.INSTITUTE);
                    approveStatus = EmployeeLoanApplicationVocStatus.APPLICATION_COMPLETED.getCode();
                    Institute institute = instituteRepository.findOneByUserIsCurrentUser();
                    System.out.println("Institute ID " + institute.getId());
                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByInstitute(institute.getId(), approveStatus, activeStatus, applyType);

                } else if (getCurrentUserRoleForEmployeeLoan() != null){
                    String currentRole = getCurrentUserRoleForEmployeeLoan();
                    EmployeeLoanApplicationVocStatus loanApplicationVocStatus = EmployeeLoanApplicationVocStatus.getLoanApplicationStatusByRole(currentRole);
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.previousLoanApplicationStatusType(loanApplicationVocStatus.getCode()).getCode());
                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    loanPendingListPage = null;
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPLICATION_COMPLETED.getCode());
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPROVED_BY_INSTITUTE.getCode());
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPROVED_BY_DG.getCode());
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPROVED_BY_DIRECTOR_VOC.getCode());
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPROVED_BY_AD_VOC.getCode());
                    approveStatusList.add(EmployeeLoanApplicationVocStatus.APPROVED_BY_MINISTRY.getCode());
                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                } else {
                    System.out.println("No Role --- ");
                    loanPendingListPage = null;
                }
            }else if (applyType.equals("Others")){
                if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
                    // status 1 then institute get loan application data
                    System.out.println("Role --- " + AuthoritiesConstants.INSTITUTE);

                    approveStatus = EmployeeLoanApplicationOthersStatus.APPLICATION_COMPLETED.getCode();
                    Institute institute = instituteRepository.findOneByUserIsCurrentUser();

                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByInstitute(institute.getId(), approveStatus, activeStatus, applyType);

                }else if (getCurrentUserRoleForEmployeeLoan() != null){
                    String currentRole = getCurrentUserRoleForEmployeeLoan();
                    EmployeeLoanApplicationOthersStatus loanApplicationOthersStatus = EmployeeLoanApplicationOthersStatus.getLoanApplicationStatusByRole(currentRole);
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.previousLoanApplicationStatusType(loanApplicationOthersStatus.getCode()).getCode());
                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                }
                else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPLICATION_COMPLETED.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_INSTITUTE.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_DG.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_DIRECTOR_ADMIN.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_AD3.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_AO.getCode());
                    approveStatusList.add(EmployeeLoanApplicationOthersStatus.APPROVED_BY_MINISTRY.getCode());

                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                } else {
                    System.out.println("No Role --- ");
                    loanPendingListPage = null;
                }

            }else if(applyType.equals("DTE")){
                if (getCurrentUserRoleForEmployeeLoan() != null){

                    String currentRole = getCurrentUserRoleForEmployeeLoan();
                    EmployeeLoanApplicationDTEStatus loanApplicationDTEStatus = EmployeeLoanApplicationDTEStatus.getLoanApplicationStatusByRole(currentRole);
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.previousLoanApplicationStatusType(loanApplicationDTEStatus.getCode()).getCode());
                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                }
                else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.ADMIN)) {
                    loanPendingListPage = null;
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.APPLICATION_COMPLETED.getCode());
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.APPROVED_BY_DG.getCode());
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.APPROVED_BY_DTE_ADMIN.getCode());
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.APPROVED_BY_AO.getCode());
                    approveStatusList.add(EmployeeLoanApplicationDTEStatus.APPROVED_BY_MINISTRY.getCode());

                    loanPendingListPage = elmsJdbcDao.getEmployeeLoanRequisitionDataByApproveStatus(activeStatus,applyType,approveStatusList);
                } else {
                    System.out.println("No Role --- ");
                    loanPendingListPage = null;
                }

            }else {
                System.out.println("No Role --- ");
                loanPendingListPage = null;
            }

        if(loanPendingListPage !=null){
            return new ResponseEntity<>(loanPendingListPage,HttpStatus.OK);
        }else{
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
    }



    @RequestMapping(value = "/employeeLoanRequisitionForms/loanRequisitionByApproveStatus/{approveStatus}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<List<EmployeeLoanRequisitionForm>> RequisitionDataByApproveStatus(@PathVariable Integer approveStatus) {
        log.debug("REST request to get Loan Approve List :{} ",approveStatus);

        return Optional.ofNullable(employeeLoanRequisitionFormRepository.findLoanRequisitionDataByApproveStatus(approveStatus))
            .map(employeeLoanRequisitionForm -> new ResponseEntity<>(
                employeeLoanRequisitionForm,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }




    public String getCurrentUserRoleForEmployeeLoan() {

        if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.INSTITUTE)) {
            return AuthoritiesConstants.INSTITUTE;

        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DG)) {
            return AuthoritiesConstants.DG;

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DIRECTOR_VOCATIONAL)) {
            return AuthoritiesConstants.DIRECTOR_VOCATIONAL;

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD_VOC)) {
            return AuthoritiesConstants.AD_VOC;

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.MINISTRY)) {
            return AuthoritiesConstants.MINISTRY;

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DIRECTOR_ADMIN)) {
            return AuthoritiesConstants.DIRECTOR_ADMIN;

        } else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AD3)) {
            return AuthoritiesConstants.AD3;

        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.AO)) {
            return AuthoritiesConstants.AO;

        }else if (SecurityUtils.isCurrentUserInRole(AuthoritiesConstants.DTE_ADMIN)) {
            return AuthoritiesConstants.DTE_ADMIN;

        }else {
            return null;
        }
    }

    @RequestMapping(value = "/employeeLoanRequisitionForms/checkEmployeeEligibleForLoanApplication/{employeeInfoID}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<EmployeeLoanRequisitionForm> checkEmployeeEligibleForLoanApplication(@PathVariable Long employeeInfoID) {
        log.debug("REST request to checkEmployeeEligibleForLoanApplication By Employee ID : {}", employeeInfoID);

        return employeeLoanRequisitionFormRepository.findRequisitionDataByEmployeeIdAndApproveStatus(employeeInfoID,EmployeeLoanApplicationVocStatus.REJECTED.getCode());

    }

}
