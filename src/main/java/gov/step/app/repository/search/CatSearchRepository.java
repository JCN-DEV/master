package gov.step.app.repository.search;

import gov.step.app.domain.Cat;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the Cat entity.
 */
public interface CatSearchRepository extends ElasticsearchRepository<Cat, Long> {
}
