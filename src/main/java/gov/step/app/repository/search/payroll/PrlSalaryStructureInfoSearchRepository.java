package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlSalaryStructureInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlSalaryStructureInfo entity.
 */
public interface PrlSalaryStructureInfoSearchRepository extends ElasticsearchRepository<PrlSalaryStructureInfo, Long> {
}
