package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAuditObjectionInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAuditObjectionInfo entity.
 */
public interface HrEmpAuditObjectionInfoSearchRepository extends ElasticsearchRepository<HrEmpAuditObjectionInfo, Long> {
}
