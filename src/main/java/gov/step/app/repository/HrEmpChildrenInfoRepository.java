package gov.step.app.repository;

import gov.step.app.domain.HrEmpChildrenInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrEmpChildrenInfo entity.
 */
public interface HrEmpChildrenInfoRepository extends JpaRepository<HrEmpChildrenInfo,Long>
{
    @Query("select modelInfo from HrEmpChildrenInfo modelInfo where modelInfo.employeeInfo.user.login = ?#{principal.username} AND modelInfo.employeeInfo.activeAccount=true")
    Page<HrEmpChildrenInfo> findAllByEmployeeIsCurrentUser(Pageable pageable);

    List<HrEmpChildrenInfo> findAllByLogStatusAndActiveStatus(Long logStatus, boolean activeStatus);

    @Query("select modelInfo from HrEmpChildrenInfo modelInfo where logStatus=:logStatus order by updateDate asc")
    List<HrEmpChildrenInfo> findAllByLogStatus(@Param("logStatus") Long logStatus);
}
