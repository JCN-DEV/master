package gov.step.app.repository.search;

import gov.step.app.domain.FeePaymentCategorySetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FeePaymentCategorySetup entity.
 */
public interface FeePaymentCategorySetupSearchRepository extends ElasticsearchRepository<FeePaymentCategorySetup, Long> {
}
