package gov.step.app.repository.search;

import gov.step.app.domain.TrainingHeadSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TrainingHeadSetup entity.
 */
public interface TrainingHeadSetupSearchRepository extends ElasticsearchRepository<TrainingHeadSetup, Long> {
}
