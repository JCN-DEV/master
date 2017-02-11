package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanApproveAndForward;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanApproveAndForward entity.
 */
public interface EmployeeLoanApproveAndForwardRepository extends JpaRepository<EmployeeLoanApproveAndForward,Long> {

    @Query("select employeeLoanApproveAndForward from EmployeeLoanApproveAndForward employeeLoanApproveAndForward where employeeLoanApproveAndForward.loanRequisitionForm.loanRequisitionCode  = :loanReqCode and employeeLoanApproveAndForward.approveStatus = :approveStatus")
    EmployeeLoanApproveAndForward findByRequisitionCodeAndApproveStatus(@Param("loanReqCode") String loanReqCode,@Param("approveStatus") Integer approveStatus);

    @Query("select employeeLoanApproveAndForward from EmployeeLoanApproveAndForward employeeLoanApproveAndForward where employeeLoanApproveAndForward.loanRequisitionForm.id  = :loanReqId and employeeLoanApproveAndForward.approveStatus = :approveStatus")
    EmployeeLoanApproveAndForward getByReqIdAndApproveStatus(@Param("loanReqId") Long loanReqCode,@Param("approveStatus") Integer approveStatus);

    @Query("select employeeLoanApproveAndForward from EmployeeLoanApproveAndForward employeeLoanApproveAndForward where employeeLoanApproveAndForward.loanRequisitionForm.id  = :loanReqId order by employeeLoanApproveAndForward.id desc")
    List<EmployeeLoanApproveAndForward>  getByReqId(@Param("loanReqId") Long loanReqId);



}
