package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpTransferApplInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpTransferApplInfo entity.
 */
public interface HrEmpTransferApplInfoSearchRepository extends ElasticsearchRepository<HrEmpTransferApplInfo, Long> {
}
