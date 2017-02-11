package gov.step.app.repository.search;

import gov.step.app.domain.InstLand;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLand entity.
 */
public interface InstLandSearchRepository extends ElasticsearchRepository<InstLand, Long> {
}
