package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAdvIncrementInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAdvIncrementInfo entity.
 */
public interface HrEmpAdvIncrementInfoSearchRepository extends ElasticsearchRepository<HrEmpAdvIncrementInfo, Long> {
}
