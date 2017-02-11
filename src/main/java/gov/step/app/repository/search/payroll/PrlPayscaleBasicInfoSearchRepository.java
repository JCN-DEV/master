package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlPayscaleBasicInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlPayscaleBasicInfo entity.
 */
public interface PrlPayscaleBasicInfoSearchRepository extends ElasticsearchRepository<PrlPayscaleBasicInfo, Long> {
}
