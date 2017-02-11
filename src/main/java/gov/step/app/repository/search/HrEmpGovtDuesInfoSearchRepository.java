package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpGovtDuesInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpGovtDuesInfo entity.
 */
public interface HrEmpGovtDuesInfoSearchRepository extends ElasticsearchRepository<HrEmpGovtDuesInfo, Long> {
}
