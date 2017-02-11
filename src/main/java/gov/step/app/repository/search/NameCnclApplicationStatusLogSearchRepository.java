package gov.step.app.repository.search;

import gov.step.app.domain.NameCnclApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NameCnclApplicationStatusLog entity.
 */
public interface NameCnclApplicationStatusLogSearchRepository extends ElasticsearchRepository<NameCnclApplicationStatusLog, Long> {
}
