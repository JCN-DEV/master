package gov.step.app.repository.search;

import gov.step.app.domain.DlContentSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlContentSetup entity.
 */
public interface DlContentSetupSearchRepository extends ElasticsearchRepository<DlContentSetup, Long> {
}
