package gov.step.app.repository.search;

import gov.step.app.domain.HrDesignationHeadSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrDesignationHeadSetup entity.
 */
public interface HrDesignationHeadSetupSearchRepository extends ElasticsearchRepository<HrDesignationHeadSetup, Long> {
}
