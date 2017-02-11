package gov.step.app.repository.search;

import gov.step.app.domain.SmsGateway;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsGateway entity.
 */
public interface SmsGatewaySearchRepository extends ElasticsearchRepository<SmsGateway, Long> {
}
