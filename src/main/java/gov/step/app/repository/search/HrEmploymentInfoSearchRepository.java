package gov.step.app.repository.search;

import gov.step.app.domain.HrEmploymentInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmploymentInfo entity.
 */
public interface HrEmploymentInfoSearchRepository extends ElasticsearchRepository<HrEmploymentInfo, Long> {
}
