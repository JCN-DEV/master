package gov.step.app.repository.search;

import gov.step.app.domain.InstEmplExperience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstEmplExperience entity.
 */
public interface InstEmplExperienceSearchRepository extends ElasticsearchRepository<InstEmplExperience, Long> {
}
