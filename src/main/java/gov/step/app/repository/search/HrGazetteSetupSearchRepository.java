package gov.step.app.repository.search;

import gov.step.app.domain.HrGazetteSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrGazetteSetup entity.
 */
public interface HrGazetteSetupSearchRepository extends ElasticsearchRepository<HrGazetteSetup, Long> {
}
