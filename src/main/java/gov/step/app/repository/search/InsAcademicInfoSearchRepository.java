package gov.step.app.repository.search;

import gov.step.app.domain.InsAcademicInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the InsAcademicInfo entity.
 */
public interface InsAcademicInfoSearchRepository extends ElasticsearchRepository<InsAcademicInfo, Long> {
}
