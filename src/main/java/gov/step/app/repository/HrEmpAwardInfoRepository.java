package gov.step.app.repository;

import gov.step.app.domain.HrEmpAwardInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpAwardInfo entity.
 */
public interface HrEmpAwardInfoRepository extends JpaRepository<HrEmpAwardInfo,Long>
{
    @Query("select modelInfo from HrEmpAwardInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpAwardInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpAwardInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpAwardInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpAwardInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
