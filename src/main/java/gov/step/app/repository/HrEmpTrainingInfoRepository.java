package gov.step.app.repository;

import gov.step.app.domain.HrEmpTrainingInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpTrainingInfo entity.
 */
public interface HrEmpTrainingInfoRepository extends JpaRepository<HrEmpTrainingInfo,Long>
{
    @Query("select modelInfo from HrEmpTrainingInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpTrainingInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpTrainingInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpTrainingInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpTrainingInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
