package gov.step.app.repository;

import gov.step.app.domain.HrDepartmentSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrDepartmentSetup entity.
 */
public interface HrDepartmentSetupRepository extends JpaRepository<HrDepartmentSetup,Long>
{
    @Query("select modelInfo from HrDepartmentSetup modelInfo where activeStatus =:activeStatus")
    Page<HrDepartmentSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDepartmentSetup modelInfo where activeStatus =:activeStatus")
    List<HrDepartmentSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDepartmentSetup modelInfo where modelInfo.organizationInfo.id =:workDtlId AND modelInfo.departmentInfo.id = :deptid ")
    List<HrDepartmentSetup> findOneByDepartmentHeadAndWorkArea(@Param("workDtlId") Long workDtlId, @Param("deptid") Long deptid);

    @Query("select modelInfo from HrDepartmentSetup modelInfo where modelInfo.institute.id =:instid AND modelInfo.departmentInfo.id = :deptid ")
    List<HrDepartmentSetup> findOneByDepartmentHeadAndInstitute(@Param("instid") Long instid, @Param("deptid") Long deptid);
}
