package gov.step.app.repository;

import gov.step.app.domain.HrWingHeadSetup;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Spring Data JPA repository for the HrWingHeadSetup entity.
 */
public interface HrWingHeadSetupRepository extends JpaRepository<HrWingHeadSetup,Long>
{
    @Query("select modelInfo from HrWingHeadSetup modelInfo where wingInfo.id = :wingId ")
    List<HrWingHeadSetup> findAllByWing(@Param("wingId") Long wingId);

    @Modifying
    @Transactional
    @Query("update HrWingHeadSetup modelInfo set modelInfo.activeHead=:stat where modelInfo.wingInfo.id = :wingId")
    void updateAllWingHeadActiveStatus(@Param("wingId") Long wingId, @Param("stat") boolean stat);

}
