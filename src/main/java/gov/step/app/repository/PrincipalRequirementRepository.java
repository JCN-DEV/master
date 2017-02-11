package gov.step.app.repository;

import gov.step.app.domain.PrincipalRequirement;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the PrincipalRequirement entity.
 */
public interface PrincipalRequirementRepository extends JpaRepository<PrincipalRequirement, Long> {

}
