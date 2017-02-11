package gov.step.app.repository.search;

import gov.step.app.domain.TimeScaleApplicationStatusLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TimeScaleApplicationStatusLog entity.
 */
public interface TimeScaleApplicationStatusLogSearchRepository extends ElasticsearchRepository<TimeScaleApplicationStatusLog, Long> {
}
