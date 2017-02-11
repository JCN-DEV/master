package gov.step.app.repository.search;

import gov.step.app.domain.DlUllimConfig;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DlUllimConfig entity.
 */
public interface DlUllimConfigSearchRepository extends ElasticsearchRepository<DlUllimConfig, Long> {
}
