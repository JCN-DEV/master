package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpPromotionInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpPromotionInfo entity.
 */
public interface HrEmpPromotionInfoSearchRepository extends ElasticsearchRepository<HrEmpPromotionInfo, Long> {
}
