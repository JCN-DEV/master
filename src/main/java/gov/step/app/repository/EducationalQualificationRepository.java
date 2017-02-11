package gov.step.app.repository;

import gov.step.app.domain.EducationalQualification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the EducationalQualification entity.
 */
public interface EducationalQualificationRepository extends JpaRepository<EducationalQualification, Long> {

    @Query("select educationalQualification from EducationalQualification educationalQualification where educationalQualification.manager.login = ?#{principal.username}")
    List<EducationalQualification> findByManagerIsCurrentUser();

    @Query("select educationalQualification from EducationalQualification educationalQualification where educationalQualification.employee.id = :id")
    List<EducationalQualification> findByEmployee(@Param("id") Long id);
}
