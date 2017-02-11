package gov.step.app.repository.search;

import gov.step.app.domain.InstitutePlayGroundInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstitutePlayGroundInfo entity.
 */
public interface InstitutePlayGroundInfoSearchRepository extends ElasticsearchRepository<InstitutePlayGroundInfo, Long> {
}
