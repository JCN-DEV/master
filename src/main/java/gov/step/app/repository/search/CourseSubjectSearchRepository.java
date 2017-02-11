package gov.step.app.repository.search;

import gov.step.app.domain.CourseSubject;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CourseSubject entity.
 */
public interface CourseSubjectSearchRepository extends ElasticsearchRepository<CourseSubject, Long> {
}
