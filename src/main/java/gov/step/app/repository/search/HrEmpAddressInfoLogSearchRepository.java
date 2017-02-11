package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAddressInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAddressInfoLog entity.
 */
public interface HrEmpAddressInfoLogSearchRepository extends ElasticsearchRepository<HrEmpAddressInfoLog, Long> {
}
