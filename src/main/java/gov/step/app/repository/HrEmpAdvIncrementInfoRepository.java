package gov.step.app.repository;

import gov.step.app.domain.HrEmpAdvIncrementInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

/**
 * Spring Data JPA repository for the HrEmpAdvIncrementInfo entity.
 */
public interface HrEmpAdvIncrementInfoRepository extends JpaRepository<HrEmpAdvIncrementInfo,Long>
{
    @Query("select modelInfo from HrEmpAdvIncrementInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    HrEmpAdvIncrementInfo findOneByEmployeeIsCurrentUser();
}
