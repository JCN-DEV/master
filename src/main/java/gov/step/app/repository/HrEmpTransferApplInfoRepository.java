package gov.step.app.repository;

import gov.step.app.domain.HrEmpTransferApplInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpTransferApplInfo entity.
 */
public interface HrEmpTransferApplInfoRepository extends JpaRepository<HrEmpTransferApplInfo,Long>
{
    @Query("select modelInfo from HrEmpTransferApplInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpTransferApplInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpTransferApplInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpTransferApplInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpTransferApplInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
