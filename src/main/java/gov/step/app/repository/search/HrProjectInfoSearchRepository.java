package gov.step.app.repository.search;

import gov.step.app.domain.HrProjectInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrProjectInfo entity.
 */
public interface HrProjectInfoSearchRepository extends ElasticsearchRepository<HrProjectInfo, Long> {
}
