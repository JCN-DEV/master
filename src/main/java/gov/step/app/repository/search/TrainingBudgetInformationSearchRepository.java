package gov.step.app.repository.search;

import gov.step.app.domain.TrainingBudgetInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingBudgetInformation entity.
 */
public interface TrainingBudgetInformationSearchRepository extends ElasticsearchRepository<TrainingBudgetInformation, Long> {
}
