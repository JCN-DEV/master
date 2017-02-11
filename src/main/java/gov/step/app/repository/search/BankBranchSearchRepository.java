package gov.step.app.repository.search;

import gov.step.app.domain.BankBranch;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the BankBranch entity.
 */
public interface BankBranchSearchRepository extends ElasticsearchRepository<BankBranch, Long> {
}
