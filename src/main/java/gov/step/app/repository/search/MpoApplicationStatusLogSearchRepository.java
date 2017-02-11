package gov.step.app.repository.search;

import gov.step.app.domain.MpoApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoApplicationStatusLog entity.
 */
public interface MpoApplicationStatusLogSearchRepository extends ElasticsearchRepository<MpoApplicationStatusLog, Long> {
}
