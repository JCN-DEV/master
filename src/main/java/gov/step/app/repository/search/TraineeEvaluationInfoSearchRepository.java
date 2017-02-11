package gov.step.app.repository.search;

import gov.step.app.domain.TraineeEvaluationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TraineeEvaluationInfo entity.
 */
public interface TraineeEvaluationInfoSearchRepository extends ElasticsearchRepository<TraineeEvaluationInfo, Long> {
}
