package gov.step.app.repository;

import gov.step.app.domain.HrDesignationSetup;
import gov.step.app.domain.enumeration.designationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrDesignationSetup entity.
 */
public interface HrDesignationSetupRepository extends JpaRepository<HrDesignationSetup,Long>
{
    @Query("select modelInfo from HrDesignationSetup modelInfo where activeStatus =:activeStatus")
    Page<HrDesignationSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDesignationSetup modelInfo where activeStatus =:activeStatus")
    List<HrDesignationSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDesignationSetup modelInfo where modelInfo.organizationInfo.id =:workDtlId AND modelInfo.designationInfo.id = :desigId ")
    List<HrDesignationSetup> findOneByDesignationHeadAndWorkArea(@Param("workDtlId") Long workDtlId, @Param("desigId") Long desigId);

    @Query("select modelInfo from HrDesignationSetup modelInfo where modelInfo.institute.id =:instid AND modelInfo.designationInfo.id = :desigId ")
    List<HrDesignationSetup> findOneByDesignationHeadAndInstitute(@Param("instid") Long instid, @Param("desigId") Long desigId);

    @Query("select modelInfo from HrDesignationSetup modelInfo where modelInfo.desigType =:desigType AND modelInfo.instLevel.id =:levelId AND modelInfo.instCategory.id = :catId ")
    List<HrDesignationSetup> findOneByInstLevelAndInstCatAndDesigLevel(@Param("desigType") designationType desigType, @Param("levelId") Long instid, @Param("catId") Long desigId);

    @Query("select modelInfo from HrDesignationSetup modelInfo where desigType =:desigType AND activeStatus =:activeStatus")
    List<HrDesignationSetup> findAllByDesigType(@Param("desigType") designationType desigType, @Param("activeStatus") boolean activeStatus);

}
