package gov.step.app.repository.search;

import gov.step.app.domain.InstShopInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstShopInfoTemp entity.
 */
public interface InstShopInfoTempSearchRepository extends ElasticsearchRepository<InstShopInfoTemp, Long> {
}
