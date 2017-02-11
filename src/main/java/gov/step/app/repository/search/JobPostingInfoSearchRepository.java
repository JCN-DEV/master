package gov.step.app.repository.search;

import gov.step.app.domain.JobPostingInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JobPostingInfo entity.
 */
public interface JobPostingInfoSearchRepository extends ElasticsearchRepository<JobPostingInfo, Long> {
}
