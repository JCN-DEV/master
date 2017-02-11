package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlEmpGenSalDetailInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlEmpGenSalDetailInfo entity.
 */
public interface PrlEmpGenSalDetailInfoRepository extends JpaRepository<PrlEmpGenSalDetailInfo,Long>
{
    @Query("select modelInfo from PrlEmpGenSalDetailInfo modelInfo where modelInfo.employeeSalaryInfo.id =:empSalId ")
    List<PrlEmpGenSalDetailInfo> findAllByEmpSalaryId(@Param("empSalId") Long empSalId);

}
