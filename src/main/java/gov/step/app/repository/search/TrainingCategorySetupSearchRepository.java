package gov.step.app.repository.search;

import gov.step.app.domain.TrainingCategorySetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingCategorySetup entity.
 */
public interface TrainingCategorySetupSearchRepository extends ElasticsearchRepository<TrainingCategorySetup, Long> {
}
