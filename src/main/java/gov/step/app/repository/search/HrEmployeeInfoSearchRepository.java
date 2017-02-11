package gov.step.app.repository.search;

import gov.step.app.domain.HrEmployeeInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmployeeInfo entity.
 */
public interface HrEmployeeInfoSearchRepository extends ElasticsearchRepository<HrEmployeeInfo, Long> {
}
