package gov.step.app.repository.search;

import gov.step.app.domain.AllowanceSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the AllowanceSetup entity.
 */
public interface AllowanceSetupSearchRepository extends ElasticsearchRepository<AllowanceSetup, Long> {
}
