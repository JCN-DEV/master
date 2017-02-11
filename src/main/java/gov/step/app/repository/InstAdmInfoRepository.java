package gov.step.app.repository;

import gov.step.app.domain.InstAdmInfo;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstAdmInfo entity.
 */
public interface InstAdmInfoRepository extends JpaRepository<InstAdmInfo,Long> {
    @Query("select instAdmInfo from InstAdmInfo instAdmInfo where instAdmInfo.institute.id =:instituteId")
    InstAdmInfo findOneByInstituteId(@Param("instituteId") Long instituteId);
}
