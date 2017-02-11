package gov.step.app.repository;

import gov.step.app.domain.EducationalQualification;
import gov.step.app.domain.JpAcademicQualification;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the JpAcademicQualification entity.
 */
public interface JpAcademicQualificationRepository extends JpaRepository<JpAcademicQualification,Long> {

    @Query("select jpAcademicQualification from JpAcademicQualification jpAcademicQualification where jpAcademicQualification.jpEmployee.id = :id")
    List<JpAcademicQualification> findByJpEmployee(@Param("id") Long id);
}
