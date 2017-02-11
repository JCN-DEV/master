package gov.step.app.repository;

import gov.step.app.domain.HrEmpProfMemberInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpProfMemberInfo entity.
 */
public interface HrEmpProfMemberInfoRepository extends JpaRepository<HrEmpProfMemberInfo,Long>
{
    @Query("select modelInfo from HrEmpProfMemberInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpProfMemberInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpProfMemberInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpProfMemberInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpProfMemberInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
