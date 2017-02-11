package gov.step.app.repository.search;

import gov.step.app.domain.InstGovBodyAccessTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstGovBodyAccessTemp entity.
 */
public interface InstGovBodyAccessTempSearchRepository extends ElasticsearchRepository<InstGovBodyAccessTemp, Long> {
}
