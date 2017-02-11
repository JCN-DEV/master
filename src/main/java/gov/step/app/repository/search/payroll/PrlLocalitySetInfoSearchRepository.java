package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlLocalitySetInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlLocalitySetInfo entity.
 */
public interface PrlLocalitySetInfoSearchRepository extends ElasticsearchRepository<PrlLocalitySetInfo, Long> {
}
