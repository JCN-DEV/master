package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlSalaryStructureInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlSalaryStructureInfo entity.
 */
public interface PrlSalaryStructureInfoRepository extends JpaRepository<PrlSalaryStructureInfo,Long>
{
    @Query("select modelInfo from PrlSalaryStructureInfo modelInfo where employeeInfo.id = :empid")
    List<PrlSalaryStructureInfo> findByEmployee(@Param("empid") long empid);
}
