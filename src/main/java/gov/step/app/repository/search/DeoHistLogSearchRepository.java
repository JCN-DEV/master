package gov.step.app.repository.search;

import gov.step.app.domain.DeoHistLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the DeoHistLog entity.
 */
public interface DeoHistLogSearchRepository extends ElasticsearchRepository<DeoHistLog, Long> {
}
