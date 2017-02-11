package gov.step.app.repository;

import gov.step.app.domain.City;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Spring Data JPA repository for the City entity.
 */
public interface CityRepository extends JpaRepository<City, Long> {

}
