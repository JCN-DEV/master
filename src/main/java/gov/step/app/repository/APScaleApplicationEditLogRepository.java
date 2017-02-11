package gov.step.app.repository;

import gov.step.app.domain.APScaleApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the APScaleApplicationEditLog entity.
 */
public interface APScaleApplicationEditLogRepository extends JpaRepository<APScaleApplicationEditLog,Long> {

}
