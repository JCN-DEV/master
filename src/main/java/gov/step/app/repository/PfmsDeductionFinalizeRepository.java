package gov.step.app.repository;

import gov.step.app.domain.PfmsDeductionFinalize;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsDeductionFinalize entity.
 */
public interface PfmsDeductionFinalizeRepository extends JpaRepository<PfmsDeductionFinalize,Long> {
    @Query("select modelInfo from PfmsDeductionFinalize modelInfo where modelInfo.employeeInfo.id = :employeeInfoId")
    List<PfmsDeductionFinalize> getPfmsDeductionFinalizeListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
