package gov.step.app.repository.search;

import gov.step.app.domain.AssetDistribution;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetDistribution entity.
 */
public interface AssetDistributionSearchRepository extends ElasticsearchRepository<AssetDistribution, Long> {
}
