package gov.step.app.repository.search;

import gov.step.app.domain.JpEmployeeExperience;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpEmployeeExperience entity.
 */
public interface JpEmployeeExperienceSearchRepository extends ElasticsearchRepository<JpEmployeeExperience, Long> {
}
