package gov.step.app.repository.search;

import gov.step.app.domain.InstLevel;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLevel entity.
 */
public interface InstLevelSearchRepository extends ElasticsearchRepository<InstLevel, Long> {
}
