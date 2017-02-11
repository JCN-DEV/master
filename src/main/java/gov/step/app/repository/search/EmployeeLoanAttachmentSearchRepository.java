package gov.step.app.repository.search;

import gov.step.app.domain.EmployeeLoanAttachment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the EmployeeLoanAttachment entity.
 */
public interface EmployeeLoanAttachmentSearchRepository extends ElasticsearchRepository<EmployeeLoanAttachment, Long> {
}
