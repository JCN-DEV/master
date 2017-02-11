package gov.step.app.repository.search;

import gov.step.app.domain.AssetDestroy;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetDestroy entity.
 */
public interface AssetDestroySearchRepository extends ElasticsearchRepository<AssetDestroy, Long> {
}
