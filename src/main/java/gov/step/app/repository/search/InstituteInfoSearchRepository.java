package gov.step.app.repository.search;

import gov.step.app.domain.InstituteInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteInfo entity.
 */
public interface InstituteInfoSearchRepository extends ElasticsearchRepository<InstituteInfo, Long> {
}
