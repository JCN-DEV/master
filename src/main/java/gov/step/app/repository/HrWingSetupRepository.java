package gov.step.app.repository;

import gov.step.app.domain.HrWingSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the HrWingSetup entity.
 */
public interface HrWingSetupRepository extends JpaRepository<HrWingSetup,Long>
{
    @Query("select modelInfo from HrWingSetup modelInfo where activeStatus =:activeStatus order by wingName")
    List<HrWingSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);

}
