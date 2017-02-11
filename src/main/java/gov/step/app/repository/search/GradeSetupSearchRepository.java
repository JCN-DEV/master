package gov.step.app.repository.search;

import gov.step.app.domain.GradeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the GradeSetup entity.
 */
public interface GradeSetupSearchRepository extends ElasticsearchRepository<GradeSetup, Long> {
}
