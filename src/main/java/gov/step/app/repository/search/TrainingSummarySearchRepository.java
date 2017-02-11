package gov.step.app.repository.search;

import gov.step.app.domain.TrainingSummary;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingSummary entity.
 */
public interface TrainingSummarySearchRepository extends ElasticsearchRepository<TrainingSummary, Long> {
}
