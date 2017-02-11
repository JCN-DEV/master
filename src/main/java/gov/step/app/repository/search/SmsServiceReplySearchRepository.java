package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceReply;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceReply entity.
 */
public interface SmsServiceReplySearchRepository extends ElasticsearchRepository<SmsServiceReply, Long> {
}
