package gov.step.app.repository.search;

import gov.step.app.domain.AssetCategorySetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetCategorySetup entity.
 */
public interface AssetCategorySetupSearchRepository extends ElasticsearchRepository<AssetCategorySetup, Long> {
}
