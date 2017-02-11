package gov.step.app.repository.search;

import gov.step.app.domain.BankAssign;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BankAssign entity.
 */
public interface BankAssignSearchRepository extends ElasticsearchRepository<BankAssign, Long> {
}
