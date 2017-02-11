package gov.step.app.repository.search;

import gov.step.app.domain.InstGovernBodyTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstGovernBodyTemp entity.
 */
public interface InstGovernBodyTempSearchRepository extends ElasticsearchRepository<InstGovernBodyTemp, Long> {
}
