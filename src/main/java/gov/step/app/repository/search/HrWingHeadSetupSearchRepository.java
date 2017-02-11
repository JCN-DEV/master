package gov.step.app.repository.search;

import gov.step.app.domain.HrWingHeadSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrWingHeadSetup entity.
 */
public interface HrWingHeadSetupSearchRepository extends ElasticsearchRepository<HrWingHeadSetup, Long> {
}
