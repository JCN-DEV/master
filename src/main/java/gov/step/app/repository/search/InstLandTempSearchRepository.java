package gov.step.app.repository.search;

import gov.step.app.domain.InstLandTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstLandTemp entity.
 */
public interface InstLandTempSearchRepository extends ElasticsearchRepository<InstLandTemp, Long> {
}
