package gov.step.app.repository.search;

import gov.step.app.domain.AssetTransfer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetTransfer entity.
 */
public interface AssetTransferSearchRepository extends ElasticsearchRepository<AssetTransfer, Long> {
}
