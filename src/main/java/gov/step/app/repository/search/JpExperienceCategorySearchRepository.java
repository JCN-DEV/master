package gov.step.app.repository.search;

import gov.step.app.domain.JpExperienceCategory;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpExperienceCategory entity.
 */
public interface JpExperienceCategorySearchRepository extends ElasticsearchRepository<JpExperienceCategory, Long> {
}
