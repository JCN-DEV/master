package gov.step.app.repository;

import gov.step.app.domain.Employer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Employer entity.
 */
public interface EmployerRepository extends JpaRepository<Employer, Long> {

    @Query("select employer from Employer employer where employer.user.login = ?#{principal.username}")
    List<Employer> findByUserIsCurrentUser();

    @Query("select employer from Employer employer where employer.manager.login = ?#{principal.username}")
    List<Employer> findByManagerIsCurrentUser();

    @Query("select employer from Employer employer where employer.user.login = ?#{principal.username}")
    Employer findOneByUserIsCurrentUser();

    @Query("select employer from Employer employer where employer.user.id = :id")
    Employer findOneByUserId(@Param("id") Long id);

    @Query("select employer from Employer employer where employer.name = :name")
    Employer findExistingEmployer(@Param("name") String name);
}
