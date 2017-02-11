package gov.step.app.repository;

import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.domain.JpLanguageProficiency;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpLanguageProficiency entity.
 */
public interface JpLanguageProficiencyRepository extends JpaRepository<JpLanguageProficiency,Long> {

    @Query("select jpLanguageProficiency from JpLanguageProficiency jpLanguageProficiency where jpLanguageProficiency.jpEmployee.id = :id")
    List<JpLanguageProficiency> findByJpEmployee(@Param("id") Long id);

}
