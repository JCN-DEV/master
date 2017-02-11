package gov.step.app.repository.search;

import gov.step.app.domain.AssetRequisition;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetRequisition entity.
 */
public interface AssetRequisitionSearchRepository extends ElasticsearchRepository<AssetRequisition, Long> {
}
