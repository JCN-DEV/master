package gov.step.app.repository;

import gov.step.app.domain.MpoApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the MpoApplicationEditLog entity.
 */
public interface MpoApplicationEditLogRepository extends JpaRepository<MpoApplicationEditLog,Long> {

}
