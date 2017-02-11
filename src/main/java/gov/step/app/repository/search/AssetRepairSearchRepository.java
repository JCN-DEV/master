package gov.step.app.repository.search;

import gov.step.app.domain.AssetRepair;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetRepair entity.
 */
public interface AssetRepairSearchRepository extends ElasticsearchRepository<AssetRepair, Long> {
}
