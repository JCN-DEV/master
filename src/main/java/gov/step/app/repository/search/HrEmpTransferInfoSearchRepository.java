package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpTransferInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpTransferInfo entity.
 */
public interface HrEmpTransferInfoSearchRepository extends ElasticsearchRepository<HrEmpTransferInfo, Long> {
}
