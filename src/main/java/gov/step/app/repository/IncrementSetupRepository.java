package gov.step.app.repository;

import gov.step.app.domain.IncrementSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the IncrementSetup entity.
 */
public interface IncrementSetupRepository extends JpaRepository<IncrementSetup,Long> {

}
