package gov.step.app.repository.search;

import gov.step.app.domain.InstPlayGroundInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstPlayGroundInfo entity.
 */
public interface InstPlayGroundInfoSearchRepository extends ElasticsearchRepository<InstPlayGroundInfo, Long> {
}
