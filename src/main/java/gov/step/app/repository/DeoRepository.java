package gov.step.app.repository;

import gov.step.app.domain.Deo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Deo entity.
 */
public interface DeoRepository extends JpaRepository<Deo,Long> {

    @Query("select deo from Deo deo where deo.createdBy.login = ?#{principal.username}")
    List<Deo> findByCreatedByIsCurrentUser();

    @Query("select deo from Deo deo where deo.createdBy.login = ?#{principal.username}")
    List<Deo> findActiveDeoByDistrict();

}
