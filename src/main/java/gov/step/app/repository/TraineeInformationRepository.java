package gov.step.app.repository;

import gov.step.app.domain.TraineeInformation;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TraineeInformation entity.
 */
public interface TraineeInformationRepository extends JpaRepository<TraineeInformation,Long> {

    @Query("select traineeInformation from TraineeInformation traineeInformation where traineeInformation.trainingHeadSetup.id=:id")
    List<TraineeInformation> findAByTisHeadSetup(@Param("id") Long id);

    @Query("select traineeInformation from TraineeInformation traineeInformation where traineeInformation.trainingRequisitionForm.id =:pTrainingFormId")
    List<TraineeInformation> findByTrainingRequisitionId(@Param("pTrainingFormId") Long pTrainingFormId);

    @Query("select traineeInformation from TraineeInformation traineeInformation where traineeInformation.hrEmployeeInfo.id =:pEmployeeInfoId and traineeInformation.status =:true")
    List<TraineeInformation> findByHrEmployeeId(@Param("pEmployeeInfoId") Long pEmployeeInfoId);

}
