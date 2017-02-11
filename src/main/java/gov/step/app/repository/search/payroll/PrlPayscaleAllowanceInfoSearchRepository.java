package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlPayscaleAllowanceInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlPayscaleAllowanceInfo entity.
 */
public interface PrlPayscaleAllowanceInfoSearchRepository extends ElasticsearchRepository<PrlPayscaleAllowanceInfo, Long> {
}
