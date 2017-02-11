package gov.step.app.repository.search;

import gov.step.app.domain.TimeScaleApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TimeScaleApplicationEditLog entity.
 */
public interface TimeScaleApplicationEditLogSearchRepository extends ElasticsearchRepository<TimeScaleApplicationEditLog, Long> {
}
