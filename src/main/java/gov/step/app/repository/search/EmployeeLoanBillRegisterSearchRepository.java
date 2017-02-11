package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanBillRegister;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanBillRegister entity.
 */
public interface EmployeeLoanBillRegisterSearchRepository extends ElasticsearchRepository<EmployeeLoanBillRegister, Long> {
}
