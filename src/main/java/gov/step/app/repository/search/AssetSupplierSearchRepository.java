package gov.step.app.repository.search;

import gov.step.app.domain.AssetSupplier;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetSupplier entity.
 */
public interface AssetSupplierSearchRepository extends ElasticsearchRepository<AssetSupplier, Long> {
}
