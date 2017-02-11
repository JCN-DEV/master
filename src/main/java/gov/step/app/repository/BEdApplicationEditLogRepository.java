package gov.step.app.repository;

import gov.step.app.domain.BEdApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the BEdApplicationEditLog entity.
 */
public interface BEdApplicationEditLogRepository extends JpaRepository<BEdApplicationEditLog,Long> {

}
