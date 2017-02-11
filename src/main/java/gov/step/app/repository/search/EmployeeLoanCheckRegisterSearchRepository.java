package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanCheckRegister;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanCheckRegister entity.
 */
public interface EmployeeLoanCheckRegisterSearchRepository extends ElasticsearchRepository<EmployeeLoanCheckRegister, Long> {
}
