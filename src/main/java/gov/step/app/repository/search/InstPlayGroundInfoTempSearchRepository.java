package gov.step.app.repository.search;

import gov.step.app.domain.InstPlayGroundInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstPlayGroundInfoTemp entity.
 */
public interface InstPlayGroundInfoTempSearchRepository extends ElasticsearchRepository<InstPlayGroundInfoTemp, Long> {
}
