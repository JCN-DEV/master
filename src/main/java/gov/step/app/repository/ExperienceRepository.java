package gov.step.app.repository;

import gov.step.app.domain.Experience;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.List;

/**
 * Spring Data JPA repository for the Experience entity.
 */
public interface ExperienceRepository extends JpaRepository<Experience, Long> {

    @Query("select experience from Experience experience where experience.manager.login = ?#{principal.username}")
    Page<Experience> findByManagerIsCurrentUser(Pageable pageable);

    @Query("select experience from Experience experience where experience.employee.id = :id")
    List<Experience> findByEmployee(@Param("id") Long id);

}
