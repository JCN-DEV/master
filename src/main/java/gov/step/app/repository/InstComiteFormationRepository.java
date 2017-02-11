package gov.step.app.repository;

import gov.step.app.domain.InstComiteFormation;

import gov.step.app.domain.InstMemShip;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the InstComiteFormation entity.
 */
public interface InstComiteFormationRepository extends JpaRepository<InstComiteFormation,Long> {

    @Query("select instComiteFormation from InstComiteFormation instComiteFormation where instComiteFormation.institute.id = :id")
    Page<InstComiteFormation> findAllByInstitute(Pageable pageable,@Param("id") Long id);

    /*@Query("SELECT instComiteFormation FROM InstComiteFormation instComiteFormation left JOIN FETCH instComiteFormation.instMemShipsnstMemShip WHERE instComiteFormation.id = :id")
    public InstComiteFormation findByIdAndFetchRolesEagerly(@Param("id") Long id);*/
}
