package gov.step.app.repository.search;

import gov.step.app.domain.GazetteSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GazetteSetup entity.
 */
public interface GazetteSetupSearchRepository extends ElasticsearchRepository<GazetteSetup, Long> {
}
