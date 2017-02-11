package gov.step.app.repository;

import gov.step.app.domain.InstComiteFormationTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstComiteFormationTemp entity.
 */
public interface InstComiteFormationTempRepository extends JpaRepository<InstComiteFormationTemp,Long> {

}
