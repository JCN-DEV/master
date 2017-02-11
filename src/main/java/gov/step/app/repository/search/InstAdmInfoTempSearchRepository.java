package gov.step.app.repository.search;

import gov.step.app.domain.InstAdmInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstAdmInfoTemp entity.
 */
public interface InstAdmInfoTempSearchRepository extends ElasticsearchRepository<InstAdmInfoTemp, Long> {
}
