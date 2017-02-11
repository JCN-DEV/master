package gov.step.app.repository.search;

import gov.step.app.domain.NotificationStep;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the NotificationStep entity.
 */
public interface NotificationStepSearchRepository extends ElasticsearchRepository<NotificationStep, Long> {
}
