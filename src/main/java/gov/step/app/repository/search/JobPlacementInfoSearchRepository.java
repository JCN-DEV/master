package gov.step.app.repository.search;

import gov.step.app.domain.JobPlacementInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JobPlacementInfo entity.
 */
public interface JobPlacementInfoSearchRepository extends ElasticsearchRepository<JobPlacementInfo, Long> {
}
