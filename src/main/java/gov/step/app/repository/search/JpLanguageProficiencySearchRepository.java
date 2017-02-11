package gov.step.app.repository.search;

import gov.step.app.domain.JpLanguageProficiency;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the JpLanguageProficiency entity.
 */
public interface JpLanguageProficiencySearchRepository extends ElasticsearchRepository<JpLanguageProficiency, Long> {
}
