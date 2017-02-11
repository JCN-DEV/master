package gov.step.app.repository.search;

import gov.step.app.domain.IisCourseInfo;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the IisCourseInfo entity.
 */
public interface IisCourseInfoSearchRepository extends ElasticsearchRepository<IisCourseInfo, Long> {
}
