package gov.step.app.repository;

import gov.step.app.domain.InstFinancialInfo;
import gov.step.app.domain.InstInfraInfo;

import gov.step.app.domain.Institute;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Objects;

/**
 * Spring Data JPA repository for the InstInfraInfo entity.
 */
public interface InstInfraInfoRepository extends JpaRepository<InstInfraInfo,Long> {

    @Query("select instInfraInfo from InstInfraInfo instInfraInfo where instInfraInfo.institute.id =:instituteId")
    List<InstInfraInfo> findListByInstituteId(@Param("instituteId") Long instituteId);
   //@Query("select instInfraInfo from InstInfraInfo instInfraInfo where instInfraInfo.institute.id =:instituteId")
    Object findOneByInstitute(@Param("institute") Institute institute);

    @Query("select instInfraInfo from InstInfraInfo instInfraInfo where instInfraInfo.institute.id =:instituteId")
    InstInfraInfo findByInstituteId(@Param("instituteId") Long instituteId);

}
