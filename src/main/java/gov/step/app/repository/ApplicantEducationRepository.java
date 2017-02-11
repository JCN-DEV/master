package gov.step.app.repository;

import gov.step.app.domain.ApplicantEducation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the ApplicantEducation entity.
 */
public interface ApplicantEducationRepository extends JpaRepository<ApplicantEducation, Long> {

    @Query("select applicantEducation from ApplicantEducation applicantEducation where applicantEducation.manager.login = ?#{principal.username}")
    List<ApplicantEducation> findByManagerIsCurrentUser();

    @Query("select applicantEducation from ApplicantEducation applicantEducation where applicantEducation.employee.id = :id")
    List<ApplicantEducation> findByEmployee(@Param("id") Long id);

}
