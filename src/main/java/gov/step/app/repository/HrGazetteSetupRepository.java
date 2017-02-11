package gov.step.app.repository;

import gov.step.app.domain.HrGazetteSetup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the HrGazetteSetup entity.
 */
public interface HrGazetteSetupRepository extends JpaRepository<HrGazetteSetup,Long>
{
    @Query("select modelInfo from HrGazetteSetup modelInfo where lower(modelInfo.gazetteCode) = :gazetteCode ")
    Optional<HrGazetteSetup> findOneByGazetteCode(@Param("gazetteCode") String gazetteCode);

    @Query("select modelInfo from HrGazetteSetup modelInfo where activeStatus =:activeStatus order by gazetteName")
    List<HrGazetteSetup> findAllByActiveStatus(@Param("activeStatus") boolean activeStatus);
}
