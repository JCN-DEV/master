package gov.step.app.repository;

import gov.step.app.domain.AlmWeekendConfiguration;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the AlmWeekendConfiguration entity.
 */
public interface AlmWeekendConfigurationRepository extends JpaRepository<AlmWeekendConfiguration,Long> {

}
