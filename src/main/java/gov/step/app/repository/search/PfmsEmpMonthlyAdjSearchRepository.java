package gov.step.app.repository.search;

import gov.step.app.domain.PfmsEmpMonthlyAdj;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsEmpMonthlyAdj entity.
 */
public interface PfmsEmpMonthlyAdjSearchRepository extends ElasticsearchRepository<PfmsEmpMonthlyAdj, Long> {
}
