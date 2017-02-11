package gov.step.app.repository;

import gov.step.app.domain.HrEducationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEducationInfo entity.
 */
public interface HrEducationInfoRepository extends JpaRepository<HrEducationInfo,Long>
{
    @Query("select modelInfo from HrEducationInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    List<HrEducationInfo> findAllByEmployeeIsCurrentUser();

    List<HrEducationInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEducationInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEducationInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
