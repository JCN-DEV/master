package gov.step.app.repository;

import gov.step.app.domain.HrEmpActingDutyInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpActingDutyInfo entity.
 */
public interface HrEmpActingDutyInfoRepository extends JpaRepository<HrEmpActingDutyInfo,Long>
{
    @Query("select modelInfo from HrEmpActingDutyInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true ")
    HrEmpActingDutyInfo findOneByEmployeeIsCurrentUser();

    List<HrEmpActingDutyInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpActingDutyInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpActingDutyInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
