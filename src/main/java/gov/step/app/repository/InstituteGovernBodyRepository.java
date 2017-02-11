package gov.step.app.repository;

import gov.step.app.domain.InstGovernBody;
import gov.step.app.domain.InstituteGovernBody;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstituteGovernBody entity.
 */
public interface InstituteGovernBodyRepository extends JpaRepository<InstituteGovernBody,Long> {

    @Query("select instGovernBody from InstituteGovernBody instGovernBody where instGovernBody.institute.id =:instituteId")
    List<InstituteGovernBody> findListByInstituteId(@Param("instituteId") Long instituteId);

}
