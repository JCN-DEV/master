package gov.step.app.repository.search;

import gov.step.app.domain.InstBuildingTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstBuildingTemp entity.
 */
public interface InstBuildingTempSearchRepository extends ElasticsearchRepository<InstBuildingTemp, Long> {
}
