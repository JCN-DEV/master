package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpProfMemberInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpProfMemberInfo entity.
 */
public interface HrEmpProfMemberInfoSearchRepository extends ElasticsearchRepository<HrEmpProfMemberInfo, Long> {
}
