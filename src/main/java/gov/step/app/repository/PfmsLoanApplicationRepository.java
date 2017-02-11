package gov.step.app.repository;

import gov.step.app.domain.PfmsLoanApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsLoanApplication entity.
 */
public interface PfmsLoanApplicationRepository extends JpaRepository<PfmsLoanApplication,Long> {
    @Query("select modelInfo from PfmsLoanApplication modelInfo where modelInfo.employeeInfo.id = :employeeId and modelInfo.activeStatus = true")
    List<PfmsLoanApplication> pfmsLoanApplicationByEmployee(@Param("employeeId") long employeeId);

}
