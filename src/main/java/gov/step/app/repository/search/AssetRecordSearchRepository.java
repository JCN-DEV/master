package gov.step.app.repository.search;

import gov.step.app.domain.AssetRecord;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetRecord entity.
 */
public interface AssetRecordSearchRepository extends ElasticsearchRepository<AssetRecord, Long> {
}
