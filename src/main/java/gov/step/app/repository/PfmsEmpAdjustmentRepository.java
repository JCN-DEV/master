package gov.step.app.repository;

import gov.step.app.domain.PfmsEmpAdjustment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PfmsEmpAdjustment entity.
 */
public interface PfmsEmpAdjustmentRepository extends JpaRepository<PfmsEmpAdjustment,Long> {
    @Query("select modelInfo from PfmsEmpAdjustment modelInfo where modelInfo.employeeInfo.id = :employeeInfoId and modelInfo.activeStatus = true")
    List<PfmsEmpAdjustment> getPfmsEmpAdjustmentListByEmployee(@Param("employeeInfoId") long employeeInfoId);
}
