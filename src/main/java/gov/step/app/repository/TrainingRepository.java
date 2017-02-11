package gov.step.app.repository;

import gov.step.app.domain.Training;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Training entity.
 */
public interface TrainingRepository extends JpaRepository<Training, Long> {

    @Query("select training from Training training where training.manager.login = ?#{principal.username}")
    List<Training> findByManagerIsCurrentUser();

    @Query("select training from Training training where training.manager.login = ?#{principal.username}")
    Page<Training> findByManagerIsCurrentUser(Pageable pageable);

    @Query("select training from Training training where training.employee.user.login = ?#{principal.username}")
    Page<Training> findByEmployeeUserIsCurrentUser(Pageable pageable);

    @Query("select training from Training training where training.employee.id = :id")
    List<Training> findByEmployee(@Param("id") Long id);

}
