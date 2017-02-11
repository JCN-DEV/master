package gov.step.app.repository.search;

import gov.step.app.domain.PfmsLoanApplication;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsLoanApplication entity.
 */
public interface PfmsLoanApplicationSearchRepository extends ElasticsearchRepository<PfmsLoanApplication, Long> {
}
