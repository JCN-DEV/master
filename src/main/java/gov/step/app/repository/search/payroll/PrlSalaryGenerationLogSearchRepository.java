package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlSalaryGenerationLog;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlSalaryGenerationLog entity.
 */
public interface PrlSalaryGenerationLogSearchRepository extends ElasticsearchRepository<PrlSalaryGenerationLog, Long> {
}
