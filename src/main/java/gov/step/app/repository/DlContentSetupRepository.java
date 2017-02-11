package gov.step.app.repository;

import gov.step.app.domain.DlContentSetup;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlContentSetup entity.
 */
public interface DlContentSetupRepository extends JpaRepository<DlContentSetup,Long> {

}
