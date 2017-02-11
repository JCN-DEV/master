package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpProfMemberInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpProfMemberInfoLog entity.
 */
public interface HrEmpProfMemberInfoLogSearchRepository extends ElasticsearchRepository<HrEmpProfMemberInfoLog, Long> {
}
