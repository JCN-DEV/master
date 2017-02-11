package gov.step.app.repository.search;

import gov.step.app.domain.InstInfraInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstInfraInfo entity.
 */
public interface InstInfraInfoSearchRepository extends ElasticsearchRepository<InstInfraInfo, Long> {
}
