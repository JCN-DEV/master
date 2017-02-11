package gov.step.app.repository.search;

import gov.step.app.domain.SmsServiceDepartment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SmsServiceDepartment entity.
 */
public interface SmsServiceDepartmentSearchRepository extends ElasticsearchRepository<SmsServiceDepartment, Long> {
}
