package gov.step.app.repository.search;

import gov.step.app.domain.Experience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Experience entity.
 */
public interface ExperienceSearchRepository extends ElasticsearchRepository<Experience, Long> {
}
