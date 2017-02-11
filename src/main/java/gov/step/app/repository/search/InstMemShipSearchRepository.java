package gov.step.app.repository.search;

import gov.step.app.domain.InstMemShip;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstMemShip entity.
 */
public interface InstMemShipSearchRepository extends ElasticsearchRepository<InstMemShip, Long> {
}
