package gov.step.app.repository;

import gov.step.app.domain.HrEmpIncrementInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpIncrementInfo entity.
 */
public interface HrEmpIncrementInfoRepository extends JpaRepository<HrEmpIncrementInfo,Long>
{
    @Query("select modelInfo from HrEmpIncrementInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    HrEmpIncrementInfo findOneByEmployeeIsCurrentUser();
}
