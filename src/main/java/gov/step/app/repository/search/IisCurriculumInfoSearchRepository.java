package gov.step.app.repository.search;

import gov.step.app.domain.IisCurriculumInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the IisCurriculumInfo entity.
 */
public interface IisCurriculumInfoSearchRepository extends ElasticsearchRepository<IisCurriculumInfo, Long> {
}
