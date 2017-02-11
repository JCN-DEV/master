package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpChildrenInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpChildrenInfo entity.
 */
public interface HrEmpChildrenInfoSearchRepository extends ElasticsearchRepository<HrEmpChildrenInfo, Long> {
}
