package gov.step.app.repository.search;

import gov.step.app.domain.CourseSub;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CourseSub entity.
 */
public interface CourseSubSearchRepository extends ElasticsearchRepository<CourseSub, Long> {
}
