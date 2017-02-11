package gov.step.app.repository.search;

import gov.step.app.domain.CourseTech;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Spring Data ElasticSearch repository for the CourseTech entity.
 */
public interface CourseTechSearchRepository extends ElasticsearchRepository<CourseTech, Long> {
}
