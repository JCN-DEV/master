package gov.step.app.repository;

import gov.step.app.domain.UmracRightsLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the UmracRightsLog entity.
 */
public interface UmracRightsLogRepository extends JpaRepository<UmracRightsLog,Long> {

}
