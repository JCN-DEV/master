package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceComplaint;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceComplaint entity.
 */
public interface SmsServiceComplaintSearchRepository extends ElasticsearchRepository<SmsServiceComplaint, Long> {
}
