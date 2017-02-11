package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlSalaryAllowDeducInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlSalaryAllowDeducInfo entity.
 */
public interface PrlSalaryAllowDeducInfoSearchRepository extends ElasticsearchRepository<PrlSalaryAllowDeducInfo, Long> {
}
