package gov.step.app.repository.search;

import gov.step.app.domain.AssetTypeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetTypeSetup entity.
 */
public interface AssetTypeSetupSearchRepository extends ElasticsearchRepository<AssetTypeSetup, Long> {
}
