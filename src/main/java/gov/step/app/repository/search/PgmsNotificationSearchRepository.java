package gov.step.app.repository.search;

import gov.step.app.domain.PgmsNotification;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PgmsNotification entity.
 */
public interface PgmsNotificationSearchRepository extends ElasticsearchRepository<PgmsNotification, Long> {
}
