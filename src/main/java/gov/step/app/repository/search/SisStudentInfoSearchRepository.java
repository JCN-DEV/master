package gov.step.app.repository.search;

import gov.step.app.domain.SisStudentInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the SisStudentInfo entity.
 */
public interface SisStudentInfoSearchRepository extends ElasticsearchRepository<SisStudentInfo, Long> {
}
