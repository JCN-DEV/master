package gov.step.app.repository.search;

import gov.step.app.domain.JpSkillLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpSkillLevel entity.
 */
public interface JpSkillLevelSearchRepository extends ElasticsearchRepository<JpSkillLevel, Long> {
}
