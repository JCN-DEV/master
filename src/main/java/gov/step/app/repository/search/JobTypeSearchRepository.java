package gov.step.app.repository.search;

import gov.step.app.domain.JobType;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JobType entity.
 */
public interface JobTypeSearchRepository extends ElasticsearchRepository<JobType, Long> {
}