package gov.step.app.repository.search;

import gov.step.app.domain.APScaleApplicationEditLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the APScaleApplicationEditLog entity.
 */
public interface APScaleApplicationEditLogSearchRepository extends ElasticsearchRepository<APScaleApplicationEditLog, Long> {
}
