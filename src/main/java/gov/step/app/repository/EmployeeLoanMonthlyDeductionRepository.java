package gov.step.app.repository;

import gov.step.app.domain.EmployeeLoanMonthlyDeduction;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the EmployeeLoanMonthlyDeduction entity.
 */
public interface EmployeeLoanMonthlyDeductionRepository extends JpaRepository<EmployeeLoanMonthlyDeduction,Long> {

}
