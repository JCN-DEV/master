package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpBankAccountInfoLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpBankAccountInfoLog entity.
 */
public interface HrEmpBankAccountInfoLogSearchRepository extends ElasticsearchRepository<HrEmpBankAccountInfoLog, Long> {
}
