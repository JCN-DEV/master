package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanRequisitionForm;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanRequisitionForm entity.
 */
public interface EmployeeLoanRequisitionFormSearchRepository extends ElasticsearchRepository<EmployeeLoanRequisitionForm, Long> {
}
