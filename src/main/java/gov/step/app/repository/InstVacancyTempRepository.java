package gov.step.app.repository;

import gov.step.app.domain.InstVacancyTemp;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the InstVacancyTemp entity.
 */
public interface InstVacancyTempRepository extends JpaRepository<InstVacancyTemp,Long> {

}
