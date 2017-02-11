package gov.step.app.repository.search;

import gov.step.app.domain.InstLabInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLabInfo entity.
 */
public interface InstLabInfoSearchRepository extends ElasticsearchRepository<InstLabInfo, Long> {
}
