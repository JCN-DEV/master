package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceName;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceName entity.
 */
public interface SmsServiceNameSearchRepository extends ElasticsearchRepository<SmsServiceName, Long> {
}
