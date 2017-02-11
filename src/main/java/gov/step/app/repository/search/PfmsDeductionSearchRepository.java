package gov.step.app.repository.search;

import gov.step.app.domain.PfmsDeduction;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsDeduction entity.
 */
public interface PfmsDeductionSearchRepository extends ElasticsearchRepository<PfmsDeduction, Long> {
}
