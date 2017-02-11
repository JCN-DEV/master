package gov.step.app.repository.search;

import gov.step.app.domain.HrDesignationSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrDesignationSetup entity.
 */
public interface HrDesignationSetupSearchRepository extends ElasticsearchRepository<HrDesignationSetup, Long> {
}
