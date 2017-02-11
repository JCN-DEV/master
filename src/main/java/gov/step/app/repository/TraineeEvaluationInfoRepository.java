package gov.step.app.repository;

import gov.step.app.domain.TraineeEvaluationInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TraineeEvaluationInfo entity.
 */
public interface TraineeEvaluationInfoRepository extends JpaRepository<TraineeEvaluationInfo,Long> {

}
