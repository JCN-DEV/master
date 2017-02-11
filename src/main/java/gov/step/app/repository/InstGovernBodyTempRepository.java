package gov.step.app.repository;

import gov.step.app.domain.InstGovernBody;
import gov.step.app.domain.InstGovernBodyTemp;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstGovernBodyTemp entity.
 */
public interface InstGovernBodyTempRepository extends JpaRepository<InstGovernBodyTemp,Long> {


    @Query("select instGovernBody from InstGovernBodyTemp instGovernBody where instGovernBody.institute.id =:instituteId")
    List<InstGovernBodyTemp> findListByInstituteId(@Param("instituteId") Long instituteId);

}
