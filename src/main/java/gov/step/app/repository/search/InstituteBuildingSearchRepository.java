package gov.step.app.repository.search;

import gov.step.app.domain.InstituteBuilding;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteBuilding entity.
 */
public interface InstituteBuildingSearchRepository extends ElasticsearchRepository<InstituteBuilding, Long> {
}
