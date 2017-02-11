package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAddressInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAddressInfo entity.
 */
public interface HrEmpAddressInfoSearchRepository extends ElasticsearchRepository<HrEmpAddressInfo, Long> {
}
