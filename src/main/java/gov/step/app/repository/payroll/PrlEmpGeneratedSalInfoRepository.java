package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlEmpGeneratedSalInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlEmpGeneratedSalInfo entity.
 */
public interface PrlEmpGeneratedSalInfoRepository extends JpaRepository<PrlEmpGeneratedSalInfo,Long>
{
    @Query("select modelInfo from PrlEmpGeneratedSalInfo modelInfo where lower(modelInfo.salaryInfo.monthName) = lower(:month) AND modelInfo.salaryInfo.yearName =:year AND modelInfo.employeeInfo.id=:empid AND lower(modelInfo.salaryInfo.salaryType) = lower(:salType)")
    List<PrlEmpGeneratedSalInfo> findOneByMonthYearEmployee(@Param("month") String month, @Param("year") Long year, @Param("empid") Long empid, @Param("salType") String salType);
}
