package gov.step.app.repository.search;

import gov.step.app.domain.FeePaymentCollection;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FeePaymentCollection entity.
 */
public interface FeePaymentCollectionSearchRepository extends ElasticsearchRepository<FeePaymentCollection, Long> {
}
