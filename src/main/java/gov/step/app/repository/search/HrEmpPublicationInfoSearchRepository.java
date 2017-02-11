package gov.step.app.repository.search;

import gov.step.app.domain.HrEmpPublicationInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the HrEmpPublicationInfo entity.
 */
public interface HrEmpPublicationInfoSearchRepository extends ElasticsearchRepository<HrEmpPublicationInfo, Long> {
}
