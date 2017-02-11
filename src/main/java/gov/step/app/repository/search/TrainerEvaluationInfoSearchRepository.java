package gov.step.app.repository.search;

import gov.step.app.domain.TrainerEvaluationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainerEvaluationInfo entity.
 */
public interface TrainerEvaluationInfoSearchRepository extends ElasticsearchRepository<TrainerEvaluationInfo, Long> {
}
