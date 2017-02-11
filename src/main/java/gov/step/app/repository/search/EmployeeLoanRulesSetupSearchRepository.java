package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanRulesSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanRulesSetup entity.
 */
public interface EmployeeLoanRulesSetupSearchRepository extends ElasticsearchRepository<EmployeeLoanRulesSetup, Long> {
}
