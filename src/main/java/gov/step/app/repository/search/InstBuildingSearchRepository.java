package gov.step.app.repository.search;

import gov.step.app.domain.InstBuilding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstBuilding entity.
 */
public interface InstBuildingSearchRepository extends ElasticsearchRepository<InstBuilding, Long> {
}
