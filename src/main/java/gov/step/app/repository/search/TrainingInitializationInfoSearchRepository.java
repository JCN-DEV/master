package gov.step.app.repository.search;

import gov.step.app.domain.TrainingInitializationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingInitializationInfo entity.
 */
public interface TrainingInitializationInfoSearchRepository extends ElasticsearchRepository<TrainingInitializationInfo, Long> {
}
