package gov.step.app.repository.search;

import gov.step.app.domain.TimeScaleApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TimeScaleApplication entity.
 */
public interface TimeScaleApplicationSearchRepository extends ElasticsearchRepository<TimeScaleApplication, Long> {
}
