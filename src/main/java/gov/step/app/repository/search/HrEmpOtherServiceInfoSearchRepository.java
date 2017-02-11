package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpOtherServiceInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpOtherServiceInfo entity.
 */
public interface HrEmpOtherServiceInfoSearchRepository extends ElasticsearchRepository<HrEmpOtherServiceInfo, Long> {
}
