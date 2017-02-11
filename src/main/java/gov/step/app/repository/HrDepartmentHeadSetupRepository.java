package gov.step.app.repository;

import gov.step.app.domain.HrDepartmentHeadSetup;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrDepartmentHeadSetup entity.
 */
public interface HrDepartmentHeadSetupRepository extends JpaRepository<HrDepartmentHeadSetup,Long>
{
    @Query("select modelInfo from HrDepartmentHeadSetup modelInfo where activeStatus =:activeStatus order by departmentName")
    Page<HrDepartmentHeadSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDepartmentHeadSetup modelInfo where activeStatus =:activeStatus order by departmentName")
    List<HrDepartmentHeadSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDepartmentHeadSetup modelInfo where lower(modelInfo.departmentCode) = :departmentCode ")
    Optional<HrDepartmentHeadSetup> findOneByDepartmentCode(@Param("departmentCode") String departmentCode);

    @Query("select modelInfo from HrDepartmentHeadSetup modelInfo where lower(modelInfo.departmentName) = :departmentName ")
    Optional<HrDepartmentHeadSetup> findOneByDepartmentName(@Param("departmentName") String departmentName);
}
