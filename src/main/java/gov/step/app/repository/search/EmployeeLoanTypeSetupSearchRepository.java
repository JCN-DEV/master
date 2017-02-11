package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanTypeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanTypeSetup entity.
 */
public interface EmployeeLoanTypeSetupSearchRepository extends ElasticsearchRepository<EmployeeLoanTypeSetup, Long> {
}
