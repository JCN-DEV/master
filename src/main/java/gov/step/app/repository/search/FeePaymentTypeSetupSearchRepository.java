package gov.step.app.repository.search;

import gov.step.app.domain.FeePaymentTypeSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FeePaymentTypeSetup entity.
 */
public interface FeePaymentTypeSetupSearchRepository extends ElasticsearchRepository<FeePaymentTypeSetup, Long> {
}
