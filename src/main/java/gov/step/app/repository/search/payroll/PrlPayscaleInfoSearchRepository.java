package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlPayscaleInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlPayscaleInfo entity.
 */
public interface PrlPayscaleInfoSearchRepository extends ElasticsearchRepository<PrlPayscaleInfo, Long> {
}
