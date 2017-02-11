package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpIncrementInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpIncrementInfo entity.
 */
public interface HrEmpIncrementInfoSearchRepository extends ElasticsearchRepository<HrEmpIncrementInfo, Long> {
}
