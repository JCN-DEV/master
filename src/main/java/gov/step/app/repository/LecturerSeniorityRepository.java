package gov.step.app.repository;

import gov.step.app.domain.LecturerSeniority;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the LecturerSeniority entity.
 */
public interface LecturerSeniorityRepository extends JpaRepository<LecturerSeniority, Long> {

}
