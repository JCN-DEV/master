package gov.step.app.repository.search;

import gov.step.app.domain.BankSetup;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BankSetup entity.
 */
public interface BankSetupSearchRepository extends ElasticsearchRepository<BankSetup, Long> {
}
