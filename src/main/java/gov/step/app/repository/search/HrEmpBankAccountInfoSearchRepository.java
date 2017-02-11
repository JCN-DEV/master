package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpBankAccountInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpBankAccountInfo entity.
 */
public interface HrEmpBankAccountInfoSearchRepository extends ElasticsearchRepository<HrEmpBankAccountInfo, Long> {
}
