package gov.step.app.repository.search;

import gov.step.app.domain.InstMemShipTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstMemShipTemp entity.
 */
public interface InstMemShipTempSearchRepository extends ElasticsearchRepository<InstMemShipTemp, Long> {
}
