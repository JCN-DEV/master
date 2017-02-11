package gov.step.app.repository;

import gov.step.app.domain.Vacancy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

/**
 * Spring Data JPA repository for the Vacancy entity.
 */
public interface VacancyRepository extends JpaRepository<Vacancy, Long> {

    @Query("select vacancy from Vacancy vacancy where vacancy.manager.login = ?#{principal.username}")
    List<Vacancy> findByManagerIsCurrentUser();

}
