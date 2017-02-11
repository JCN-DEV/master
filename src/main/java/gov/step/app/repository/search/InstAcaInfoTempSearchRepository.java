package gov.step.app.repository.search;

import gov.step.app.domain.InstAcaInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstAcaInfoTemp entity.
 */
public interface InstAcaInfoTempSearchRepository extends ElasticsearchRepository<InstAcaInfoTemp, Long> {
}
