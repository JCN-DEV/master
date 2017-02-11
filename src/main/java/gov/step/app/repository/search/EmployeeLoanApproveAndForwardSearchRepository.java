package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanApproveAndForward;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanApproveAndForward entity.
 */
public interface EmployeeLoanApproveAndForwardSearchRepository extends ElasticsearchRepository<EmployeeLoanApproveAndForward, Long> {
}
