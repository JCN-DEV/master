package gov.step.app.repository.search;

import gov.step.app.domain.CmsCurriculum;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CmsCurriculum entity.
 */
public interface CmsCurriculumSearchRepository extends ElasticsearchRepository<CmsCurriculum, Long> {
}



