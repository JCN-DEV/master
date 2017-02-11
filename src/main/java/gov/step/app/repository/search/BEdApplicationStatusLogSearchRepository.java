package gov.step.app.repository.search;

import gov.step.app.domain.BEdApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BEdApplicationStatusLog entity.
 */
public interface BEdApplicationStatusLogSearchRepository extends ElasticsearchRepository<BEdApplicationStatusLog, Long> {
}
