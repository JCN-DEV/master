package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceAssign entity.
 */
public interface SmsServiceAssignSearchRepository extends ElasticsearchRepository<SmsServiceAssign, Long> {
}
