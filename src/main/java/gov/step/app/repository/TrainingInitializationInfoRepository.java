package gov.step.app.repository;

import gov.step.app.domain.TrainingInitializationInfo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainingInitializationInfo entity.
 */
public interface TrainingInitializationInfoRepository extends JpaRepository<TrainingInitializationInfo,Long> {

    @Query("SELECT modelInfo FROM TrainingInitializationInfo modelInfo where modelInfo.trainingCode = :pTrainingCode")
    TrainingInitializationInfo findByTrainingCode(@Param("pTrainingCode") String pTrainingCode);

    public Page<TrainingInitializationInfo> findAllByOrderByIdDesc(Pageable pageable);


}
