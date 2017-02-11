package gov.step.app.repository;

import gov.step.app.domain.PfmsDeduction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsDeduction entity.
 */
public interface PfmsDeductionRepository extends JpaRepository<PfmsDeduction,Long> {
    @Query("select modelInfo from PfmsDeduction modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsDeduction> getPfmsDeductionListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
