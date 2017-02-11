package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlAllowDeductInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlAllowDeductInfo entity.
 */
public interface PrlAllowDeductInfoSearchRepository extends ElasticsearchRepository<PrlAllowDeductInfo, Long> {
}
