package gov.step.app.repository.search;

import gov.step.app.domain.MpoTrade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoTrade entity.
 */
public interface MpoTradeSearchRepository extends ElasticsearchRepository<MpoTrade, Long> {
}
