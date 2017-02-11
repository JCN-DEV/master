package gov.step.app.repository.search;

import gov.step.app.domain.MiscConfigSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MiscConfigSetup entity.
 */
public interface MiscConfigSetupSearchRepository extends ElasticsearchRepository<MiscConfigSetup, Long> {
}
