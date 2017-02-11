package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlGeneratedSalaryInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlGeneratedSalaryInfo entity.
 */
public interface PrlGeneratedSalaryInfoSearchRepository extends ElasticsearchRepository<PrlGeneratedSalaryInfo, Long> {
}
