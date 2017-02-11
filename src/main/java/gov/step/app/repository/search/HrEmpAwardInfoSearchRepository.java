package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpAwardInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpAwardInfo entity.
 */
public interface HrEmpAwardInfoSearchRepository extends ElasticsearchRepository<HrEmpAwardInfo, Long> {
}
