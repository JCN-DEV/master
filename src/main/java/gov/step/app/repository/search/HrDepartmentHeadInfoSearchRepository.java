package gov.step.app.repository.search;

import gov.step.app.domain.HrDepartmentHeadInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrDepartmentHeadInfo entity.
 */
public interface HrDepartmentHeadInfoSearchRepository extends ElasticsearchRepository<HrDepartmentHeadInfo, Long> {
}
