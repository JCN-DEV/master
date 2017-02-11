package gov.step.app.repository;

import gov.step.app.domain.HrEmpPromotionInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpPromotionInfo entity.
 */
public interface HrEmpPromotionInfoRepository extends JpaRepository<HrEmpPromotionInfo,Long>
{
    @Query("select modelInfo from HrEmpPromotionInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    List<HrEmpPromotionInfo> findAllByEmployeeIsCurrentUser();

    List<HrEmpPromotionInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpPromotionInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpPromotionInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
