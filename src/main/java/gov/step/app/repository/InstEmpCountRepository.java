package gov.step.app.repository;

import gov.step.app.domain.InstAdmInfo;
import gov.step.app.domain.InstEmpCount;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmpCount entity.
 */
public interface InstEmpCountRepository extends JpaRepository<InstEmpCount,Long> {

    @Query("select instEmpCount from InstEmpCount instEmpCount where instEmpCount.institute.id =:instituteId")
    InstEmpCount findOneByInstituteId(@Param("instituteId") Long instituteId);

}
