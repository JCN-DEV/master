package gov.step.app.repository;

import gov.step.app.domain.JpEmployeeExperience;
import gov.step.app.domain.JpEmployeeReference;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpEmployeeReference entity.
 */
public interface JpEmployeeReferenceRepository extends JpaRepository<JpEmployeeReference,Long> {

    @Query("select jpEmployeeReference from JpEmployeeReference jpEmployeeReference where jpEmployeeReference.jpEmployee.id = :id")
    List<JpEmployeeReference> findByJpEmployee(@Param("id") Long id);
}
