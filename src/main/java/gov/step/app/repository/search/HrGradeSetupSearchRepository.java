package gov.step.app.repository.search;

import gov.step.app.domain.HrGradeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrGradeSetup entity.
 */
public interface HrGradeSetupSearchRepository extends ElasticsearchRepository<HrGradeSetup, Long> {
}
