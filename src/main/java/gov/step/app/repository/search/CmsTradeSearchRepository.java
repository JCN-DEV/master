package gov.step.app.repository.search;

import gov.step.app.domain.CmsTrade;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsTrade entity.
 */
public interface CmsTradeSearchRepository extends ElasticsearchRepository<CmsTrade, Long> {
}
