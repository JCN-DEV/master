package gov.step.app.repository;

import gov.step.app.domain.InstEmplExperience;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstEmplExperience entity.
 */
public interface InstEmplExperienceRepository extends JpaRepository<InstEmplExperience,Long> {

    @Query("select instEmplExperience from InstEmplExperience instEmplExperience where instEmplExperience.instEmployee.id =:id")
    List<InstEmplExperience> findByInstEmployeeId(@Param("id") Long id);

    @Query("select instEmplExperience from InstEmplExperience instEmplExperience where instEmplExperience.instEmployee.user.login =?#{principal.username}")
    List<InstEmplExperience> findAllByCurrentLogin();
}
