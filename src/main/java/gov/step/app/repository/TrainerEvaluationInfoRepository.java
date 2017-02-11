package gov.step.app.repository;

import gov.step.app.domain.TrainerEvaluationInfo;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the TrainerEvaluationInfo entity.
 */
public interface TrainerEvaluationInfoRepository extends JpaRepository<TrainerEvaluationInfo,Long> {

}
