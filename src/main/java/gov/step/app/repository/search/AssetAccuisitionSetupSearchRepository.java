package gov.step.app.repository.search;

import gov.step.app.domain.AssetAccuisitionSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetAccuisitionSetup entity.
 */
public interface AssetAccuisitionSetupSearchRepository extends ElasticsearchRepository<AssetAccuisitionSetup, Long> {
}
