package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlEmpGeneratedSalInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlEmpGeneratedSalInfo entity.
 */
public interface PrlEmpGeneratedSalInfoSearchRepository extends ElasticsearchRepository<PrlEmpGeneratedSalInfo, Long> {
}
