package gov.step.app.repository.search;

import gov.step.app.domain.TimeScale;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TimeScale entity.
 */
public interface TimeScaleSearchRepository extends ElasticsearchRepository<TimeScale, Long> {
}
