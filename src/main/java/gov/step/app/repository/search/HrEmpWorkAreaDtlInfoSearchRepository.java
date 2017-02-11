package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpWorkAreaDtlInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpWorkAreaDtlInfo entity.
 */
public interface HrEmpWorkAreaDtlInfoSearchRepository extends ElasticsearchRepository<HrEmpWorkAreaDtlInfo, Long> {
}
