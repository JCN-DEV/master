package gov.step.app.repository.search;

import gov.step.app.domain.AssetAuctionInformation;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AssetAuctionInformation entity.
 */
public interface AssetAuctionInformationSearchRepository extends ElasticsearchRepository<AssetAuctionInformation, Long> {
}
