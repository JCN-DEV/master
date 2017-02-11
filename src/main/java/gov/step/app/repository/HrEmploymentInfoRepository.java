package gov.step.app.repository;

import gov.step.app.domain.HrEmploymentInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmploymentInfo entity.
 */
public interface HrEmploymentInfoRepository extends JpaRepository<HrEmploymentInfo,Long>
{
    @Query("select modelInfo from HrEmploymentInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    List<HrEmploymentInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmploymentInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmploymentInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmploymentInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
