package gov.step.app.repository.search;

import gov.step.app.domain.APScaleApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the APScaleApplicationStatusLog entity.
 */
public interface APScaleApplicationStatusLogSearchRepository extends ElasticsearchRepository<APScaleApplicationStatusLog, Long> {
}
