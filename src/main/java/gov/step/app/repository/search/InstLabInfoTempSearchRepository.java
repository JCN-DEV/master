package gov.step.app.repository.search;

import gov.step.app.domain.InstLabInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLabInfoTemp entity.
 */
public interface InstLabInfoTempSearchRepository extends ElasticsearchRepository<InstLabInfoTemp, Long> {
}
