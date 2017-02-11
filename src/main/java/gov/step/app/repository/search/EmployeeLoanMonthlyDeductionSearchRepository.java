package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanMonthlyDeduction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanMonthlyDeduction entity.
 */
public interface EmployeeLoanMonthlyDeductionSearchRepository extends ElasticsearchRepository<EmployeeLoanMonthlyDeduction, Long> {
}
