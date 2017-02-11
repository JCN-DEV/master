package gov.step.app.repository.search;

import gov.step.app.domain.IncrementSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the IncrementSetup entity.
 */
public interface IncrementSetupSearchRepository extends ElasticsearchRepository<IncrementSetup, Long> {
}
