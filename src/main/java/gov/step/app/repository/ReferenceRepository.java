package gov.step.app.repository;

import gov.step.app.domain.Reference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Reference entity.
 */
public interface ReferenceRepository extends JpaRepository<Reference, Long> {

    @Query("select reference from Reference reference where reference.manager.login = ?#{principal.username}")
    List<Reference> findByManagerIsCurrentUser();

    @Query("select reference from Reference reference where reference.employee.user.login = ?#{principal.username}")
    Page<Reference> findByEmployeeUserIsCurrentUser(Pageable pageable);

    @Query("select reference from Reference reference where reference.manager.login = ?#{principal.username}")
    Page<Reference> findByManagerIsCurrentUser(Pageable pageable);

    @Query("select reference from Reference reference where reference.employee.id = :id")
    List<Reference> findByEmployee(@Param("id") Long id);
}
