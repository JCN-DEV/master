package gov.step.app.repository.search;

import gov.step.app.domain.UmracRightsSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the UmracRightsSetup entity.
 */
public interface UmracRightsSetupSearchRepository extends ElasticsearchRepository<UmracRightsSetup, Long> {
}
