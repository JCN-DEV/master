package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlBudgetSetupInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlBudgetSetupInfo entity.
 */
public interface PrlBudgetSetupInfoSearchRepository extends ElasticsearchRepository<PrlBudgetSetupInfo, Long> {
}
