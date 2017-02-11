package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlLocalityInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlLocalityInfo entity.
 */
public interface PrlLocalityInfoSearchRepository extends ElasticsearchRepository<PrlLocalityInfo, Long> {
}
