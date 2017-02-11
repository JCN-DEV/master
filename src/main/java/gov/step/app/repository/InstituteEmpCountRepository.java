package gov.step.app.repository;

import gov.step.app.domain.InstituteEmpCount;

import org.springframework.data.jpa.repository.*;

import java.util.List;
import org.springframework.data.repository.query.Param;

/**
 * Spring Data JPA repository for the InstituteEmpCount entity.
 */
public interface InstituteEmpCountRepository extends JpaRepository<InstituteEmpCount,Long> {

    @Query("select instEmpCount from InstituteEmpCount instEmpCount where instEmpCount.institute.id =:instituteId")
    InstituteEmpCount findOneByInstituteId(@Param("instituteId") Long instituteId);

}
