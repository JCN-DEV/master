package gov.step.app.repository;

import gov.step.app.domain.GazetteSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the GazetteSetup entity.
 */
public interface GazetteSetupRepository extends JpaRepository<GazetteSetup,Long> {

}
