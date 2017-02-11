package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanRulesSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the EmployeeLoanRulesSetup entity.
 */
public interface EmployeeLoanRulesSetupRepository extends JpaRepository<EmployeeLoanRulesSetup,Long> {

    @Query("select employeeLoanRules from EmployeeLoanRulesSetup employeeLoanRules where employeeLoanRules.employeeLoanTypeSetup.id = :loanTypeID and employeeLoanRules.status = 1")
    List<EmployeeLoanRulesSetup> findByLoanType(@Param("loanTypeID") Long loanTypeID);

    @Query("select employeeLoanRules from EmployeeLoanRulesSetup employeeLoanRules where employeeLoanRules.loanName = :loanName")
    Optional<EmployeeLoanRulesSetup> findByLoanName(@Param("loanName") String loanName);

}
