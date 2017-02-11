package gov.step.app.repository;

import gov.step.app.domain.UmracActionSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the UmracActionSetup entity.
 */
public interface UmracActionSetupRepository extends JpaRepository<UmracActionSetup,Long> {

    @Query("select umracActionSetup from UmracActionSetup umracActionSetup order by umracActionSetup.actionId")
    List<UmracActionSetup> findAllActionId();
}
