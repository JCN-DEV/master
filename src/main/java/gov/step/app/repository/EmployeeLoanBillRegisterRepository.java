package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanBillRegister;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanBillRegister entity.
 */
public interface EmployeeLoanBillRegisterRepository extends JpaRepository<EmployeeLoanBillRegister,Long> {

    @Query("select loanBillRegister from EmployeeLoanBillRegister loanBillRegister where loanBillRegister.loanRequisitionForm.id = :loanRequisitionID and loanBillRegister.status = 1")
    EmployeeLoanBillRegister findByLoanRequisitionID(@Param("loanRequisitionID") Long loanRequisitionID);

    @Query("select loanBillRegister from EmployeeLoanBillRegister loanBillRegister where loanBillRegister.billNo = :billNo and loanBillRegister.status = 1")
    EmployeeLoanBillRegister findByBillNo(@Param("billNo") String billNo);

}
