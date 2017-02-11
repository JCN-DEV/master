package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceType entity.
 */
public interface SmsServiceTypeSearchRepository extends ElasticsearchRepository<SmsServiceType, Long> {
}
