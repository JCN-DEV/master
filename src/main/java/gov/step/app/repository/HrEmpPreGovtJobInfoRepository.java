package gov.step.app.repository;

import gov.step.app.domain.HrEmpPreGovtJobInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpPreGovtJobInfo entity.
 */
public interface HrEmpPreGovtJobInfoRepository extends JpaRepository<HrEmpPreGovtJobInfo,Long>
{
    @Query("select modelInfo from HrEmpPreGovtJobInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpPreGovtJobInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpPreGovtJobInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpPreGovtJobInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpPreGovtJobInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
