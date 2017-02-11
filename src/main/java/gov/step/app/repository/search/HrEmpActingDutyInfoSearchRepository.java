package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpActingDutyInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpActingDutyInfo entity.
 */
public interface HrEmpActingDutyInfoSearchRepository extends ElasticsearchRepository<HrEmpActingDutyInfo, Long> {
}
