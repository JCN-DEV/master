package gov.step.app.repository.search;

import gov.step.app.domain.TempInstGenInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the TempInstGenInfo entity.
 */
public interface TempInstGenInfoSearchRepository extends ElasticsearchRepository<TempInstGenInfo, Long> {
}
