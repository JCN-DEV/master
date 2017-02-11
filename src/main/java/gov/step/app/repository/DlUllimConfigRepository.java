package gov.step.app.repository;

import gov.step.app.domain.DlUllimConfig;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the DlUllimConfig entity.
 */
public interface DlUllimConfigRepository extends JpaRepository<DlUllimConfig,Long> {

}
