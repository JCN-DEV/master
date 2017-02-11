package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpPreGovtJobInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpPreGovtJobInfo entity.
 */
public interface HrEmpPreGovtJobInfoSearchRepository extends ElasticsearchRepository<HrEmpPreGovtJobInfo, Long> {
}
