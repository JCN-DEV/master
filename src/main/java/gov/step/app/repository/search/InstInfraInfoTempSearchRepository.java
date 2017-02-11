package gov.step.app.repository.search;

import gov.step.app.domain.InstInfraInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstInfraInfoTemp entity.
 */
public interface InstInfraInfoTempSearchRepository extends ElasticsearchRepository<InstInfraInfoTemp, Long> {
}
