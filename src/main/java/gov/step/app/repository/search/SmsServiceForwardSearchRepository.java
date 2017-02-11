package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceForward;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceForward entity.
 */
public interface SmsServiceForwardSearchRepository extends ElasticsearchRepository<SmsServiceForward, Long> {
}
