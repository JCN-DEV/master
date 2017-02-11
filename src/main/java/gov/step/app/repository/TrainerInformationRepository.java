package gov.step.app.repository;

import gov.step.app.domain.TraineeInformation;
import gov.step.app.domain.TrainerInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainerInformation entity.
 */
public interface TrainerInformationRepository extends JpaRepository<TrainerInformation,Long> {


    @Query("select trainerInformation from TrainerInformation trainerInformation where trainerInformation.trainingInitializationInfo.trainingCode =:pTrainingCode")
    List<TrainerInformation> findByTrainingCode(@Param("pTrainingCode") String pTrainingCode);

    @Query("select trainerInformation from TrainerInformation trainerInformation where trainerInformation.trainingInitializationInfo.id =:pInitializationId")
    List<TrainerInformation> findByInitializationId(@Param("pInitializationId") Long pInitializationId);


}
