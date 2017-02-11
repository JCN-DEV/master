package gov.step.app.repository.search;

import gov.step.app.domain.IisCourseInfoTemp;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the IisCourseInfoTemp entity.
 */
public interface IisCourseInfoTempSearchRepository extends ElasticsearchRepository<IisCourseInfoTemp, Long> {
}
