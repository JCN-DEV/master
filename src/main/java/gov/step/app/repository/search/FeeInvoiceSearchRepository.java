package gov.step.app.repository.search;

import gov.step.app.domain.FeeInvoice;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the FeeInvoice entity.
 */
public interface FeeInvoiceSearchRepository extends ElasticsearchRepository<FeeInvoice, Long> {
}
