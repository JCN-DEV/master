package gov.step.app.repository.search;

import gov.step.app.domain.InstShopInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstShopInfo entity.
 */
public interface InstShopInfoSearchRepository extends ElasticsearchRepository<InstShopInfo, Long> {
}
