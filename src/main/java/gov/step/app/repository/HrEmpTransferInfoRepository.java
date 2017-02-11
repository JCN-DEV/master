package gov.step.app.repository;

import gov.step.app.domain.HrEmpTransferInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpTransferInfo entity.
 */
public interface HrEmpTransferInfoRepository extends JpaRepository<HrEmpTransferInfo,Long>
{
    @Query("select modelInfo from HrEmpTransferInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpTransferInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpTransferInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpTransferInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpTransferInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
