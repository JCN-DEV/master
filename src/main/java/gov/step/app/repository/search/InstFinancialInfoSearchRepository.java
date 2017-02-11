package gov.step.app.repository.search;

import gov.step.app.domain.InstFinancialInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstFinancialInfo entity.
 */
public interface InstFinancialInfoSearchRepository extends ElasticsearchRepository<InstFinancialInfo, Long> {
}