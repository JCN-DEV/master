package gov.step.app.repository;

import gov.step.app.domain.InstGovernBody;

import gov.step.app.domain.InstInfraInfo;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstGovernBody entity.
 */
public interface InstGovernBodyRepository extends JpaRepository<InstGovernBody,Long> {

    @Query("select instGovernBody from InstGovernBody instGovernBody where instGovernBody.institute.id =:instituteId")
    List<InstGovernBody> findListByInstituteId(@Param("instituteId") Long instituteId);

    @Query("select instGovernBody from InstGovernBody instGovernBody where instGovernBody.institute.id =:instituteId")
    InstGovernBody findByInstituteId(@Param("instituteId") Long instituteId);

}
