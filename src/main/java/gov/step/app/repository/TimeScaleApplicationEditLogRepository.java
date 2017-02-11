package gov.step.app.repository;

import gov.step.app.domain.TimeScaleApplicationEditLog;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TimeScaleApplicationEditLog entity.
 */
public interface TimeScaleApplicationEditLogRepository extends JpaRepository<TimeScaleApplicationEditLog,Long> {

}
