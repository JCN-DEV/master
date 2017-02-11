package gov.step.app.repository;

import gov.step.app.domain.AlmAttendanceConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmAttendanceConfiguration entity.
 */
public interface AlmAttendanceConfigurationRepository extends JpaRepository<AlmAttendanceConfiguration,Long> {

}
