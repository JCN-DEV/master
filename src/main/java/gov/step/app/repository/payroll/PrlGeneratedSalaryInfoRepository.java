package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlGeneratedSalaryInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlGeneratedSalaryInfo entity.
 */
public interface PrlGeneratedSalaryInfoRepository extends JpaRepository<PrlGeneratedSalaryInfo,Long>
{
    @Query("select modelInfo from PrlGeneratedSalaryInfo modelInfo where lower(modelInfo.monthName) = lower(:month) AND modelInfo.yearName =:year AND lower(modelInfo.salaryType) = lower(:salType)")
    List<PrlGeneratedSalaryInfo> findOneByMonthYearType(@Param("month") String month, @Param("year") Long year, @Param("salType") String salType);

    @Query("select modelInfo from PrlGeneratedSalaryInfo modelInfo order by createDate desc")
    List<PrlGeneratedSalaryInfo> getLastInsertedData();

}
