package gov.step.app.repository;

import gov.step.app.domain.HrEmpOtherServiceInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpOtherServiceInfo entity.
 */
public interface HrEmpOtherServiceInfoRepository extends JpaRepository<HrEmpOtherServiceInfo,Long>
{
    @Query("select modelInfo from HrEmpOtherServiceInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpOtherServiceInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpOtherServiceInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpOtherServiceInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpOtherServiceInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
