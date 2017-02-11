package gov.step.app.repository;

import gov.step.app.domain.JpAcademicQualification;
import gov.step.app.domain.JpEmployeeExperience;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpEmployeeExperience entity.
 */
public interface JpEmployeeExperienceRepository extends JpaRepository<JpEmployeeExperience,Long> {

    @Query("select jpEmployeeExperience from JpEmployeeExperience jpEmployeeExperience where jpEmployeeExperience.jpEmployee.id = :id")
    List<JpEmployeeExperience> findByJpEmployee(@Param("id") Long id);
}
