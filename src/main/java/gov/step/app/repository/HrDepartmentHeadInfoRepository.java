package gov.step.app.repository;

import gov.step.app.domain.HrDepartmentHeadInfo;

import gov.step.app.domain.HrDepartmentHeadSetup;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the HrDepartmentHeadInfo entity.
 */
public interface HrDepartmentHeadInfoRepository extends JpaRepository<HrDepartmentHeadInfo,Long>
{
    @Query("select modelInfo from HrDepartmentHeadInfo modelInfo where departmentInfo.id = :deptId ")
    List<HrDepartmentHeadInfo> findAllByDepartment(@Param("deptId") Long deptId);

    @Modifying
    @Transactional
    @Query("update HrDepartmentHeadInfo modelInfo set modelInfo.activeHead=:stat where modelInfo.departmentInfo.id = :deptId")
    void updateAllDepartmentHeadActiveStatus(@Param("deptId") Long deptId, @Param("stat") boolean stat);

}
