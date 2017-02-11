package gov.step.app.repository.search;

import gov.step.app.domain.InstLevelTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLevelTemp entity.
 */
public interface InstLevelTempSearchRepository extends ElasticsearchRepository<InstLevelTemp, Long> {
}
