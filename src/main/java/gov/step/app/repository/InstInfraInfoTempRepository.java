package gov.step.app.repository;

import gov.step.app.domain.InstInfraInfo;
import gov.step.app.domain.InstInfraInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstInfraInfoTemp entity.
 */
public interface InstInfraInfoTempRepository extends JpaRepository<InstInfraInfoTemp,Long> {


    @Query("select instInfraInfo from InstInfraInfoTemp instInfraInfo where instInfraInfo.institute.id =:instituteId")
    InstInfraInfoTemp findByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select instInfraInfo from InstInfraInfoTemp instInfraInfo where instInfraInfo.institute.id =:id")
    InstInfraInfoTemp findByInstituteIdforapprove(@Param("id") Long id);
}
