package gov.step.app.repository.search;

import gov.step.app.domain.JpSkill;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpSkill entity.
 */
public interface JpSkillSearchRepository extends ElasticsearchRepository<JpSkill, Long> {
}
