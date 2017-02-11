package gov.step.app.repository;

import gov.step.app.domain.Division;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the Division entity.
 */
public interface DivisionRepository extends JpaRepository<Division,Long> {
    @Query("select division from Division division where division.country.id = :id")
    List<Division> findByCountry(@Param("id") Long id);

}
