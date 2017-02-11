package gov.step.app.repository;

import gov.step.app.domain.HrDesignationHeadSetup;
import gov.step.app.domain.enumeration.designationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrDesignationHeadSetup entity.
 */
public interface HrDesignationHeadSetupRepository extends JpaRepository<HrDesignationHeadSetup,Long>
{
    @Query("select modelInfo from HrDesignationHeadSetup modelInfo where activeStatus =:activeStatus order by designationName")
    Page<HrDesignationHeadSetup> findAllByActiveStatus(Pageable pageable, @Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDesignationHeadSetup modelInfo where activeStatus =:activeStatus order by designationName")
    List<HrDesignationHeadSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

    @Query("select modelInfo from HrDesignationHeadSetup modelInfo where lower(modelInfo.designationCode) = :designationCode ")
    Optional<HrDesignationHeadSetup> findOneByDesignationCode(@Param("designationCode") String designationCode);

    @Query("select modelInfo from HrDesignationHeadSetup modelInfo where lower(modelInfo.designationName) = :designationName ")
    Optional<HrDesignationHeadSetup> findOneByDesignationName(@Param("designationName") String designationName);

    @Query("select modelInfo from HrDesignationHeadSetup modelInfo where desigType =:desigType AND activeStatus =:activeStatus order by designationName")
    List<HrDesignationHeadSetup> findAllByDesigType(@Param("desigType") designationType desigType, @Param("activeStatus") boolean activeStatus);

}
