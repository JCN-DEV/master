package gov.step.app.repository;

import gov.step.app.domain.InstituteInfraInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the InstituteInfraInfo entity.
 */
public interface InstituteInfraInfoRepository extends JpaRepository<InstituteInfraInfo,Long> {

    @Query("select instituteInfraInfo from InstituteInfraInfo instituteInfraInfo where instituteInfraInfo.institute.id =:instituteId")
    InstituteInfraInfo findOneByInstituteId(@Param("instituteId") Long instituteId);

}
