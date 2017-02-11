package gov.step.app.repository;

import gov.step.app.domain.InsAcademicInfo;

import gov.step.app.domain.Institute;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InsAcademicInfo entity.
 */
public interface InsAcademicInfoRepository extends JpaRepository<InsAcademicInfo,Long> {

    @Query("select insAcademicInfo from InsAcademicInfo insAcademicInfo where insAcademicInfo.institute.id =:instituteId")
    InsAcademicInfo findByInstituteId(@Param("instituteId") Long instituteId);


}
