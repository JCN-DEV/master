package gov.step.app.repository.search;

import gov.step.app.domain.MiscTypeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MiscTypeSetup entity.
 */
public interface MiscTypeSetupSearchRepository extends ElasticsearchRepository<MiscTypeSetup, Long> {
}
