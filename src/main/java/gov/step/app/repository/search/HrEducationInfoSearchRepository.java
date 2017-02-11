package gov.step.app.repository.search;

import gov.step.app.domain.HrEducationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEducationInfo entity.
 */
public interface HrEducationInfoSearchRepository extends ElasticsearchRepository<HrEducationInfo, Long> {
}
