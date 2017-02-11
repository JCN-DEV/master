package gov.step.app.repository;

import gov.step.app.domain.ProfessionalQualification;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProfessionalQualification entity.
 */
public interface ProfessionalQualificationRepository extends JpaRepository<ProfessionalQualification,Long> {

}
