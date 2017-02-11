package gov.step.app.repository.search;

import gov.step.app.domain.PfmsEmpAdjustment;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PfmsEmpAdjustment entity.
 */
public interface PfmsEmpAdjustmentSearchRepository extends ElasticsearchRepository<PfmsEmpAdjustment, Long> {
}
