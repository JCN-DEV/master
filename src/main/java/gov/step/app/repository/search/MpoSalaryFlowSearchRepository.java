package gov.step.app.repository.search;

import gov.step.app.domain.MpoSalaryFlow;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the MpoSalaryFlow entity.
 */
public interface MpoSalaryFlowSearchRepository extends ElasticsearchRepository<MpoSalaryFlow, Long> {
}
