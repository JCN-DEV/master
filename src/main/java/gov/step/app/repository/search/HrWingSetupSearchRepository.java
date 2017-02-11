package gov.step.app.repository.search;

import gov.step.app.domain.HrWingSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrWingSetup entity.
 */
public interface HrWingSetupSearchRepository extends ElasticsearchRepository<HrWingSetup, Long> {
}
