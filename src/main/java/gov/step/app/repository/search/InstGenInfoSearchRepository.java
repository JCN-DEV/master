package gov.step.app.repository.search;

import gov.step.app.domain.InstGenInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstGenInfo entity.
 */
public interface InstGenInfoSearchRepository extends ElasticsearchRepository<InstGenInfo, Long> {
}
