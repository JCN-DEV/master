package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAcrInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAcrInfo entity.
 */
public interface HrEmpAcrInfoSearchRepository extends ElasticsearchRepository<HrEmpAcrInfo, Long> {
}
