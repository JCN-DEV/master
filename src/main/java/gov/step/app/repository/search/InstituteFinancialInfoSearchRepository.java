package gov.step.app.repository.search;

import gov.step.app.domain.InstituteFinancialInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InstituteFinancialInfo entity.
 */
public interface InstituteFinancialInfoSearchRepository extends ElasticsearchRepository<InstituteFinancialInfo, Long> {
}
