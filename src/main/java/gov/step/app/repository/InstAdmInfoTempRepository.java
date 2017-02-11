package gov.step.app.repository;

import gov.step.app.domain.InstAdmInfo;
import gov.step.app.domain.InstAdmInfoTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstAdmInfoTemp entity.
 */
public interface InstAdmInfoTempRepository extends JpaRepository<InstAdmInfoTemp,Long> {

    @Query("select instAdmInfo from InstAdmInfoTemp instAdmInfo where instAdmInfo.institute.id =:instituteId")
    InstAdmInfoTemp findOneByInstituteId(@Param("instituteId") Long instituteId);
}
