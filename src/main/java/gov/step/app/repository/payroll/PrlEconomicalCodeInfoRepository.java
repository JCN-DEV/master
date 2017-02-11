package gov.step.app.repository.payroll;

import gov.step.app.domain.payroll.PrlEconomicalCodeInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the PrlEconomicalCodeInfo entity.
 */
public interface PrlEconomicalCodeInfoRepository extends JpaRepository<PrlEconomicalCodeInfo,Long>
{
    @Query("select modelInfo from PrlEconomicalCodeInfo modelInfo WHERE codeType IN ('Allowance','Salary')")
    List<PrlEconomicalCodeInfo> findAllowanceAndSalaryCode();

    @Query("select modelInfo from PrlEconomicalCodeInfo modelInfo WHERE codeName=:code")
    List<PrlEconomicalCodeInfo> findAllowanceByCode(@Param("code") String code);

    @Query("select modelInfo from PrlEconomicalCodeInfo modelInfo WHERE codeName IN :codeList")
    List<PrlEconomicalCodeInfo> findAllowanceListByCodes(@Param("codeList") List<String> codeList);

    @Query("select modelInfo from PrlEconomicalCodeInfo modelInfo")
    List<PrlEconomicalCodeInfo> findAllCode();

}
