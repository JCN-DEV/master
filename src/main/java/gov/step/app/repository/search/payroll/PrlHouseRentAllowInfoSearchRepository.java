package gov.step.app.repository.search.payroll;

import gov.step.app.domain.payroll.PrlHouseRentAllowInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the PrlHouseRentAllowInfo entity.
 */
public interface PrlHouseRentAllowInfoSearchRepository extends ElasticsearchRepository<PrlHouseRentAllowInfo, Long> {
}
