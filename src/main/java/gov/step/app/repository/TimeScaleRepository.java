package gov.step.app.repository;

import gov.step.app.domain.TimeScale;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the TimeScale entity.
 */
public interface TimeScaleRepository extends JpaRepository<TimeScale, Long> {

}
