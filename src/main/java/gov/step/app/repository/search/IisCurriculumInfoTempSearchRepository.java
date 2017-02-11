package gov.step.app.repository.search;

import gov.step.app.domain.IisCurriculumInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the IisCurriculumInfoTemp entity.
 */
public interface IisCurriculumInfoTempSearchRepository extends ElasticsearchRepository<IisCurriculumInfoTemp, Long> {
}
