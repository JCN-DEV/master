package gov.step.app.repository;

import gov.step.app.domain.TrainingBudgetInformation;


import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

/**
 * Spring Data JPA repository for the TrainingBudgetInformation entity.
 */
public interface TrainingBudgetInformationRepository extends JpaRepository<TrainingBudgetInformation,Long> {

    @Query("select modelInfo from TrainingBudgetInformation modelInfo where modelInfo.trainingInitializationInfo.id = :initializeId")
    TrainingBudgetInformation findByInitializeId(@Param("initializeId") Long initializeId );
}
