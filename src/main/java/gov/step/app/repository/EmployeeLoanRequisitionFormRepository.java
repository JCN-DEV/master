package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanRequisitionForm;

import gov.step.app.domain.EmployeeLoanRulesSetup;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanRequisitionForm entity.
 */
public interface EmployeeLoanRequisitionFormRepository extends JpaRepository<EmployeeLoanRequisitionForm,Long> {


//    @Query("select employeeLoanRulesSetup from EmployeeLoanRulesSetup employeeLoanRulesSetup where employeeLoanRulesSetup.id =:loanRuleId " +
//                                                                                                 "and employeeLoanRulesSetup.maximumWithdrawal >=:amount")
//    List<EmployeeLoanRulesSetup> checkLoanAmount(@Param("loanRuleId") Long loanTypeID,@Param("amount") Long amount);

    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.employeeInfo.id = :employeeInfoID and loanRequisitionForm.approveStatus = :approveStatus")
    EmployeeLoanRequisitionForm findByHrEmpID(@Param("employeeInfoID") Long employeeInfoID,@Param("approveStatus") Integer approveStatus);

//    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.employeeInfo.id = :employeeInfoID and loanRequisitionForm.approveStatus = 5")
//    EmployeeLoanRequisitionForm findByHrEmpIDAndInstitute(@Param("employeeInfoID") Long employeeInfoID);

    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.employeeInfo.id = :employeeInfoID")
    List<EmployeeLoanRequisitionForm> findReqDataByHrEmpID(@Param("employeeInfoID") Long employeeInfoID);

    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.approveStatus = 0")
    List<EmployeeLoanRequisitionForm> findReqDataByApproveStatus();

//    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.instituteEmployeeId IN (:instEmployeeIDs) and loanRequisitionForm.approveStatus =:status")
//    List<EmployeeLoanRequisitionForm> findReqDataByInstIDs(@Param("instEmployeeIDs") String instEmployeeIDs,@Param("status") Integer status);

//    @Query("select loanRequisitionForm FROM EmployeeLoanRequisitionForm  loanRequisitionForm WHERE loanRequisitionForm.instituteEmployeeId in (21183151454, 17691151454, 21448151454, 21450151454, 56788151454, 60996151454)")
//    List<EmployeeLoanRequisitionForm>  findByInstituteEmployeeIdIn();

    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.approveStatus =:status")
    List<EmployeeLoanRequisitionForm> findLoanRequisitionDataByApproveStatus(@Param("status") Integer status);


    @Query("select loanRequisitionForm from EmployeeLoanRequisitionForm loanRequisitionForm where loanRequisitionForm.employeeInfo.id = :employeeInfoID and " +
                                                                        "loanRequisitionForm.approveStatus > :approveStatus and loanRequisitionForm.status = true ")
    List<EmployeeLoanRequisitionForm> findRequisitionDataByEmployeeIdAndApproveStatus(@Param("employeeInfoID") Long employeeInfoID,@Param("approveStatus") Integer approveStatus);

}
