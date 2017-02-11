package gov.step.app.repository.search;

import gov.step.app.domain.PfmsDeductionFinalize;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsDeductionFinalize entity.
 */
public interface PfmsDeductionFinalizeSearchRepository extends ElasticsearchRepository<PfmsDeductionFinalize, Long> {
}
