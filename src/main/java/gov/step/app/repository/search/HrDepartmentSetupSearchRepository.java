package gov.step.app.repository.search;

import gov.step.app.domain.HrDepartmentSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrDepartmentSetup entity.
 */
public interface HrDepartmentSetupSearchRepository extends ElasticsearchRepository<HrDepartmentSetup, Long> {
}
