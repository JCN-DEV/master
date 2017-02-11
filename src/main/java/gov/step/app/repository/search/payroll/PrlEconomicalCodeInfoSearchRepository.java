package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlEconomicalCodeInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlEconomicalCodeInfo entity.
 */
public interface PrlEconomicalCodeInfoSearchRepository extends ElasticsearchRepository<PrlEconomicalCodeInfo, Long> {
}
