package gov.step.app.repository.search;

import gov.step.app.domain.Jobapplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Jobapplication entity.
 */
public interface JobapplicationSearchRepository extends ElasticsearchRepository<Jobapplication, Long> {
}
