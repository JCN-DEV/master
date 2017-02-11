package gov.step.app.repository;

import gov.step.app.domain.TrainingHeadSetup;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the TrainingHeadSetup entity.
 */
public interface TrainingHeadSetupRepository extends JpaRepository<TrainingHeadSetup,Long> {


    public Page<TrainingHeadSetup> findAllByOrderByIdDesc(Pageable pageable);

    @Query("select modelInfo from TrainingHeadSetup modelInfo where modelInfo.headName = :headName")
    Optional<TrainingHeadSetup> findByHeadName(@Param("headName") String headName );

    // Get All Training Head Setup By Status
    public Page<TrainingHeadSetup> findAllByStatus(Boolean status,Pageable pageable);
}
