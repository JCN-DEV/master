package gov.step.app.repository.search;

import gov.step.app.domain.HrPayScaleSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrPayScaleSetup entity.
 */
public interface HrPayScaleSetupSearchRepository extends ElasticsearchRepository<HrPayScaleSetup, Long> {
}
