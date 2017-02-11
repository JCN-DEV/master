package gov.step.app.repository;

import gov.step.app.domain.RisNewVacancy;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the RisNewVacancy entity.
 */
public interface RisNewVacancyRepository extends JpaRepository<RisNewVacancy,Long> {

}
