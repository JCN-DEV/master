package gov.step.app.repository.search;

import gov.step.app.domain.InstituteShopInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteShopInfo entity.
 */
public interface InstituteShopInfoSearchRepository extends ElasticsearchRepository<InstituteShopInfo, Long> {
}
